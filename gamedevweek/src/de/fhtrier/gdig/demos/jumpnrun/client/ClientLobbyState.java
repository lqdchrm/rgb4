package de.fhtrier.gdig.demos.jumpnrun.client;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryLeave;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryStartGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkLevel;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkPlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckConnect;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckLeave;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckNewPlayerList;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckStartGame;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ClientQueryDisconnect;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckDisconnect;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.CreateButtonControl;
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.controls.dynamic.CustomControlCreator;
import de.lessvoid.nifty.controls.dynamic.LabelCreator;
import de.lessvoid.nifty.controls.listbox.CreateListBoxControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientLobbyState extends NiftyGameState implements INetworkCommandListener, ScreenController {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "client_lobby.xml";
	public static String menuAssetPath = "content/jumpnrun/default/gui";
	
	private Queue<INetworkCommand> queue;
	private ArrayList<NetworkPlayer> players;
	private ArrayList<NetworkLevel> levels;
	
	private StateBasedGame game;
	private boolean isGameCreator = false;
	private NetworkLevel currentLevel;
	
	
	// gui-elements
	private Element guiPlayerList;
	private Element guiLevelList;
	private TextRenderer guiCurrentLevelRenderer;
	private Element guiStartButton;
	
	public ClientLobbyState() {
		super(GameStates.CLIENT_LOBBY);
		queue = new LinkedList<INetworkCommand>();
		players = new ArrayList<NetworkPlayer>();
		
		levels = new ArrayList<NetworkLevel>();
		levels.add(new NetworkLevel(0,"content/jumpnrun/default/", "Level 12"));
		levels.add(new NetworkLevel(1,"content/jumpnrun/default/", "Level 234"));
		levels.add(new NetworkLevel(2,"content/jumpnrun/default/", "Level 32222"));
	}
	
	public boolean isGameCreator() {
		return isGameCreator;
	}

	public void setGameCreator(boolean isGameCreator) {
		this.isGameCreator = isGameCreator;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
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
		super.render(container, game, g);
//		float offset = 30;
//		g.drawString("Client Currently connected: ", 10.0f, offset);
//		for (NetworkPlayer player : players) {
//			g.drawString(player.getPlayerName(), 10.0f, 20.0f + offset);
//			offset += 10.0f;
//		}
//		g.drawString("PRESS ENTER TO START", 200, 400);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		// TODO Auto-generated method stub
		super.update(container, game, d);
		
		
		// recv and execute items in queue
		for (INetworkCommand data : this.queue) {
			if (data != null && !data.isHandled()) {

				if (data instanceof ProtocolCommand) {
					handleProtocolCommands(data);
					data.setHandled(true);
				}
			}
		}
		
//		if(container.getInput().isKeyPressed(Input.KEY_ENTER)) {
//			if(isGameCreator) {
//				Log.debug("trying to start game");
//				NetworkComponent.getInstance().sendCommand(new QueryStartGame());
//			} else {
//				Log.debug("You tried to start the game but you are not the GameCreator.");
//			}
//		}
		// clear all commands even if not handled
		queue.clear();
		
		NetworkComponent.getInstance().update();
	}
	
	private void handleProtocolCommands(INetworkCommand cmd) {
		
		Log.debug("try to handle:"+cmd);
		
		if (cmd instanceof AckConnect) {
			Log.debug("Client connected to serverlobby");
		}
		if (cmd instanceof AckNewPlayerList) {
			players = ((AckNewPlayerList) cmd).getPlayerList();
			drawPlayers(players);
		}
		if (cmd instanceof AckStartGame) {
			game.enterState(GameStates.PLAYING);
		}
		if (cmd instanceof ServerAckDisconnect) {
			Log.debug("Player left server!");
			game.enterState(GameStates.SERVER_SELECTION);
		}
			
	}

	private void drawPlayers(List<NetworkPlayer> players)
	{
		clearList(guiPlayerList);
		for (NetworkPlayer player : players)
		{
			// get template from XML ( <controlDefinition name='playerContrl'> )
//			CustomControlCreator lC = new CustomControlCreator("playerControl");
//			lC.setWidth("100%");
//			lC.setHeight("30px");
//			lC.setAlign("left");
//			Element element = lC.create(nifty, nifty.getCurrentScreen(), guiPlayerList);
//			element.findElementByName("playername").getRenderer(TextRenderer.class).setText();
			// fill in the name
			LabelCreator label = new LabelCreator(player.getPlayerName());
			label.setAlign("left");
			label.create(nifty, nifty.getCurrentScreen(), guiPlayerList);
		}
	}
	
	private void drawLevels(List<NetworkLevel> levels)
	{
		clearList(guiLevelList);
		for (NetworkLevel level : levels)
		{
			if (isGameCreator)
			{
				CreateButtonControl createButton = new CreateButtonControl("button");
			    createButton.setHeight("30px");
			    createButton.setWidth("150px");
			    createButton.set("label", level.getLevelName());
			    createButton.setAlign("left");
			    // TODO setin real values
			    createButton.setInteractOnClick("chooseLevel("+level.getLevelID()+")");
			    createButton.create(nifty, nifty.getCurrentScreen(), guiLevelList);
			}
			else
			{
				LabelCreator labelCreator = new LabelCreator(level.getLevelName());
				labelCreator.setAlign("left");
				labelCreator.create(nifty, nifty.getCurrentScreen(), guiLevelList);
			}
		}
	}
	
	public void chooseLevel(String id)
	{
		int chooseLevelID = Integer.parseInt(id);
		for (NetworkLevel level : levels)
		{
			if (level.getLevelID() == chooseLevelID)
			{
				this.currentLevel = level;
				guiCurrentLevelRenderer.setText(level.getLevelName());
				break;
			}
		}
		// TODO: Benachrichtige Server und andere Clients
	}
	
	
	private void clearList(Element e)
	{
		for (Element child : e.getElements())
		{
			child.markForRemoval();
		}
	}
	
	@Override
	public void notify(INetworkCommand cmd) {
		queue.add(cmd);
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		guiPlayerList = screen.findElementByName("player_list");
		guiLevelList = screen.findElementByName("level_list");
		guiCurrentLevelRenderer = screen.findElementByName("current_level").getRenderer(TextRenderer.class);
		guiStartButton = screen.findElementByName("start_button");
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
		drawLevels(levels);
		if (!isGameCreator)
		{
			Element buttonParent = guiStartButton.getParent();
			guiStartButton.markForRemoval();
			LabelCreator labelCreator = new LabelCreator("Waiting for Master to Start!");
			labelCreator.create(nifty, nifty.getCurrentScreen(), buttonParent);
		}
	}
	
	public void back()
	{
		NetworkComponent.getInstance().disconnect();
	}

	public void startGame()
	{
		if(isGameCreator)
		{
			NetworkComponent.getInstance().sendCommand(new QueryStartGame());
		}
	}
	
	
}
