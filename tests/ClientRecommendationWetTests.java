package tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import client.ClientConsumable;
import client.ClientRecommendation;
import client.ClientUser;

import server.ServerConsumable;
import server.ServerRecommendation;
import server.ServerUser;


public class ClientRecommendationWetTests {
	static String consumableName = UUID.randomUUID().toString();
	static ServerUser create_u = new ServerUser(1);
	static ServerConsumable create_c = new ServerConsumable(consumableName, "type");
	
	
	
	static ClientUser create_cu = new ClientUser(create_u.getUserId());
	static ClientConsumable create_cc = new ClientConsumable(create_c.getConsumableId());
	static ClientRecommendation create_r = new ClientRecommendation(create_cu, create_cc, 5);
	
	static ServerRecommendation create_sr = new ServerRecommendation(create_u,create_c);
	
	@Test
	public void dbInsertion()
	{
		if(create_sr.getUser() == null)
		{
			fail("DBAbstractionException in db load");
		}
		if(create_r.getUser().getUserId() == 0)
		{
			fail("DBAbstractionException in db insertion");
		}
	}
	@Test
	public void createConsumabeMatch()
	{
		
		if(create_sr.getConsumable().getConsumableId() != create_r.getConsumable().getConsumableId())
			fail("Consumables don't match.");
	}
	@Test
	public void createUserMatch()
	{
		if(create_sr.getUser().getUserId() != create_r.getUser().getUserId())
			fail("users don't match");
	}
	@Test
	public void createInitialRatingMatch()
	{
		if(create_sr.getInitialRating() != create_r.getInitialRating())
			fail("initial ratings don't match.");
	}
	@Test
	public void createRatingMatch()
	{
		if(create_sr.getRevisedRating() != create_r.getRevisedRating())
		{
			StringBuilder message = new StringBuilder();
			message.append("Revised ratings don't match.");
			message.append(create_sr.getRevisedRating());
			message.append("!=");
			message.append(create_r.getRevisedRating());
			
			fail(message.toString());
		}
	}
	@Test
	public void createUserIdCorrect()
	{
		if(create_sr.getUser().getUserId() != create_cu.getUserId())
			fail("user id isn't correct.");
	}
	@Test
	public void createConsumableIdCorrect()
	{
		if(create_sr.getConsumable().getConsumableId() != create_cc.getConsumableId())
			fail("consumable id isn't correct");
	}
	@Test
	public void revisedRatingCorrect()
	{
		if(create_sr.getRevisedRating() != 5)
			fail("revised rating isn't correct.");
	}
	
	@Test
	public void loadConstructor()
	{
		ClientRecommendation load_r = new ClientRecommendation(create_cu, create_cc);
		
		if(load_r.getInitialRating() != create_sr.getInitialRating())
		{
			fail("initial rating not set correct");
		}
		if(load_r.getRevisedRating() != create_sr.getRevisedRating())
		{
			fail("revised rating not set correct");
		}
	}
	
	@Test
	public void commit()
	{
		String name = UUID.randomUUID().toString();
		
		ClientUser c_user = new ClientUser(create_u.getUserId());
		
		ServerConsumable s_consumable = new ServerConsumable(name, "type");
		ClientConsumable c_consumable = new ClientConsumable(s_consumable.getConsumableId());		
		
		if(c_consumable.getConsumableId() == 0)
		{
			fail("Consumable id is 0");
		}
		
		ClientRecommendation cr = new ClientRecommendation(c_user, c_consumable, 5);
		
		
		ServerUser s_user = new ServerUser(create_u.getUserId());
		
		ServerRecommendation sr = new ServerRecommendation(s_user, s_consumable);
		
		if(sr.getUser() == null)
		{
			fail("SQL Exception happened");
		}
		if(cr.getUser().getUserId() == 0)
		{
			fail("SQLException happened during set from client");
		}
		
		int initialRevised = cr.getRevisedRating();
		
		cr.setRevisedRating(initialRevised+1);
		
		ServerRecommendation commit = new ServerRecommendation(s_user,s_consumable);
		
		if(commit.getRevisedRating() != initialRevised+1)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Failed to set on server side:");
			sb.append(commit.getRevisedRating());
			sb.append("!=");
			sb.append(initialRevised+1);
			
			fail(sb.toString());
		}
		if(cr.getRevisedRating() != initialRevised+1)
		{
			fail("Failed to update the client object");
		}
	}
}
