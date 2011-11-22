import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JOptionPane;

import core.Attribute;
import core.AttributeRating;
import core.LoginException;

import client.Client;
import client.ClientAttribute;
import client.ClientException;
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
		SQLDatabase.getInstance("BeerGenomeDatabase.db");
		s = new Server(2332);
		s.startServer();
		
		Client.initializeClient(2332, "127.0.0.1");
		
		while(true)
		{
			menu();
		}
	}
	
	public static void menu()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Menu:\n");
		sb.append("1. Register\n");
		sb.append("2. Login\n");
		sb.append("3. See information for currently logged in user\n");
		sb.append("4. List Attributes\n");
		sb.append("5. Add Attribute\n");
		sb.append("6. Rate Attribute\n");
		sb.append("7. Set Password\n");
		sb.append("8. Exit\n");
		
		String num = JOptionPane.showInputDialog(sb.toString());
		
		processMenuChoice(Integer.parseInt(num));
	}
	
	public static void processMenuChoice(int menuChoice)
	{
		
		
		//Register
		if(menuChoice == 1)
		{
			try {
				user = new ClientUser(
						getString("Username"),
						getString("Password"),
						true
				);
			} catch (LoginException e) {
				print("Registration failed.");
				return;
			}
			print("OK. Welcome to the Beer Genome Project!");
		}
		
		if(menuChoice == 2)
		{
			try {
				user = new ClientUser(
						getString("Username"),
						getString("Password"),
						false
				);
			} catch (LoginException e) {
				print("Login failed.");
				return;
			}
			print("OK. Welcome back!");
		}
		
		if(menuChoice == 3)
		{
			if(user == null)
			{
				print("User is null.");
				return;
			}
			
			print(user.getUserId() + "," + user.getUsername() + 
					"," + user.getPassword());
		}
		
		if(menuChoice == 4)
		{
			ClientAttribute attribute = new ClientAttribute(1);
			
			
			List<AttributeRating> rated = user.getAllRatedAttributes();
			List<Attribute> unrated = attribute.getAllAttributes(); 
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Rated Attributes:\n");
			for(AttributeRating rating : rated)
			{
				sb.append(rating.getAttribute().getName() + ",rated:" + rating.getRating()+"\n");
			}
			sb.append("Unrated Attributes:\n");
			for(Attribute r : unrated)
			{
				sb.append(r.getAttributeId() + "," + r.getName() + "\n");
			}
			
			print(sb.toString());
		}
		
		if(menuChoice == 5)
		{
			String name = getString("Attribute name");
			ClientAttribute ca = new ClientAttribute(name);
			print(ca.getAttributeId() + "," + ca.getName());
		}
		
		if(menuChoice == 6)
		{
			String attributeId = getString("Attribute ID");
			
			Attribute attribute = new ClientAttribute(Integer.parseInt(attributeId));
			int newRating = Integer.parseInt(getString("Attribute Rating"));
			
			user.setAttributeRating(attribute, newRating);
		}
		
		if(menuChoice == 7)
		{
			String newPass = getString("New Password");
			user.setPassword(newPass);
		}
		
		
		if(menuChoice == 8)
		{
			try {
				s.stopServer();
			} catch (ServerException e) {
				e.printStackTrace();
			}
			
			try {
				SQLDatabase.getInstance().close();
			} catch (DBAbstractionException e) {
				e.printStackTrace();
			}
			
			System.exit(0);
		}
	}
	
	public static void print(String s)
	{
		JOptionPane.showMessageDialog(null, s);
	}
	
	public static String getString(String fieldName)
	{
		return JOptionPane.showInputDialog("Enter " + fieldName + ":");
	}
}
