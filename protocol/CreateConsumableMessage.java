package protocol;

import server.PrefixParser;
import server.ServerConsumable;
import client.ClientConsumable;

public class CreateConsumableMessage implements ProtocolMessage
{
	public static final String PREFIX = CreateConsumableMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "success";
	public static final String FAIL_PREFIX = "fail";
	
	private ClientConsumable consumable;
	
	public CreateConsumableMessage(ClientConsumable consumable)
	{
		setConsumable(consumable);
	}
	
	public CreateConsumableMessage()
	{
		
	}

	@Override
	public String generateMessage()
	{
		StringBuilder message = new StringBuilder();
		message.append(PREFIX);
		message.append(PrefixParser.DELIMITER);
		message.append(getConsumable().getName());
		message.append(PrefixParser.DELIMITER);
		message.append(getConsumable().getType());
		return message.toString();
	}

	@Override
	public String generateResponse(String message)
	{
		String tokens[] = message.split(PrefixParser.DELIMITER);
		ServerConsumable sc = new ServerConsumable(tokens[1], tokens[2]);
		return SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(sc.getConsumableId()).toString();
	}

	@Override
	public void processResponse(String response) throws ProtocolException
	{
		String tokens[] = response.split(PrefixParser.DELIMITER);
		getConsumable().setID(Integer.parseInt(tokens[1]));
	}

	private ClientConsumable getConsumable()
	{
		return consumable;
	}
	private void setConsumable(ClientConsumable consumable)
	{
		this.consumable = consumable;
	}
}
