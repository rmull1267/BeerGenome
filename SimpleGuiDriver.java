import javax.swing.JOptionPane;

import gui.GUISimple;
import client.Client;
import client.ClientException;


public class SimpleGuiDriver {
	public static void main(String[] args)
	{
		String ip = JOptionPane.showInputDialog("IP Address:");
		int port = Integer.parseInt(JOptionPane.showInputDialog("Port:"));
		
		try {
			Client.initializeClient(port,ip);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		while(true)
		{
			GUISimple.menu();
		}
	}
}
