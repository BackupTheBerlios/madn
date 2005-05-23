package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.Constants;
import model.InvalidMoveException;
import model.Piece;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class GameFrame extends JFrame implements ActionListener, MouseListener, ClientListener, AppletContext, AppletStub, PawnPickingListener, AnimationListener{
  
  public static Color[] colors = {new Color(255,0,0), new Color(60,60,60), new Color(0,0,255), new Color(0, 153, 51)};	
 
  private HashMap streams = new HashMap();	
  private InfoDialog dlgInfo;
  private AboutDialog dlgAbout;
  protected Client client;
  private int clientColor = Constants.RED;
  private String nickname = "";
  
  private JPanel boardPanel = null;
  private BoardApplet boardApplet = null;
  
  private JToolBar tbStatus = new JToolBar("Status");
  private JLabel lbPlayer = new JLabel();
  private JLabel lbStatus = new JLabel();
  private JLabel lbTip = new JLabel();
  private JTextArea taTip = new JTextArea();
  
  private JPanel pnDice = new JPanel();
  private JButton btDice = new JButton(Toolbox.loadDiceIcon(this.getClass()));
  private JPanel dicePanel = new JPanel();
  private DiceApplet diceApplet = null;
  //private JTextField tfDiceResult = new JTextField();
  
  private JPanel pnPieces = new JPanel();
  private JLabel[] lbPieces = new JLabel[4];
  
  private JButton btMove = new JButton (Toolbox.loadPawnIcon(this.getClass()));
  
  private JPanel pnRadio = new JPanel();
  private JTextArea taRadio = new JTextArea("Start: "  + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM).format(new Date()));
  private JScrollPane spRadio = new JScrollPane(taRadio);
  
  private int selectedPiece = -1;
  private JDesktopPane desktop = null;
  private Server server = null;
  
  private boolean animationRunning = false;
  
  public GameFrame(Client client) {
    
  	super("Mensch ärgere Dich nicht!");
  	this.client = client;
  	
  	try {
  	    client.setClientListener(this);
		clientColor = client.getColor();
		nickname = client.getNickname();
	} catch (RemoteException e1) {
		//e1.printStackTrace();
	}
	
  	int xSize = 772, ySize = 668;
	
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	d.width = (d.width - xSize)/2;
	d.height =(d.height-ySize)/2;
	this.setBounds( d.width, d.height, xSize, ySize);
	
    this.setResizable(false);
    
    this.addWindowListener(
    		
    		new WindowAdapter(){
    			
    			public void windowClosing(WindowEvent event){
    				
    				GameFrame frame = (GameFrame)event.getWindow();
    				
    				try {
    					
    					if (frame.client.existsServer()){ 
    						frame.client.getServer().removeClient(frame.client.getColor());
    					}
    					
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (boardApplet!=null){
			        	boardApplet.stop();
			        	boardApplet.destroy();
			        }
					
					if (diceApplet!=null){
						diceApplet.stop();
						diceApplet.destroy();
			        }
					
    				if (dlgInfo != null){
    					dlgInfo.dispose();
    				}
       				if (dlgAbout != null){
       					dlgAbout.dispose();
    				}    				
    				
    				//frame.setVisible(false);
    				frame.dispose();
    				System.exit(0);
    			}	   			
    		}
    	);
    this.getContentPane().setLayout(null);
    this.setJMenuBar(createMenuBar());
    
    boardPanel = new JPanel();
    boardPanel.setLayout(new BorderLayout());
    boardPanel.setBounds(new Rectangle(8,50, 512, 512));
    boardPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
  
    boardApplet =  new BoardApplet();
    boardApplet.setStub(this);
    boardApplet.setPawnPickingListener(this);
    boardApplet.init();
    boardApplet.start();
    boardPanel.add(boardApplet, BorderLayout.CENTER);
    
    pnDice.setBounds(new Rectangle(525, 42, 235, 205));
    pnDice.setLayout(null);
    pnDice.setBorder(new TitledBorder("  Würfel  "));

       dicePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
       dicePanel.setBounds(new Rectangle(45, 20, 145, 145));
       dicePanel.setLayout(new BorderLayout());
          //tfDiceResult.setSize(150, 21);
       	  //diceApplet.add(tfDiceResult, BorderLayout.CENTER);
       	  diceApplet = new DiceApplet(this);
       	  diceApplet.setStub(this);
       	  diceApplet.init();
       	  diceApplet.start();
       	  dicePanel.add(diceApplet, BorderLayout.CENTER);
       		
       btDice.setBounds(new Rectangle(45, 170, 145, 25));
       btDice.setText("Würfeln");
       btDice.setActionCommand("dice");
       btDice.addActionListener(this);

    pnDice.add(btDice, null);
    pnDice.add(dicePanel, null);

    pnPieces.setBorder(new TitledBorder("  Spielsteine  "));
    pnPieces.setBounds(new Rectangle(525, 257, 235, 125));
    pnPieces.setLayout(null);
       
       lbPieces[0] = new JLabel(Toolbox.loadImageIcon(clientColor + "1.gif", this.getClass()));   
       lbPieces[0].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPieces[0].setHorizontalAlignment(SwingConstants.CENTER);
       lbPieces[0].setBounds(new Rectangle(10, 20, 50, 65));
       lbPieces[0].addMouseListener(this);
       
       lbPieces[1] = new JLabel(Toolbox.loadImageIcon(clientColor + "2.gif", this.getClass()));   
       lbPieces[1].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPieces[1].setHorizontalAlignment(SwingConstants.CENTER);
       lbPieces[1].setBounds(new Rectangle(65, 20, 50, 65));
       lbPieces[1].addMouseListener(this);
		
       lbPieces[2] = new JLabel(Toolbox.loadImageIcon(clientColor + "3.gif", this.getClass()));   
       lbPieces[2].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPieces[2].setHorizontalAlignment(SwingConstants.CENTER);
       lbPieces[2].setBounds(new Rectangle(120, 20, 50, 65));
       lbPieces[2].addMouseListener(this);
       
       lbPieces[3] = new JLabel(Toolbox.loadImageIcon(clientColor + "4.gif", this.getClass()));   
       lbPieces[3].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPieces[3].setHorizontalAlignment(SwingConstants.CENTER);
       lbPieces[3].setBounds(new Rectangle(175, 20, 50, 65));
       lbPieces[3].addMouseListener(this);

       btMove.setBounds(new Rectangle(45, 90, 145, 25));
       btMove.setText("Ziehen");
       btMove.setActionCommand("move");
       btMove.addActionListener(this);
       
    pnPieces.add(lbPieces[0], null);
    pnPieces.add(lbPieces[1], null);
    pnPieces.add(lbPieces[2], null);
    pnPieces.add(lbPieces[3], null);
    pnPieces.add(btMove, null);
    
    pnRadio.setBorder(new TitledBorder("  Radio  "));
    pnRadio.setBounds(new Rectangle(525, 392, 235, 170));
    pnRadio.setLayout(null);
    
       spRadio.setBounds(new Rectangle(5, 20, 225, 145));
       taRadio.setEditable(false);

    pnRadio.add(spRadio, null);

    tbStatus.setBounds(new Rectangle(0,567, 772, 45));
    tbStatus.setBorderPainted(true);
    tbStatus.setEnabled(false);
    
    	lbPlayer.setIcon(Toolbox.loadPlayerIcon(this.getClass()));
    	lbPlayer.setForeground(colors[clientColor]);
    	lbPlayer.setText(nickname + " [" + ((client instanceof Server)?"Server":"Client") + "]");
    	lbPlayer.setFont(new java.awt.Font("Dialog", 0, 11));
       	tbStatus.add(lbPlayer);
    
    tbStatus.addSeparator();
       	
   		lbStatus.setFont(new java.awt.Font("Dialog", 1, 11));
    	tbStatus.add(lbStatus);
  
    tbStatus.addSeparator();
         
    	//lbTip.setFont(new java.awt.Font("Dialog", 1, 11));
       	taTip.setFont(new java.awt.Font("Dialog", 1, 11));
    	taTip.setEditable(false);
       	taTip.setBackground(this.getBackground());
       	tbStatus.add(lbTip);
       	tbStatus.addSeparator();
       	tbStatus.add(taTip);
    	
    this.getContentPane().add(boardPanel, null);
    this.getContentPane().add(pnPieces, null);
    this.getContentPane().add(pnDice, null);
    this.getContentPane().add(pnRadio, null);
    this.getContentPane().add(tbStatus, null);
    
    updateComponentEnabling();
    updateStatusBar();
    
    doLayout();
    setVisible(true);
    
  }
  public static void main(String[] args) {
    
  	GameFrame f1, f2, f3;
    
	try {
		
		Server s = new ServerImpl("madn", "Mario");
		f1 = new GameFrame((Client)s);
		((Client)s).setClientListener(f1);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1){}
		
		Client c = new ClientImpl("localhost", "madn", "Robert");
		s.newClient(c);
		f2 = new GameFrame(c);
		c.setClientListener(f2);
//		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e1){}
//		
//		c = s.newClient("Waldemar");
//		f3 = new GameFrame(c);
//		c.setClientListener(f3);
		
		
	} catch (RemoteException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
  }

  private JMenuBar createMenuBar(){
    JMenuBar mb = new JMenuBar();
    JMenuItem mi = null;

    JMenu gameMenu = new JMenu("Spiel");
    gameMenu.setMnemonic('S');

       mi = new JMenuItem("Neues Spiel", Toolbox.loadStartIcon(this.getClass()));
       mi.addActionListener(this);
       mi.setActionCommand("newGame");
       mi.setEnabled(client instanceof Server);
       setCtrlAccelerator(mi, 'N');
       gameMenu.add(mi);

       gameMenu.addSeparator();

       mi = new JMenuItem("Beenden", Toolbox.loadStopIcon(this.getClass()));
       mi.addActionListener(this);
       mi.setActionCommand("dispose");
       setCtrlAccelerator(mi, 'E');
       gameMenu.add(mi);

//    JMenu settingsMenu = new JMenu("Optionen");
//    settingsMenu.setMnemonic('O');

    JMenu helpMenu = new JMenu("?");

      mi = new JMenuItem("Spielregeln/Steuerung", Toolbox.loadRulesIcon(this.getClass()));
      mi.addActionListener(this);
      mi.setActionCommand("showRules");
      setCtrlAccelerator(mi, 'S');
      helpMenu.add(mi);
     
      helpMenu.addSeparator();

      mi = new JMenuItem("Über...", Toolbox.loadInfoIcon(this.getClass()));
      mi.addActionListener(this);
      mi.setActionCommand("showAbout");
      setCtrlAccelerator(mi, 'I');
      helpMenu.add(mi);

    mb.add(gameMenu);
    mb.add(helpMenu);


    return mb;
  }

  
  private void showErrorInStatusBar(String errorMsg, int millis){
  	int delay = millis; //Millisekunden
    
  	lbTip.setIcon(Toolbox.loadStop24Icon(this.getClass()));
  	taTip.setText(errorMsg);
  	
  	ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ((Timer)e.getSource()).stop();
          	lbTip.setIcon(null);
          	taTip.setText("");            
        }
    };
    
    new Timer(delay, taskPerformer).start();
  }
  
  private void showTipInStatusBar(String tip, int millis){
  	int delay = millis; //Millisekunden
    
  	lbTip.setIcon(Toolbox.loadTip24Icon(this.getClass()));
  	taTip.setText(tip);
  	
  	ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ((Timer)e.getSource()).stop();
          	lbTip.setIcon(null);
          	taTip.setText("");            
        }
    };
    
    new Timer(delay, taskPerformer).start();
  }
  
  private void setCtrlAccelerator(JMenuItem mi, char acc){
    KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
    mi.setAccelerator(ks);
  }

  private void updateComponentEnabling(){
  	try {
  		btMove.setEnabled(!animationRunning && (client.getStatus() == Constants.ACTIVE_MOVE));
		btDice.setEnabled(!animationRunning && (client.getStatus() == Constants.ACTIVE_DICE));
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  private void updateStatusBar(){
	try {
		
		if (client.getStatus() == Constants.INACTIVE){
			lbStatus.setForeground(colors[Constants.RED]);
			lbStatus.setText("Inaktiv");
		}else{
			lbStatus.setForeground(colors[Constants.GREEN]);
			if (client.getStatus() == Constants.ACTIVE_DICE)
				lbStatus.setText("Würfeln");
			else
				lbStatus.setText("Ziehen");
		}
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  private void move(){
	if ((selectedPiece >= 0)&&(selectedPiece < 4)){
		try {
			int dice = client.getDiceResult();
			if (dice > 0){
				client.getServer().move(client.getColor(), selectedPiece, dice);
				//tfDiceResult.setText("");
			}else{
				showTipInStatusBar("Noch nicht gewürfelt?!", 5000);
			}
		} catch (InvalidMoveException e1) {
				if (e1.getErrorCode() == Constants.NO_MOVEABLE_PIECE){
					//tfDiceResult.setText("");
				}
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}else{
		showTipInStatusBar("Spielstein ausgewählt?!", 5000);
	}  	
  }
  
  private void dice(){
   	try {
   		
		client.throwTheDice();
		diceApplet.startAnimation(client.getDiceResult());
		
		//tfDiceResult.setText("Würfelergebnis: " + client.getDiceResult());
		
	} catch (RemoteException e1) {
		// e1.printStackTrace();
		//tfDiceResult.setText("Würfelergebnis: n.a.");
	}  	
  }
  
  public void actionPerformed(ActionEvent e){
    String cmd = e.getActionCommand();

    if (cmd.equals("newGame")){
    	try {
			client.getServer().startNewGame();
			if (e.getSource() instanceof JMenuItem){
				JMenuItem mi = (JMenuItem)e.getSource();
				mi.setText("Spiel beenden");
				mi.setIcon(Toolbox.loadQuitIcon(this.getClass()));
			    mi.setActionCommand("quitGame");
			    setCtrlAccelerator(mi, 'Q');
			}
		} catch (RemoteException e1) {
			
			e1.printStackTrace();
		}
    }else if (cmd.equals("quitGame")){
    	try {
			client.getServer().quitGame();
			
			if (e.getSource() instanceof JMenuItem){
				JMenuItem mi = (JMenuItem)e.getSource();
				mi.setText("Neues Spiel");
				mi.setIcon(Toolbox.loadStartIcon(this.getClass()));
			    mi.setActionCommand("newGame");
			    setCtrlAccelerator(mi, 'N');
			}
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }else if (cmd.equals("dispose")){
        
        
    	this.processEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    
    }else if (cmd.equals("showRules")){
    	if (dlgInfo == null)
    		dlgInfo = new InfoDialog(this);
    	
    	dlgInfo.setVisible(true);
    	
    }else if (cmd.equals("showAbout")){
    	if (dlgAbout == null)
    		dlgAbout = new AboutDialog(this);
    	
    	dlgAbout.setVisible(true);
    }else if (cmd.equals("dice")){
    	dice();
    }else if (cmd.equals("move")){
    	move();
    }
  }
/* 
 * Methods inherited from java.applet.AppletContext
 * @see java.applet.AppletContext
 */
public AudioClip getAudioClip(URL url) {
	return Applet.newAudioClip(url);
}

public Image getImage(URL url) {
	Image img = null;
	try{
		img = ImageIO.read(url);
	}catch (IOException e){
		img = getToolkit().getImage(url);
	}
	return img;
}

public Applet getApplet(String name) {
	return null;
}

public Enumeration getApplets() {
	return null;
}

public void showDocument(URL url, String target) {}

public void showStatus(String status) {}

public void setStream(String key, InputStream stream) throws IOException {
	streams.put(key, stream);
	
}
public void showDocument(URL url) {}

public InputStream getStream(String key) {
	if (!streams.containsKey(key)){
		return null;
	}else {
		return (InputStream)streams.get(key);
	}
}

public Iterator getStreamKeys() {
	return streams.keySet().iterator();
}
/* 
 * Methods from interface java.applet.AppletStub
 * @see java.applet.AppletStub
 */
public URL getDocumentBase() {
	return null;
}
public URL getCodeBase() {
	return null;
}

public String getParameter(String name) {
	return null;
}

public AppletContext getAppletContext() {
	return this;
}

public void appletResize(int width, int height) {}

/* 
 * Methods from interface java.awt.event.MouseListener
 * @see java.awt.event.MouseListener
 */
public void mouseClicked(MouseEvent e) {
}

public void mousePressed(MouseEvent e) {
	
	// Umrahmung des bisher gewählten Spielstein entfernen
	if ((selectedPiece!= -1) && !e.getSource().equals(lbPieces[selectedPiece]))
		lbPieces[selectedPiece].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
		
	// Neuen Spielstein wählen
	for (int i = 0; i < lbPieces.length; i++) {
		if (e.getSource().equals(lbPieces[i])){
			selectedPiece = i;
			break;
		}
	}
	
	// Umrahmung entsprechend der Spielerfarbe setzen
	((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(colors[clientColor],2));	
	
}

public void mouseReleased(MouseEvent e) {
}

public void mouseEntered(MouseEvent e) {
	
	((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0),2));	
}

public void mouseExited(MouseEvent e) {
	
	if ((selectedPiece != -1)&& e.getSource().equals(lbPieces[selectedPiece])){
		((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(colors[clientColor],2));
	}else{
		((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	}

}

/* (non-Javadoc)
 * @see gui.ClientListener#boardConstellationChanged()
 */
public void boardConstellationChanged(Piece[][] pieces) {
	boardApplet.actualizePawnPositions(pieces);
}

public void enablingChanged() {
	
	updateComponentEnabling();
	updateStatusBar();
	
}

public void addRadioMessage(String msg) {
	taRadio.setText(msg + "\n" + taRadio.getText()) ;
	taRadio.setCaretPosition(0);
}

public void showErrorMessage(String msg) {
	showErrorInStatusBar(msg, 6000);
}

public void showMessage(String msg) {
	showTipInStatusBar(msg, 6000);
}
/* (non-Javadoc)
 * @see gui.PawnPickingListener#pawnClicked(int, int, int)
 */
public void pawnClicked(int color, int id, int clickCount) {
	if (color == clientColor){
		if (id != selectedPiece){
			if ((selectedPiece >= 0)&&(selectedPiece <lbPieces.length))
				lbPieces[selectedPiece].setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
			
			selectedPiece = id;
			lbPieces[selectedPiece].setBorder(BorderFactory.createLineBorder(colors[clientColor],2));
		}
		
		if ((clickCount == 2 /*double click*/) && btMove.isEnabled()){
			move();
		}
	}
}
/* (non-Javadoc)
 * @see gui.PawnPickingListener#rightMouseButtonDblClicked()
 */
public void rightMouseButtonClicked() {
	if (btDice.isEnabled()){
		dice();
	}
}
/* (non-Javadoc)
 * @see gui.AnimationListener#animationStarted()
 */
public void animationStarted() {
	
	animationRunning = true;
	updateComponentEnabling();
	
}
/* (non-Javadoc)
 * @see gui.AnimationListener#animationFinished()
 */
public void animationFinished() {
	
	animationRunning = false;
	updateComponentEnabling();
	
}
}