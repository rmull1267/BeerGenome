import database.SQLDatabase;
import server.Server;
import server.ServerException;

public class beergenomeserver {

	public static void main(String[] args)
	{
		SQLDatabase.getInstance("BeerGenomeDatabase.db");
		Server s = new Server(2332);
		s.setLoggerEnabled(true);
		try {
			s.startServer();
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}
}
