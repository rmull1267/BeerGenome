package client;

import core.Consumable;
import core.Recommendation;
import core.User;
import database.DBAbstractionException;

/**
 * TODO-nf
 * @author nfulton
 *
 */
public class ClientRecommendation extends Recommendation {

	public ClientRecommendation(ClientUser user, ClientConsumable consumable) {
		setUserWithoutCommit(user);
		setConsumableWithoutCommit(consumable);
	}
	
	public ClientRecommendation(ClientUser user, ClientConsumable consumable, int revisedRating) {
		setUserWithoutCommit(user);
		setConsumableWithoutCommit(consumable);
		setRevisedRating(revisedRating);
	}
	
	@Override
	protected void constructorHelper(User user, Consumable consumable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void constructorHelper(User user, Consumable consumable,
			int revisedRating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() throws DBAbstractionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() throws DBAbstractionException {
		// TODO Auto-generated method stub
		
	}

}
