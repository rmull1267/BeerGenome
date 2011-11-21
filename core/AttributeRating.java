package core;

/**
 * Note that unlike many of the classes in core, AttributeRating is NOT an ORM class.
 * @author nfulton
 *
 */
public class AttributeRating {
	private Attribute attribute;
	private int rating;
	public AttributeRating(Attribute a, int rating)
	{
		setAttribute(a);
		setRating(rating);
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getRating() {
		return rating;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public Attribute getAttribute() {
		return attribute;
	}
}
