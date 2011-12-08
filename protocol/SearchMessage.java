package protocol;

import java.util.ArrayList;
import java.util.List;

import client.ClientConsumable;

import core.Consumable;

import server.PrefixParser;
import server.ServerConsumable;
import server.ServerUser;

public class SearchMessage implements ProtocolMessage {

	public boolean consumablesOrAttributes;
	public Consumable consumable;
	public String term;
	
	public static final String PREFIX = SearchMessage.class.getCanonicalName();
	
	public SearchMessage() {
	}
	
	public SearchMessage(Consumable c , String term)
	{
		this.term = term;
		this.consumablesOrAttributes = true;
		this.consumable = c;
	}
	
	@Override
	public String generateMessage() {
		String message = PREFIX + PrefixParser.DELIMITER + 
						(consumablesOrAttributes ? 
								"consumable" + PrefixParser.DELIMITER + term : 
								"attribute" + PrefixParser.DELIMITER + term); 
		
		return message;
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		String searchType = parts[1];
		String phrase = parts[2];
		
		if(searchType.equals("consumable"))
		{
			ServerConsumable cons = new ServerConsumable(1);
			List<Consumable> results = cons.search(phrase);
			
			StringBuffer response = new StringBuffer();
			
			for(Consumable c: results)
			{
				response.append(c.getConsumableId());
				response.append(PrefixParser.DELIMITER);
			}
			
			return response.toString();
		}
		else if(searchType.equals("attributes")) 
		{
			System.err.println("unimplemented");
			return "unimplemented.";
		}
		else {
			System.err.println("Could not resolve search type.");
			return "unimplemented.";
			
		}
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		int partCount = PrefixParser.getNumParts(response) - 1;
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		if(this.consumablesOrAttributes == true)
		{
			List<Consumable> searchResults = new ArrayList<Consumable>();
			
			for(int i = 0 ; i < partCount; i++)
			{
				int consumableId = Integer.parseInt(parts[i]);
				
				ClientConsumable c = new ClientConsumable(consumableId);
				searchResults.add(c);
			}
			
			consumable.setSearchResults(searchResults);
		}
	}
	

}
