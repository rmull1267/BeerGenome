import static org.junit.Assert.fail;
import server.ServerConsumable;
import database.DBAbstractionException;
import database.SQLDatabase;
import database.SQLUserAttribute;


public class Driver {
	public static void main(String[] args)
	{
		SQLDatabase db = SQLDatabase.getInstance("/home/nfulton/Dropbox/softdev_project/BeerGenome/test.db");
	
		try {
			SQLUserAttribute.getInstance().addAtrributeRating(1, 1, 4);
			SQLUserAttribute.getInstance().editAtrributeRating(1, 1, 5);
		} catch (DBAbstractionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String name = "name";
		String newName = "newname";
		String type = "type";
		String newType = "newtype";
		
		ServerConsumable c1 = new ServerConsumable(name, type);
		
		c1.setName(newName);
		if(!c1.getName().equals(newName))
		{
			System.out.println("adsf");
		}
		else 
		{
			System.out.println(c1.getName());
		}
		
		c1.setType(newType);
		if(!c1.getType().equals(newType))
		{
			System.out.println("adsf");
		}
		else
		{
			System.out.println(c1.getType());
		}
		
		try {
			SQLDatabase.getInstance().close();
		} catch (DBAbstractionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		ServerConsumable consumable1 = new ServerConsumable("asdf","asdf3");
		ServerConsumable consumable = new ServerConsumable(consumable1.getConsumableId());
		System.out.println(consumable.getType());
		System.out.println(consumable.getName());
		*/		
	}
}
