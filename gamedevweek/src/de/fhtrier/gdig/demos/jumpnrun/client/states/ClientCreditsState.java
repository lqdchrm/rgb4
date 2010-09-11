package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.CustomControlCreator;
import de.lessvoid.nifty.controls.dynamic.LabelCreator;
import de.lessvoid.nifty.controls.dynamic.attributes.ControlEffectAttributes;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientCreditsState extends NiftyGameState implements ScreenController {

	private static final String CROSSHAIR_PNG = "crosshair.png";

	private static String menuNiftyXMLFile = "credits.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;
	private static String creditsFile = menuAssetPath+"/credits.txt";
	private static float timePerLine = 1000;
	private static float currentTimeCounter = timePerLine;
	private static int currentBlock = 0;
	private Element creditsPanel;

	private StateBasedGame game;
	
	private List<Cred> creditsList;
	
	public ClientCreditsState()
	{
		super(GameStates.CLIENT_CREDITS);
		creditsList = new ArrayList<Cred>();
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
			BufferedReader br = new BufferedReader(new FileReader(new File(creditsFile)));
			String line;
			int count = 0;
			while ((line=br.readLine())!=null)
			{
				Cred c = new CredText(line);
				c.x = 0;
				c.y = (count++)*20;
				creditsList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		creditsPanel = screen.findElementByName("credits");		

	}

	@Override
	public void onEndScreen() {

	}

	@Override
	public void onStartScreen() {
		if (nifty.getCurrentScreen().getScreenId().equals("start"))
		{
			nifty.gotoScreen("1");
		}
		
	}
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		for (Cred c : creditsList)
		{
			c.y-=delta;
			c.render(container.getGraphics());
		}
	}
	

	
	abstract public class Cred
	{
		public int x,y;
		abstract public void render(Graphics g);
	}
	
	public class CredText extends Cred
	{
		public String text;
		public CredText(String text)
		{
			this.text=text;
		}
		@Override
		public void render(Graphics g) {
			g.drawString(text, x,y);
		}
		
	}
	
	public class CredImage extends Cred
	{
		public Image image;
		public CredImage(String imageFile)
		{
			try {
				image = new Image(imageFile);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void render(Graphics g) {
			g.drawImage(image, x,y );
		}
	}

	
	
}
