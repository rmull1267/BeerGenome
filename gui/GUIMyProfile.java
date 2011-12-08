package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import core.LoginException;
import client.ClientUser;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIMyProfile.java
 *
 * Created on Nov 21, 2011, 4:17:35 PM
 */
/**
 *
 * @author Russell Mull
 */
public class GUIMyProfile extends javax.swing.JPanel {

	private GUIMainTabbedPane mainTabbedPane;
	
    /** Creates new form GUIMyProfile */
    public GUIMyProfile(GUIMainTabbedPane tabbedPane) 
    {
        initComponents();
        
        mainTabbedPane = tabbedPane;
    }
    //********************************************
    public void login(boolean isNewAccount)
    {
    	String username = usernameTextField.getText();
    	String password = new String(passwordField.getPassword());
    	
    	try 
    	{
			DataAbstraction.getInstance().setUser(new ClientUser(username, password, isNewAccount));
		} 
    	catch (LoginException e) 
    	{
			//e.printStackTrace();
    		if(isNewAccount == true)
    		{
    			JOptionPane.showMessageDialog(null, "Sorry mate, someone is using that name. Get a new one.");
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "Hey, you definitely need a valid password.");
    		}
		}
    	
    	mainTabbedPane.myAttributes.populateAttributes();
    	mainTabbedPane.myConsumables.populateConsumables();
    }
    //********************************************

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        usernameTextField = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(400, 300));

        usernameLabel.setText("Username:");

        passwordLabel.setText("Password:");

        loginButton.setText("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) 
            {
                login(false);
                JOptionPane.showMessageDialog(null, "Login Successful");
            }
        });

        registerButton.setText("New User");
        registerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		login(true);
        		JOptionPane.showMessageDialog(null, "Registration Successful");
        	}
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameLabel)
                    .addComponent(passwordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerButton)
                    .addComponent(loginButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(passwordField)
                        .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(loginButton)
                .addGap(18, 18, 18)
                .addComponent(registerButton)
                .addContainerGap(84, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
