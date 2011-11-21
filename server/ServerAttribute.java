package server;

import java.util.List;

import core.Attribute;
import database.DBAbstractionException;
import database.SQLAttribute;
import database.SQLDatabase;

public class ServerAttribute extends Attribute {

	/**
	 * Load an existing attribute from the database.
	 * @param attributeId
	 */
	public ServerAttribute(int attributeId)
	{
		constructorHelper(attributeId);
	}
	@Override
	protected void constructorHelper(int attributeId)
	{
		setAttributeId(attributeId);
		try {
			refresh();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a new attribute and add to the database.
	 * @param name
	 */
	public ServerAttribute(String name)
	{
		constructorHelper(name);
	}
	@Override
	protected void constructorHelper(String name)
	{
		try {
			setAttributeId(SQLDatabase.getInstance().addAttribute(name));
			refresh();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}	
	}
	
	
	@Override
	public void commit() throws DBAbstractionException {
		SQLDatabase.getInstance().setAttributeName(this.getAttributeId(), this.getName());		
	}

	@Override
	public void refresh() throws DBAbstractionException {
		setNameWithoutCommit(SQLDatabase.getInstance().getAttributeName(this.getAttributeId()));		
	}
	@Override
	public List<Attribute> getAllAttributes() {
		try {
			return SQLAttribute.getInstance().getAllAttributes();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
