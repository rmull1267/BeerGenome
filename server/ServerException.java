package server;

public class ServerException extends Exception {
	public ServerException(Exception e)
	{
		super(e);
	}
	
	public ServerException(String message)
	{
		super(message);
	}
}
