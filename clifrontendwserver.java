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
		sb.append("0. Exit\n");
		sb.append("1. Register\n");
		sb.append("2. Login\n");
		sb.append("3. See information for currently logged in user\n");
		sb.append("4. Set Password\n\n");
		
		sb.append("5. List Attributes\n");
		sb.append("6. Add Attribute\n");
		sb.append("7. Rate Attribute\n\n");
		
		sb.append("8. List Consumables\n");
		sb.append("9. Add Consumable\n");
		sb.append("10. Rate Consumable\n");
		
		
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
		
		
		if(menuChoice > 2 && user == null)
		{
			print("user must be set to continue.");
			return;
		}
		
		if(menuChoice == 3)
		{	
			print(user.getUserId() + "," + user.getUsername() + 
					"," + user.getPassword());
		}
		
		if(menuChoice == 4)
		{
			String newPass = getString("New Password");
			user.setPassword(newPass);
			print("Password set. View information about currently logged in user to see");
		}
		
		if(menuChoice == 5)
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
		
		if(menuChoice == 6)
		{
			String name = getString("Attribute name");
			ClientAttribute ca = new ClientAttribute(name);
			print(ca.getAttributeId() + "," + ca.getName());
		}
		
		if(menuChoice == 7)
		{
			String attributeId = getString("Attribute ID");
			
			Attribute attribute = new ClientAttribute(Integer.parseInt(attributeId));
			int newRating = Integer.parseInt(getString("Attribute Rating"));
			
			user.setAttributeRating(attribute, newRating);
			print("rating appllied. List attribtues to see.");
		}
		
		if(menuChoice == 8)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("RAted consumables (id,name,type,initialRating,revisedRating): \n");
			for(Recommendation r : user.getRatedConsumables())
			{
				sb.append(r.getConsumable().getConsumableId());
				sb.append("," + r.getConsumable().getName() + "," + r.getConsumable().getType());
				sb.append(",");
				sb.append(r.getInitialRating());
				sb.append(",");
				sb.append(r.getRevisedRating());
				sb.append("\n");
			}
			
			sb.append("All consumables:\n");
			//we have to create a consumable on which to call getAllConsumables
			ClientConsumable initialc = new ClientConsumable(1); 
			for(Consumable c : initialc.getAllConsumables())
			{
				sb.append(c.getConsumableId());
				sb.append(",");
				sb.append(c.getName() + "," + c.getType());
				sb.append("\n");
			}
			
			print(sb.toString());
		}
		
		if(menuChoice == 9)
		{
			try
			{
				new ClientConsumable(
						getString("Name (must be unique)"),
						getString("Type")
				);
			}
			catch(ClientException e)
			{
				print("did not create consumable -- name must be unique.");
				return;
			}
			
			print("Added consumable.");
		}
		
		if(menuChoice == 10)
		{
			int consumableId = Integer.parseInt(getString("Consumable ID"));
			ClientConsumable c = new ClientConsumable(consumableId);
			
			Rating r = new Rating(user,c);
			print("Rating " + c.getName() + " With initial rating " + r.getInitialRating());
			
			int revisedRating = Integer.parseInt(getString("New Rating"));
			

			user.setRecommendationRating(c, revisedRating);
			print("Creommendation created");
		}
		
		
		if(menuChoice == 0)
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
