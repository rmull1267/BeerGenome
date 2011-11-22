package protocol;

import client.ClientUser;
import server.PrefixParser;
import server.ServerUser;

public class SetPasswordMessage implements ProtocolMessage {

	public static final String PREFIX = SetPasswordMessage.class.getSimpleName();
	public static final String SUCCESS_PREFIX = "succ";
	public static final String FAIL_PREFIX = "fail";
	
	private ClientUser user;
	
	public SetPasswordMessage() {}
	public SetPasswordMessage(ClientUser user)
	{
		setUser(user);
	}
	
	@Override
	public String generateMessage() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(PREFIX);
		sb.append(PrefixParser.DELIMITER);
		sb.append(user.getUserId());
		sb.append(PrefixParser.DELIMITER);
		sb.append(user.getPassword());
		
		return sb.toString();
	}

	@Override
	public String generateResponse(String message) {
		String parts[] = message.split(PrefixParser.DELIMITER);
		
		int userId 		= Integer.parseInt(parts[1]);
		String password = parts[2];
		
		ServerUser u = new ServerUser(userId);
		u.setPassword(password);
		
		return SUCCESS_PREFIX;
	}

	@Override
	public void processResponse(String response) throws ProtocolException {
		
	}
	public void setUser(ClientUser user) {
		this.user = user;
	}
	public ClientUser getUser() {
		return user;
	}
	
}
