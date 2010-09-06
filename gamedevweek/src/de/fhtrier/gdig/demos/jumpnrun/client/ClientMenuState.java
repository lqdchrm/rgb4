package de.fhtrier.gdig.demos.jumpnrun.client;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.GameStates;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import de.lessvoid.nifty.tools.resourceloader.ResourceLoader;

public class ClientMenuState extends NiftyGameState implements ScreenController {

  public static String menuNiftyXMLFile="mainmenu.xml";
  public static String menuAssetPath = "content/jumpnrun/default/gui";
  
  private StateBasedGame game;
  private GameContainer container;
  private Screen screen;
  
  public ClientMenuState(int id,
      final GameContainer newContainer,
      final StateBasedGame newGame
     )
      throws SlickException {
    super(id);
    
    this.id = id;
    game = newGame;
    container = newContainer;
    // add asset-folder to the ResourceLocators of nifty and slick2d
    ResourceLoader.addResourceLocation(new FileSystemLocation(new File(menuAssetPath)));
    org.newdawn.slick.util.ResourceLoader.addResourceLocation(new org.newdawn.slick.util.FileSystemLocation(new File(menuAssetPath)));
    // read the nifty-xml-fiel
    fromXml(menuNiftyXMLFile,ResourceLoader.getResourceAsStream(menuNiftyXMLFile), this);
    // show the mouse
    enableMouseImage(new Image(ResourceLoader.getResourceAsStream("crosshair.png"),"crosshair.png",false));
    
  }

  public void bind(final Nifty newNifty, final Screen newScreen) {
    screen = newScreen;
  }

  public void onStartScreen() {
    if (screen.getScreenId().equals("start")) {
      nifty.gotoScreen("mainMenu");
    } else if (screen.getScreenId().equals("newGame")) {
//      screen.findElementByName("newGame").setFocus();
    }
	screen.getFocusHandler().setKeyFocus(null);
  }

  public void onEndScreen() {
  }

  public void newGame() {
    Log.debug("mouseX: " + mouseX + ", mouseY: " + mouseY);
    game.enterState(GameStates.PLAYING,new FadeOutTransition(),new FadeInTransition());
  }

  public void exit() {
    screen.endScreen(new EndNotify() {
      public void perform() {
        container.exit();
      }
    });
  }

  public void mouseMoved(final int oldx, final int oldy, final int newx, final int newy) {
    super.mouseMoved(oldx, oldy, newx, newy);
    Log.debug(oldx + ", " + oldy + ", " + newx + ", " + newy);
  }
  
  public Nifty getNifty() {
	  return nifty;
  }
}
