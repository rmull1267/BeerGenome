package database;

public interface SQLTable {
	public void createIfNotExists(SQLDatabase sqlDatabase) throws DBAbstractionException;
	public void drop() throws DBAbstractionException;
	public void empty() throws DBAbstractionException;
}
