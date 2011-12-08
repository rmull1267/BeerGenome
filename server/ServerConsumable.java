package server;
import java.util.ArrayList;
import java.util.List;

import core.Consumable;
import database.DBAbstraction;
import database.DBAbstractionException;
import database.SQLDatabase;


/**
 * The class representing the Consumable table.
 * @author nfulton
 *
 */
public class ServerConsumable extends Consumable {
	private DBAbstraction database;
	
	/**
	 * Loads a consumable from the database
	 * @param consumableId
	 */
	public ServerConsumable(int consumableId)
	{
		constructorHelper(consumableId);
	}
	@Override
	protected void constructorHelper(int consumableId)
	{
		setDatabase(SQLDatabase.getInstance());
		setConsumableId(consumableId);
		
		try
		{
			setNameWithoutCommit(getDatabase().getConsumableName(consumableId));
			setTypeWithoutCommit(getDatabase().getConsumableType(consumableId));
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new consumable.
	 * @param name
	 * @param type
	 */
	public ServerConsumable(String name, String type)
	{
		constructorHelper(name,type);
	}
	@Override
	protected void constructorHelper(String name, String type)
	{
		setDatabase(SQLDatabase.getInstance());
		
		int id = 0;
		try
		{
			id = getDatabase().addConsumable(name, type);
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
		
		setConsumableId(id);
		setName(name);
		setType(type);	
	}

	@Override
	public void commit() {
		try
		{
			getDatabase().setConsumableName(getConsumableId(), getName());
			getDatabase().setConsumableType(getConsumableId(), getType());
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		try
		{
			setName(getDatabase().getConsumableName(getConsumableId()));
			setType(getDatabase().getConsumableType(getConsumableId()));
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
	}

	private void setDatabase(DBAbstraction database) {
		this.database = database;
	}

	private DBAbstraction getDatabase() {
		return database;
	}
	@Override
	public List<Consumable> getAllConsumables() {
		try {
			return SQLDatabase.getInstance().getAllConsumables();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Consumable>();
	}
	@Override
	public List<Consumable> search(String term) {
		try {
			return SQLDatabase.getInstance().searchConsumable(term);
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
