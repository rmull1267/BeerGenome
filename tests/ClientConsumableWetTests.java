package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import server.ServerConsumable;
import client.ClientConsumable;

public class ClientConsumableWetTests
{
	@Test
	public void idConstructor()
	{
		ClientConsumable cc = new ClientConsumable(1);
		ServerConsumable sc = new ServerConsumable(1);
		
		if(cc.getConsumableId() != sc.getConsumableId())
		{
			fail("IDs don't match.");
		}
		
		if(! cc.getName().equals(sc.getName()))
		{
			fail("Names don't match.");
		}
		
		if(! cc.getType().equals(sc.getType()))
		{
			fail("Types don't match.");
		}
	}
}
