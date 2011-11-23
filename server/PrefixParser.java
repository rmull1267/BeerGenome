package server;

import protocol.CreateAttributeMessage;
import protocol.CreateConsumableMessage;
import protocol.GetAllAttributesMessage;
import protocol.GetAttributeMessage;
import protocol.GetConsumableMessage;
import protocol.GetRatedConsumablesMessage;
import protocol.CreateRecommendationMessage;
import protocol.GetRecommendationMessage;
import protocol.ProtocolException;
import protocol.GetAllRatedAttributesMessage;
import protocol.LoginMessage;
import protocol.RegisterMessage;
import protocol.RenameAttributeMessage;
import protocol.SetAttributeRatingMessage;
import protocol.SetPasswordMessage;
import protocol.SetRecommendationsRevisedRatingMessage;
import protocol.StopMessage;

/**
 * TODO-nf-refactor instead of using .PREFIX use .class.getSimpleName()
 * @author nfulton
 *
 */
public class PrefixParser {
	//SHOULD BE A SINGLE CHARACTER IF getNumParts is to work.
	public static final String DELIMITER = ":";
	
	public static int getNumParts(String message)
	{
		int retVal = 1;
		for(int i = 0 ; i < message.length(); i++)
		{
			if(message.charAt(i) == DELIMITER.charAt(0))
			{
				retVal++;
			}
		}
		
		return retVal;
	}
	
	private static PrefixParser instance;
	public static PrefixParser getinstance()
	{
		if(instance == null)
		{
			instance = new PrefixParser();
		}
		return instance;
	}
	private PrefixParser() {}
	
	
	
	public String getPrefix(String request)
	{
		String[] parts = request.split(DELIMITER);
		return parts[0];
	}
	
	//TODO-nf-refactor replace with state-strategy pattern?
	public String getResponse(String request) throws ProtocolException
	{
		//USER MESSAGES
		if(getPrefix(request).equals(LoginMessage.PREFIX))
		{
			LoginMessage m = new LoginMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(RegisterMessage.PREFIX))
		{
			RegisterMessage m = new RegisterMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(SetAttributeRatingMessage.PREFIX))
		{
			SetAttributeRatingMessage m = new SetAttributeRatingMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(GetAllRatedAttributesMessage.PREFIX))
		{
			GetAllRatedAttributesMessage m = new GetAllRatedAttributesMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(GetRatedConsumablesMessage.PREFIX))
		{
			GetRatedConsumablesMessage m = new GetRatedConsumablesMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(SetPasswordMessage.PREFIX))
		{
			SetPasswordMessage m = new SetPasswordMessage();
			return m.generateResponse(request);
		}
		
		//ATTRIBUTE MESSAGES
		else if(getPrefix(request).equals(GetAttributeMessage.PREFIX))
		{
			GetAttributeMessage m = new GetAttributeMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(CreateAttributeMessage.PREFIX))
		{
			CreateAttributeMessage m = new CreateAttributeMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(GetAllAttributesMessage.PREFIX))
		{
			GetAllAttributesMessage m = new GetAllAttributesMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(RenameAttributeMessage.PREFIX))
		{
			RenameAttributeMessage m = new RenameAttributeMessage();
			return m.generateResponse(request);
		}
		
		
		//RECOMMENDATION MESSAGES
		else if(getPrefix(request).equals(CreateRecommendationMessage.PREFIX))
		{
			CreateRecommendationMessage m = new CreateRecommendationMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(GetRecommendationMessage.PREFIX))
		{
			GetRecommendationMessage m = new GetRecommendationMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(SetRecommendationsRevisedRatingMessage.PREFIX))
		{
			SetRecommendationsRevisedRatingMessage m = 
				new SetRecommendationsRevisedRatingMessage();
			
			return m.generateResponse(request);
		}
		
		/*
		 * JRC:
		 * CONSUMABLE MESSAGES
		 */
		else if(getPrefix(request).equals(CreateConsumableMessage.PREFIX))
		{
			CreateConsumableMessage m = new CreateConsumableMessage();
			return m.generateResponse(request);
		}
		else if(getPrefix(request).equals(GetConsumableMessage.PREFIX))
		{
			GetConsumableMessage m = new GetConsumableMessage();
			return m.generateResponse(request);
		}
		
		//STOP MESSAGE
		else if(getPrefix(request).equals(StopMessage.PREFIX))
		{
			//Stop message.
			return "";
		}
		else
		{
			throw new ProtocolException("Invalid Prefix:" + request);
		}
	}
	
}
