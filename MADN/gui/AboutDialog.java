/*
 * Created on 17.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutDialog extends JDialog implements ActionListener {
    
    JLabel lbLogo = null;
    JLabel lbP3 = new JLabel();
    JLabel lbP1 = new JLabel();
    JLabel lbP2 = new JLabel();
    JLabel lbM3 = new JLabel();
    JLabel lbM1 = new JLabel();
    JLabel lbM2 = new JLabel();
    JLabel lbEvent = new JLabel();
    JLabel lbFH = new JLabel();
    JLabel lbGroup = new JLabel();
    JButton btClose = new JButton();
    
   public AboutDialog (GameFrame parent) {
		super(parent, "‹ber...", false );
	  	int xSize = 321, ySize=288;
	  	
	  	this.setBounds(parent.getBounds().x + (parent.getBounds().width - xSize)/2, parent.getBounds().y + (parent.getBounds().height - xSize)/2, xSize, ySize); 
	    this.setResizable(false);
	    
	    this.getContentPane().setLayout(null);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            jbInit();
        } catch(Exception e) {
            e.printStackTrace();
        }
		
    }
    
    /**
     * Initialisierung der graphischen Oberfl‰che. 
     * @throws Exception
     */
    private void jbInit() throws Exception {
        
        lbLogo = new JLabel(Toolbox.loadLogoIcon(getClass()), JLabel.CENTER);
		lbLogo.setText("");
        lbLogo.setBounds(new Rectangle(10, 10, 295, 80));
 
       	lbFH.setText("FH-Mannheim SS2005");
        lbFH.setBounds(new Rectangle(20, 100, 200, 16));
        
        lbEvent.setText("Vorlesung VSY (8I)");
        lbEvent.setBounds(new Rectangle(20, 120, 200, 16));
            	
    	lbGroup.setText("Gruppe:");
        lbGroup.setBounds(new Rectangle(20, 150, 60, 16));
        lbGroup.setHorizontalAlignment(SwingConstants.RIGHT);
        
        lbP1.setText("Drienser, Robert");
        lbP1.setBounds(new Rectangle(90, 150, 90, 15));
        lbP1.setFont(new java.awt.Font("Dialog", 0, 11));  
  
        lbM1.setText("[0220540]");
        lbM1.setBounds(new Rectangle(180, 150, 125, 15));
        lbM1.setFont(new java.awt.Font("Dialog", 0, 11));          
        
        lbP2.setText("Flat, Waldemar");
        lbP2.setBounds(new Rectangle(90, 170, 90, 15));
        lbP2.setFont(new java.awt.Font("Dialog", 0, 11));
  
        lbM2.setText("[0221571]");
        lbM2.setBounds(new Rectangle(180, 170, 125, 15));
        lbM2.setFont(new java.awt.Font("Dialog", 0, 11));          
        
        lbP3.setText("Schmitt, Mario");
        lbP3.setBounds(new Rectangle(90, 190, 90, 15));
        lbP3.setFont(new java.awt.Font("Dialog", 0, 11));

        lbM3.setText("[0220591]");
        lbM3.setBounds(new Rectangle(180, 190, 125, 15));
        lbM3.setFont(new java.awt.Font("Dialog", 0, 11));        
        
        btClose.setBounds(new Rectangle(195, 220, 110, 25));
        btClose.setText("Schlieﬂen");
        btClose.setActionCommand("dispose");
        btClose.addActionListener( this );
        btClose.setIcon(Toolbox.loadStopIcon(getClass()));
        
        this.getContentPane().setLayout(null);
        this.getContentPane().add(lbLogo, null);
        this.getContentPane().add(lbP3, null);
        this.getContentPane().add(lbP1, null);
        this.getContentPane().add(lbP2, null);
        this.getContentPane().add(lbM3, null);
        this.getContentPane().add(lbM1, null);
        this.getContentPane().add(lbM2, null);        
        this.getContentPane().add(lbEvent, null);
        this.getContentPane().add(lbFH, null);
        this.getContentPane().add(lbGroup, null);
        this.getContentPane().add(btClose, null);
    }

    /**
     * Reaktion auf den Beenden-Button.
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("dispose")) {
            this.dispose();
        }
    }

}
