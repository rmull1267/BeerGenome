package server;

import java.util.ArrayList;
import java.util.List;

import client.ClientConsumable;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.LoginException;
import core.Rating;
import core.Recommendation;
import core.User;
import database.DBAbstraction;
import database.DBAbstractionException;
import database.SQLDatabase;
import database.SQLUserAttribute;

public class ServerUser extends User {
	private DBAbstraction database;
	
	public ServerUser(int userId) {
		constructorHelper(userId);
	}
	
	@Override
	protected void constructorHelper(int userId) {
		setUserId(userId);
		try {
			refresh();
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param newAccount
	 * @throws Exception LoginException
	 */
	public ServerUser(String username, String password, Boolean newAccount) throws LoginException
	{
		constructorHelper(username, password, newAccount);
	}
	
	@Override
	protected void constructorHelper(String username, String password, Boolean newAccount) throws LoginException {
		if(newAccount)
		{
			try {
				setUserId(getDatabase().addUser(username, password));
				refresh();
			} catch (DBAbstractionException e) {
				throw new LoginException(e);
			}
		}
		else
		{
			try {
				setUserId(getDatabase().getIdFromUsername(username));
				refresh();
			} catch (DBAbstractionException e) {
				throw new LoginException(e);
			}
			
			if(!authenticate(username, password))
			{
				setUserId(0);
				throw new LoginException("Invalid username/password.");
			}
		}
	}
	
	
	@Override
	public Boolean authenticate(String username, String password) {
		try
		{
			if(getUserId() == 0)
			{
				return false;
			}
			if(username.equals(getDatabase().getUsername(getUserId())) &&
					password.equals(getDatabase().getPassword(getUserId())))
			{
				return true;
			}
		}
		catch(DBAbstractionException e)
		{
			return false;
		}
		return false;
	}

	@Override
	public void commit() throws DBAbstractionException {
		getDatabase().setUsername(getUserId(), getUsername());
		getDatabase().setPassword(getUserId(), getPassword());
	}

	@Override
	public void refresh() throws DBAbstractionException {
		setUsernameWithoutCommit(getDatabase().getUsername(getUserId()));
		setPasswordWithoutCommit(getDatabase().getPassword(getUserId()));
	}

	public void setDatabase(DBAbstraction database) {
		this.database = database;
	}

	public DBAbstraction getDatabase() {
		if(database == null)
		{
			database = SQLDatabase.getInstance();
		}
		return database;
	}

	@Override
	public void setAttributeRating(Attribute attribute, int newRating) {
		setAttributeRating(attribute.getAttributeId(), newRating);
	}
	
	@Override
	public int getAttributeRating(Attribute attribute)
	{
		for(AttributeRating r : this.getAllRatedAttributes())
		{
			if(r.getAttribute().getAttributeId() == attribute.getAttributeId())
			{
				return r.getRating();
			}
		}
		
		return -1;
	}

	@Override
	public void setAttributeRating(int attributeId, int newRating) {
		for(AttributeRating a : this.getAllRatedAttributes())
		{
			if(a.getAttribute().getAttributeId() == attributeId)
			{
				try {
					SQLUserAttribute.getInstance().editAtrributeRating(getUserId(), attributeId, newRating);
				} catch (DBAbstractionException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		
		try {
			SQLUserAttribute.getInstance().addAtrributeRating(getUserId(), attributeId, newRating);
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<AttributeRating> getAllRatedAttributes() {
		try {
			return SQLDatabase.getInstance().getAllRatedAttributes(getUserId());
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
		
		List<AttributeRating> list = new ArrayList<AttributeRating>();
		return list;
	}
	
	@Override
	public List<Recommendation> getRatedConsumables() {
		try {
			return SQLDatabase.getInstance().getAllConsumablesRated(getUserId());
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Recommendation>();
	}

	@Override
	public List<Consumable> getRecommendedConsumables(Consumable anyConsumable)
	{
		List<Consumable> allConsumables = anyConsumable.getAllConsumables();
		
		//Remove all rated consumables from allConsumables
		List<Recommendation> ratedConsumables = this.getRatedConsumables();
		
		ArrayList<Consumable> toRemove = new ArrayList<Consumable>();
		
		int size =allConsumables.size();
		for(int i = 0 ; i < size; i++)
		{
			for(Recommendation r : ratedConsumables)
			{
				if(r.getConsumable().getConsumableId() == allConsumables.get(i).getConsumableId())
				{
					toRemove.add(allConsumables.get(i));
				}
			}
		}
		allConsumables.removeAll(toRemove);
		
		
		List<Rating> ratings = new ArrayList<Rating>();
		int count = 0;
		for(Consumable c : allConsumables)
		{
			if(count++ > 20)
			{
				break;
			}
			Rating r = new Rating(this, c);
			ratings.add(r);
		}
		
		ratings = quickSort(ratings);
		
		List<Consumable> returnValue = new ArrayList<Consumable>();
		for(int i = 0 ; i < ratings.size(); i++)
		{
			Rating r = ratings.get(i);
			returnValue.add(r.getConsumable());
		}
		
		System.err.println(ratings.size());
		System.err.println(returnValue.size());
		
		return returnValue;
	}
	
	private List<Rating> quickSort(List<Rating> list)
	{
		if(list.size() <= 1)
		{
			return list;
		}
		
		Rating pivot = list.get(list.size() / 2);
		list.remove(pivot);
		
		List<Rating> less = new ArrayList<Rating>();
		List<Rating> more = new ArrayList<Rating>();
		
		int pivotInitialRating = pivot.getInitialRating();
		for(Rating r : list)
		{
			if(r.getInitialRating() <= pivotInitialRating)
			{
				less.add(r);
			}
			else {
				more.add(r);
			}
		}
		
		list = null; //free up some space.
		less = quickSort(less);
		more = quickSort(more);
		more.add(pivot);
		more.addAll(less);
		
		return more;
	}

	@Override
	public void setRecommendationRating(Consumable consumable, int newRating) {
		setRecommendationRating(consumable.getConsumableId(), newRating);
	}

	@Override
	public void setRecommendationRating(int consumableId, int newRating) {
		Consumable consumable = new ServerConsumable(consumableId);
		
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
		
		if(!set)
		{
			new ServerRecommendation(this, consumable, newRating);
		}		
	}
}
