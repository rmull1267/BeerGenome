package server;

import core.Consumable;
import core.Rating;
import core.Recommendation;
import core.User;
import database.DBAbstractionException;
import database.SQLDatabase;

public class ServerRecommendation extends Recommendation {

	public ServerRecommendation(User user, Consumable consumable)
	{
		constructorHelper(user, consumable);
	}
	
	@Override
	protected void constructorHelper(User user, Consumable consumable) {
		setUserWithoutCommit(user);
		setConsumableWithoutCommit(consumable);
		
		try
		{
			this.setInitialRatingWithoutCommit(
					SQLDatabase.getInstance().getInitialRating(user.getUserId(), consumable.getConsumableId())
			);
					
			this.setRevisedRatingWithoutCommit(
					SQLDatabase.getInstance().getRevisedRating(user.getUserId(), consumable.getConsumableId())
			);
		}
		catch(DBAbstractionException e)
		{
			e.printStackTrace();
		}
	}
	
	public ServerRecommendation(User user, Consumable consumable, int revisedRating)
	{
		constructorHelper(user, consumable, revisedRating);
	}

	//TODO-nf-refactor throw exceptions.
	@Override
	protected void constructorHelper(User user, Consumable consumable, int revisedRating) {
		Rating rating = new Rating(user, consumable);
		
		this.setUserWithoutCommit(user);
		this.setConsumableWithoutCommit(consumable);
		this.setRevisedRatingWithoutCommit(revisedRating);
		this.setInitialRatingWithoutCommit(rating.getInitialRating());
		
		try {
			SQLDatabase.getInstance().addRecommendationRating(user.getUserId(), 
					consumable.getConsumableId(), 
					getInitialRating(), 
					getRevisedRating()
			);
		} catch (DBAbstractionException e) {
			this.setUserWithoutCommit(null);
			this.setConsumableWithoutCommit(null);
			this.setRevisedRatingWithoutCommit(0);
			this.setInitialRatingWithoutCommit(0); 
			e.printStackTrace();
		}
		
	}

	@Override
	public void commit() throws DBAbstractionException {
		SQLDatabase.getInstance().editRecommendationRating(getUser().getUserId(), 
				getConsumable().getConsumableId(), 
				getRevisedRating());
	}

	@Override
	public void refresh() throws DBAbstractionException {
		setRevisedRatingWithoutCommit(
				SQLDatabase.getInstance().getRevisedRating(getUser().getUserId(), getConsumable().getConsumableId())
		);
		
		setInitialRatingWithoutCommit(
				SQLDatabase.getInstance().getInitialRating(getUser().getUserId(), getConsumable().getConsumableId())
		);
		
	}

}
