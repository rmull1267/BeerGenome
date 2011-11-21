package core;

import database.DBAbstractionException;

/**
 * An ORMClass is a class that represents a row in a database.
 *
 */
public interface ORMClass {
	/**
	 * Replaces the private data items that are not part of the primary key
	 * with values from the database/network.
	 * @throws DBAbstractionException 
	 */
	public void refresh() throws DBAbstractionException;
	
	/**
	 * Saves changes made to non-primary key data items to the database/network.
	 * @throws DBAbstractionException 
	 */
	public void commit() throws DBAbstractionException; 
}
