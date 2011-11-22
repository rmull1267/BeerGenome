package protocol;

public interface ProtocolMessage
{
	/**
	 * Creates a message which generateResponse(String) will send to the server.
	 * @return A message to send to the server.
	 */
	public String generateMessage();
	
	/**
	 * Sends the message created by generateMessage() to the server.
	 * @param message - A message created by generateMessage().
	 * @return A response to send back to the client.
	 */
	public String generateResponse(String message);
	
	/**
	 * Takes some action based on the response.
	 * @param response - A message created by generateResponse(String)
	 * @throws ProtocolException 
	 */
	public void processResponse(String response) throws ProtocolException;
}
