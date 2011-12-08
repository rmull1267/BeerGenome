package tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import core.Consumable;

import server.ServerConsumable;
import client.ClientConsumable;
import client.ClientException;

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
	
	@Test
	public void search()
	{
		String name = UUID.randomUUID().toString();
		
		ClientConsumable cc = null;
		try {
			cc = new ClientConsumable(name,"type");
		} catch (ClientException e) {
			fail(e.getStackTrace().toString());
		}
		
		List<Consumable> consumables = cc.search(name);
		
		if(consumables.size() != 1)
		{
			fail("too big or too small" + consumables.toString());
		}
		else
		{
			if(!consumables.get(0).getName().equals(name))
			{
				fail("search failed wrong one");
			}
		}
		
		
	}
	
	@Test
	public void nameAndTypeConstructor()
	{
		String name = UUID.randomUUID().toString();
		String type = UUID.randomUUID().toString();
		
		ClientConsumable cc = null;
		try {
			cc = new ClientConsumable(name, type);
		} catch (ClientException e) {
			fail("could not create.");
		}
		ServerConsumable sc = new ServerConsumable(name, type);
		
		if(! cc.getName().equals(sc.getName()))
		{
			fail("Names don't match.");
		}
		
		if(! cc.getType().equals(sc.getType()))
		{
			fail("Types don't match.");
		}
	}
	
	@Test
	public void commit() //NRF
	{
		String name = UUID.randomUUID().toString();
		String type = "type";
		String newName = name + "new";
		String newType = type + "new";
		
		ClientConsumable cc = null;
		try {
			cc = new ClientConsumable(name, type);
		} catch (ClientException e) {
			fail("Could not create");
		}
		cc.setName(newName);
		cc.setType(newType);
		
		ServerConsumable sc = new ServerConsumable(cc.getConsumableId());
		
		if(!sc.getName().equals(cc.getName()))
		{
			fail("names don't match.");
		}
		if(!sc.getType().equals(cc.getType()))
		{
			fail("types don't match.");
		}
	}
	
	@Test
	public void getAllConsumables()
	{
		String name = UUID.randomUUID().toString();
		String type = "type";

		ClientConsumable cc = null;
		try {
			cc = new ClientConsumable(name,type);
		} catch (ClientException e) {
			fail("could not create");
		}
		
		List<Consumable> clist = cc.getAllConsumables();
		
		ServerConsumable sc = new ServerConsumable(cc.getConsumableId());
		
		List<Consumable> slist = sc.getAllConsumables();
		
		if(clist.size() != slist.size())
		{
			fail("Client and server lists are not the same size.");
		}
		
		for(int i = 0 ; i < clist.size() ; i++)
		{
			Consumable clientConsumable = clist.get(i);
			Consumable serverConsumable = slist.get(i);
			
			if(clientConsumable.getConsumableId() != serverConsumable.getConsumableId())
			{
				fail("IDs don't match");
			}
			
			if(!clientConsumable.getName().equals(serverConsumable.getName()))
			{
				fail("Names don't match");
			}
			
			if(!clientConsumable.getType().equals(serverConsumable.getType()))
			{
				fail("types don't match");
			}
		}
	}
}
