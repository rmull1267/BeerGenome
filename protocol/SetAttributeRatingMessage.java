package protocol;

import core.Attribute;
import server.PrefixParser;
import server.ServerAttribute;
import server.ServerUser;
import client.ClientAttribute;
import client.ClientUser;

public class SetAttributeRatingMessage implements ProtocolMessage {
	private ClientUser user;
	private Attribute attribute;
	private int rating;
	
	
	public static final String PREFIX = "attrrating";
	public static final String SUCCESS_PREFIX = "success";
	public static final String FAILURE_PREFIX = "failure";
	
	public SetAttributeRatingMessage(ClientUser user, Attribute attribute, int rating)
	{
		setUser(user);
		setAttribute(attribute);
		setRating(rating);
	}
	
	public SetAttributeRatingMessage()
	{
		
	}
	
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(getUser().getUserId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(getAttribute().getAttributeId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(rating);
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		ServerUser u = new ServerUser(Integer.parseInt(parts[1]));
		ServerAttribute a = new ServerAttribute(Integer.parseInt(parts[2]));
		
		u.setAttributeRating(a, Integer.parseInt(parts[3]));
		
		return SUCCESS_PREFIX;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
	}


	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}


	public Attribute getAttribute() {
		return attribute;
	}


	public void setUser(ClientUser user) {
		this.user = user;
	}


	public ClientUser getUser() {
		return user;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

}
