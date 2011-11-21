package protocol;

import server.PrefixParser;
import server.ServerUser;
import core.LoginException;
import core.User;
import client.ClientUser;

public class RegisterMessage implements ProtocolMessage {
	private ClientUser user;
	
	public static final String PREFIX					= "register";
	public static final String DELIMITER				= PrefixParser.DELIMITER;
	public static final String SUCCESSFUL_REGISTRATION_PREFIX = "true";
	public static final String FAILED_REGISTRATION_PREFIX     = "false";
	
	public RegisterMessage(ClientUser user)
	{
		setUser(user);
	}
	
	public RegisterMessage()
	{
		
	}
	
	@Override
	public String generateMessage() {
		StringBuilder message = new StringBuilder();
		message.append(PREFIX + DELIMITER);
		message.append(user.getUsername());
		message.append(DELIMITER);
		message.append(user.getPassword());
		
		return message.toString();
	}

	@Override
	public String generateResponse(String message) {
		String[] messageParts = message.split(DELIMITER);
		
		ServerUser u;
		try {
			u = new ServerUser(messageParts[1] , messageParts[2] , true);
		}
		catch(LoginException e)
		{
			return FAILED_REGISTRATION_PREFIX;
		}
		
		return SUCCESSFUL_REGISTRATION_PREFIX + PrefixParser.DELIMITER + u.getUserId();
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		if(response.contains(SUCCESSFUL_REGISTRATION_PREFIX))
		{
			String[] messages = response.split(DELIMITER);
			getUser().setUserIdPublic(Integer.parseInt(messages[1]));
		}
		else if(response.contains(FAILED_REGISTRATION_PREFIX))
		{
			getUser().setUserIdPublic(0);
		}
		else
		{
			throw new ProtocolException("Invalid response from server on registration message.");
		}
	}
	

	private void setUser(ClientUser user2) {
		this.user = user2;
	}

	private ClientUser getUser() {
		return user;
	}	
}
