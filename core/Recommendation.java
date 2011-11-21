package core;

import database.DBAbstractionException;

public abstract class Recommendation implements ORMClass {
	private User user;
	private int initialRating;
	private int revisedRating;
	private Consumable consumable;
	
	/* ***************** METHODS TO IMPLEMENT ***************** */
	//"Load Constructor"
	protected abstract void constructorHelper(User user, Consumable consumable);
	//"Create Constructor"
	protected abstract void constructorHelper(User user, Consumable consumable, int revisedRating);
	
	public void setUserWithoutCommit(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	public void setInitialRatingWithoutCommit(int initialRating) {
		this.initialRating = initialRating;
	}
	public int getInitialRating() {
		return initialRating;
	}
	
	public void setRevisedRatingWithoutCommit(int revisedRating) {
		this.revisedRating = revisedRating;
	}
	public void setRevisedRating(int revisedRating) {
		setRevisedRatingWithoutCommit(revisedRating);
		try {
			commit();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
	}
	public int getRevisedRating() {
		return revisedRating;
	}
	public void setConsumableWithoutCommit(Consumable consumable) {
		this.consumable = consumable;
	}
	public Consumable getConsumable() {
		return consumable;
	}
	
	
}
