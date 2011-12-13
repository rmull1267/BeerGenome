package gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.ClientUser;
import database.DBAbstractionException;
import database.SQLDatabase;

public class GUIMainTabbedPane extends JFrame implements WindowListener, ChangeListener
{
	public GUIMyConsumables myConsumables;
	public GUIMyAttributes myAttributes;;
	public GUISearch mySearch;
	public GUIRecommendations myRecommendations;
	
	private static ClientUser user;
	
	public GUIMainTabbedPane()
	{
		JTabbedPane jtp = new JTabbedPane();
		jtp.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(e.getSource() == myConsumables)
				{
					myConsumables.populateConsumables();
				}
				else if(e.getSource() == mySearch)
				{
					System.out.println("Search Tab");
					mySearch.updateAllRecommendations();
					DataAbstraction.getInstance().getMainPane().myConsumables.populateConsumables();
					
					mySearch.search(mySearch.searchPhrase);			
				}
			}
		});
		
		initializeComponents();
		
		jtp.addTab("My Profile", new GUIMyProfile(this));
		jtp.addTab("My Consumables", myConsumables);
		jtp.addTab("My Attributes", myAttributes);
		jtp.addTab("Search", mySearch);
		jtp.addTab("Recommendations", myRecommendations);
		
		add(jtp);
		setSize(450, 350);
		setMaximumSize(new Dimension(450,350));
		setVisible(true);
		
		
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	private void initializeComponents()
	{
		DataAbstraction.getInstance().setMainPane(this);
		myConsumables = new GUIMyConsumables();
		myAttributes = new GUIMyAttributes();
		mySearch = new GUISearch();
		myRecommendations = new GUIRecommendations();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {

		try {
			SQLDatabase.getInstance().close();
		} catch (DBAbstractionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//Overriden so when we change between tabs, the selected tab refreshes
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		if(e.getSource() == myConsumables)
		{
			myConsumables.populateConsumables();
		}
		else if(e.getSource() == mySearch)
		{
			System.out.println("Search Tab");
			mySearch.updateAllRecommendations();
			DataAbstraction.getInstance().getMainPane().myConsumables.populateConsumables();
			
			mySearch.search(mySearch.searchPhrase);			
		}
	}	
}
