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
	  
	  JButton btClose = new JButton(Toolbox.loadStopIcon(this.getClass()));

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
		    	taRules.setText(
		    			"<Spielstart>\n"+
		    			"Jeder Spieler besitzt vier gleichfarbige Spielkegel,\n" +
						"die zu Beginn des Spiels auf ihren jeweiligen Start-\n" +
						"bereichen in den Ecken des Spielbretts positioniert\n"+
						"werden. Ausgehend vom Spieler mit der Spielfarbe Rot\n"+
						"(=Serverhost) kommen im Uhrzeigersinn nacheinander\n" +
						"immer wieder alle Spieler an die Reihe.\n\n" +
						"<Spielablauf>\n" +
						"Ist ein Spieler am Zug, muss er zunächst würfeln. Hat\n" +
						"ein Spieler keine Spielkegel auf der Spielbahn und im\n"+
						"Zielbereich oder kann ein Spieler, unabhängig vom Würfel-\n"+
						"ergebnis, mit keinem seiner Spielkegel ziehen, so stehen\n"+
						"dem Spieler maximal drei Würfelversuche, ansonsten nur\n"+
						"ein einziger Würfelversuch zur Verfügung. Bei Würfeln der\n"+
						"Augenzahl 6 muss der Spieler (wenn möglich) einen seiner\n"+
						"Spielkegel aus dem Startbereich auf dem Startfeld ansetzen.\n"+
						"Ansonsten, wenn alle Spielkegel bereits angesetzt wurden\n"+
						"oder das Startfeld besetzt ist, kann mit einem anderen\n"+
						"Spielkegel um die gewürfelte Augenzahl vorgerückt werden.\n"+
						"Konnte ein Spieler nach Würfeln der Augenzahl 6 einen\n"+
						"Spielkegel ansetzen oder einen Spielkegel vorrücken, ist\n"+
						"er abermals am Zug. Würfelt ein Spieler eine Augenzahl\n"+
						"ungleich 6, so darf er nur Spielkegel auf der Spielbahn\n"+
						"oder im Zielbereich um diese Augenzahl vorrücken.\n"+
						"Befindet sich auf dem Zielfeld ein Spielkegel der\n"+
						"gleichen Farbe, so darf nicht gerückt werden. Ist das\n"+
						"Zielfeld durch einen Kegel einer anderen Farbe besetzt,\n"+
						"so darf vorgerückt werden. Der andersfarbige Spielkegel\n"+
						"wird wieder in seinen Startbereich zurückgesetzt. Die ge-\n"+
						"würfelte Augensumme darf auch dann nicht mit einem Spiel-\n"+
						"kegel gerückt werden, wenn dadurch ein eigener Spielkegel\n"+
						"im Zielbereich übersprungen wird. Ist das Startfeld durch\n"+
						"einen eigenen Spielkegel besetzt und befinden sich noch\n"+
						"Kegel im Startfeld, so muss wenn möglich zuerst das Start-\n"+
						"feld geräumt werden.\n\n"+    
						"<Spielende>\n"+
						"Das Spiel ist beendet, wenn ein Spieler durch geschicktes\n"+
						"Würfeln, als Erster alle seine Spielkegel entlang der\n"+
						"Spielbahn in den Zielbereich (Mittelbahn) seiner Farbe bewegt\n"+
						"hat."

		    	);
		    taRules.setCaretPosition(0);
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
