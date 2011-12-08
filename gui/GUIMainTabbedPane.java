package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import client.ClientUser;

public class GUIMainTabbedPane extends JFrame
{
	public GUIMyConsumables myConsumables;
	public GUIMyAttributes myAttributes;
	private GUISearch mySearch;
	public GUIRecommendations myRecommendations;
	
	private static ClientUser user;
	
	public GUIMainTabbedPane()
	{
		JTabbedPane jtp = new JTabbedPane();
		
		initializeComponents();
		
		jtp.add("My Profile", new GUIMyProfile(this));
		jtp.add("My Consumables", myConsumables);
		jtp.add("My Attributes", myAttributes);
		jtp.add("Search", mySearch);
		jtp.add("Recommendations", myRecommendations);
		
		add(jtp);
		setSize(450, 350);
		setMaximumSize(new Dimension(450,350));
		setVisible(true);
	}
	
	private void initializeComponents()
	{
		myConsumables = new GUIMyConsumables();
		myAttributes = new GUIMyAttributes();
		mySearch = new GUISearch();
		myRecommendations = new GUIRecommendations();
	}
}
