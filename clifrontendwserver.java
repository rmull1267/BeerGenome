import gui.GUIMainTabbedPane;
import gui.GUISimple;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JOptionPane;

import core.Attribute;
import core.AttributeRating;
import core.Consumable;
import core.CoreException;
import core.LoginException;
import core.Rating;
import core.Recommendation;

import client.Client;
import client.ClientAttribute;
import client.ClientConsumable;
import client.ClientException;
import client.ClientRecommendation;
import client.ClientUser;
import server.Server;
import server.ServerException;
import database.DBAbstractionException;
import database.SQLDatabase;


public class clifrontendwserver {
	static ClientUser user;
	static Server 	  s;
	
	public static void main(String[] args) throws ClientException, ServerException
	{
		SQLDatabase.getInstance("russell.db");
		s = new Server(2332);
		s.startServer();
		//s.setLoggerEnabled(true);
		
		Client.initializeClient(2332, "127.0.0.1");

		GUIMainTabbedPane menu = new GUIMainTabbedPane();

	}
}
