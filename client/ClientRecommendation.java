package client;

import protocol.CreateRecommendationMessage;
import protocol.GetRecommendationMessage;
import protocol.ProtocolException;
import protocol.SetRecommendationsRevisedRatingMessage;
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
		constructorHelper(user, consumable);
	}
	
	public ClientRecommendation(ClientUser user, ClientConsumable consumable, int revisedRating) {
		constructorHelper(user, consumable, revisedRating);
	}
	
	@Override
	protected void constructorHelper(User user, Consumable consumable) {
		setUserWithoutCommit(user);
		setConsumableWithoutCommit(consumable);
		
		GetRecommendationMessage m = new GetRecommendationMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void constructorHelper(User user, Consumable consumable, int revisedRating) {
		setUserWithoutCommit(user);
		setConsumableWithoutCommit(consumable);
		setRevisedRating(revisedRating);
		
		CreateRecommendationMessage m = new CreateRecommendationMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commit() throws DBAbstractionException {
		SetRecommendationsRevisedRatingMessage m = 
			new SetRecommendationsRevisedRatingMessage(this);
		
		try {
			m.processResponse(Client.getInstance().sendMessage(m.generateMessage()));
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	/*
	 * JRC: Not sure if I did this one the right way.  If there's a problem with refresh, assume it's my fault.
	 */
	@Override
	public void refresh() throws DBAbstractionException {
		constructorHelper(this.getUser(), this.getConsumable(), this.getRevisedRating());
	}

}
