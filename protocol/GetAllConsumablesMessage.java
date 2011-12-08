package protocol;

import java.util.List;

import core.Consumable;

import server.PrefixParser;
import server.ServerConsumable;
import client.ClientConsumable;

public class GetAllConsumablesMessage implements ProtocolMessage  {
	public static final String PREFIX = GetAllConsumablesMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private List<Consumable> consumables;
	
	public GetAllConsumablesMessage(List<Consumable> c)
	{
		setConsumables(c);
	}
	public GetAllConsumablesMessage() {}
	
	@Override
	public String generateMessage() {
		return PREFIX;
	}

	@Override
	public String generateResponse(String message) {
		StringBuilder sb = new StringBuilder();
		
		ServerConsumable sc = new ServerConsumable(1);
		
		sb.append(SUCCESS_PREFIX + PrefixParser.DELIMITER);
		for(Consumable c : sc.getAllConsumables())
		{
			sb.append(c.getConsumableId());
			sb.append(PrefixParser.DELIMITER);
			sb.append(c.getName());
			sb.append(PrefixParser.DELIMITER);
			sb.append(c.getType());
			sb.append(PrefixParser.DELIMITER);
		}
		
		return sb.toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		int numParts = PrefixParser.getNumParts(response);
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		int consumables = 0;
		for(int i = 1 ; i < numParts-1 ; i += 3)
		{
			//if(++consumables > 20)
			//{
			//	break;
			//}
			int consumableId = Integer.parseInt(parts[i]);
			String consumableName = parts[i+1];
			String consumableType = parts[i+2];
			
			//TODO-nf sending this stuff in the first place is unnecessary.
			ClientConsumable c = new ClientConsumable(consumableId);
			c.setNameWithoutCommit(consumableName);
			c.setTypeWithoutCommit(consumableType);
			getConsumables().add(c);
		}
	}

	public void setConsumables(List<Consumable> consumables) {
		this.consumables = consumables;
	}
	public List<Consumable> getConsumables() {
		return consumables;
	}

}
