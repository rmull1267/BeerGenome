package protocol;

import java.util.ArrayList;

import client.ClientConsumable;
import client.ClientUser;

import server.PrefixParser;
import server.ServerConsumable;
import server.ServerUser;

import core.Consumable;

public class GetRecommendationsMessage implements ProtocolMessage {
	public static final String PREFIX = GetRecommendationsMessage.class.getCanonicalName();
	
	private ArrayList<Consumable> result;
	private ClientUser user;
	
	
	public GetRecommendationsMessage()
	{
		
	}
	
	public GetRecommendationsMessage(ClientUser u , ArrayList<Consumable> result)
	{
		setResult(result);
		setUser(u);
	}
	
	@Override
	public String generateMessage() {
		StringBuffer message = new StringBuffer();
		message.append(PREFIX + PrefixParser.DELIMITER);
		message.append(getUser().getUserId());
		return message.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		int userId = Integer.parseInt(parts[1]);
		
		ServerUser u = new ServerUser(userId);
		
		ServerConsumable co = new ServerConsumable(1);
		
		StringBuilder ret = new StringBuilder();
		for(Consumable c : u.getRecommendedConsumables(co))
		{
			ret.append(c.getConsumableId());
			ret.append(PrefixParser.DELIMITER);
		}
		
		return ret.toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		int num = PrefixParser.getNumParts(response)  -1 ;
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		for(int i = 0 ; i < num ; i++)
		{
			ClientConsumable c = new ClientConsumable(Integer.parseInt(parts[i]));
			this.getResult().add(c);
		}
	}

	public void setResult(ArrayList<Consumable> result) {
		this.result = result;
	}

	public ArrayList<Consumable> getResult() {
		return result;
	}

	public void setUser(ClientUser user) {
		this.user = user;
	}

	public ClientUser getUser() {
		return user;
	}

}
