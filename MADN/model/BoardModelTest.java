/*
 * Created on 05.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardModelTest extends JFrame implements ActionListener{
  
  BoardModel bm;
  JComboBox cbColor;
  JComboBox cbIds;
  JComboBox cbDistances;
  JButton btOk = new JButton();
  JButton btDispose = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea taMessages = new JTextArea();


  public BoardModelTest() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    BoardModelTest boardModelTest = new BoardModelTest();
    boardModelTest.setVisible(true);
  }
  private void jbInit() throws Exception {
    this.setSize(new Dimension(300, 175));
    this.setTitle("BoardModelTest");
    this.getContentPane().setLayout(null);
    Object[] colors = {"Rot","Schwarz","Blau","Grün",};
    cbColor = new JComboBox(colors);
    cbColor.setEditable(false);
    cbColor.setBounds(new Rectangle(5, 7, 60, 21));
    Object[] ids = {"1","2","3","4"};
    cbIds = new JComboBox(ids);
    cbIds.setEditable(false);
    cbIds.setBounds(new Rectangle(70, 7, 60, 21));
    Object[] distances = {"1","2","3","4","5","6"};
    cbDistances = new JComboBox(distances);
    cbDistances.setEditable(false);
    cbDistances.setBounds(new Rectangle(135, 7, 60, 21));
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setResizable(false);
    btOk.setBounds(new Rectangle(200, 5, 90, 25));
    btOk.setActionCommand("action");
    btOk.setText("OK");
    btOk.addActionListener(this);
    btDispose.setBounds(new Rectangle(200, 115, 90, 25));
    btDispose.setActionCommand("dispose");
    btDispose.setText("Beenden");
    btDispose.addActionListener(this);
    jScrollPane1.setBounds(new Rectangle(5, 35, 285, 75));
    taMessages.setEditable(false);
    this.getContentPane().add(cbIds, null);
    this.getContentPane().add(cbDistances, null);
    this.getContentPane().add(cbColor, null);
    this.getContentPane().add(btDispose, null);
    this.getContentPane().add(btOk, null);
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(taMessages, null);
    bm = new BoardModel();
  }

  
public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand()=="dispose"){
		this.dispose();
	}else if (e.getActionCommand()=="action"){
		try {
			int pos = bm.move(cbColor.getSelectedIndex(), cbIds.getSelectedIndex(), cbDistances.getSelectedIndex()+1);
			taMessages.setText("["+ cbColor.getSelectedItem() + " " + cbIds.getSelectedItem() + " (+"+ cbDistances.getSelectedItem() +")] "+"Neue Position: " + pos);
			if (bm.isGameOver()){
				taMessages.setText(taMessages.getText() + "\nGAME OVER!!!" + "\n Neues Spiel");
				bm.reset();
			}
		} catch (InvalidMoveException e1) {
			e1.printStackTrace();
			taMessages.setText("["+ cbColor.getSelectedItem() + " " + cbIds.getSelectedItem() + " (+"+ cbDistances.getSelectedItem() +")] "+ e1.getMessage());
		}
	}
	
}
}