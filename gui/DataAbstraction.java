package gui;

import java.util.List;

import core.AttributeRating;
import core.Recommendation;

import client.ClientUser;

public class DataAbstraction 
{
	private ClientUser user;
	
	private static DataAbstraction instance;
	
	private DataAbstraction()
	{
		
	}
	
	public static DataAbstraction getInstance()
	{
		if(instance == null)
		{
			instance = new DataAbstraction();
		}
		return instance;
	}
	
	//User Information
	public ClientUser getUser()
	{
		return user;
	}
	
	public void setUser(ClientUser user)
	{
		this.user = user;
	}
	
	//Attribute Information
	public List< AttributeRating > getUserRatedAttributes()
	{
		return user.getAllRatedAttributes();
	}
	
	public AttributeRating getAttributeAtIndex(int index)
	{
		return getUserRatedAttributes().get(index);
	}
	
	public List< Recommendation > getUserRatedConsumables()
	{
		return user.getRatedConsumables();
	}
}
