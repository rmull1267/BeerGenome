package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.Attribute;

import server.ServerAttribute;

public class SQLAttribute implements SQLTable {
	public static final String TABLE	   = "Attribute";
	public static final String ATTRIBUTEID = "attributeID";
	public static final String NAME 	   = "name";
	
	private static SQLAttribute instance;
	public static SQLAttribute getInstance()
	{
		if(instance == null)
		{
			instance = new SQLAttribute();
		}
		return instance;
	}
	
	private SQLAttribute()
	{
		
	}
	
	@Override
	public void createIfNotExists(SQLDatabase sqlDatabase) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.CREATE_IF_NOT_EXISTS);
		sb.append(TABLE);
		sb.append(SQLDatabase.OP);
		
		sb.append(SQLDatabase.OPENING_QUOTE + ATTRIBUTEID + SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER + SQLDatabase.PRIMARY_KEY +
				SQLDatabase.AUTOINCREMENT + SQLDatabase.NOT_NULL +
				SQLDatabase.UNIQUE);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE + NAME + SQLDatabase.CLOSING_QUOTE);
		
		sb.append(SQLDatabase.VARCHAR + SQLDatabase.NOT_NULL + SQLDatabase.UNIQUE);
		
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
	
	
	//INSERT INTO Attribute (name) VALUES('name');
	public int addAttribute(String name) throws DBAbstractionException 
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.INSERT_INTO);
		sb.append(TABLE);
		sb.append(SQLDatabase.OP);
		sb.append(NAME);
		sb.append(SQLDatabase.CP);
		sb.append(SQLDatabase.VALUES);
		sb.append(SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(name);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.CP);
		
		SQLDatabase.getInstance().runQuery(sb.toString());
		
		ResultSet rs = SQLDatabase.getInstance().runResult(
				SQLDatabase.SELECT_STAR_FROM + TABLE + 
				SQLDatabase.ORDER_BY + ATTRIBUTEID + SQLDatabase.DESC);
		try
		{
			while(rs.next())
			{
				//System.err.println("HERE" + rs.getString(ATTRIBUTEID));
				return Integer.parseInt(rs.getString(ATTRIBUTEID));
			}
		}
		catch(Exception e)
		{
			throw new DBAbstractionException(e);
		}
		
		return 0;
	}
	
	public String getName(int attributeId) throws DBAbstractionException {
		String query = SQLDatabase.SELECT_STAR_FROM + TABLE + 
		SQLDatabase.WHERE + ATTRIBUTEID + SQLDatabase.EQUAL_TO +
		SQLDatabase.OPENING_QUOTE + new Integer(attributeId).toString() +
		SQLDatabase.CLOSING_QUOTE;
		
		ResultSet rs = SQLDatabase.getInstance().runResult(query);
		
		//System.err.println(query);
		
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
	
	//UPDATE Attribute SET name='name' WHERE id=attributeId
	public void setName(int attributeId , String name) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		sb.append(SQLDatabase.UPDATE);
		sb.append(TABLE);
		sb.append(SQLDatabase.SET);
		sb.append(NAME);
		sb.append(SQLDatabase.EQUAL_TO);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(name);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.WHERE);
		sb.append(ATTRIBUTEID);
		sb.append(SQLDatabase.EQUAL_TO);
		sb.append(new Integer(attributeId).toString());
		
		SQLDatabase.getInstance().runQuery(sb.toString());		
	}
	
	public List<Attribute> getAllAttributes() throws DBAbstractionException
	{
		List< Attribute > list = new ArrayList<Attribute>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(SQLDatabase.SELECT_STAR_FROM);
		sb.append(TABLE);
		
		ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
		try {
			while(rs.next())
			{
				int id = rs.getInt(ATTRIBUTEID);
				list.add(new ServerAttribute(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}
