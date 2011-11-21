package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Attribute;
import core.AttributeRating;
import core.LoginException;
import core.Recommendation;

import server.Server;
import server.ServerAttribute;
import server.ServerConsumable;
import server.ServerException;
import server.ServerRecommendation;
import server.ServerUser;
import client.Client;
import client.ClientAttribute;
import client.ClientException;
import client.ClientUser;

import database.SQLDatabase;

public class ClientUserWetTests {	
	@Test
	public void idConstructor()
	{
		ClientUser u = new ClientUser(2);
		
		ServerUser u2 = new ServerUser(2);
		
		if(u.getUserId() != u2.getUserId())
		{
			fail("user id's don't match.");
		}
		
		if(!u.getUsername().equals(u2.getUsername()))
		{
			fail("usernames don't match.");
		}
		
		if(!u.getPassword().equals(u2.getPassword()))
		{
			fail("passwords don't match.");
		}
	}

	@Test
	public void nameConstructor()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		
		try{
			ServerUser u = new ServerUser(username,password,true);
			ClientUser u1 = new ClientUser(username,password,false);
			ServerUser u2 = new ServerUser(username,password,false);
			
			if(u1.getUserId() != u.getUserId())
			{
				fail("name constructor doesn't work -- u and u1");
			}
			
			if(u1.getUserId() != u2.getUserId())
			{
				fail("name contructor doesn't work -- u1 and u2");
			}
			
			
		}
		catch(LoginException e)
		{
			e.printStackTrace();
			fail("login exception on valid data.");
		}
	}
	
	@Test
	public void registerAccount()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		try
		{
			ClientUser u1 = new ClientUser(username,password,true);
			ServerUser u2 = new ServerUser(username, password, false);
			
			if(u1.getUserId() <= 0)
			{
				fail("Login failed.");
			}
			
			if(u1.getUserId() != u2.getUserId())
			{
				fail("login not correct user.");
			}
		}
		catch(LoginException e)
		{
			e.printStackTrace();
			fail("Login excpetion");
		}
	}
	
	@Test
	public void setAttribute()
	{
		
		ServerUser u1 = new ServerUser(1);
		ServerAttribute a1 = new ServerAttribute(1);
		
		u1.setAttributeRating(a1, 5);
		
		ClientUser u2 = new ClientUser(1);
		ClientAttribute a2 = new ClientAttribute(1);
		
		u2.setAttributeRating(a2, 1);
		
		if(u1.getAttributeRating(a1) != 1)
		{
			fail("Setting attribute rating failed.");
		}
	}
	
	@Test
	public void getAllRatedAttributes()
	{
		ClientUser u = new ClientUser(1);
		
		List<AttributeRating> ar = u.getAllRatedAttributes();
		
		if(ar == null)
		{
			fail("could not get attribute rating list.");
		}
		
		if(ar.size() == 0)
		{
			fail("got a list but it was empty.");
		}
	}
	
	@Test
	public void getAttributeRating()
	{
		int initial_rating = 5;
		
		ServerUser u1 = new ServerUser(1);
		ServerAttribute a1 = new ServerAttribute(1);		
		u1.setAttributeRating(a1, initial_rating);

		ClientUser u = new ClientUser(1);
		ClientAttribute a = new ClientAttribute(1);
		int rating = u.getAttributeRating(a);
		
		if(rating != initial_rating)
		{
			fail("Rating and Initial Rating failed.");
		}
	}
	
	@Test
	public void getRatedConsumables()
	{
		//Rate 1,1 with 3.
		ServerUser u1 = new ServerUser(1);
		ServerConsumable c1 = new ServerConsumable(1);
		u1.setRecommendationRating(c1, 3);
		ServerRecommendation scr = new ServerRecommendation(u1, c1);
		int initialRating = scr.getInitialRating();
		scr.setRevisedRating(initialRating+1);
		
		
		
		ClientUser u = new ClientUser(1);
		List<Recommendation> testSubject = u.getRatedConsumables();
		
		if(testSubject == null || testSubject.isEmpty())
		{
			fail("testSubject not set properly.");
		}
		
		for(Recommendation cr : testSubject)
		{
			if(cr.getConsumable().getConsumableId() == 1)
			{
				if(cr.getInitialRating() != initialRating)
				{
					fail("incorrect initial rating.");
				}
				if(cr.getRevisedRating() != initialRating + 1)
				{
					fail("incorrect revised rating.");
				}
			}
		}
	}
}
