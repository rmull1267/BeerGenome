package gui;

import java.util.List;

import core.AttributeRating;

import client.ClientUser;

public class DataAbstraction 
{
	private ClientUser user;
	
	private DataAbstraction instance;
	
	private DataAbstraction()
	{
		
	}
	
	public DataAbstraction getInstance()
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
}
