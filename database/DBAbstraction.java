package database;

import java.sql.ResultSet;
import java.util.List;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.Recommendation;

import server.ServerAttribute;

public interface DBAbstraction {
	/* ************* close and refresh *********************** */
	void close() throws DBAbstractionException;
	void refresh() throws DBAbstractionException;
	
	/* ************* General Tools *********************** */
	public ResultSet runResult(String query) throws DBAbstractionException;
	public void runQuery(String query) throws DBAbstractionException;
	
	/* ************* Consumable Information ************* */
	public int addConsumable(String name, String type) throws DBAbstractionException;
	public String getConsumableName(int consumableId) throws DBAbstractionException;
	public String getConsumableType(int consumableId) throws DBAbstractionException;
	public void setConsumableName(int consumableId, String name) throws DBAbstractionException;
	public void setConsumableType(int consumableId, String type) throws DBAbstractionException;
	public List<Consumable> getAllConsumables() throws DBAbstractionException;
	public List<Consumable> searchConsumable(String phrase) throws DBAbstractionException;
	
	/* ************* User Information ************* */
	public int addUser(String username, String password) throws DBAbstractionException;
	
	
	
	/**
	 * 
	 * @param username
	 * @return 0 if the username doesn't exist, the username otherwise.
	 */
	public int getIdFromUsername(String username) throws DBAbstractionException;
	public String getUsername(int userId) throws DBAbstractionException;
	public String getPassword(int userId) throws DBAbstractionException;
	public void setUsername(int userId, String name) throws DBAbstractionException;
	public void setPassword(int userId, String password) throws DBAbstractionException;
	
	
	/* ************* Attribute Information ************* */
	public int addAttribute(String name) throws DBAbstractionException;
	public String getAttributeName(int attributeId) throws DBAbstractionException;
	public void setAttributeName(int attributeId, String name) throws DBAbstractionException;
	public List<Attribute> getAllAttributes() throws DBAbstractionException;
	
	/* ************* User Attribute Join Table Information ************* */
	public void addAtrributeRating(int userId, int attributeId, int rating) throws DBAbstractionException;
	public void editAtrributeRating(int userId, int attributeId, int rating) throws DBAbstractionException;
	public List<AttributeRating> getAllRatedAttributes(int userId) throws DBAbstractionException;
	
	/* ************* Recommendation Table Information ************* */
	public void addRecommendationRating(int userId, int consumableId, int inititalRating, int newRating) throws DBAbstractionException;
	public void editRecommendationRating(int userId, int consumableId, int rating) throws DBAbstractionException;
	public List<Recommendation> getAllConsumablesRated(int userId) throws DBAbstractionException;
	public int getInitialRating(int userId, int consumableId) throws DBAbstractionException;
	public int getRevisedRating(int userId, int consumableId) throws DBAbstractionException;
	
}
