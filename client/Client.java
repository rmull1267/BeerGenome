package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static Client instance;
	private int port;
	private String serverAddress;
	
	public static void initializeClient(int port, String serverAddress) throws ClientException 
	{
		if(instance == null)
		{
			instance = new Client(port, serverAddress);
		}
		else
		{
			throw new ClientException("Error: instance already exists.");
		}
	}
	
	/**
	 * Should only be used for manual testing.
	 */
	public static void initializeForNetcat()
	{
		instance = new Client(1337, "127.0.0.1");
	}
	
	public static Client getInstance() throws ClientException
	{
		if(instance == null)
		{
			throw new ClientException("Error: Instance does not yet exist. Must initializeClient first.");
		}
		return instance;
	}
	private Client(int port, String serverAddress)
	{
		setPort(port);
		setServerAddress(serverAddress);
	}
	
	
	/**
	 * TODO-nf
	 * @param message
	 * @return response
	 * @throws ClientException 
	 */
	public String sendMessage(String message) throws ClientException
	{
		String response = "";
		try 
		{
			Socket clientSocket = new Socket(getServerAddress(), getPort());
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
			outToServer.writeBytes(message + "\n");

			response = inFromServer.readLine();
			while(inFromServer.ready())
			{
				response = response + "\n" + inFromServer.readLine();
			}
			clientSocket.close();
		}
		catch (UnknownHostException e) {
			throw new ClientException(e);
		} 
		catch (IOException e) {
			throw new ClientException(e);
		}
		
		return response;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getServerAddress() {
		return serverAddress;
	}
}
