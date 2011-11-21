package gui;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIMyConsumables extends JPanel
{
	public GUIMyConsumables()
	{
		setLayout(new GridBagLayout());
	}
	
	public void setUpGUI()
	{
		JLabel nameLabel = new JLabel("My Consumables");
	}
}

//**************************************************************
//*		My Consumables(JLabel)								   *
//*															   *
//*     **************************************************     *
//*     * - Bud Light                                 *  *     *
//*     * - Miller Light                              *  *     *
//*     *                                             *  *     *
//*     *					(JTextArea)               *  *     *
//*     *                                             *  *     *
//*     * etc......                                   *  *     *
//*     **************************************************     *
//*															   *
//**************************************************************
