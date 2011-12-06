package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import client.ClientUser;

public class GUIMainTabbedPane extends JFrame
{
	private static ClientUser user;
	
	public GUIMainTabbedPane()
	{
		JTabbedPane jtp = new JTabbedPane();
		
		jtp.add("My Profile", new GUIMyProfile(user));
		jtp.add("My Consumables", new GUIMyConsumables(user));
		jtp.add("My Attributes", new GUIMyAttributes(user));
		jtp.add("Search", new GUISearch(user));
		jtp.add("Recommendations", new GUIRecommendations(user));
		
		add(jtp);
		setSize(450, 350);
		setMaximumSize(new Dimension(450,350));
		setVisible(true);
	}
}
