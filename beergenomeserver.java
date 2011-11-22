import database.SQLDatabase;
import server.Server;

public class beergenomeserver {

	public static void main(String[] args)
	{
		SQLDatabase.getInstance("BeerGenomeDatabase.db");
		Server s = new Server(2332);
		s.run();
	}
}
