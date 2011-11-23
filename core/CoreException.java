package core;

public class CoreException extends Exception{
	public CoreException(String s)
	{
		super(s);
	}
	
	public CoreException(Exception e)
	{
		super(e);
	}
}
