package client;

import java.util.List;

import protocol.GetRatedConsumablesMessage;
import core.Consumable;
import database.DBAbstractionException;

/**
 * TODO- JRC
 * @author nfulton
 * @author JRC
 */
public class ClientConsumable extends Consumable
{
	public ClientConsumable(int consumableId)
	{
		constructorHelper(consumableId);
	}
	
	public ClientConsumable(String user, String type)
	{
		constructorHelper(user, type);
	}
	
	@Override
	protected void constructorHelper(int consumableId)
	{
		this.setConsumableId(consumableId);
	}

	@Override
	protected void constructorHelper(String name, String type)
	{
		// TODO Auto-generated method stub
		this.setName(name);
		this.setType(type);
	}
	
	@Override
	public void commit() throws DBAbstractionException
	{
		// TODO Auto-generated method stub	
	}

	@Override
	public void refresh() throws DBAbstractionException
	{
		// TODO Auto-generated method stub	
	}

	@Override
	public List<Consumable> getAllConsumables()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
