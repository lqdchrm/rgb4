package de.fhtrier.gdig.demos.jumpnrun.client;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.client.network.protocol.QueryConnect;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.DedicatedServer;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ServerSettingsState extends NiftyGameState implements ScreenController {

	private static final String CROSSHAIR_PNG = "crosshair.png";
	public static String menuNiftyXMLFile = "server_settings.xml";
	public static String menuAssetPath = "content/jumpnrun/default/gui";
	
	private StateBasedGame game;
	private TextFieldControl portControl;
	private TextFieldControl serverNameControl;
	private TextFieldControl playerNameControl;
	private boolean connecting = false;
	private boolean serverStarting = false;
	  
	public ServerSettingsState(final StateBasedGame game) {
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
	public void bind(Nifty arg0, Screen screen) {
		portControl = screen.findControl("portnumber", TextFieldControl.class);
		serverNameControl = screen.findControl("servername", TextFieldControl.class);
		playerNameControl = screen.findControl("playername", TextFieldControl.class);
	}

	@Override
	public void onEndScreen() {
		// left intentionally blank
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		((ClientLobbyState)game.getState(GameStates.CLIENT_LOBBY)).setGameCreator(true);
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
		if(connecting) {
			NetworkComponent.getInstance().update();
		}
		if(NetworkComponent.getInstance().getNetworkId() != -1) {
			NetworkComponent.getInstance().sendCommand(new QueryConnect(playerNameControl.getText()));
			game.enterState(GameStates.CLIENT_LOBBY);
		}
	}
	
	public void createServer() {
		if(!serverStarting){
			serverStarting = true;
			
			String serverName = serverNameControl.getText();
			int port = Integer.parseInt(portControl.getText());
			
			//TODO Fehlerbehandlung
			//TODO in eigenen Process packen
			//TODO server wirklich starten
			//TODO krumholt thomas
			NetworkComponent.getInstance().connect("localhost", 49999);
			connecting = true;
		} else {
			Log.debug("Allready tried creating a server");
		}
	}
	

	public void back() {
		game.enterState(GameStates.MENU,new FadeOutTransition(),new FadeInTransition());
	}	
	
	
	
	

}
