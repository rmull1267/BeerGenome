import server.Server;
import server.ServerException;
import client.Client;
import client.ClientException;
import client.ClientUser;
import database.SQLDatabase;
import gui.GUIMainTabbedPane;
import gui.GUISimple;


public class GUITestDriver 
{
	static ClientUser user;
	static Server 	  s;
	
	public static void main(String[] args) throws ClientException, ServerException
	{
		GUIMainTabbedPane pane = new GUIMainTabbedPane();
		
		SQLDatabase.getInstance("BeerGenomeDatabase.db");
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
