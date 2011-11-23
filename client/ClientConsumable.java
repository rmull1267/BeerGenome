package client;

import java.util.List;

import protocol.CreateConsumableMessage;
import protocol.GetConsumableMessage;
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
		GetConsumableMessage m = new GetConsumableMessage(this);
		
		try
		{
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void constructorHelper(String name, String type)
	{
		setNameWithoutCommit(name);
		setTypeWithoutCommit(type);
		
		CreateConsumableMessage m = new CreateConsumableMessage(this);
		try
		{
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	
	public void setID(int id)
	{
		this.setConsumableId(id);
	}
}
