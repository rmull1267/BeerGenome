package gui;

import java.util.List;

import core.AttributeRating;
import core.Consumable;
import core.Recommendation;

import client.ClientConsumable;
import client.ClientUser;

public class DataAbstraction 
{
	private ClientUser user;
	
	private GUIMainTabbedPane mainPane;
	
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
	
	public List< Consumable > getRecommendedConsumable()
	{
		return user.getRecommendedConsumables(new ClientConsumable(1));	
	}
	
	public void setMainPane(GUIMainTabbedPane mainPane) {
		this.mainPane = mainPane;
	}

	public GUIMainTabbedPane getMainPane() {
		return mainPane;
	}
}
