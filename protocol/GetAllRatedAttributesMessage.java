package protocol;

import java.util.List;

import core.AttributeRating;
import server.PrefixParser;
import server.ServerUser;
import client.ClientAttribute;
import client.ClientUser;

public class GetAllRatedAttributesMessage implements ProtocolMessage {

	public static final String PREFIX = "getallratedattr";
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	
	private ClientUser user;
	private List<AttributeRating> attributeRatingList;
	
	
	public GetAllRatedAttributesMessage(ClientUser user, List<AttributeRating> ar)
	{
		setUser(user);
		setAttributeRatingList(ar);
	}
	
	public GetAllRatedAttributesMessage()
	{
		
	}
	
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX + PrefixParser.DELIMITER);
		sb.append(user.getUserId());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		StringBuilder response = new StringBuilder();
		
		String[] parts = message.split(PrefixParser.DELIMITER);
		int userId = Integer.parseInt(parts[1]);
		
		ServerUser s = new ServerUser(userId);
		
		for(AttributeRating r : s.getAllRatedAttributes())
		{
			response.append(r.getAttribute().getAttributeId());
			response.append(PrefixParser.DELIMITER);
			response.append(r.getRating());
			response.append(PrefixParser.DELIMITER);
		}
		
		return response.toString();
	}

	//0:1:2:3:
	@Override
	public void processResponse(String response) throws ProtocolException {
		int numParts = PrefixParser.getNumParts(response);
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		for(int i = 0 ; i < numParts -1 ; i += 2)
		{
			int attributeId = Integer.parseInt(parts[i]);
			int rating		= Integer.parseInt(parts[i+1]);
			
			ClientAttribute attribute = new ClientAttribute(attributeId);
			AttributeRating ar = new AttributeRating(attribute, rating);
			
			getAttributeRatingList().add(ar);
		}
		
	}
	public void setUser(ClientUser user) {
		this.user = user;
	}
	public ClientUser getUser() {
		return user;
	}

	public void setAttributeRatingList(List<AttributeRating> attributeRating) {
		this.attributeRatingList = attributeRating;
	}

	public List<AttributeRating> getAttributeRatingList() {
		return attributeRatingList;
	}

}
