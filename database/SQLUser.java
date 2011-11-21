package database;

import java.sql.ResultSet;

public class SQLUser implements SQLTable {

	public static final String TABLE = "User";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String USERID   = "userID";
	
	private static SQLUser instance;
	private SQLUser()
	{
		
	}
	public static SQLUser getInstance()
	{
		if(instance == null)
		{
			instance = new SQLUser();
		}
		return instance;
	}
	
	//CREATE  TABLE  IF NOT EXISTS "main"."User" ("userID" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  
	//UNIQUE , "username" VARCHAR NOT NULL  UNIQUE , "password" VARCHAR NOT NULL );
	@Override
	public void createIfNotExists(SQLDatabase sqlDatabase) throws DBAbstractionException {
		StringBuilder q = new StringBuilder();
		q.append(SQLDatabase.CREATE_IF_NOT_EXISTS);
		q.append(SQLDatabase.OPENING_QUOTE);
		q.append(TABLE);
		q.append(SQLDatabase.CLOSING_QUOTE);
		
		q.append(SQLDatabase.OP);
		
		q.append(SQLDatabase.OPENING_QUOTE);
		q.append(USERID);
		q.append(SQLDatabase.CLOSING_QUOTE);
		q.append(SQLDatabase.INTEGER);
		q.append(SQLDatabase.PRIMARY_KEY);
		q.append(SQLDatabase.AUTOINCREMENT);
		q.append(SQLDatabase.NOT_NULL);
		q.append(SQLDatabase.UNIQUE);
		
		q.append(SQLDatabase.COMMA);
		
		q.append(SQLDatabase.OPENING_QUOTE + USERNAME + SQLDatabase.CLOSING_QUOTE);
		q.append(SQLDatabase.VARCHAR + SQLDatabase.NOT_NULL + SQLDatabase.UNIQUE); 
		
		q.append(SQLDatabase.COMMA);
		
		q.append(SQLDatabase.OPENING_QUOTE + PASSWORD + SQLDatabase.CLOSING_QUOTE);
		q.append(SQLDatabase.VARCHAR + SQLDatabase.NOT_NULL); 
		
		q.append(SQLDatabase.CP);
		
		sqlDatabase.runQuery(q.toString());
	}

	@Override
	public void drop() throws DBAbstractionException {
		SQLDatabase.getInstance().runQuery("DROP TABLE " + TABLE);
	}

	@Override
	public void empty() throws DBAbstractionException {
		SQLDatabase.getInstance().runQuery("TRUNCATE TABLE " + TABLE);
	}
	
	
	public int addUser(String name, String password) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.INSERT_INTO);
		sb.append(TABLE);
		sb.append(SQLDatabase.OP);
		sb.append(USERNAME);
		sb.append(SQLDatabase.COMMA);
		sb.append(PASSWORD);
		sb.append(SQLDatabase.CP);
		sb.append(SQLDatabase.VALUES);
		sb.append(SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(name);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.COMMA);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(password);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.CP);
		
		SQLDatabase.getInstance().runQuery(sb.toString());
		
		ResultSet rs = SQLDatabase.getInstance().runResult(
				SQLDatabase.SELECT_STAR_FROM + TABLE + 
				SQLDatabase.ORDER_BY + USERID + SQLDatabase.DESC);
		try
		{
			while(rs.next())
			{
				//System.err.println("HERE" + rs.getString(ATTRIBUTEID));
				return Integer.parseInt(rs.getString(USERID));
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return 0;
	}
	
	public String getPassword(int userId) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE);
		sb.append(SQLDatabase.WHERE + USERID + SQLDatabase.EQUAL_TO + userId );
		
		
		
		try
		{
			ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
			while(rs.next())
			{
				return rs.getString(PASSWORD);
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return null;
	}
	
	
	public String getUsername(int userId) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE);
		sb.append(SQLDatabase.WHERE + USERID + SQLDatabase.EQUAL_TO + userId );
		
		try
		{
			ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
			while(rs.next())
			{
				return rs.getString(USERNAME);
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return null;
	}
	
	
	public int getIdFromUsername(String username) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE);
		sb.append(SQLDatabase.WHERE + USERNAME + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE +
				username + SQLDatabase.CLOSING_QUOTE);
		
		try
		{
			ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
			while(rs.next())
			{
				return Integer.parseInt(rs.getString(USERID));
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return 0;
	}
	
	
	public void setPassword(int userId, String password) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.UPDATE + TABLE + SQLDatabase.SET);
		sb.append(PASSWORD + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE + password + 
				SQLDatabase.CLOSING_QUOTE + SQLDatabase.WHERE);
		sb.append(USERID + SQLDatabase.EQUAL_TO + new Integer(userId).toString());
		
		SQLDatabase.getInstance().runQuery(sb.toString());
		
	}
	
	public void setUsername(int userId, String name) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.UPDATE + TABLE + SQLDatabase.SET);
		sb.append(USERNAME + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE + name + 
				SQLDatabase.CLOSING_QUOTE + SQLDatabase.WHERE);
		sb.append(USERID + SQLDatabase.EQUAL_TO + new Integer(userId).toString());
		
		SQLDatabase.getInstance().runQuery(sb.toString());
		
	}
}
