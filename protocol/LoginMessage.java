package protocol;

import client.ClientUser;	//JRC: Used so we can store a ClientUser variable locally
import server.PrefixParser;	//JRC: Used to access the static DELIMITER, getNumParts()
import server.ServerUser;	//JRC: Used to log in to the server
import core.LoginException;	//JRC: Exception thrown during login process
//import core.User;			//JRC: This is marked by the compiler as unused.  Commenting out until it is needed.

public class LoginMessage implements ProtocolMessage 
{
	private ClientUser user;
	
	public static final String PREFIX = "login";
	public static final String SUCCESS_PREFIX = "success";
	public static final String FAIL_PREFIX = "failure";
	
	/**
	 * JRC: Creates an instance of LoginMessage and sets its ClientUser to user
	 * @param user - An instance of ClientUser passed in by the user of this class
	 */
	public LoginMessage(ClientUser user)
	{
		setUser(user);
	}
	
	/*
	 * JRC: If we change setUser(ClientUser) to private, it would be wise to remove the default constructor.
	 * This is something we can discuss in greater detail another time.
	 */
	/**
	 * JRC: Creates an instance of LoginMessage and sets no data members.
	 */
	public LoginMessage()
	{
		
	}
	
	/**
	 * JRC: This will generate a message of the format "PREFIX:USERNAME:PASSWORD" where PREFIX indicates the
	 * type of this request (login), and USERNAME and PASSWORD are data members of this class' ClientUser.
	 * The colon (:) in this example is a standin for PrefixParser.DELIMITER.
	 * The String returned will either contain the username and password of an extant user or it will
	 * contain the user ID of a newly created user.
	 * @return A message to send to the server
	 */
	@Override
	public String generateMessage() 
	{
		//JRC: If the user already exists, return a String of the format "PREFIX:USERNAME:PASSWORD"
		if(user.getUsername() != null)
		{
			return PREFIX + PrefixParser.DELIMITER + user.getUsername() + PrefixParser.DELIMITER + 
					user.getPassword();
		}
		//JRC: If the user does not yet exist, return a String of the format "PREFIX:USERID"
		else
		{
			return PREFIX + PrefixParser.DELIMITER + new Integer(user.getUserId()).toString();
		}
	}
	
	/**
	 * JRC: This will send the message created by generateMessage() to the server
	 * @return message - A String indicating the success or failure of the login attempt
	 */
	@Override
	public String generateResponse(String message)
	{
		String response = ""; //JRC: The response is initially empty but not null
		
		int numParts = PrefixParser.getNumParts(message); //JRC: This is the number of parts of message as separated by PrefixParser.DELIMITER
		String[] parts = message.split(PrefixParser.DELIMITER); //JRC: These are the actual parts of message
		
		if(numParts == 2)
		{
			/*
			 * JRC: If the message has 2 parts, they are LoginMessage.PREFIX and user.getUserId().toString()
			 * Create a new ServerUser by passing in parts[1], the UserID of user
			 */
			ServerUser u = new ServerUser(Integer.parseInt(parts[1]));
			/*
			 * JRC: Response is "success:userID:username:password" to indicate that the user has successfully logged in
			 */
			response = SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(u.getUserId()).toString() +
						PrefixParser.DELIMITER + u.getUsername() + PrefixParser.DELIMITER + u.getPassword();
		}
		else
		{
			/*
			 * JRC: If the message has a number of parts other than 2, they should be
			 * LoginMessage.PREFIX, user.getUsername(), and user.getPassword()
			 * Attempt to create a new ServerUser by passing in the username, password and false to
			 * the ServerUser constructor.  The false indicates that the user identity is not new.
			 * If this succeeds, response is "success:userID:username:password".
			 * If this fails, response is "failure".
			 */
			ServerUser u;
			try
			{
				u = new ServerUser(parts[1], parts[2], false);
				response = SUCCESS_PREFIX + PrefixParser.DELIMITER + new Integer(u.getUserId()).toString() + 
				   PrefixParser.DELIMITER + u.getUsername() + PrefixParser.DELIMITER +u.getPassword();
				
			} 
			catch (LoginException e)
			{
				response = FAIL_PREFIX;
			}
		}
		
		return response;
	}

	/**
	 * JRC: This takes the response from generateReesponse(String) and takes some action
	 * based on whether the response indicates a failure to login or a success.
	 * If there was a failure, set the userID to 0.
	 * If there was a success, set the userID, username, and password for user to the
	 * userID, username, and password contained in response.
	 * @param response - The response created by generateResponse(String)
	 * @throws ProtocolException
	 */
	@Override
	public void processResponse(String response) throws ProtocolException
	{
		/*
		 * JRC: Split the response on PrefixParser.DELIMITER
		 */
		String[] parts = response.split(PrefixParser.DELIMITER);
		
		/*
		 * JRC: If the response indicate that login failed, set user.userID to 0
		 */
		if(parts[0].equals(FAIL_PREFIX))
		{
			user.setUserIdPublic(0);
		}
		/*
		 * JRC: If the response indicates success, set the user data members to those indicated by the response
		 */
		else {
			getUser().setUserIdPublic(Integer.parseInt(parts[1]));
			getUser().setUsernameWithoutCommit(parts[2]);
			getUser().setPasswordWithoutCommit(parts[3]);
		}
		
	}

	/*
	 * JRC: Is this something we really want to leave public?
	 * I don't know the other possible uses for this method call but, on first inspection,
	 * it seems like it would be dangerous to leave this method public.
	 */
	//JRC: Sets the private ClientUser member of this class equal to the passed in ClientUser variable
	public void setUser(ClientUser user)
	{
		this.user = user;
	}
	//JRC: Returns the private ClientUser member of this class
	public ClientUser getUser()
	{
		return user;
	}

}
