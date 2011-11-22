package tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.Server;
import server.ServerAttribute;
import server.ServerException;
import client.Client;
import client.ClientAttribute;
import client.ClientException;
import database.SQLDatabase;

public class ClientAttributeWetTests {
	
	
	@Test
	public void loadAttribute()
	{
		String name = UUID.randomUUID().toString();
		ServerAttribute sa = new ServerAttribute(name);
		
		ClientAttribute ca = new ClientAttribute(sa.getAttributeId());

		if(!ca.getName().equals(sa.getName()))
		{
			fail("Names doesn't match up.");
		}
	}
	
	@Test
	public void createAttribute()
	{
		String name = UUID.randomUUID().toString();
		ClientAttribute ca = new ClientAttribute(name);
		
		if(ca.getAttributeId() == 0)
		{
			fail("ID not set properly.");
		}
		
		ServerAttribute sa = new ServerAttribute(ca.getAttributeId());
		
		if(!sa.getName().equals(ca.getName()))
		{
			//Did not set name properly on server side.
			fail(sa.getName() + "!=" + ca.getName());
		}
		
		if(sa.getAttributeId() != ca.getAttributeId())
		{
			fail("Did not set ID properly on create.");
		}
	}
}
