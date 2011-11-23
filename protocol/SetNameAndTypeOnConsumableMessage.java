package protocol;

import server.PrefixParser;
import server.ServerConsumable;
import client.ClientConsumable;

public class SetNameAndTypeOnConsumableMessage implements ProtocolMessage {
	public static final String PREFIX = SetNameAndTypeOnConsumableMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAILURE_PREFIX = "fail";
	
	private ClientConsumable consumable;

	public SetNameAndTypeOnConsumableMessage(ClientConsumable consumable)
	{
		setConsumable(consumable);
	}
	public SetNameAndTypeOnConsumableMessage() {}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(consumable.getConsumableId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(consumable.getName());
		sb.append(PrefixParser.DELIMITER);
		sb.append(consumable.getType());
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		int consumableId = Integer.parseInt(parts[1]);
		String newName = parts[2];
		String newType = parts[3];
		
		ServerConsumable sc = new ServerConsumable(consumableId);
		
		sc.setNameWithoutCommit(newName);
		sc.setTypeWithoutCommit(newType);
		sc.commit();
		
		return SUCCESS_PREFIX;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
	}

	public void setConsumable(ClientConsumable consumable) {
		this.consumable = consumable;
	}

	public ClientConsumable getConsumable() {
		return consumable;
	}
	
}
