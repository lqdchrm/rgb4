package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;
import de.fhtrier.gdig.engine.network.IAddServerListener;
import de.fhtrier.gdig.engine.network.INetworkLobby;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.NetworkServerObject;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.CreateButtonControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientSelectServerState extends NiftyGameState implements
		ScreenController, IAddServerListener {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "client_server_select.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;

	private List<InterfaceAddress> interfaces;
	private List<NetworkServerObject> serverList;
	private Semaphore serverMutex;

	private INetworkLobby networkLobby;
	private boolean connecting = false;

	// private boolean startConnect = false;
	private String currentConnectionIp = null;
	private int currentConnectionPort = -1;

	// gui-elements
	private Element guiInterfacePanel;
	private Element guiServerPanel;
	private TextFieldControl guiPlayernameTextField;

	public ClientSelectServerState() {
		super(GameStates.SERVER_SELECTION);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		networkLobby = new NetworkLobby(this);
		interfaces = NetworkHelper.getInterfaces();
		drawInterfaces(interfaces);
		chooseInterface(Integer.toString(0));
	}

	public void drawInterfaces(List<InterfaceAddress> interfaces) {
		// interfaces.add("server "+new Date().getTime());
		clearList(guiInterfacePanel);
		for (int i = 0; i < interfaces.size(); i++) {
			InterfaceAddress iA = interfaces.get(i);
			CreateButtonControl createButton = new CreateButtonControl("button");
			createButton.setHeight("20px");
			createButton.setWidth("100%");
			createButton.set("label", iA.getAddress().getCanonicalHostName());
			createButton.setAlign("left");
			// TODO setin real values
			createButton.setInteractOnClick("chooseInterface(" + i + ")");
			createButton.create(nifty, nifty.getCurrentScreen(),
					guiInterfacePanel);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);

		serverList = new ArrayList<NetworkServerObject>();
		serverMutex = new Semaphore(1);

		// add asset-folder to the ResourceLocators of nifty and slick2d
		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(
				menuAssetPath)));
		org.newdawn.slick.util.ResourceLoader
				.addResourceLocation(new org.newdawn.slick.util.FileSystemLocation(
						new File(menuAssetPath)));
		// read the nifty-xml-fiel
		fromXml(menuNiftyXMLFile,
				ResourceLoader.getResourceAsStream(menuNiftyXMLFile), this);
		// show the mouse
		enableMouseImage(new Image(
				ResourceLoader.getResourceAsStream(CROSSHAIR_PNG),
				CROSSHAIR_PNG, false));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		try {
			super.render(container, game, g);
		} catch (Exception e) {
		}
		// g.drawString("PRESS ENTER", 10, 10);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		super.update(container, game, d);

		if (connecting) {
			NetworkComponent.getInstance().update();
		}

		if (NetworkComponent.getInstance().getNetworkId() != -1) {
			NetworkComponent.getInstance().sendCommand(
					new QueryConnect(guiPlayernameTextField.getText()));
			game.enterState(GameStates.CLIENT_LOBBY);
			connecting = false;
		}

		try {
			serverMutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NetworkServerObject server : serverList) {
			CreateButtonControl createButton = new CreateButtonControl(
					"mybutton");
			createButton.setHeight("30px");
			createButton.setWidth("100%");
			createButton.set("label", server.getName() + "(" + server.getIp()
					+ ")");
			createButton.setAlign("left");
			// TODO setin real values
			createButton.setInteractOnClick("chooseServer(" + server.getIp()
					+ "," + server.getPort() + ")");
			createButton
					.create(nifty, nifty.getCurrentScreen(), guiServerPanel);
		}

		serverList.clear();

		serverMutex.release();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
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
		// TODO Auto-generated method stub

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
		InterfaceAddress iA = interfaces.get(Integer.parseInt(id));
		networkLobby.stopGetServers();
		networkLobby.getServers(iA);
	}

	public void chooseServer(String ip, String port) {
		this.currentConnectionIp = ip;
		this.currentConnectionPort = Integer.parseInt(port);
		Log.debug("set server-info to :" + ip + ":" + port);
		networkLobby.stopGetServers();
	}

	public void connect() {

		if (currentConnectionIp != null && !connecting) {
			NetworkComponent.getInstance().connect(
					currentConnectionIp.replace('/', ' ').trim(),
					currentConnectionPort);
			connecting = true;
			currentConnectionIp = null;
			currentConnectionPort = -1;

		}
	}

}
