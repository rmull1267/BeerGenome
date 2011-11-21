package core;

public class LoginException extends Exception {
	public LoginException(Exception e)
	{
		super(e);
	}
	
	public LoginException()
	{
		
	}
	
	public LoginException(String message)
	{
		super(message);
	}
}
