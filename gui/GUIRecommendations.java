package gui;

import java.util.Vector;

import core.Consumable;

import client.ClientUser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUIRecommendations.java
 *
 * Created on Nov 29, 2011, 3:24:37 PM
 */
/**
 *
 * @author Russell Mull
 */
public class GUIRecommendations extends javax.swing.JPanel {

    /** Creates new form GUIRecommendations */
    public GUIRecommendations() 
    {
        initComponents();
    }

    //********************************************
    public Vector<String> getUserRecommendations()
    {
    	Vector<String> retVec = new Vector<String>();
    	
    	for(Consumable c: DataAbstraction.getInstance().getRecommendedConsumable())
    	{
    		retVec.add(c.getName());
    	}
    	
    	return retVec;
    }
    
    public void populateList()
    {
    	resultsList.setModel(new javax.swing.AbstractListModel() {
            Vector<String> strings = getUserRecommendations();
            public int getSize() { return strings.size(); }
            public Object getElementAt(int i) { return strings.get(i); }
        });
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

        listScrollPane = new javax.swing.JScrollPane();
        resultsList = new javax.swing.JList();
        nameLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        textAreaScrollPane = new javax.swing.JScrollPane();
        attributeTextArea = new javax.swing.JTextArea();
        addToConsumablesButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(4, 32767));

        listScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

//        resultsList.setModel(new javax.swing.AbstractListModel() {
//            Vector<String> strings = getUserRecommendations();
//            public int getSize() { return strings.size(); }
//            public Object getElementAt(int i) { return strings.get(i); }
//        });
        resultsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                resultsListValueChanged(evt);
            }
        });
        listScrollPane.setViewportView(resultsList);

        nameLabel.setText("Name:");

        typeLabel.setText("Type:");

        attributeTextArea.setColumns(20);
        attributeTextArea.setRows(5);
        textAreaScrollPane.setViewportView(attributeTextArea);

        addToConsumablesButton.setText("Add To MyConsumables");
        addToConsumablesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToConsumablesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addToConsumablesButton)
                    .addComponent(nameLabel)
                    .addComponent(typeLabel)
                    .addComponent(textAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addToConsumablesButton)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void resultsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_resultsListValueChanged
// TODO add your handling code here:
}//GEN-LAST:event_resultsListValueChanged

private void addToConsumablesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToConsumablesButtonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_addToConsumablesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToConsumablesButton;
    private javax.swing.JTextArea attributeTextArea;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JList resultsList;
    private javax.swing.JScrollPane textAreaScrollPane;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
