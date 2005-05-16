/*
 * Created on 16.05.2005
 */
package gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author Mario
 */
public class InfoDialog extends JDialog implements ActionListener {

	  JPanel pnInfos = new JPanel();
	  JLabel lbRules = new JLabel();
	  JTextArea taRules = new JTextArea();
	  JScrollPane spRules = new JScrollPane();
	  
	  JLabel lbControls = new JLabel();
	  JTextArea taControls = new JTextArea();
	  JScrollPane spControls = new JScrollPane();
	  
	  JButton btClose = new JButton(Toolbox.loadExitIcon(this.getClass()));

	  public InfoDialog(GameFrame parent) {
	  	
	  	super(parent, "Spielregeln und Steuerung", false);
	    
	  	int xSize = 426, ySize=386;
	  	
	  	this.setBounds(parent.getBounds().x + (parent.getBounds().width - xSize)/2, parent.getBounds().y + (parent.getBounds().height - xSize)/2, xSize, ySize); 
	    this.setResizable(false);
	    
	    this.getContentPane().setLayout(null);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	   	    
	    pnInfos.setBackground(this.getBackground());
	    pnInfos.setBorder(null/*BorderFactory.createEtchedBorder(EtchedBorder.RAISED)*/);
	    pnInfos.setLayout(null);
	    pnInfos.setBounds(new Rectangle(10, 10, 400, 300));
	    
	    	lbRules.setText("Spielanleitung");
	   	    lbRules.setFont(new java.awt.Font("Dialog", 1, 12));
	    	lbRules.setBounds(new Rectangle(10,10,100,25));
	    	
	    	spRules.setBounds(new Rectangle(20, 45, 370, 125));
	    	spRules.setBorder(null);
	    	spRules.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    	spRules.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    		taRules.setBorder(null);
		    	taRules.setBackground(this.getBackground());
	    		taRules.setFont(new java.awt.Font("Dialog", 0, 11));
		    	taRules.setEditable(false);
		    	taRules.setText("");
		    spRules.getViewport().add(taRules, null);
	    	
	    	lbControls.setText("Steuerung");
	    	lbControls.setFont(new java.awt.Font("Dialog", 1, 12));
	    	lbControls.setBounds(new Rectangle(10,190,100,25));

	    	spControls.setBounds(new Rectangle(20, 215, 370, 75));
	    	spControls.setBorder(null);
	    	spControls.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    	spControls.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    		taControls.setBorder(null);
	    		taControls.setBackground(this.getBackground());
	    		taControls.setFont(new java.awt.Font("Dialog", 0, 11));
	    		taControls.setEditable(false);
	    		taControls.setText(
	    				"1. Linke Maustaste + Mausbewegung: Rotation Spielfeld\n" +
						"2. Mittlere Maustaste + Mausbewegung: Zoom in/out\n" +
						"3. Linke Maustaste Klick auf Spielstein: Selektion\n" +
						"4. Linke Maustaste Doppelklick auf Spielstein: Selektion und Rücken\n" +
						"5. Rechte Maustaste: Würfeln"
	    		);
		    spControls.getViewport().add(taControls, null);	    	
	    	
	    pnInfos.add(lbRules);
	    pnInfos.add(spRules);
	    pnInfos.add(spControls);
	    pnInfos.add(lbControls);
	    	

	    btClose.setBounds(new Rectangle(300, 320, 110, 25));
	    btClose.setToolTipText("");
	    btClose.setText("Schließen");
	    btClose.setActionCommand("dispose");
	    btClose.addActionListener(this);
	    //this.getContentPane().add(spInfos, null);
	    this.getContentPane().add(pnInfos, null);
	    this.getContentPane().add(btClose, null);
	    
	  }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("dispose")){
			this.dispose();
		}
	}
}
