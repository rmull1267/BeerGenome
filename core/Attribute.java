package core;

import java.util.List;

import database.DBAbstractionException;


public abstract class Attribute implements ORMClass {
	private int attributeId;
	private String name;
	
	//Abstract stuff to be implemented
	protected abstract void constructorHelper(int attributeId);
	protected abstract void constructorHelper(String name);
	
	public abstract List<Attribute> getAllAttributes();
	
	
	protected void setAttributeId(int attributeId)
	{
		this.attributeId = attributeId;
	}
	
	public int getAttributeId()
	{
		return attributeId;
	}

	public void setNameWithoutCommit(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		setNameWithoutCommit(name);
		try {
			commit();
		} catch (DBAbstractionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}
	
	
	
}
