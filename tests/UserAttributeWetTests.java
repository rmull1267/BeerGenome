package tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerUser;

import core.AttributeRating;
import core.LoginException;
import database.SQLDatabase;
import database.SQLUserAttribute;



public class UserAttributeWetTests {

	@Before
	public void setUp() throws Exception {
		SQLDatabase.getInstance("test.db");
	}

	@After
	public void tearDown() throws Exception {
		SQLDatabase.getInstance().close();
	}
	
	@Test
	public void setAttributeRating()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser user = null;
		try {
			user = new ServerUser(username, password, true);
		} catch (LoginException e) {
			fail("Login failure on account creation.");
		}
		
		user.setAttributeRating(1, 1);
		
		Boolean found = false;
		for(AttributeRating item : user.getAllRatedAttributes())
		{
			if(found == true && item.getAttribute().getAttributeId() == 1)
			{
				fail("Multiple ratings for the same attribute.");
			}
			if(item.getAttribute().getAttributeId() == 1 && item.getRating() == 1)
			{
				found = true;
			}
		}
		
		if(found == false) fail("Did not find an attribute after a set.");
		
		user.setAttributeRating(1, 2);
		
		found = false;
		for(AttributeRating item : user.getAllRatedAttributes())
		{
			if(found == true && item.getAttribute().getAttributeId() == 1)
			{
				fail("Multiple ratings for the same attribute.");
			}
			if(item.getAttribute().getAttributeId() == 1 && item.getRating() == 2)
			{
				found = true;
			}
		}
		
		if(found == false) fail("Did not find an attribute after a set.");		
	}
}
