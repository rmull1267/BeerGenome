import static org.junit.Assert.fail;
import gui.GUISimple;
import client.Client;
import client.ClientException;
import client.ClientUser;
import server.Server;
import server.ServerConsumable;
import server.ServerException;
import database.DBAbstractionException;
import database.SQLDatabase;
import database.SQLUserAttribute;


public class Driver {
	static ClientUser user;
	static Server 	  s;
	public static void main(String[] args) throws ServerException, ClientException
	{
		s = new Server(2332);
		s.startServer();
		
		Client.initializeClient(2332, "127.0.0.1");
		
		GUISimple.s = s;
		while(true)
		{
			GUISimple.menu();
		}
	}
}
