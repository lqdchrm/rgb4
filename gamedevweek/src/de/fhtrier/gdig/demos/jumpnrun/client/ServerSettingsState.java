package de.fhtrier.gdig.demos.jumpnrun.client;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.fhtrier.gdig.demos.jumpnrun.server.DedicatedServer;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
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
	}

	@Override
	public void onEndScreen() {
		// left intentionally blank
	}

	@Override
	public void onStartScreen() {
		// left intentionally blank
	}
	
	public void createServer() {
		String serverName = serverNameControl.getText();
		int port = Integer.parseInt(portControl.getText());
		
		//TODO Fehlerbehandlung
		//TODO in eigenen Process packen
		game.enterState(GameStates.CLIENT_LOBBY,new FadeOutTransition(),new FadeInTransition());
	}
	

	public void back() {
		game.enterState(GameStates.MENU,new FadeOutTransition(),new FadeInTransition());
	}	
	
	
	
	

}
