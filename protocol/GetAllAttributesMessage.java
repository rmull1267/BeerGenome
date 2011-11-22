package protocol;

import java.util.List;

import core.Attribute;

import server.PrefixParser;
import server.ServerAttribute;

import client.ClientAttribute;

public class GetAllAttributesMessage implements ProtocolMessage {

	public static final String PREFIX = GetAllAttributesMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private List<Attribute> attributes;
	
	public GetAllAttributesMessage() {}
	
	public GetAllAttributesMessage(List<Attribute> attributes)
	{
		setAttributes(attributes);
	}
	
	@Override
	public String generateMessage() {
		return PREFIX;
	}

	@Override
	public String generateResponse(String message) {
		StringBuilder sb = new StringBuilder();
		
		List<Attribute> list = (new ServerAttribute(1)).getAllAttributes();
		for(Attribute a : list)
		{
			sb.append(a.getAttributeId());
			sb.append(PrefixParser.DELIMITER);
		}
		
		return sb.toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		int numParts = PrefixParser.getNumParts(response);
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		for(int i = 0; i < numParts - 1; i++)
		{
			Attribute a = new ClientAttribute(Integer.parseInt(parts[i]));
			getAttributes().add(a);
		}
	}

	public void setAttributes(List<Attribute> attributes2) {
		this.attributes = attributes2;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

}
