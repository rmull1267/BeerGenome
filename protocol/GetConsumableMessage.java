package protocol;

import server.PrefixParser;
import server.ServerConsumable;
import client.ClientConsumable;

/**
 * @author Jason
 */

public class GetConsumableMessage implements ProtocolMessage
{
	private ClientConsumable consumable;
	
	public static final String PREFIX = "getconsumable";
	public static final String SUCCESS_PREFIX = "success";
	public static final String FAIL_PREFIX = "fail";
	
	public GetConsumableMessage(ClientConsumable c)
	{
		setConsumable(c);
	}
	public GetConsumableMessage()
	{
		
	}

	/**
	 * @author Jason
	 */
	@Override
	public String generateMessage()
	{
		StringBuilder message = new StringBuilder();
		message.append(PREFIX);
		message.append(PrefixParser.DELIMITER);
		message.append(getConsumable().getConsumableId());
		return message.toString();
	}

	/**
	 * @author Jason
	 */
	@Override
	public String generateResponse(String message)
	{
		String tokens[] = message.split(PrefixParser.DELIMITER);
		ServerConsumable sc = new ServerConsumable(Integer.parseInt(tokens[1]));
		return SUCCESS_PREFIX + PrefixParser.DELIMITER + sc.getName() + PrefixParser.DELIMITER + sc.getType();
	}

	/**
	 * @author Jason
	 */
	@Override
	public void processResponse(String response) throws ProtocolException
	{
		if(response.contains(SUCCESS_PREFIX))
		{
			String tokens[] = response.split(PrefixParser.DELIMITER);
			getConsumable().setNameWithoutCommit(tokens[1]);
			getConsumable().setTypeWithoutCommit(tokens[2]);
		}
		else
		{
			throw new ProtocolException("Could not load name or type.");
		}
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
