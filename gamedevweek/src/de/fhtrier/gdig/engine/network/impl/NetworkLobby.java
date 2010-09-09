package de.fhtrier.gdig.engine.network.impl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import de.fhtrier.gdig.engine.network.IAddServerListener;
import de.fhtrier.gdig.engine.network.INetworkLobby;
import de.fhtrier.gdig.engine.network.NetworkServerObject;

public class NetworkLobby implements INetworkLobby, IAddServerListener 
{
	private NetworkLobbyListener listener;
	private Semaphore serverMutex;
	private List<NetworkServerObject> serverList;
	private IAddServerListener serverListener; 
	
	public NetworkLobby()
	{
	   serverList = new ArrayList<NetworkServerObject>();
	   listener = new NetworkLobbyListener( this );
	   serverMutex = new Semaphore( 1 );
	}
	
	public NetworkLobby(IAddServerListener serverlistener)
	{
	   this.serverListener = serverlistener;
	   serverList = new ArrayList<NetworkServerObject>();
	   listener = new NetworkLobbyListener( serverlistener );
	   serverMutex = new Semaphore( 1 );
	}
		
	@Override
	public void getServers( InterfaceAddress networkInterface ) 
	{		
	   if ( listener.isAlive() )
	   {
		 System.out.println( "Already looking for Servers. Please cancel last request first" );
	     return;
	   }
	   
	   serverList.clear();
	   
	   String cqData = "CQ,CQ,CQ RGB4 " + networkInterface.getAddress().getHostAddress() + " K";
	
 	   try
	   {
 		 ServerSocket rcvSocket = new ServerSocket( 50000, 0, networkInterface.getAddress() );
 		 listener.setSocket( rcvSocket );
 		 listener.start();
 		 DatagramSocket socket = new DatagramSocket();
	     DatagramPacket packet = new DatagramPacket( cqData.getBytes(), 0, cqData.getBytes().length, networkInterface.getBroadcast(), 50000 );
		 socket.send( packet ); // Send Broadcast to all
	   }
  	   // HACK should only catch IOExceptions
 	   catch( Exception e )
	   {
	     System.out.println( e.getLocalizedMessage() );
	     listener.finish();
	   }
	}
	
	@Override
	public void stopGetServers()
	{
	   listener.finish();

	   if (serverListener!=null)
		   listener = new NetworkLobbyListener( serverListener );
	   else
		   listener = new NetworkLobbyListener( this );
		   
	}

	@Override
	public List<NetworkServerObject> getServerList()
	{
	   List<NetworkServerObject> list = new ArrayList<NetworkServerObject>();
	   try
	   {
	      serverMutex.acquire();
          list = serverList;
          serverMutex.release();
	   }
	   catch( InterruptedException e )
	   {
	      System.out.println( e.getLocalizedMessage() );   
	   }
	   
	   return list;
	}
	
	public void addServer( NetworkServerObject server )
	{
	   try
	   {
	      serverMutex.acquire();
	      System.out.println( "New Server Detected: " + server.getName() + "  " + server.getIp().getHostAddress() + " " + server.getLatency() + " ms" );
	      serverList.add( server );
	      serverMutex.release();
	   }
	   catch( InterruptedException e )
	   {
	      System.out.println( e.getLocalizedMessage() );
	   }
	}
}
