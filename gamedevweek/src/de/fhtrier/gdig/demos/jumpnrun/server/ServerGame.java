package de.fhtrier.gdig.demos.jumpnrun.server;

import java.net.InterfaceAddress;
import java.util.List;

import javax.swing.JPanel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.RGB4Game;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Assets;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;
import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants.Debug;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;

public class ServerGame extends RGB4Game {
	public static int port = 49999;
	public static InterfaceAddress networkInterface = null;

	public ServerGame() {
		super(Assets.GameTitle + " (Server)");
		
	   if ( networkInterface == null )
	   {
	      NetworkLobby lobby = new NetworkLobby();
	      List<InterfaceAddress> networkInterfaces = lobby.getInterfaces();
	      
	      if ( networkInterfaces.size() > 0 )
	      {
	         networkInterface = networkInterfaces.get( 0 );
	      }
	      else
	      {
	         System.out.println( "No network interfaces detected" );
	         return;
	      }
	   }
		
       NetworkComponent.createServerInstance();
       NetworkComponent.getInstance().startListening( networkInterface, port );
       
		Constants.GamePlayConstants c = new Constants.GamePlayConstants();
		Constants.Debug d = new Debug();
		d.showEditor("Server",
				new JPanel[] { d.getEdittingPanel(), c.getEdittingPanel() });
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException
	{
		addState(new ServerPlayingState());
	}
}
