package tests;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.ServerConsumable;

import database.DBAbstractionException;
import database.SQLConsumable;
import database.SQLDatabase;


public class ServerConsumableWetTests {
	
	@Before
	public void setUp() throws Exception {
		SQLDatabase.getInstance("test.db");
	}

	@After
	public void tearDown() throws Exception {
		SQLDatabase.getInstance().close();
	}
	
	@Test
	public void constructorTest() throws DBAbstractionException
	{
		ServerConsumable c1;
		ServerConsumable c2;
		
		String name = "name";
		String type = "type";
		
		c1 = new ServerConsumable(name, type);
		if(c1.getConsumableId() <= 0)
		{
			fail("new consumable constructor");
		}
		if(!c1.getType().equals(type))
		{
			fail("new consumable constructor type");
		}
		
		if(!SQLConsumable.getInstance().getType(c1.getConsumableId()).equals(type))
		{
			fail("SQLConsumable type pre-id constructor");
		}
		
		c2 = new ServerConsumable(c1.getConsumableId());
		if(!c2.getName().equals(name))
		{
			fail("name consumable ID constructor: " + c2.getName());
		}
		

		if(!SQLConsumable.getInstance().getType(c2.getConsumableId()).equals(type))
		{
			fail("SQLConsumable type post-id constructor");
		}
		
		
		if(!c2.getType().equals(type))
		{
			fail("type consumable ID constructor: " + c2.getType());
		}
	}
	
	@Test
	public void commitTest()
	{
		String name = "name";
		String newName = "newname";
		String type = "type";
		String newType = "newtype";
		
		ServerConsumable c1 = new ServerConsumable(name, type);
		
		c1.setName(newName);
		if(!c1.getName().equals(newName))
		{
			fail("username commit");
		}
		
		c1.setType(newType);
		if(!c1.getType().equals(newType))
		{
			fail("type commit");
		}
	}
	
	@Test
	public void refreshTest()
	{
		String name = "name";
		String type = "type";
		
		ServerConsumable c1 = new ServerConsumable(name, type);
		
		c1.setNameWithoutCommit("null");
		c1.refresh();
		if(!c1.getName().equals(name))
		{
			fail("name refresh");
		}
		
		c1.setTypeWithoutCommit("null");
		c1.refresh();
		if(!c1.getName().equals(name))
		{
			fail("type refresh");
		}
	}

}
