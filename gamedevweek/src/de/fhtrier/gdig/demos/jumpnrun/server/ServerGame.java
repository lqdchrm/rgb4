package de.fhtrier.gdig.demos.jumpnrun.server;

import java.net.InterfaceAddress;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.fhtrier.gdig.demos.jumpnrun.common.JumpNRunGame;
import de.fhtrier.gdig.engine.network.NetworkComponent;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;

public class ServerGame extends JumpNRunGame {
	public static int port = 49999;
	public static InterfaceAddress networkInterface = null;

	public ServerGame() 
	{
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
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new ServerPlayingState());
	}
}
