package de.fhtrier.gdig.demos.jumpnrun.client.states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.ImageCreator;
import de.lessvoid.nifty.controls.dynamic.LabelCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

/**
 * 
 * credits: edit content/.../gui/credits.txt
 * 
 * Different-Fonts mapped by the number of #
 * (see rgb-style.xml and the <style id="credits-n">-descriptions)
 * 
 * 
 * special one is # for loading images
 * ##test.png,200px,100px : loads picture from gui-dir with size 200,100
 * 
 * For now this 4 types are mapped:
 * Hola
 * #Blabla 
 * ###Hola  
 * ####Hola 
 *  
 * 
 * In order to change the length of the scroller edit in rgb-style.xml the scroller-style
 * 
 * @author ttrocha
 *
 */
public class ClientCreditsState extends NiftyGameState implements ScreenController {

	private static String menuNiftyXMLFile = "credits.xml";
	public static String menuAssetPath = Assets.Config.AssetGuiPath;
	private static String creditsFile = menuAssetPath+"/credits.txt";
	private StateBasedGame game;
	private Element creditsPanel;
	private boolean waitingForTransition;

	
	public ClientCreditsState()
	{
		super(GameStates.CLIENT_CREDITS);
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
		// read the nifty-xml-file
		fromXml(menuNiftyXMLFile,
				ResourceLoader.getResourceAsStream(menuNiftyXMLFile), this);
		
		 try {
		        enableMouseImage(new Image(
		            ResourceLoader.getResourceAsStream(Assets.Config.AssetGuiPath + "/images/crosshair.png"), "Cursor", false));
		    } catch (SlickException e) {
		        Log.error("Image loading failed in ServerSettingsState");
		        e.printStackTrace();
		    }

		    
		this.game = game;

	}


	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (this.isAcceptingInput() && key==Input.KEY_ESCAPE)
		{
			translateToGamestate(GameStates.MENU);
		}
	}

	private int amountMarkerElements(String st)
	{
		int count=0;
		if (st.equals(""))
			return 0;
		while (count<st.length() && st.charAt(count)=='#')
		{
			count++;
		}
		return count;
	}
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		creditsPanel = screen.findElementByName("credits");		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(creditsFile)));
			String line;
			int count = 0;
			while ((line=br.readLine())!=null)
			{
				int markElements = amountMarkerElements(line);
				String trimmedLine = line.substring(markElements);

				if (markElements == 2)
				{
					ImageCreator image = new ImageCreator("img"+count);
					StringTokenizer stk = new StringTokenizer(trimmedLine,",");
					image.setFilename(Assets.Config.AssetGuiPath+"/"+stk.nextToken());
					if (stk.hasMoreTokens())
					{
						image.setWidth(stk.nextToken());
						if (stk.hasMoreTokens())
						{
							image.setHeight(stk.nextToken());
						}
						else
						{
							image.setHeight("20%");
						}
					}
					else
					{
						image.setHeight("20%");
						image.setWidth("100%");
					}
					image.setAlign("center");
					image.create(nifty, nifty.getCurrentScreen(),creditsPanel);
				}
				else
				{
						LabelCreator creator = new LabelCreator(count+"creds", trimmedLine);
						creator.setStyle("credits-"+markElements);
						creator.setAlign("center");
						creator.setWidth("100%");
						creator.setHeight("10%");
						creator.create(nifty, nifty.getCurrentScreen(), creditsPanel);
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
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
