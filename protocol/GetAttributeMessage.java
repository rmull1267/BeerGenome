package protocol;

import server.PrefixParser;
import server.ServerAttribute;
import client.ClientAttribute;

public class GetAttributeMessage implements ProtocolMessage {
	public static final String PREFIX = "getattr";
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAIL_PREFIX = "fail";
	
	private ClientAttribute attribute;
	
	public GetAttributeMessage(ClientAttribute a)
	{
		setAttribute(a);
	}
	public GetAttributeMessage() {}
	
	
	@Override
	public String generateMessage() {
		StringBuilder message = new StringBuilder();
		
		message.append(PREFIX);
		message.append(PrefixParser.DELIMITER);
		message.append(attribute.getAttributeId());
		
		return message.toString();
	}

	@Override
	public String generateResponse(String message) {
		String parts[] = message.split(PrefixParser.DELIMITER);
		
		ServerAttribute sa = new ServerAttribute(Integer.parseInt(parts[1]));
		
		return SUCCESS_PREFIX + PrefixParser.DELIMITER + sa.getName();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
		if(response.contains(SUCCESS_PREFIX))
		{
			String[] parts = response.split(PrefixParser.DELIMITER);
			getAttribute().setNameWithoutCommit(parts[1]);
		}
		else
		{
			throw new ProtocolException("Could not load name.");
		}
	}


	public void setAttribute(ClientAttribute attribute) {
		this.attribute = attribute;
	}


	public ClientAttribute getAttribute() {
		return attribute;
	}

}
