package tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerConsumable;
import server.ServerRecommendation;
import server.ServerUser;

import database.SQLDatabase;

/**
 * Note: These tests are also partially covered by the ServerUserWetTests.
 * @author nfulton
 *
 */
public class ServerRecommendationWetTests {
	@Before
	public void setUp() throws Exception {
		SQLDatabase.getInstance("test.db");
	}

	@After
	public void tearDown() throws Exception {
		SQLDatabase.getInstance().close();
	}

	@Test
	public void testConstructors()
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser u = null;
		try
		{
			u = new ServerUser(username, password, true);
		}
		catch (core.LoginException e) {
			e.printStackTrace();
			fail("Login exception on registration.");
		}
		
		ServerConsumable c = new ServerConsumable(1);
		
		ServerRecommendation r = new ServerRecommendation(u,c,5);
		
		ServerRecommendation r2 = new ServerRecommendation(u,c);
		if(!r2.getConsumable().equals(c))
		{
			fail("Consumable doesn't match.");
		}
		
		if(!r2.getUser().equals(u))
		{
			fail("User doesn't match.");
		}
		
		if(!(r2.getInitialRating() == r.getInitialRating()))
		{
			fail("Initial rating doesn't match.");
		}
		
		if(!(r2.getRevisedRating() == r.getRevisedRating()))
		{
			fail("Revised rating doesn't match.");
		}
	}
	
	@Test
	public void testSetRevised()
	{
		int initialRating = 5;
		int revisedRating = 3;
		
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		
		ServerUser u = null;
		try
		{
			u = new ServerUser(username, password, true);
		}
		catch (core.LoginException e) {
			e.printStackTrace();
			fail("Login exception on registration.");
		}
		
		ServerConsumable c = new ServerConsumable(1);
		
		ServerRecommendation r = new ServerRecommendation(u,c,initialRating);
		
		r.setRevisedRating(revisedRating);
		
		ServerRecommendation r2 = new ServerRecommendation(u,c);
		
		if(r2.getRevisedRating() != revisedRating)
		{
			fail("setRevisedRating doesn't work.");
		}
	}
}
