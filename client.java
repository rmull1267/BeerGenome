
import server.Server;
import server.ServerException;
import client.Client;
import client.ClientException;
import client.ClientUser;
import database.SQLDatabase;

public class client {
	public static void main(String[] args)
	{
		SQLDatabase.getInstance("test.db");
		Server s = new Server(2332);
		try {
			s.startServer();
		} catch (ServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Client.initializeClient(2332, "127.0.0.1");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		

		ClientUser u = new ClientUser(2);
		System.out.println(u.getPassword());
		
		try {
			s.stopServer();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
