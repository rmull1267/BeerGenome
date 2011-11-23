package protocol;

import java.util.List;

import server.PrefixParser;
import server.ServerUser;

import client.ClientConsumable;
import client.ClientRecommendation;
import client.ClientUser;

import core.LoginException;
import core.Recommendation;

public class GetRatedConsumablesMessage implements ProtocolMessage {

	public static final String PREFIX = "usergetratedconsumables";
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAIL_PREFIX = "fail";
	
	private List<Recommendation> recommendations;
	private ClientUser user;
	
	public GetRatedConsumablesMessage()
	{
		
	}
	
	public GetRatedConsumablesMessage(ClientUser user, List<Recommendation> recommendations)
	{
		setUser(user);
		setRecommendations(recommendations);
	}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(user.getUserId());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		int userId = Integer.parseInt(parts[1]);
		
		StringBuilder sb = new StringBuilder();
		
		ServerUser u = new ServerUser(userId);
		
		for(Recommendation r : u.getRatedConsumables())
		{
			sb.append(userId);
			sb.append(PrefixParser.DELIMITER);
			sb.append(r.getConsumable().getConsumableId());
			sb.append(PrefixParser.DELIMITER);
			sb.append(r.getRevisedRating());
			sb.append(PrefixParser.DELIMITER);
			sb.append(r.getInitialRating());
			sb.append(PrefixParser.DELIMITER);
		}
		
		return sb.toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		int numParts = PrefixParser.getNumParts(response);
		
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		for(int i = 0; i < numParts - 1 ; i += 4)
		{
			int userId 		  = Integer.parseInt(parts[i]);
			int consumableId  = Integer.parseInt(parts[i+1]);
			int revisedRating = Integer.parseInt(parts[i+2]);
			int initialRating = Integer.parseInt(parts[i+3]);
			
			ClientRecommendation r = null;
			r = new ClientRecommendation(
					new ClientUser(userId),
					new ClientConsumable(consumableId)
			);
			
			r.setInitialRatingWithoutCommit(initialRating);
			r.setRevisedRatingWithoutCommit(revisedRating);
			this.getRecommendations().add(r);
		}
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setUser(ClientUser user) {
		this.user = user;
	}

	public ClientUser getUser() {
		return user;
	}

}
