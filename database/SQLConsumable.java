package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.ServerConsumable;

import core.Consumable;

public class SQLConsumable implements SQLTable {
	
	//Constants
	public static final String TABLE = "Consumable";
	public static final String NAME  = "name";
	public static final String TYPE  = "type";
	public static final String ID	 = "consumableID";
	
	private static SQLConsumable instance;
	public static SQLConsumable getInstance()
	{
		if(instance == null)
		{
			instance = new SQLConsumable();
		}
		return instance;
	}
	
	private SQLConsumable()
	{
	}
	
	public List<Consumable> search(String phrase) throws DBAbstractionException
	{
		ResultSet rs = SQLDatabase.getInstance().runResult("SELECT * FROM Consumable WHERE name LIKE '" + phrase + "' OR type LIKE '" + phrase + "'");
		
		List<Consumable> ret = new ArrayList<Consumable>();
		try
		{
			while(rs.next())
			{
				Consumable c = new ServerConsumable(rs.getInt(ID));
				ret.add(c);
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return ret;
	}
	
	public String getName(int id) throws DBAbstractionException
	{
		ResultSet rs = SQLDatabase.getInstance().runResult(SQLDatabase.SELECT_STAR_FROM + TABLE + 
				SQLDatabase.WHERE + ID + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE 
				+ (new Integer(id)).toString() + SQLDatabase.CLOSING_QUOTE);
		
		try
		{
			while(rs.next())
			{
				return rs.getString(NAME);
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return null;
	}
	
	public String getType(int id) throws DBAbstractionException
	{
		ResultSet rs = SQLDatabase.getInstance().runResult(SQLDatabase.SELECT_STAR_FROM + TABLE + 
				SQLDatabase.WHERE + ID + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE 
				+ (new Integer(id)).toString() + SQLDatabase.CLOSING_QUOTE);
		
		try
		{
			while(rs.next())
			{
				return rs.getString(TYPE);
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return null;
	}
	
	public void setConsumableName(int consumableId, String name) throws DBAbstractionException {
		StringBuilder q = new StringBuilder();
		
		q.append(SQLDatabase.UPDATE);
		q.append(TABLE);
		q.append(SQLDatabase.SET + NAME + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE + name 
				+ SQLDatabase.CLOSING_QUOTE);
		q.append(SQLDatabase.WHERE + ID + SQLDatabase.EQUAL_TO);
		q.append(new Integer(consumableId).toString());
		
		SQLDatabase.getInstance().runQuery(q.toString());
	}
	
	public void setConsumableType(int consumableId, String type) throws DBAbstractionException {
		StringBuilder q = new StringBuilder();
		
		q.append(SQLDatabase.UPDATE);
		q.append(TABLE);
		q.append(SQLDatabase.SET + TYPE + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE + type 
				+ SQLDatabase.CLOSING_QUOTE);
		q.append(SQLDatabase.WHERE + ID + SQLDatabase.EQUAL_TO);
		q.append(new Integer(consumableId).toString());
		
		SQLDatabase.getInstance().runQuery(q.toString());
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @return id of new row.
	 */
	public int addConsumable(String name, String type) throws DBAbstractionException
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.INSERT_INTO);
		sb.append(TABLE);
		sb.append(SQLDatabase.OP);
		sb.append(NAME);
		sb.append(SQLDatabase.COMMA);
		sb.append(TYPE);
		sb.append(SQLDatabase.CP);
		
		sb.append(SQLDatabase.VALUES);
		
		sb.append(SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(name);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(type);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		
		sb.append(SQLDatabase.CP);
		
		SQLDatabase.getInstance().runQuery(sb.toString());
		
		ResultSet rs = SQLDatabase.getInstance().runResult(SQLDatabase.SELECT_STAR_FROM + TABLE + SQLDatabase.ORDER_BY + ID + SQLDatabase.DESC);
		try
		{
			while(rs.next())
			{
				return Integer.parseInt(rs.getString(ID));
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return 0;
	}
	

	@Override
	public void createIfNotExists(SQLDatabase sqlDatabase) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.CREATE_IF_NOT_EXISTS);
		sb.append("main.");
		sb.append(TABLE);
		
		sb.append(SQLDatabase.OP);
		
		sb.append(SQLDatabase.QUOTE);
		sb.append(ID);
		sb.append(SQLDatabase.QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.PRIMARY_KEY);
		sb.append(SQLDatabase.AUTOINCREMENT);
		sb.append(SQLDatabase.NOT_NULL);
		sb.append(SQLDatabase.UNIQUE);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(NAME);
		sb.append(SQLDatabase.VARCHAR);
		sb.append(SQLDatabase.NOT_NULL);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(TYPE);
		sb.append(SQLDatabase.VARCHAR);
		sb.append(SQLDatabase.NOT_NULL);
		
		sb.append(SQLDatabase.CP);
		
		sqlDatabase.runQuery(sb.toString());
		
	}

	@Override
	public void drop() throws DBAbstractionException {
		 SQLDatabase.getInstance().runQuery("DROP TABLE " + TABLE);
		
	}

	@Override
	public void empty() throws DBAbstractionException {
		SQLDatabase.getInstance().runQuery("TRUNCATE TABLE " + TABLE);
	}
	
	public List<Consumable> getAllConsumables() throws DBAbstractionException {
		List<Consumable> retVal = new ArrayList<Consumable>();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM);
		sb.append(TABLE);
		
		ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
		
		try {
			while(rs.next())
			{
				retVal.add(new ServerConsumable(rs.getInt(ID)));
			}
		} catch (SQLException e) {
			throw new DBAbstractionException(e);
		}
		
		return retVal;
	}
}
