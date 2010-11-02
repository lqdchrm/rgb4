package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;
import de.fhtrier.gdig.engine.network.IAddServerListener;
import de.fhtrier.gdig.engine.network.INetworkLobby;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.NetworkServerObject;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.CreateButtonControl;
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientSelectServerState extends NiftyGameState implements
		ScreenController, IAddServerListener {

	public static String menuNiftyXMLFile = "client_server_select.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;

	private List<InterfaceAddress> interfaces;
	private List<NetworkServerObject> serverList;
	private Semaphore serverMutex;

	private INetworkLobby networkLobby;
	private boolean connecting = false;
	private int counter = 0;
	private String currentConnectionIp = null;
	private int currentConnectionPort = -1;
	private int currentServerId = -1;

	private StateBasedGame game;

	// gui-elements
	private Element guiInterfacePanel;
	private Element guiServerPanel;
	private TextFieldControl guiPlayernameTextField;

	private ArrayList<ButtonControl> interfaceButtons = new ArrayList<ButtonControl>();
	private ArrayList<ButtonControl> serverButtons = new ArrayList<ButtonControl>();
	private boolean waitingForTransition;

	public ClientSelectServerState() {
		super(GameStates.SERVER_SELECTION);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		networkLobby = new NetworkLobby(this);

	}

	public void drawInterfaces(List<InterfaceAddress> interfaces) {
		// interfaces.add("server "+new Date().getTime());
		clearList(guiInterfacePanel);
		interfaceButtons.clear();
		for (int i = 0; i < interfaces.size(); i++) {
			InterfaceAddress iA = interfaces.get(i);
			CreateButtonControl createButton = new CreateButtonControl("button"
					+ counter++);
			createButton.setHeight("20px");
			createButton.setWidth("100%");
			createButton.set("label", iA.getAddress().getCanonicalHostName());
			createButton.setAlign("left");
			// TODO setin real values
			createButton.setInteractOnClick("chooseInterface(" + i + ")");
			ButtonControl bC = createButton.create(nifty,
					nifty.getCurrentScreen(), guiInterfacePanel);
			interfaceButtons.add(bC);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);

		serverList = new ArrayList<NetworkServerObject>();
		serverMutex = new Semaphore(1);

		this.game = game;

		// add asset-folder to the ResourceLocators of nifty and slick2d
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(
				menuAssetPath)));

		org.newdawn.slick.util.ResourceLoader
				.addResourceLocation(new org.newdawn.slick.util.FileSystemLocation(
						new File(menuAssetPath)));

		// read the nifty-xml-fiel
		fromXml(menuNiftyXMLFile,
				ResourceLoader.getResourceAsStream(menuNiftyXMLFile), this);

		try {
			enableMouseImage(new Image(
					ResourceLoader.getResourceAsStream(Assets.Config.AssetGuiPath
							+ "/crosshair.png"), "Cursor", false));
		} catch (SlickException e) {
			Log.error("Image loading failed in ServerSettingsState");
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		try {
			MenuBackgroundRenderer.getInstance().render(container, game, g);
			super.render(container, game, g);
		} catch (Exception e) {
			Log.error(e);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		super.update(container, game, d);

		if (connecting) {
			NetworkComponent.getInstance().update();
		}

		if (NetworkComponent.getInstance().getNetworkId() != -1
				&& !waitingForTransition) {
			gotoLobby();
		}

		try {
			serverMutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int count = 0;
		for (NetworkServerObject server : serverList) {
			CreateButtonControl createButton = new CreateButtonControl(
					"mybutton" + count);
			createButton.setHeight("30px");
			createButton.setWidth("100%");
			createButton.set("label", server.getName() + "(" + server.getIp()
					+ ")");
			createButton.setAlign("left");
			// TODO setin real values
			createButton.setInteractOnClick("chooseServer(" + count + ","
					+ server.getIp() + "," + server.getPort() + ")");
			ButtonControl bC = createButton.create(nifty,
					nifty.getCurrentScreen(), guiServerPanel);
			if (count == 0 && currentConnectionIp == null) {
				chooseServer("0", server.getIp().toString(),
						Integer.toString(server.getPort()));
			}
			count++;
			serverButtons.add(bC);
		}

		setButton(currentServerId, serverButtons);

		serverList.clear();

		serverMutex.release();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		networkLobby.stopGetServers();
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		guiInterfacePanel = screen.findElementByName("interfaces");
		guiServerPanel = screen.findElementByName("servers");
		guiPlayernameTextField = screen.findControl("playername",
				TextFieldControl.class);
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		interfaces = NetworkHelper.getInterfaces();
		drawInterfaces(interfaces);
		chooseInterface(Integer.toString(0));
	}

	private void clearList(Element e) {
		for (Element child : e.getElements()) {
			child.markForRemoval();
		}
	}

	@Override
	public void addServer(NetworkServerObject server) {

		try {
			serverMutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		serverList.add(server);
		serverMutex.release();
	}

	public void chooseInterface(String id) {
		clearList(guiServerPanel);
		serverButtons.clear();
		InterfaceAddress iA = interfaces.get(Integer.parseInt(id));
		setButton(Integer.parseInt(id), interfaceButtons);
		networkLobby.stopGetServers();
		networkLobby.getServers(iA);
		currentConnectionIp = null;
		currentConnectionPort = -1;
		currentServerId = -1;
	}

	public void setButton(int nr, List<ButtonControl> buttons) {
		for (int i = 0; i < buttons.size(); i++) {
			ButtonControl b = buttons.get(i);
			if (i == nr)
				b.setColor(Constants.GuiConfig.btnSelectedColor);
			else
				b.setColor(Constants.GuiConfig.btnNotSelectedColor);
		}
	}

	public void chooseServer(String id, String ip, String port) {
		this.currentConnectionIp = ip;
		this.currentConnectionPort = Integer.parseInt(port);
		this.currentServerId = Integer.parseInt(id);
		if (Constants.Debug.guiDebug) {
			Log.debug("set server-info to :" + ip + ":" + port);
		}
		networkLobby.stopGetServers();
		setButton(Integer.parseInt(id), serverButtons);

	}

	public void connect() {

		if (currentConnectionIp == null) {
			popupNoServer();
			return;
		} else if (guiPlayernameTextField.getText().trim().equals("")) {
			popupNoName();
		} else if (currentConnectionIp != null && !connecting) {
			NetworkComponent.getInstance().connect(
					currentConnectionIp.replace('/', ' ').trim(),
					currentConnectionPort);
			connecting = true;
			currentConnectionIp = null;
			currentConnectionPort = -1;

		} else
			JOptionPane.showMessageDialog(null, "Fehler! currentConnectionIP="
					+ currentConnectionIp + " connection=" + connecting);
	}

	public void back() {
		nifty.getCurrentScreen().endScreen(new EndNotify() {
			@Override
			public void perform() {
				game.enterState(GameStates.MENU);
			}
		});
	}

	public void popupNoServer() {
		Element element = nifty.createPopupWithId("popupServer");
		nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		return;
	}

	public void popupNoName() {
		Element element = nifty.createPopupWithId("popupName");
		nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		return;
	}

	public void removePopup() {
		nifty.closePopup(nifty.getCurrentScreen().getTopMostPopup().getId(),
				null);
	}

	public void gotoLobby() {
		if (waitingForTransition == false) {
			waitingForTransition = true;
			nifty.getCurrentScreen().endScreen(new EndNotify() {
				public void perform() {
					game.enterState(GameStates.CLIENT_LOBBY);
					NetworkComponent.getInstance().sendCommand(
							new QueryConnect(guiPlayernameTextField.getText()));
					connecting = false;
					waitingForTransition = false;
				}
			});
		}
	}

}
