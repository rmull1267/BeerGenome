package core;

import java.util.List;

/**
 * Note that, unlike most stuff in core, this is NOT an ormclass.
 * @author nfulton
 *
 */
public class Rating {
	private User user;
	private Consumable consumable;
	int cachedRating = -1;
	
	public static final int OUT_OF = 5;
	
	public Rating(User user, Consumable consumable)
	{
		setUser(user);
		setConsumable(consumable);
	}
	
	public int getInitialRating()
	{
		if(cachedRating != -1)
		{
			return cachedRating;
		}
		
		int rating = 0;
		
		if(getUser() == null)
		{
			return 0;
		}
		if(getConsumable() == null)
		{
			return 0;
		}
		
		List<Recommendation> ratedConsumables = getUser().getRatedConsumables();
		
		if(ratedConsumables == null)
		{
			return 0;
		}
		
		int confidence = 0;
		for(Recommendation r : ratedConsumables)
		{
			if(r == null)
			{
				continue;
			}
			if(r.getConsumable() == null)
			{
				continue;
			}
			if(r.getConsumable().getType() == null)
			{
				continue;
			}
			if(getConsumable().getType() == null)
			{
				continue;
			}
			
			if(r.getConsumable().getType().equals(getConsumable().getType()))
			{				
				if(r.getConsumable() == null)
				{
					return 0;
				}
				
				if(r.getRevisedRating() > rating)
				{
					if(r.getInitialRating() > r.getRevisedRating())
					{
						rating += 1;
					}
					else
					{
						rating += 2;
					}
				}
				else if(r.getRevisedRating() < rating)
				{
					if(r.getInitialRating() > r.getRevisedRating())
					{
						rating -= 2;
					}
					else
					{
						rating -= 1;
					}
				}
				else
				{
					if(++confidence == 5)
					{
						break;
					}
				}
			}
		}
		
		cachedRating = rating;
		return rating;
		//return getConsumable().getConsumableId();
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
