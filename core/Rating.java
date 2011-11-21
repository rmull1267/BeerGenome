package core;

/**
 * Note that, unlike most stuff in core, this is NOT an ormclass.
 * @author nfulton
 *
 */
public class Rating {
	private User user;
	private Consumable consumable;
	
	public static final int OUT_OF = 5;
	
	public Rating(User user, Consumable consumable)
	{
		setUser(user);
		setConsumable(consumable);
	}
	
	public int getInitialRating()
	{
		//TODO-nf the recommendation algorithm.
		return 0;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	public Consumable getConsumable() {
		return consumable;
	}
}
