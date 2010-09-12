package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientCreditsState extends NiftyGameState implements ScreenController {

	private static String menuNiftyXMLFile = "credits.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;
	private static String creditsFile = menuAssetPath+"/credits.txt";
	private static float timePerLine = 1000;
	private static float currentTimeCounter = timePerLine;
	private static int currentBlock = 0;

	private List<String> creditsList;
	
	public ClientCreditsState()
	{
		super(GameStates.CLIENT_CREDITS);
		creditsList = new ArrayList<String>();
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		
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
			BufferedReader br = new BufferedReader(new FileReader(new File(creditsFile)));
			String line;
			while ((line=br.readLine())!=null)
			{
				creditsList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (currentTimeCounter <= 0)
		{
//			addNextBlock();
		}
		else
		{
			currentTimeCounter-=delta;
		}
	}
}
