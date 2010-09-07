package de.fhtrier.gdig.engine.network.impl;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import de.fhtrier.gdig.engine.network.NetworkServerObject;

public class NetworkLobbyListener extends Thread 
{
   private ServerSocket socket;
   private boolean halt;
   private NetworkLobby parent;
   
   public NetworkLobbyListener( NetworkLobby myParent )
   {
	  parent = myParent;
      halt = false;	   
   }
   
   public void setSocket( ServerSocket rcvSocket )
   {
      socket = rcvSocket;   
   }
   
   public void run()
   {
	  while ( !halt )
	  {
		 try
		 {
	        Socket userSocket = socket.accept();
	        ObjectInputStream serverStream = new ObjectInputStream( userSocket.getInputStream() );
	        NetworkServerObject server = (NetworkServerObject) serverStream.readObject();
	        
	        if ( server.getIp() == null )
	        {
	           server.setIp( userSocket.getInetAddress() );
	        }

	        long curr = System.currentTimeMillis();
	        
	        server.setLatency( curr - server.getLatency() );
	        
	        parent.addServer( server );
	        serverStream.close();
		 }
		 catch( EOFException e )
		 {
	        //Ignore
		 }
		 catch( IOException e )
		 {
		    System.out.println( e.getLocalizedMessage() );
		 }
		 catch( ClassNotFoundException e )
		 {
			finish();
			System.out.println( e.getLocalizedMessage() );
		 }
	  }
   }
   
   public void finish()
   {
	  try
	  {
		 if ( socket != null )
	        socket.close();
         halt = true;
	  }
	  catch( IOException e )
	  {
	     System.out.println( e.getLocalizedMessage() );  
	  }
   }
}
