package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import core.Attribute;

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
	
	@Test
	public void getAllAttributes()
	{
		String name = UUID.randomUUID().toString();
		ClientAttribute ca = new ClientAttribute(name);
		List<Attribute> cattr = ca.getAllAttributes();
		
		ServerAttribute sa = new ServerAttribute(ca.getAttributeId());
		List<Attribute> sattr = sa.getAllAttributes();
		
		if(cattr.size() != sattr.size())
		{
			fail("unequal sizes.");
		}
		
		for(int i = 0 ; i < cattr.size(); i++)
		{
			if(cattr.get(i).getAttributeId() != sattr.get(i).getAttributeId())
			{
				fail("unequal IDs " + i);
			}
			
			if(!cattr.get(i).getName().equals(sattr.get(i).getName()))
			{
				fail("unequal names " + i);
			}
		}
	}
}
