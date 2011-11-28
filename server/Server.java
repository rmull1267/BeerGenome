package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;

import protocol.StopMessage;

import client.Client;
import client.ClientException;
import database.DBAbstractionException;
import database.SQLDatabase;

/**
 * You can actually have multiple servers running, but if you try to run them on the same port
 * you'll get an exception. So this is defacto singleton modulo port number. Perhaps that exception
 * could be handled with a hash map? TODO-nf
 * For now this isn't really runnable just call run.
 * @author nfulton
 *
 */
public class Server implements Runnable {
	private int port;
	
	private Thread listener;
	private boolean loggerEnabled;
	
	public Server(int port)
	{
		setPort(port);
		setLoggerEnabled(false);
	}
	
	@Override
	public void run() {
		System.err.println("beergenomed recieved a START command.");
		
		
		
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(getPort());
		} catch (IOException e1) {
			e1.printStackTrace();
			setListener(null);
		}
        
        while(true)
        {	
        	if(Thread.currentThread() != this.getListener())
        	{
        		System.err.println("beergenomed received a STOP command");
        		return;
        	}
        	try
        	{
	            //BLOCK the server until a connection is created
	            //then create a new socket to transfer data
	            Socket serverSocket = welcomeSocket.accept();
	
	            DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream());
	            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	
	            //read the request from the client
	            String request = inFromClient.readLine();
				while(inFromClient.ready())
				{
					request = request + "\n" + inFromClient.readLine();
				}
				if(isLoggerEnabled()) System.out.println(request);
	
				//get a response
				String response = "Invalid Request!";
				response = PrefixParser.getinstance().getResponse(request);
				if(isLoggerEnabled()) System.out.println("\t" + response);
	            
	            //write the result back to the client
	            outToClient.writeBytes(response + "\n");
	
	            //close the socket
	            serverSocket.close();
	            
	            //Additionally, if the logger is enabled we are
	            //in production mode so go ahead and do a commit
	            //to the database.
	            if(isLoggerEnabled())
	            {
	            	SQLDatabase.getInstance().refresh();
	            }
        	}
        	catch (IOException e) {
        		e.printStackTrace();
        		setListener(null);
			} catch (protocol.ProtocolException e) {
				e.printStackTrace();
			} catch (DBAbstractionException e) {
				e.printStackTrace();
			}
        }
	}
	
	
	public void startServer() throws ServerException
	{
		if(isRunning())
		{
			throw new ServerException("Server is already running.");
		}
		
		setListener(new Thread(this, "beergenomed"));
		getListener().start();
	}
	
	public void stopServer() throws ServerException
	{
		setListener(null);
		
		//Send a message to the server to force a loop through the read.
		Client c = null;
		try {
			c = Client.getInstance();
		} catch (ClientException e) {
			try {
				Client.initializeClient(getPort(), "127.0.0.1");
				c = Client.getInstance();
			} catch (ClientException e1) {
				//We should never get here.
				throw new ServerException(e1);
			}	
		}
		
		StopMessage s = new StopMessage();
		try {
			c.sendMessage(s.generateMessage());
		} catch (ClientException e) {
			throw new ServerException(e);
		}		
		
	}
	
	public void restartServer() throws ServerException
	{
		//TODO there's a nicer way of doing this.
		stopServer();
		startServer();
	}
	
	public boolean isRunning()
	{
		return !(this.getListener() == null);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}


	private void setListener(Thread listener) {
		this.listener = listener;
	}


	private Thread getListener() {
		return listener;
	}

	public void setLoggerEnabled(boolean loggerEnabled) {
		this.loggerEnabled = loggerEnabled;
	}

	private boolean isLoggerEnabled() {
		return loggerEnabled;
	}

}
