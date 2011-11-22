package protocol;

import server.PrefixParser;
import server.ServerConsumable;
import server.ServerRecommendation;
import server.ServerUser;
import client.ClientRecommendation;

public class GetRecommendationMessage implements ProtocolMessage {

	public static final String PREFIX = GetRecommendationMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private ClientRecommendation recommendation;
	
	public GetRecommendationMessage(ClientRecommendation r)
	{
		setRecommendation(r);
	}
	public GetRecommendationMessage() {}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(getRecommendation().getUser().getUserId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(getRecommendation().getConsumable().getConsumableId());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		ServerUser u = new ServerUser(Integer.parseInt(parts[1]));
		ServerConsumable c = new ServerConsumable(Integer.parseInt(parts[2]));
		
		ServerRecommendation r = new ServerRecommendation(u,c);
		
		StringBuilder sb = new StringBuilder();
		sb.append(SUCCESS_PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(r.getInitialRating());
		sb.append(PrefixParser.DELIMITER);
		sb.append(r.getRevisedRating());
		
		return sb.toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		int initialRating = Integer.parseInt(parts[1]);
		int revisedRating = Integer.parseInt(parts[2]);
		
		getRecommendation().setInitialRatingWithoutCommit(initialRating);
		getRecommendation().setRevisedRatingWithoutCommit(revisedRating);
	}

	public void setRecommendation(ClientRecommendation r) {
		this.recommendation = r;
	}

	public ClientRecommendation getRecommendation() {
		return recommendation;
	}

}
