package tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerAttribute;
import server.ServerConsumable;
import server.ServerUser;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.LoginException;
import core.Recommendation;
import core.User;
import database.SQLDatabase;



public class ServerUserWetTests {

	@Before
	public void setUp() throws Exception {
		SQLDatabase.getInstance("test.db");
	}

	@After
	public void tearDown() throws Exception {
		SQLDatabase.getInstance().close();
	}

	@Test
	public void testAddConstructor()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser u1 = null;
		
		try {
			u1 = new ServerUser(username, password, true);
		} catch (LoginException e) {
			fail("Login exception when registering new account.");
			e.printStackTrace();
		}
		
		if(u1.getUserId() == 0)
		{
			fail("could not get a valid user id.");
		}
		
		if(!u1.getUsername().equals(username))
		{
			fail("Wrong username at registration.");
		}
		
		if(!u1.getPassword().equals(password))
		{
			fail("Wrong password at registration.");
		}
	}
	
	@Test
	public void authenticate()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser u1 = null;
		
		try {
			u1 = new ServerUser(username, password, true);
			u1 = new ServerUser(username, password, false);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		if(u1.authenticate(username, password) != true)
		{
			fail("Authenticate Failed.");
		}
	}
	
	@Test
	public void setTests() throws LoginException
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser u1 = null;
		
		u1 = new ServerUser(username, password, true);
		
		u1.setPassword("password");
		u1 = new ServerUser(u1.getUserId());	
		if(!u1.getPassword().equals("password"))
		{
			fail("Password set failed.");
		}
		
		u1.setUsername(password + "1");
		
		u1 = new ServerUser(u1.getUserId());
		if(!u1.getUsername().equals(password + "1"))
		{
			fail("Username set failed.");
		}
	}
	
	@Test
	public void setAttributeRating()
	{
		User u = new ServerUser(1);
		Attribute a = new ServerAttribute(1);
		
		u.setAttributeRating(a, 1);
		if(u.getAttributeRating(a) != 1)
		{
			fail("Initial set to 1 failed.");
		}
		
		//we need to set again just in case the initial set was setting to the original value.
		u.setAttributeRating(a, 2);
		if(u.getAttributeRating(a) != 2)
		{
			fail("Set to 2 failed.");
		}
	}
	
	
	@Test
	public void getRecommendations()
	{
		User u = new ServerUser(1);
		Consumable c = new ServerConsumable(1);
		
		u.getRecommendedConsumables(c);
		
	}
	@Test
	public void setRecommendationRating()
	{
		User u = new ServerUser(1);
		Consumable c = new ServerConsumable("name", "type");
		
		u.setRecommendationRating(c, 3);
		
		boolean found = false;
		User u1 = new ServerUser(1);
		for(Recommendation r : u1.getRatedConsumables())
		{
			if(r.getConsumable().equals(c))
			{
				found = true;
				if(r.getRevisedRating() != 3)
				{
					fail("set recommended rating failed.");
				}
			}
		}
		if(!found)
		{
			fail("created new rating failed."); 
		}
		
		u1.setRecommendationRating(c.getConsumableId(), 4);
		
		found = false;
		User u2 = new ServerUser(1);
		for(Recommendation r : u2.getRatedConsumables())
		{
			if(r.getConsumable().equals(c))
			{
				found = true;
				if(r.getRevisedRating() != 4)
				{
					fail("set recommended rating failed.");
				}
			}
		}
		if(!found)
		{
			fail("created new rating failed."); 
		}
		
	}
}
