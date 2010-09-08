package de.fhtrier.gdig.demos.jumpnrun.common;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.net.*;
import java.util.List;

import de.fhtrier.gdig.demos.jumpnrun.client.ClientGame;
import de.fhtrier.gdig.demos.jumpnrun.server.ServerGame;
import de.fhtrier.gdig.engine.network.INetworkLobby;
import de.fhtrier.gdig.engine.network.NetworkServerObject;
import de.fhtrier.gdig.engine.network.impl.NetworkLobby;

public class Lobby extends JDialog {

	public static final int SERVER = 0;
	public static final int CLIENT = 1;
	public static final int SPECTATOR = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8056874862301688643L;

	public static ServerGame createServer(InterfaceAddress ni, int port) {
		ServerGame.port = port;
		ServerGame.networkInterface = ni;
		return new ServerGame();
	}

	public static ClientGame createClient(String serverIP, int port) {
		ClientGame.nameOrIp = serverIP;
		ClientGame.port = port;

		return new ClientGame();
	}

	public static RGB4Game createGameByArgs(String[] args) {
		
		String address = "";

		INetworkLobby networkLobby = new NetworkLobby();
		List<InterfaceAddress> interfaces = networkLobby.getInterfaces();
		InterfaceAddress ni = null;
		
		switch (args.length - 1) 
		{
		case SERVER:
			
			for ( int x = 0; x < interfaces.size(); x++ )
			{
			    if ( interfaces.get( x ).getAddress().getHostAddress().contains( "127.0.0.1" ) )
				   ni = interfaces.get( x );
			}
			
			if ( ( interfaces.size() > 0 ) && ( ( ni == null ) ) )
			{
			   System.out.println( "Localhost not available falling back to " + interfaces.get( 0 ).getAddress().getHostAddress() );
               ni = interfaces.get( 0 );
			}
			
			return createServer( ni, Integer.parseInt(args[0]) ); // assume port give
															// -> server
		case SPECTATOR:
			ClientGame.isSpectator = true; // assume we are client in spectator
											// mode
		case CLIENT:
			
			try
			{
			   address = InetAddress.getByName( args[0] ).getHostAddress();
			}
			catch( UnknownHostException e )
			{
			   try
			   {
                  address = InetAddress.getLocalHost().getHostAddress();
			   }
			   catch( UnknownHostException e2 )
			   {
			      address = "localhost";
			   }
			}
			
			return createClient( address, Integer.parseInt(args[1])); // assume
																		// ip
																		// and
																		// port
																		// ->
																		// client
		default:
			return createGameViaDialog(); // via Dialog
		}
	}

	public static RGB4Game createGameViaDialog() {

		// Ask whether we want to be server
		Object[] options = { "Server", "Client", "Spectator" };
		int result = JOptionPane.showOptionDialog(null,
				"Do you want to be Server or Client", "Lobby", 0,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		switch (result) {
		case SERVER: 
		{
			INetworkLobby networkLobby = new NetworkLobby();
			List<InterfaceAddress> interfaces = networkLobby.getInterfaces();
			
			Object[] serverListe = new Object[interfaces.size()];
			
			for ( int x = 0; x < interfaces.size(); x++ )
			{
			  serverListe[x] = interfaces.get( x ).getAddress().getHostAddress();
			}
			
			String Interface = (String) JOptionPane
			.showInputDialog(
					null,
					"Please select interface to host on",
					"You are Server", JOptionPane.PLAIN_MESSAGE, null,
					serverListe, null );
			
			InterfaceAddress ni = null;
			
			for ( int x = 0; x < interfaces.size(); x++ )
			{
				if ( Interface.contains( interfaces.get( x ).getAddress().getHostAddress() ) )
					ni = interfaces.get( x );
			}
			
			String strPort = (String) JOptionPane.showInputDialog(null,
					"Please enter port number to host on", "You are Server",
					JOptionPane.PLAIN_MESSAGE, null, null, ServerGame.port);

			return createServer(ni, Integer.parseInt(strPort));
		}
		case SPECTATOR:
			ClientGame.isSpectator = true;
		case CLIENT: {
			NetworkLobby networkLobby = new NetworkLobby();
			List<InterfaceAddress> interfaces = networkLobby.getInterfaces();
			
			Object[] serverListe = new Object[interfaces.size()];
			
			for ( int x = 0; x < interfaces.size(); x++ )
			{
			  serverListe[x] = interfaces.get( x ).getAddress().getHostAddress();
			}
			
			String Interface = (String) JOptionPane
			.showInputDialog(
					null,
					"Please select interface to scan for servers",
					"You are Client", JOptionPane.PLAIN_MESSAGE, null,
					serverListe, null );
			
			InterfaceAddress ni = null;
			
			for ( int x = 0; x < interfaces.size(); x++ )
			{
				if ( Interface.contains( interfaces.get( x ).getAddress().getHostAddress() ) )
					ni = interfaces.get( x );
			}

            networkLobby.getServers( ni );
            
            try
            {
               Thread.sleep( 1000 );   
            }
            catch( InterruptedException e )
            {
               System.out.println( e.getLocalizedMessage() );
            }
            
            List<NetworkServerObject> sList = networkLobby.getServerList();
            
            Object[] servers = new Object[sList.size()];
            
            for ( int x = 0; x < sList.size(); x++ )
            {
               servers[x] = sList.get( x ).getIp().getHostAddress();
            }
			
			// if client ask for server ip and port number
			String strServer = (String) JOptionPane
					.showInputDialog(
							null,
							"Please select server",
							"You are Client", JOptionPane.PLAIN_MESSAGE, null,
							servers, ClientGame.nameOrIp );
			
			String ip = "127.0.0.1";
			int port = 49999;
			
			for ( int x = 0; x < sList.size(); x++ )
			{
			   if ( sList.get( x ).getIp().getHostAddress().contains( strServer ) )
			   {
			      ip = sList.get( x ).getIp().getHostAddress();
			      port = sList.get( x ).getPort();
			   }
			}

			networkLobby.stopGetServers();
			return createClient( ip, port );
		}
		default:
			return null;
		}
	}
}
