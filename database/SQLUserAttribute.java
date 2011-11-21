package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.ServerAttribute;

import core.Attribute;
import core.AttributeRating;

public class SQLUserAttribute implements SQLTable {

	//CREATE  TABLE  IF NOT EXISTS "main"."UserAttribute" ("userID" INTEGER NOT NULL , 
	//"attributeID" INTEGER NOT NULL , "rating" INTEGER NOT NULL , PRIMARY KEY ("userID", "attributeID"));

	public static final String TABLE	   = "UserAttribute";
	public static final String USERID 	   = "userId";
	public static final String ATTRIBUTEID = "attributeId";
	public static final String RATING	   = "rating";
	
	private static SQLUserAttribute instance;
	public static SQLUserAttribute getInstance()
	{
		if(instance == null)
		{
			instance = new SQLUserAttribute();
		}
		return instance;
	}
	
	private SQLUserAttribute()
	{
		
	}
	
	
	@Override
	public void createIfNotExists(SQLDatabase sqlDatabase) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.CREATE_IF_NOT_EXISTS);
		sb.append(TABLE);
		
		sb.append(SQLDatabase.OP);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(USERID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(ATTRIBUTEID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(RATING);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.PRIMARY_KEY);
		sb.append(SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(USERID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.COMMA);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(ATTRIBUTEID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.CP);
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
	
	public void addAtrributeRating(int userId, int attributeId, int rating)
	throws DBAbstractionException {
		
		String userIdString = new Integer(userId).toString();
		String attributeIdString = new Integer(attributeId).toString();
		String ratingString = new Integer(rating).toString();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.INSERT_INTO);
		sb.append(TABLE);
		sb.append(SQLDatabase.OP);
		sb.append(USERID + SQLDatabase.COMMA);
		sb.append(ATTRIBUTEID + SQLDatabase.COMMA);
		sb.append(RATING);
		sb.append(SQLDatabase.CP);
		
		sb.append(SQLDatabase.VALUES);
		sb.append(SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE + userIdString + SQLDatabase.CLOSING_QUOTE + SQLDatabase.COMMA);
		sb.append(SQLDatabase.OPENING_QUOTE + attributeIdString + SQLDatabase.CLOSING_QUOTE + SQLDatabase.COMMA);
		sb.append(SQLDatabase.OPENING_QUOTE + ratingString + SQLDatabase.CLOSING_QUOTE );
		sb.append(SQLDatabase.CP);
		
		SQLDatabase.getInstance().runQuery(sb.toString());
	}
	
	public void editAtrributeRating(int userId, int attributeId, int rating)
	throws DBAbstractionException {
		List<String> keys = new ArrayList<String>();
		keys.add(RATING);
		List<String> values = new ArrayList<String>();
		values.add(new Integer(rating).toString());
		
		List<String> primaryKeys = new ArrayList<String>();
		primaryKeys.add(USERID);
		primaryKeys.add(ATTRIBUTEID);
		
		List<String> primaryKeyValues = new ArrayList<String>();
		primaryKeyValues.add(new Integer(userId).toString());
		primaryKeyValues.add(new Integer(attributeId).toString());
		
		String query = SQLDatabase.updateQueryBuilder(TABLE, keys, values, primaryKeys, primaryKeyValues);
		
		SQLDatabase.getInstance().runQuery(query);
	}

	public List<AttributeRating> getAllRatedAttributes(int userId) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		List<AttributeRating> list = new ArrayList<AttributeRating>();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE + SQLDatabase.WHERE);
		sb.append(USERID + SQLDatabase.EQUAL_TO + SQLDatabase.OPENING_QUOTE);
		sb.append(new Integer(userId).toString() + SQLDatabase.CLOSING_QUOTE);
		
		try {
			ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
			while(rs.next())
			{
				int aid = rs.getInt(ATTRIBUTEID);
				int rating = rs.getInt(RATING);
				ServerAttribute a = new ServerAttribute(aid);
				AttributeRating ar = new AttributeRating(a, rating);
				list.add(ar);
			}
		} catch (DBAbstractionException e) {
			throw e;
		} catch (SQLException e) {
			throw new DBAbstractionException(e);
		}
		
		return list;
		
	}
}
