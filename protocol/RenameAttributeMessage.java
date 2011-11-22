package protocol;

import server.PrefixParser;
import server.ServerAttribute;
import client.ClientAttribute;

public class RenameAttributeMessage implements ProtocolMessage {
	public static final String PREFIX = RenameAttributeMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAIL_PREFIX = "fail";
	
	private ClientAttribute attribute;
	
	public RenameAttributeMessage() {}
	
	public RenameAttributeMessage(ClientAttribute attribute)
	{
		setAttribute(attribute);
	}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(getAttribute().getAttributeId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(getAttribute().getName());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		ServerAttribute sa = new ServerAttribute(Integer.parseInt(parts[1]));
		sa.setName(parts[2]);
		
		return SUCCESS_PREFIX;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
	}

	public void setAttribute(ClientAttribute attribute) {
		this.attribute = attribute;
	}

	public ClientAttribute getAttribute() {
		return attribute;
	}

}
