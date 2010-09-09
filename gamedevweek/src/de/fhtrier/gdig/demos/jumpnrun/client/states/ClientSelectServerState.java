package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

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
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientSelectServerState extends NiftyGameState implements
		ScreenController, IAddServerListener {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "client_server_select.xml";
	public static String menuAssetPath = Assets.AssetGuiPath;

	private List<InterfaceAddress> interfaces;

	private INetworkLobby networkLobby;
	private boolean connecting = false;
	private int counter=0;
//	private boolean startConnect = false;
	private String currentConnectionIp = null;
	private int currentConnectionPort = -1;
	
	private StateBasedGame game;
	
	// gui-elements
	private Element guiInterfacePanel;
	private Element guiServerPanel;
	private TextFieldControl guiPlayernameTextField;
	
	private ArrayList<ButtonControl> interfaceButtons = new ArrayList<ButtonControl>();
	private ArrayList<ButtonControl> serverButtons = new ArrayList<ButtonControl>();
	
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
			CreateButtonControl createButton = new CreateButtonControl("button"+counter++);
			createButton.setHeight("20px");
			createButton.setWidth("100%");
			createButton.set("label", iA.getAddress().getCanonicalHostName());
			createButton.setAlign("left");
			// TODO setin real values
			createButton.setInteractOnClick("chooseInterface(" + i + ")");
			ButtonControl bC = createButton.create(nifty, nifty.getCurrentScreen(),
					guiInterfacePanel);
			interfaceButtons.add(bC);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		
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
//		setButton(0, interfaceButtons, new Color(1,0,0,1), new Color(1,1,1,0));
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
		CreateButtonControl createButton = new CreateButtonControl("button"+counter++);
		createButton.setHeight("30px");
		createButton.setWidth("100%");
		createButton
				.set("label", server.getName() + "(" + server.getIp() + ")");
		createButton.setAlign("left");
		// TODO setin real values
		createButton.setInteractOnClick("chooseServer("+serverButtons.size()+"," + server.getIp() + ","
				+ server.getPort() + ")");
		serverButtons.add(createButton.create(nifty, nifty.getCurrentScreen(), guiServerPanel));
	}

	public void chooseInterface(String id) {
		clearList(guiServerPanel);
		serverButtons.clear();
		InterfaceAddress iA = interfaces.get(Integer.parseInt(id));
		setButton(Integer.parseInt(id), interfaceButtons, new Color(1,0,0,1), new Color(1,1,1,1));
		networkLobby.stopGetServers();
		networkLobby.getServers(iA);
	}

	public void setButton(int nr,List<ButtonControl> buttons,Color setColor,Color notSetColor)
	{
		for (int i=0;i < buttons.size(); i++)
		{
			ButtonControl b = buttons.get(i);
			if (i==nr)
				b.setColor(setColor);
			else
				b.setColor(notSetColor);
		}
	}
	
	public void chooseServer(String id,String ip, String port) {
		this.currentConnectionIp = ip;
		this.currentConnectionPort = Integer.parseInt(port);
		Log.debug("set server-info to :" + ip + ":" + port);
		networkLobby.stopGetServers();
		setButton(Integer.parseInt(id), serverButtons, new Color(1,0,0,1), new Color(1,1,1,1));
	}

	public void connect() {
		
		if (currentConnectionIp==null)
		{
			popupNoServer();
			return;
		}
		else if (guiPlayernameTextField.getText().trim().equals(""))
		{
			popupNoName();
		}
		else if (currentConnectionIp != null && !connecting) {
			NetworkComponent.getInstance().connect(
					currentConnectionIp.replace('/', ' ').trim(),
					currentConnectionPort);
			connecting = true;
			currentConnectionIp = null;
			currentConnectionPort = -1;

		}

			 
	}
	
	public void back() {
		game.enterState(GameStates.MENU);
	}

	  public void popupNoServer() {
		    Element element = nifty.createPopupWithId("popupServer");
//		    TextRenderer tR = element.getRenderer(TextRenderer.class);
//		    tR.setText(text);
		    nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		    return;
	  }

	  public void popupNoName() {
		    Element element = nifty.createPopupWithId("popupName");
//		    TextRenderer tR = element.getRenderer(TextRenderer.class);
//		    tR.setText(text);
		    nifty.showPopup(nifty.getCurrentScreen(), element.getId(), null);
		    return;
	  }
	  
	  
	  public void removePopup() {
	      nifty.closePopup(nifty.getCurrentScreen().getTopMostPopup().getId(), null);
	  }

}
