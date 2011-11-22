package protocol;

import client.ClientUser;
import server.PrefixParser;
import server.ServerUser;
import core.LoginException;
import core.User;

public class LoginMessage implements ProtocolMessage {

	private ClientUser user;
	
	public static final String PREFIX = "login";
	public static final String SUCCESS_PREFIX = "success";
	public static final String FAIL_PREFIX = "failure";
	
	public LoginMessage(ClientUser user)
	{
		setUser(user);
	}
	
	public LoginMessage()
	{
		
	}
	
	@Override
	public String generateMessage() {
		if(user.getUsername() != null)
		{
			return PREFIX + PrefixParser.DELIMITER + user.getUsername() + PrefixParser.DELIMITER + 
					user.getPassword();
		}
		else
		{
			return PREFIX + PrefixParser.DELIMITER + new Integer(user.getUserId()).toString();
		}
	}
	
	@Override
	public String generateResponse(String message) {
		String response = "";
		
		int numParts = PrefixParser.getNumParts(message);
		String[] parts = message.split(PrefixParser.DELIMITER);
		
		if(numParts == 2)
		{
			ServerUser u = new ServerUser(Integer.parseInt(parts[1]));
			response = SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(u.getUserId()).toString() +
						PrefixParser.DELIMITER + u.getUsername() + PrefixParser.DELIMITER + u.getPassword();
		}
		else
		{
			ServerUser u;
			try {
				u = new ServerUser(parts[1], parts[2], false);
				response = SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(u.getUserId()).toString() + 
				   PrefixParser.DELIMITER + u.getUsername() + PrefixParser.DELIMITER +u.getPassword();
				
			} catch (LoginException e) {
				response = FAIL_PREFIX;
			}
		}
		
		return response;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		if(parts[0].equals(FAIL_PREFIX))
		{
			user.setUserIdPublic(0);
		}
		else {
			getUser().setUserIdPublic(Integer.parseInt(parts[1]));
			getUser().setUsernameWithoutCommit(parts[2]);
			getUser().setPasswordWithoutCommit(parts[3]);
		}
		
	}

	public void setUser(ClientUser user) {
		this.user = user;
	}

	public ClientUser getUser() {
		return user;
	}

}
