package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.client.states.gui.MenuBackground;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkHelper;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.CreateButtonControl;
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientHostServerState extends NiftyGameState implements
		ScreenController {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "server_settings.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;

	private StateBasedGame game;
	private TextFieldControl portControl;
	private TextFieldControl serverNameControl;
	private TextFieldControl playerNameControl;
	private Element guiInterfacePanel;

	private boolean connecting = false;
	private boolean serverStarting = false;

	private List<InterfaceAddress> interfaces;
	private int selectedInterfaceIndex = -1;
	private List<ButtonControl> interfaceButtons = new ArrayList<ButtonControl>();

	public ClientHostServerState(final StateBasedGame game) {
		super(GameStates.SERVER_SETTINGS);
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
		// show the mouse
		try {
			enableMouseImage(new Image(
					ResourceLoader.getResourceAsStream(CROSSHAIR_PNG),
					CROSSHAIR_PNG, false));
		} catch (SlickException e) {
			Log.error("Image loading failed in ServerSettingsState");
			e.printStackTrace();
		}

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

		interfaces = NetworkHelper.getInterfaces();
		drawInterfaces();
	}

	@Override
	public void bind(Nifty arg0, Screen screen) {
		portControl = screen.findControl("portnumber", TextFieldControl.class);
		serverNameControl = screen.findControl("servername",
				TextFieldControl.class);
		playerNameControl = screen.findControl("playername",
				TextFieldControl.class);
		guiInterfacePanel = screen.findElementByName("interfaces");
	}

	@Override
	public void onEndScreen() {
		// left intentionally blank
	}

	public void setButton(int nr, List<ButtonControl> buttons, Color setColor,
			Color notSetColor) {
		for (int i = 0; i < buttons.size(); i++) {
			ButtonControl b = buttons.get(i);
			if (i == nr)
				b.setColor(setColor);
			else
				b.setColor(notSetColor);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		((ClientLobbyState) game.getState(GameStates.CLIENT_LOBBY))
				.setGameCreator(true);
	}

	@Override
	public void onStartScreen() {
		// left intentionally blank
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		// TODO Auto-generated method stub
		super.update(container, game, d);

		if (connecting) {
			NetworkComponent.getInstance().update();
		}
		if (NetworkComponent.getInstance().getNetworkId() != -1) {
			NetworkComponent.getInstance().sendCommand(
					new QueryConnect(playerNameControl.getText()));
			game.enterState(GameStates.CLIENT_LOBBY);
			connecting = false;
		}
	}

	public void createServer() {

		if (!serverStarting) {

			if (selectedInterfaceIndex == -1) {
				popupNoServer();
				return;
			} else if (playerNameControl.getText().trim().equals("")) {
				popupNoName();
			} else {
				serverStarting = true;
//				String interfaceA = interfaces.get(selectedInterfaceIndex)
//						.getAddress().getHostAddress().replace('/', ' ').trim();
//				// ProcessBuilder pb = new ProcessBuilder("bash",
//				// "server.bat",serverNameControl.getText(),interfaceA,portControl.getText());
//				ProcessBuilder pb = new ProcessBuilder("java",
//						"-Djava.library.path=lib/native", "-jar",
//						"server/server.jar", serverNameControl.getText(),
//						interfaceA, portControl.getText());
//				// pb.directory("myDir");
//				try {
//					Process p = pb.start();
//					System.setOut(new PrintStream(p.getOutputStream()));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

				// TODO spawn server

				// connect as client master
				connect();
			}
		} else {
			Log.debug("Allready tried creating a server");
		}
	}

	private void connect() {
		String currentConnectionIp = interfaces.get(selectedInterfaceIndex)
				.getAddress().getHostAddress();
		int currentConnectionPort = Integer.parseInt(portControl.getText());

		if (currentConnectionIp != null && !connecting) {
			NetworkComponent.getInstance().connect(
					currentConnectionIp.replace('/', ' ').trim(),
					currentConnectionPort);
			connecting = true;
		}
	}

	public void back() {
		game.enterState(GameStates.MENU, new FadeOutTransition(),
				new FadeInTransition());
	}

	public void drawInterfaces() {
		// interfaces.add("server "+new Date().getTime());
		clearList(guiInterfacePanel);
		interfaceButtons.clear();
		for (int i = 0; i < interfaces.size(); i++) {
			InterfaceAddress iA = interfaces.get(i);
			CreateButtonControl createButton = new CreateButtonControl("button"
					+ i);
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

	public void chooseInterface(String id) {
		// clearList(guiServerPanel);

		// set interface as active
		selectedInterfaceIndex = Integer.parseInt(id);
		setButton(Integer.parseInt(id), interfaceButtons,
				new Color(1, 0, 0, 1), new Color(1, 1, 1, 1));
	}

	private void clearList(Element e) {
		for (Element child : e.getElements()) {
			child.markForRemoval();
		}
	}

	public void popupNoServer() {
		Element element = nifty.createPopupWithId("popupServer");
		// TextRenderer tR = element.getRenderer(TextRenderer.class);
		// tR.setText(text);
		nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		return;
	}

	public void popupNoName() {
		Element element = nifty.createPopupWithId("popupName");
		// TextRenderer tR = element.getRenderer(TextRenderer.class);
		// tR.setText(text);
		nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		return;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		try {
			MenuBackground.getInstance().render(container, game, g);
			super.render(container, game, g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removePopup() {
		nifty.closePopup(nifty.getCurrentScreen().getTopMostPopup().getId(),
				null);
	}
}
