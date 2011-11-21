package protocol;

public interface ProtocolMessage {
	/**
	 * 
	 * @return A message to send to the server.
	 */
	public String generateMessage();
	
	/**
	 * 
	 * @param message a message created by generatemessage.
	 * @return A response to send back to the client.
	 */
	public String generateResponse(String message);
	
	/**
	 * Takes some action based on the response.
	 * @param response a message created by generateResponse
	 * @throws ProtocolException 
	 */
	public void processResponse(String response) throws ProtocolException;
}
