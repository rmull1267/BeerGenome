package protocol;

import server.PrefixParser;
import server.ServerAttribute;
import client.ClientAttribute;

public class CreateAttributeMessage implements ProtocolMessage {
	public static final String PREFIX = CreateAttributeMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private ClientAttribute attribute;
	
	public CreateAttributeMessage()
	{
		
	}
	
	public CreateAttributeMessage(ClientAttribute attribute)
	{
		setAttribute(attribute);
	}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(getAttribute().getName());
		
		return sb.toString();
	}
	
	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		//Create a new attribute with the given name.
		ServerAttribute sa = new ServerAttribute(parts[1]);
	
		//send the new attribute's id.
		return SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(sa.getAttributeId()).toString();
	}
	@Override
	public void processResponse(String response) throws ProtocolException {
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		//set the attribute's id.
		getAttribute().setAttributeIdPublic(Integer.parseInt(parts[1]));
		
	}

	public void setAttribute(ClientAttribute attribute) {
		this.attribute = attribute;
	}

	public ClientAttribute getAttribute() {
		return attribute;
	}
	
	
}
