package core;

import java.util.List;

import database.DBAbstractionException;

public abstract class Consumable implements ORMClass {
	private int consumableId;
	private String name;
	private String type;
	
	
	//Abstract methods that need to be implemented
	protected abstract void constructorHelper(int consumableId);
	protected abstract void constructorHelper(String name, String type);
	public abstract List<Consumable> getAllConsumables();
	
	
	
	//Getters and Setters for data items.
	protected void setConsumableId(int consumableId) 
	{
		this.consumableId = consumableId;
	}
	
	public int getConsumableId() {
		return consumableId;
	}
	
	public void setName(String name) 
	{
		setNameWithoutCommit(name);
		try {
			commit();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
	}
	
	public void setNameWithoutCommit(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTypeWithoutCommit(String type) 
	{
		this.type = type;
	}
	
	public void setType(String type)
	{
		setTypeWithoutCommit(type);
		try {
			commit();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
	}
	
	public String getType() {
		return type;
	}
	
	
	public boolean equals(Consumable c)
	{
		if(c.getConsumableId() == this.getConsumableId())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
