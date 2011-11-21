package tests;


import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerAttribute;

import database.SQLDatabase;


public class ServerAttributeWetTests {

	
	
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
		String name = UUID.randomUUID().toString();
		
		ServerAttribute newat = new ServerAttribute(name);
		
		if(newat.getAttributeId() == 0)
		{
			fail("There's a problem setting the attribute id");
		}
		
		if(!newat.getName().equals(name))
		{
			fail("constructor creator");
		}
		
		ServerAttribute oldat = new ServerAttribute(newat.getAttributeId());
		
		if(!oldat.getName().equals(name))
		{
			fail("constructor loader");
		}	
	}
	
	@Test
	public void testSetter()
	{
		String name = UUID.randomUUID().toString();
		String name2 = UUID.randomUUID().toString();
		
		ServerAttribute newat = new ServerAttribute(name);
		
		newat.setName(name2);
		
		if(!newat.getName().equals(name2))
		{
			fail("name setter failed");
		}
	}
	
	

}
