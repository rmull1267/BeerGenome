package client;

import protocol.GetAllRatedAttributesMessage;
import protocol.GetRatedConsumablesMessage;
import protocol.GetRecommendationsMessage;
import protocol.LoginMessage;
import protocol.ProtocolException;
import protocol.SetAttributeRatingMessage;
import protocol.SetPasswordMessage;
import protocol.RegisterMessage;
import java.util.ArrayList;

import java.util.List;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.LoginException;
import core.Recommendation;
import core.User;

import database.DBAbstractionException;

public class ClientUser extends User {

	public ClientUser(int userId)
	{
		try {
			constructorHelper(userId);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	public ClientUser(String username, String password, Boolean newAccount) throws LoginException
	{
		constructorHelper(username, password, newAccount);
	}
	
	public void setUserIdPublic(int userId)
	{
		setUserId(userId);
	}
	
	@Override
	protected void constructorHelper(String username, String password, Boolean newAccount) throws LoginException {
		this.setUsernameWithoutCommit(username);
		this.setPasswordWithoutCommit(password);
		
		if(newAccount)
		{
			RegisterMessage m = new RegisterMessage(this);
			
			String message = m.generateMessage();

			try
			{
				String response = Client.getInstance().sendMessage(message);
				m.processResponse(response);
			}
			catch(ProtocolException e)
			{
				throw new LoginException(e);
			} catch (ClientException e) {
				throw new LoginException(e);
			}
			
			if(this.getUserId() <= 0)
			{
				throw new LoginException("Could not login after registration.");
			}
		}
		else
		{
			LoginMessage m = new LoginMessage(this);
			
			String message = m.generateMessage();
			
			try
			{
				String response = Client.getInstance().sendMessage(message);
				
				m.processResponse(response);
				
				if(getUserId() == 0)
				{
					throw new LoginException("Could not load from Id");
				}
			}
			catch (ClientException e) {
				throw new LoginException(e);
			} catch (ProtocolException e) {
				throw new LoginException(e);
			}
		}
		
	}

	@Override
	protected void constructorHelper(int userId) throws LoginException {
		setUserId(userId);
		
		LoginMessage m = new LoginMessage(this);
		
		String message = m.generateMessage();
		
		try
		{
			String response = Client.getInstance().sendMessage(message);
			
			m.processResponse(response);
			
			if(getUserId() == 0)
			{
				throw new LoginException("Could not load from Id");
			}
		}
		catch (ClientException e) {
			throw new LoginException(e);
		} catch (ProtocolException e) {
			throw new LoginException(e);
		}
		
	}
	
	//The logic for this is really handled in the constructors.
	@Override
	public Boolean authenticate(String username, String password) {
		if(getUserId() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void setAttributeRating(Attribute attribute, int newRating) {
		SetAttributeRatingMessage m = new SetAttributeRatingMessage(this, attribute, newRating);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setAttributeRating(int attributeId, int newRating) {
		ClientAttribute attribute = new ClientAttribute(attributeId);
		setAttributeRating(attribute, newRating);
	}
	
	@Override
	public List<AttributeRating> getAllRatedAttributes() {
		List<AttributeRating> retVal = new ArrayList<AttributeRating>();
		
		GetAllRatedAttributesMessage m = new GetAllRatedAttributesMessage(this, retVal);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
			return retVal;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public int getAttributeRating(Attribute attribute) {
		List<AttributeRating> ratings = getAllRatedAttributes();
		
		for(AttributeRating ar : ratings)
		{
			if(ar.getAttribute().getAttributeId() == attribute.getAttributeId())
			{
				return ar.getRating();
			}
		}
		
		return -1;
	}

	@Override
	public void setRecommendationRating(Consumable consumable, int newRating) {
		setRecommendationRating(consumable.getConsumableId(), newRating);
		
	}

	//TODO-nf-tests not tested because ClientRecommendation.setRevisedRating needs to be implemented first.
	@Override
	public void setRecommendationRating(int consumableId, int newRating) {
		ClientConsumable consumable = new ClientConsumable(consumableId);
		
		//Work through all of the existing rated consumalbes to see if this is laready rated.
		boolean set = false;
		for(Recommendation r : getRatedConsumables())
		{
			if(r.getConsumable().getConsumableId() == consumableId)
			{
				r.setRevisedRating(newRating);
				set = true;
				break;
			}
		}
		
		//if not already rated, then create a new rating.
		if(!set)
		{
			new ClientRecommendation(this, consumable, newRating);
		}	
		
	}
	
	@Override
	public List<Recommendation> getRatedConsumables() {
		List<Recommendation> retVal = new ArrayList<Recommendation>();
		
		GetRatedConsumablesMessage m = new GetRatedConsumablesMessage(this, retVal);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

	@Override
	public void commit() throws DBAbstractionException {
		SetPasswordMessage m = new SetPasswordMessage(this);
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws DBAbstractionException {
		try
		{
			constructorHelper(this.getUserId());
		}
		catch(LoginException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public List<Consumable> getRecommendedConsumables(Consumable anyConsumable) {
		ArrayList<Consumable> ret = new ArrayList<Consumable>();
		
		GetRecommendationsMessage m = new GetRecommendationsMessage(this,ret);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}
}
