package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasse: DiceAppletTest
 * ======================
 * Frame zur Eingabe von Würfelergebnissen.
 * Verwendung: Test der Würfelanimation. 
 */
public class DiceAppletTest extends JFrame implements ActionListener {
  JButton btDice = new JButton();
  JComboBox cbDistances = new JComboBox();
  DiceApplet da = null;
  public DiceAppletTest(DiceApplet da) {
  	this.da = da;
  	try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    
  	this.setSize(164, 80);
    Object[] distances = {"1","2","3","4","5","6"};
    cbDistances = new JComboBox(distances);
    cbDistances.setEditable(false);
    cbDistances.setBounds(new Rectangle(15, 15, 45, 25));
    this.getContentPane().setLayout(null);
    btDice.setBounds(new Rectangle(70, 15, 75, 25));
    btDice.setText("Würfeln");
    btDice.setActionCommand("dice");
    btDice.addActionListener(this);
    this.getContentPane().add(cbDistances, null);
    this.getContentPane().add(btDice, null);
    this.setVisible(true);
  }

public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("dice")){
		da.startAnimation(cbDistances.getSelectedIndex()+1);
	}
	
}
}
