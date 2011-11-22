package core;

import database.DBAbstractionException;

/**
 * TODO-nf-refactor : Change DBAbstractionException to ProxyException or something in implementing classes.
 * TODO-nf-refactor : rename to DataSourceProxy
 * @author nfulton
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
