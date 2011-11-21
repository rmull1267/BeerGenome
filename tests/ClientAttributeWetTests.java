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
}
