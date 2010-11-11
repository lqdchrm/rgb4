package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QuerySetLevel;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QuerySetTeam;
import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryStartGame;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkLevel;
import de.fhtrier.gdig.demos.jumpnrun.server.network.NetworkPlayer;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckConnect;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckNewPlayerList;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckSetLevel;
import de.fhtrier.gdig.demos.jumpnrun.server.network.protocol.AckStartGame;
import de.fhtrier.gdig.engine.management.AssetMgr;
import de.fhtrier.gdig.engine.network.INetworkCommand;
import de.fhtrier.gdig.engine.network.INetworkCommandListener;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.protocol.ProtocolCommand;
import de.fhtrier.gdig.engine.network.impl.protocol.ServerAckDisconnect;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.CreateButtonControl;
import de.lessvoid.nifty.controls.dynamic.CustomControlCreator;
import de.lessvoid.nifty.controls.dynamic.LabelCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientLobbyState extends NiftyGameState implements
		INetworkCommandListener, ScreenController {
	
	public static String menuNiftyXMLFile = "client_lobby.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;

	private Queue<INetworkCommand> queue;
	private HashMap<Integer, NetworkPlayer> players;
	private ArrayList<NetworkLevel> levels;
	private int currentTeam = 1;

	private StateBasedGame game;
	private boolean isGameCreator = false;

	// gui-elements
	private Element guiListTeam1;
	private Element guiListTeam2;
	private Element guiLevelList;
	private TextRenderer guiCurrentLevelRenderer;
	private Element guiButtonPanel;
	private NetworkLevel currentSelectedLevel;
	private boolean waitingForTransition;

	public String formatLevelname(String levelName) {
		String helpString = levelName.substring(6);

		if (!helpString.equals(""))
			return helpString;
		return levelName;
	}

	public void readLevels(File dir, ArrayList<NetworkLevel> levels) {

		File[] files = dir.listFiles();
		Arrays.sort(files);
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory() && fileName.startsWith("Level")) {
					levels.add(new NetworkLevel(i, 
							AssetMgr.combinePathStrings(
							Assets.Level.AssetLevelPath,
							fileName), formatLevelname(fileName)));
				}
			}
		}
	}

	public ClientLobbyState() {
		super(GameStates.CLIENT_LOBBY);
		queue = new LinkedList<INetworkCommand>();
		players = new HashMap<Integer, NetworkPlayer>();

		levels = new ArrayList<NetworkLevel>();

		File dir = new File(Assets.Level.AssetLevelPath);
		readLevels(dir, levels);
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
		
		 try {
		        enableMouseImage(new Image(
		            ResourceLoader.getResourceAsStream(Assets.Config.AssetGuiPath + "/images/crosshair.png"), "Cursor", false));
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
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int d)
			throws SlickException {
		super.update(container, game, d);

		// recv and execute items in queue
		for (INetworkCommand data : this.queue) {
			if (data != null) {

				if (data instanceof ProtocolCommand) {
					handleProtocolCommands(data);
				}
			}
		}

		// clear all commands even if not handled
		queue.clear();

		NetworkComponent.getInstance().update();
	}

	private void handleProtocolCommands(INetworkCommand cmd) {

		if (Constants.Debug.networkDebug) {
			Log.debug("try to handle:" + cmd);
		}
		
		if (cmd instanceof AckConnect) {
			if (Constants.Debug.networkDebug) {
				Log.debug("Client connected to serverlobby");
			}
		} else if (cmd instanceof AckNewPlayerList) {
			players = ((AckNewPlayerList) cmd).getPlayerList();
			drawPlayers(players.values());
			cmd.setHandled(true);
		} else if (cmd instanceof AckStartGame) {
			translateToGamestate(GameStates.PLAYING);
			cmd.setHandled(true);
		} else if (cmd instanceof ServerAckDisconnect) {
			if (Constants.Debug.networkDebug) {
				Log.debug("Player left server!");
			}
			translateToGamestate(GameStates.SERVER_SELECTION);
		} else if (cmd instanceof AckSetLevel) {
			if (Constants.Debug.networkDebug) {
				Log.debug("Level: " + ((AckSetLevel) cmd).getNetworkLevel().getAssetPath());
			}
			selectLevel(((AckSetLevel) cmd).getNetworkLevel());
			cmd.setHandled(true);
		}
	}

	private void selectLevel(NetworkLevel networkLevel) {
		guiCurrentLevelRenderer.setText(networkLevel.getLevelName());
		Assets.Config.AssetManagerPath = networkLevel.getAssetPath();
	}

	private void drawPlayers(Collection<NetworkPlayer> players) {
		clearList(guiListTeam1);
		clearList(guiListTeam2);
		for (NetworkPlayer player : players) {
			LabelCreator label = new LabelCreator(player.getPlayerName());
			label.setAlign("left");
			if (player.getTeamId() == 1) {
				label.create(nifty, nifty.getCurrentScreen(), guiListTeam1);
			} else if (player.getTeamId() == 2) {
				label.create(nifty, nifty.getCurrentScreen(), guiListTeam2);
			}
		}
	}

	private void drawLevels(List<NetworkLevel> levels) {
		clearList(guiLevelList);
		for (NetworkLevel level : levels) {
			if (isGameCreator) {
				CreateButtonControl createButton = new CreateButtonControl(
						"button");
				createButton.setHeight("25px");
				createButton.setWidth("90%");
				createButton.set("label", level.getLevelName());
				createButton.setAlign("center");
				// TODO setin real values
				createButton.setInteractOnClick("chooseLevel("
						+ level.getLevelID() + ")");
				createButton.create(nifty, nifty.getCurrentScreen(),
						guiLevelList);
			} else {
				LabelCreator labelCreator = new LabelCreator(
						level.getLevelName());
				labelCreator.setAlign("center");
				labelCreator.setStyle("console-text");
				labelCreator.setColor("#fffe");
				labelCreator.create(nifty, nifty.getCurrentScreen(),
						guiLevelList);
			}
		}
	}

	public void chooseLevel(String id) {
		int chooseLevelID = Integer.parseInt(id);
		for (NetworkLevel level : levels) {
			if (level.getLevelID() == chooseLevelID) {
				NetworkComponent.getInstance().sendCommand(
						new QuerySetLevel(level));
				break;
			}
		}
	}

	private void clearList(Element e) {
		for (Element child : e.getElements()) {
			child.markForRemoval();
		}
	}

	@Override
	public void notify(INetworkCommand cmd) {
		queue.add(cmd);
	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		guiListTeam1 = screen.findElementByName("team_1");
		guiListTeam2 = screen.findElementByName("team_2");
		guiLevelList = screen.findElementByName("level_list");
		guiCurrentLevelRenderer = screen.findElementByName("current_level")
				.getRenderer(TextRenderer.class);
		guiButtonPanel = screen.findElementByName("button-panel");
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
		drawLevels(levels);
		if (!isGameCreator) {
			LabelCreator labelCreator = new LabelCreator(
					Constants.GuiConfig.WAITING_FOR_MASTER_TEXT);
			labelCreator.setAlign("right");
			labelCreator.setStyle("waiting_for_master");
			labelCreator
					.create(nifty, nifty.getCurrentScreen(), guiButtonPanel);
			
		} else {
			CustomControlCreator button = new CustomControlCreator("mybutton");
			button.create(nifty, nifty.getCurrentScreen(), guiButtonPanel);
		
			// autoselect first level if no current-level is set
			if (levels.size()>0) {
				if (currentSelectedLevel==null)
					chooseLevel(Integer.toString(levels.get(0).getLevelID()));
				else
					chooseLevel(Integer.toString(currentSelectedLevel.getLevelID()));
			}
		}
	}

	public void back() {
		NetworkComponent.getInstance().disconnect();
	}

	public void startGame() {
		if (isGameCreator) {
			NetworkComponent.getInstance().sendCommand(new QueryStartGame());
		}
	}

	public void chooseTeam(String teamID) {
		if (Constants.Debug.guiDebug) {
			Log.debug("Choose Team:" + teamID);
		}

		if (currentTeam != Integer.parseInt(teamID)) {
			NetworkComponent.getInstance().sendCommand(
					new QuerySetTeam(Integer.parseInt(teamID)));
			currentTeam = Integer.parseInt(teamID);
		}
	}

	public void translateToGamestate(final int gameState)
	{
		if (waitingForTransition==false)
		{
			waitingForTransition = true;
			nifty.getCurrentScreen().endScreen(new EndNotify() {
				public void perform() {
					game.enterState(gameState);				
					waitingForTransition = false;
				}
			});
		}
	}		
	


}
