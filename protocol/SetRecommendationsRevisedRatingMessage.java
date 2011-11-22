package protocol;

import server.PrefixParser;
import server.ServerConsumable;
import server.ServerRecommendation;
import server.ServerUser;

import client.ClientRecommendation;

public class SetRecommendationsRevisedRatingMessage implements ProtocolMessage {

	public static final String PREFIX = SetRecommendationsRevisedRatingMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private ClientRecommendation recommendation;
	
	public SetRecommendationsRevisedRatingMessage(ClientRecommendation recommendation)
	{
		setRecommendation(recommendation);
	}
	
	public SetRecommendationsRevisedRatingMessage() {}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(getRecommendation().getUser().getUserId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(getRecommendation().getConsumable().getConsumableId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(getRecommendation().getRevisedRating());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		if(PrefixParser.getNumParts(message) != 4)
		{
			return FAILURE_PREFIX;
		}
		
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		ServerRecommendation sr = new ServerRecommendation(
				new ServerUser(Integer.parseInt(parts[1])),
				new ServerConsumable(Integer.parseInt(parts[2]))
		);
		
		sr.setRevisedRating(Integer.parseInt(parts[3]));
		
		return SUCCESS_PREFIX;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		if(response.equals(FAILURE_PREFIX))
		{
			throw new ProtocolException("Server got an invalid message");
		}
	}

	public void setRecommendation(ClientRecommendation recommendation) {
		this.recommendation = recommendation;
	}

	public ClientRecommendation getRecommendation() {
		return recommendation;
	}

}
