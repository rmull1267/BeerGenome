package client;

import java.util.ArrayList;
import java.util.List;

import protocol.CreateConsumableMessage;
import protocol.GetAllConsumablesMessage;
import protocol.GetConsumableMessage;
import protocol.ProtocolException;
import protocol.SetNameAndTypeOnConsumableMessage;
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
	
	public ClientConsumable(String user, String type) throws ClientException
	{
		constructorHelper(user, type);
		
		if(getConsumableId() == 0)
		{
			throw new ClientException("consumableid not set.");
		}
	}
	
	@Override
	protected void constructorHelper(int consumableId)
	{
		this.setConsumableId(consumableId);
		GetConsumableMessage m = new GetConsumableMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			this.setConsumableId(0);
			e.printStackTrace();
		} catch (ClientException e) {
			this.setConsumableId(0);
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
			this.setConsumableId(0);
		}
	}
	
	@Override
	public void commit() throws DBAbstractionException
	{
		SetNameAndTypeOnConsumableMessage m = new SetNameAndTypeOnConsumableMessage(this);
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			throw new DBAbstractionException(e);
		} catch (ClientException e) {
			throw new DBAbstractionException(e);
		}
	}

	@Override
	public void refresh() throws DBAbstractionException
	{
		throw new DBAbstractionException("Unimplemented.");
	}

	@Override
	public List<Consumable> getAllConsumables()
	{
		List<Consumable> list = new ArrayList<Consumable>();
		
		GetAllConsumablesMessage m = new GetAllConsumablesMessage(list);
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void setID(int id)
	{
		this.setConsumableId(id);
	}
}
