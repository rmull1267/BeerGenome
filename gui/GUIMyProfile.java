package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//TODO make prettier.
public class GUIMyProfile extends JPanel
{
	public GUIMyProfile()
	{
		setLayout(new GridLayout(2,2));
		
		setUpGUI();
		
		setVisible(true);
	}
	
	public void setUpGUI()
	{
		JLabel usernameLabel = new JLabel("Username:");
		JTextField usernameTextBox = new JTextField();
		
		JLabel passwordLabel = new JLabel("Password:");
		JTextField passwordTextBox = new JTextField();
		
		add(usernameLabel);
		add(usernameTextBox);
		add(passwordLabel);
		add(passwordTextBox);
	}	
}
