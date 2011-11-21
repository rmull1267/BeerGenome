package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.ServerConsumable;
import server.ServerRecommendation;
import server.ServerUser;

import core.Consumable;
import core.Recommendation;
import core.User;

public class SQLRecommendation implements SQLTable {
	public static final String TABLE 		  = "Recommendation";
	public static final String USERID 		  = "userId"; 			//pk
	public static final String CONSUMABLEID   = "consumableId"; 	//pk
	public static final String INITIAL_RATING = "initialRating";
	public static final String REVISED_RATING = "revisedRating";
	
	private static SQLRecommendation instance;
	public static SQLRecommendation getInstance()
	{
		if(instance == null)
		{
			instance = new SQLRecommendation();
		}
		return instance;
	}
	private SQLRecommendation() {}
	
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
		sb.append(CONSUMABLEID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(INITIAL_RATING);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(REVISED_RATING);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.INTEGER);
		sb.append(SQLDatabase.NOT_NULL);
		sb.append(SQLDatabase.COMMA);
		
		sb.append(SQLDatabase.PRIMARY_KEY + SQLDatabase.OP);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(CONSUMABLEID);
		sb.append(SQLDatabase.CLOSING_QUOTE);
		sb.append(SQLDatabase.COMMA);
		sb.append(SQLDatabase.OPENING_QUOTE);
		sb.append(USERID);
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
	
	public void addRecommendationRating(int userId, int consumableId, int initialRating, int newRating)
	throws DBAbstractionException {
		List<String> keys = new ArrayList<String>();
		keys.add(USERID);
		keys.add(CONSUMABLEID);
		keys.add(INITIAL_RATING);
		keys.add(REVISED_RATING);
		
		List<String> values = new ArrayList<String>();
		values.add(new Integer(userId).toString());
		values.add(new Integer(consumableId).toString());
		values.add(new Integer(initialRating).toString());
		values.add(new Integer(newRating).toString());
		
		String query = SQLDatabase.insertQueryBuilder(TABLE, keys, values);
		
		SQLDatabase.getInstance().runQuery(query);
	}
	
	public void editRecommendationRating(int userId, int consumableId, int rating) 
	throws DBAbstractionException {
		List<String> keys = new ArrayList<String>();
		keys.add(REVISED_RATING);
		List<String> values = new ArrayList<String>();
		values.add(new Integer(rating).toString());
		
		List<String> primaryKeys = new ArrayList<String>();
		primaryKeys.add(USERID);
		primaryKeys.add(CONSUMABLEID);
		List<String> primaryKeyValues = new ArrayList<String>();
		primaryKeyValues.add(new Integer(userId).toString());
		primaryKeyValues.add(new Integer(consumableId).toString());
		
		String query = SQLDatabase.updateQueryBuilder(TABLE, keys, values, primaryKeys, primaryKeyValues);
		
		SQLDatabase.getInstance().runQuery(query);
	}
	
	public List<Recommendation> getAllConsumablesRated(int userId) throws DBAbstractionException {
		List<Recommendation> retVal = new ArrayList<Recommendation>();
		
		String query = SQLDatabase.SELECT_STAR_FROM + TABLE + SQLDatabase.WHERE +
							USERID + SQLDatabase.EQUAL_TO + new Integer(userId).toString();
		
		ResultSet rs = SQLDatabase.getInstance().runResult(query);
		
		try {
			while(rs.next())
			{
				Consumable c = new ServerConsumable(rs.getInt(CONSUMABLEID));
				User u = new ServerUser(rs.getInt(USERID));
				Recommendation r = new ServerRecommendation(u, c);
				retVal.add(r);
			}
		} catch (SQLException e) {
			throw new DBAbstractionException(e);
		}
		
		
		return retVal;
	}
	
	public int getInitialRating(int userId, int consumableId) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE + SQLDatabase.WHERE + USERID + 
				  SQLDatabase.EQUAL_TO);
		sb.append(userId);
		sb.append(SQLDatabase.AND + CONSUMABLEID + SQLDatabase.EQUAL_TO);
		sb.append(consumableId);
		
		ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
		
		try {
			while(rs.next())
			{
				return rs.getInt(INITIAL_RATING);
			}
		} catch (SQLException e) {
			throw new DBAbstractionException(e);
		}
		
		return -1;
	}
	
	public int getRevisedRating(int userId, int consumableId) throws DBAbstractionException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(SQLDatabase.SELECT_STAR_FROM + TABLE + SQLDatabase.WHERE + USERID + 
				  SQLDatabase.EQUAL_TO);
		sb.append(userId);
		sb.append(SQLDatabase.AND + CONSUMABLEID + SQLDatabase.EQUAL_TO);
		sb.append(consumableId);
		
		ResultSet rs = SQLDatabase.getInstance().runResult(sb.toString());
		
		try {
			while(rs.next())
			{
				return rs.getInt(REVISED_RATING);
			}
		} catch (SQLException e) {
			throw new DBAbstractionException(e);
		}
		
		return -1;
	}

}
