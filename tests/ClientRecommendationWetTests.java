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
			fail("revised ratings don't match.");
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
		int initialRevised = create_r.getRevisedRating();
		
		create_r.setRevisedRating(initialRevised+1);
		
		ServerRecommendation commit = new ServerRecommendation(create_u,create_c);
		
		if(commit.getRevisedRating() != initialRevised+1)
		{
			fail("Failed to set on server side");
		}
		if(create_r.getRevisedRating() != initialRevised+1)
		{
			fail("Failed to update the client object");
		}
	}
}
