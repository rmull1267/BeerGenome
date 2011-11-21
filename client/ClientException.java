package client;

public class ClientException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientException(Exception e) {
		super(e);
	}
	
	public ClientException(String message)
	{
		super(message);
	}
}
