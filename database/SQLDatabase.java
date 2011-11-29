package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.Recommendation;

import server.ServerAttribute;


/**
 * A singleton interface to the database.
 * @author nfulton
 *
 */
public class SQLDatabase implements DBAbstraction {
	private static SQLDatabase instance;
	
	private String filename;
	private Connection connection;
	
	/* ********************** CONSTANTS *********************** */
	//Terms and keywords
	public static final String CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
	public static final String NOT_NULL = " NOT NULL ";
	public static final String UNIQUE = " UNIQUE ";
	public static final String PRIMARY_KEY = " PRIMARY KEY ";
	public static final String AUTOINCREMENT = " AUTOINCREMENT ";
	public static final String CREATE_TABLE = " CREATE TABLE ";
	public static final String INSERT_INTO = " INSERT INTO ";
	public static final String DELETE_FROM = " DELETE FROM ";
	public static final String VALUES = " VALUES ";
	public static final String UPDATE = " UPDATE ";
	public static final String SET = " SET ";
	public static final String SELECT_STAR_FROM = " SELECT * FROM ";
	public static final String SELECT = " SELECT ";
	public static final String DISTINCT = " DISTINCT ";
	public static final String NUM = "NUM";
	public static final String SELECT_COUNT_AS_NUM = " SELECT COUNT(*) AS " + NUM;
	public static final String LIMIT_ONE = " LIMIT 1 ";
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";	
	public static final String ORDER_BY = " ORDER BY ";
	public static final String IN = " IN ";
	public static final String NOT_IN = " NOT IN ";
	public static final String ASC = " ASC ";
	public static final String DESC = " DESC ";
	public static final String EQUAL_TO = " = ";
	public static final String NOT_EQUAL_TO = " != ";
	public static final String LESS_THAN_OR_EQUAL_TO = " <= ";
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	public static final String ROWID = " rowid ";

	
	//Punctuation
	public static final String OP = " ( ";
	public static final String CP = " ) ";
	public static final String COMMA = " , ";
	public static final String QUOTE = "\"";
	
	public static final String OPENING_QUOTE = " '";
	public static final String CLOSING_QUOTE = "' ";
	
	//Types
	public static final String VARCHAR = " VARCHAR ";
	public static final String INTEGER = " INTEGER ";
	public static final String TEXT = " TEXT ";
	
	
	
	/**
	 * Changes the instance!
	 * @param filename
	 * @return
	 */
	public static SQLDatabase getInstance(String filename)
	{
		if(instance != null)
		{
			try {
				instance.close();
			} catch (DBAbstractionException e) {
				e.printStackTrace();
			}
		}

		instance = new SQLDatabase(filename);
		return instance;
	}
	public static SQLDatabase getInstance()
	{
		if(instance == null)
		{
			instance = new SQLDatabase("drunkonbeer.sqlite");
		}	
		return instance;
	}
	
	private SQLDatabase(String filename)
	{
		// load the sqlite-JDBC driver using the class loader
		try
		{
			Class.forName("org.sqlite.JDBC");
			
			//set the path to the sqlite database file
			setFilename(filename);
			
			//create a database connection, will open the sqlite db if it exists 
			//and create a new sqlite database if it does not exist
			setConnection(DriverManager.getConnection("jdbc:sqlite:" +
					getFilename()));
			
			SQLConsumable.getInstance().createIfNotExists(this);
			SQLUser.getInstance().createIfNotExists(this);
			SQLAttribute.getInstance().createIfNotExists(this);
			SQLUserAttribute.getInstance().createIfNotExists(this);
			SQLRecommendation.getInstance().createIfNotExists(this);
		}
		catch(Exception e)
		{
			System.err.println("Could not connect to database: " + 
					e.getMessage());
			e.printStackTrace();
		}
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	private String getFilename() {
		return filename;
	}
	private void setConnection(Connection connection) {
		this.connection = connection;
	}
	private Connection getConnection() {
		return connection;
	}
	
	/* ******************************* SQL STUFF STARTS HERE **************** */
	@Override
	public void runQuery(String query) throws DBAbstractionException
	{
		try
		{
			Statement statement;
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			statement.executeUpdate(query);
		}
		catch(Exception e)
		{
			System.err.println("In a runQuery call, " + query + " failed.");
			throw new DBAbstractionException(e);
		}
	}
	
	@Override
	public ResultSet runResult(String query) throws DBAbstractionException
	{
		try
		{
			Statement statement;
			ResultSet results = null;
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			results = statement.executeQuery(query);
			return results;
		}
		catch(Exception e)
		{
			System.err.println("In a runResult class, " + query + " failed.");
			throw new DBAbstractionException(e);
		}
	}
	
    @Override
    public void close() throws DBAbstractionException
    {
    	try
    	{
    		//close the connection to the database
    		connection.close();
    	}
    	catch(SQLException ex)
    	{
    		throw new DBAbstractionException(ex);
    	}
    }
	
    @Override
	public void refresh() throws DBAbstractionException {
		if(instance != null)
		{
			try {
				instance.close();
			} catch (DBAbstractionException e) {
				e.printStackTrace();
			}
			instance = getInstance(getFilename());
		}	
		
	}
	
	
    /* **************************** QUERY HELPER FUNCTIONS **************************** */
	public static String updateQueryBuilder(String table, 
			List<String> keys, 
			List<String> values,
			List<String> primaryKeys,
			List<String> primaryKeyValues)
	{
		
		if(keys.size() != values.size())
		{
			return "Invalid query because you called upatequerybuilder with two different sized lists.";
		}
		if(primaryKeys.size() != primaryKeyValues.size())
		{
			return "Invalid query because you called updatequeryvuilder with two different size pk lists.";
		}
		
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(UPDATE + table + SET);
		
		for(int i = 0 ; i < keys.size() - 1; i++)
		{
			sb.append(keys.get(i));
			sb.append(EQUAL_TO);
			sb.append(OPENING_QUOTE);
			sb.append(values.get(i));
			sb.append(CLOSING_QUOTE);
			sb.append(COMMA);
		}
		sb.append(keys.get(keys.size() - 1));
		sb.append(EQUAL_TO);
		sb.append(OPENING_QUOTE);
		sb.append(values.get(keys.size() - 1));
		sb.append(CLOSING_QUOTE);
		
		sb.append(WHERE);
		
		for(int i = 0 ; i < primaryKeys.size() - 1; i++)
		{
			sb.append(primaryKeys.get(i));
			sb.append(EQUAL_TO);
			sb.append(OPENING_QUOTE);
			sb.append(primaryKeyValues.get(i));
			sb.append(CLOSING_QUOTE);
			sb.append(AND);
		}
		sb.append(primaryKeys.get(primaryKeys.size() - 1));
		sb.append(EQUAL_TO);
		sb.append(OPENING_QUOTE);
		sb.append(primaryKeyValues.get(primaryKeys.size() - 1));
		sb.append(CLOSING_QUOTE);
		
		return sb.toString();
	}
	
	public static String insertQueryBuilder(String table, 
			List<String> keys, 
			List<String> values) 
	{
		if(keys.size() != values.size())
		{
			return "Getting an invalid query b/c insertquierybuilder got different sized lists.";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(INSERT_INTO + table);
		
		sb.append(OP);
		for(int i = 0 ; i < keys.size() - 1; i++)
		{
			sb.append(keys.get(i));
			sb.append(COMMA);
		}
		sb.append(keys.get(keys.size() - 1));
		sb.append(CP);
		
		sb.append(VALUES);
		sb.append(OP);
		for(int i = 0 ; i < keys.size() - 1; i++)
		{
			sb.append(OPENING_QUOTE);
			sb.append(values.get(i));
			sb.append(CLOSING_QUOTE);
			sb.append(COMMA);
		}
		sb.append(values.get(keys.size() - 1));
		sb.append(CP);
		
		return sb.toString();
	}
	
	
	/* **************************** SPECIFIC QUERIES START HERE **************************** */
	
	
	/* ****************** CONSUMABLE QUERIES ****************** */
	@Override
	public int addConsumable(String name, String type) throws DBAbstractionException {
		return SQLConsumable.getInstance().addConsumable(name, type);
	}
	
	@Override
	public String getConsumableName(int consumableId) throws DBAbstractionException {
		return SQLConsumable.getInstance().getName(consumableId);
	}
	
	@Override
	public String getConsumableType(int consumableId) throws DBAbstractionException {
		return SQLConsumable.getInstance().getType(consumableId);
	}
	
	@Override
	public void setConsumableName(int consumableId, String name) throws DBAbstractionException {
		SQLConsumable.getInstance().setConsumableName(consumableId, name);
	}
	
	@Override
	public void setConsumableType(int consumableId, String type) throws DBAbstractionException {
		SQLConsumable.getInstance().setConsumableType(consumableId, type);
		
	}
	
	public List<Consumable> getAllConsumables() throws DBAbstractionException {
		return SQLConsumable.getInstance().getAllConsumables();
	}
	
	
	/* ****************** USER STUFF ****************** */
	@Override
	public int addUser(String username, String password) throws DBAbstractionException {
		return SQLUser.getInstance().addUser(username, password);
	}

	@Override
	public String getPassword(int userId) throws DBAbstractionException {
		return SQLUser.getInstance().getPassword(userId);
	}
	@Override
	public String getUsername(int userId) throws DBAbstractionException {
		return SQLUser.getInstance().getUsername(userId);
	}
	
	@Override
	public void setPassword(int userId, String password) throws DBAbstractionException {
		SQLUser.getInstance().setPassword(userId, password);
		
	}
	@Override
	public void setUsername(int userId, String name) throws DBAbstractionException {
		SQLUser.getInstance().setUsername(userId, name);
		
	}
	@Override
	public int getIdFromUsername(String username) throws DBAbstractionException {
		return SQLUser.getInstance().getIdFromUsername(username);
	}
	
	
	
	/* ****************** ATTRIBUTE STUFF ****************** */
	@Override
	public int addAttribute(String name) throws DBAbstractionException {
		return SQLAttribute.getInstance().addAttribute(name);
	}
	
	@Override
	public String getAttributeName(int attributeId) throws DBAbstractionException {
		return SQLAttribute.getInstance().getName(attributeId);
	}
	@Override
	public void setAttributeName(int attributeId, String name) throws DBAbstractionException {
		SQLAttribute.getInstance().setName(attributeId, name);
	}
	@Override
	public void addAtrributeRating(int userId, int attributeId, int rating)
			throws DBAbstractionException {
		SQLUserAttribute.getInstance().addAtrributeRating(userId, attributeId, rating);
		
	}
	@Override
	public void editAtrributeRating(int userId, int attributeId, int rating)
			throws DBAbstractionException {
		SQLUserAttribute.getInstance().editAtrributeRating(userId, attributeId, rating);
	}
	@Override
	public List<Attribute> getAllAttributes()
			throws DBAbstractionException {
		return SQLAttribute.getInstance().getAllAttributes();
	}
	@Override
	public List<AttributeRating> getAllRatedAttributes(int userId) throws DBAbstractionException {
		return SQLUserAttribute.getInstance().getAllRatedAttributes(userId);
		
	}
	
	
	/* ************* RECOMMENDATION STUFF ************* */
	@Override
	public void addRecommendationRating(int userId, int consumableId, int initialRating, int newRating) 
	throws DBAbstractionException {
		SQLRecommendation.getInstance().addRecommendationRating(userId, consumableId, initialRating, newRating);
		
	}
	@Override
	public void editRecommendationRating(int userId, int consumableId, int rating) 
	throws DBAbstractionException {
		SQLRecommendation.getInstance().editRecommendationRating(userId, consumableId, rating);
		
	}
	@Override
	public List<Recommendation> getAllConsumablesRated(int userId)
	throws DBAbstractionException {
		return SQLRecommendation.getInstance().getAllConsumablesRated(userId);
	}
	@Override
	public int getInitialRating(int userId, int consumableId)
	throws DBAbstractionException {
		return SQLRecommendation.getInstance().getInitialRating(userId, consumableId);
	}
	@Override
	public int getRevisedRating(int userId, int consumableId)
	throws DBAbstractionException {
		return SQLRecommendation.getInstance().getRevisedRating(userId, consumableId);
	}

}
