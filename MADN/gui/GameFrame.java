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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class GameFrame extends JFrame implements ActionListener, MouseListener, ClientListener, AppletContext, AppletStub{
  
  public static Color[] colors = {new Color(255,0,0), new Color(60,60,60), new Color(0,0,255), new Color(0, 153, 51)};	
 		
  
  private HashMap streams = new HashMap();	
 
  private Client client;
  private int clientColor = Constants.RED;
  
  private JPanel boardPanel = null;
  private BoardApplet boardApplet = null;
  
  private JPanel pnDice = new JPanel();
  private JButton btDice = new JButton(Toolbox.loadDiceIcon(this.getClass()));
  private JPanel diceApplet = new JPanel();
  private JTextField tfDiceResult = new JTextField();
  
  private JPanel pnPieces = new JPanel();
  private JLabel lbPiece1;
  private JLabel lbPiece2 = new JLabel();
  private JLabel lbPiece3 = new JLabel();
  private JLabel lbPiece4 = new JLabel();  
  
  private JButton btMove = new JButton (Toolbox.loadPawnIcon(this.getClass()));
  
  private JPanel pnRadio = new JPanel();
  private JTextArea taRadio = new JTextArea("Start: "  + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM).format(new Date()));
  private JScrollPane spRadio = new JScrollPane(taRadio);
  
  private int selectedPiece = -1;
  private int dice = 0;
  
  public GameFrame(Client client) {
    
  	super("Mensch ärgere Dich nicht!");
  	this.client = client;
  	
  	try {
		clientColor = client.getColor();
	} catch (RemoteException e1) {
		//e1.printStackTrace();
	}
	
    this.setSize(772,700);
    //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setResizable(false);
    //this.setState(Frame.ICONIFIED);
    this.getContentPane().setLayout(null);
    this.setJMenuBar(createMenuBar());
    
    boardPanel = new JPanel();
    boardPanel.setLayout(new BorderLayout());
    boardPanel.setBounds(new Rectangle(8,50, 512, 512));
    boardPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    
    boardApplet =  new BoardApplet();
    boardApplet.setStub(this);
    boardApplet.init();
    boardApplet.start();
    boardPanel.add(boardApplet, BorderLayout.CENTER);
 
    pnDice.setBounds(new Rectangle(525, 42, 235, 205));
    pnDice.setLayout(null);
    pnDice.setBorder(new TitledBorder("  Würfel  "));

       diceApplet.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
       diceApplet.setBounds(new Rectangle(45, 20, 145, 145));
       diceApplet.setLayout(new BorderLayout());
          tfDiceResult.setSize(150, 21);
       diceApplet.add(tfDiceResult, BorderLayout.CENTER);

       btDice.setBounds(new Rectangle(45, 170, 145, 25));
       btDice.setText("Würfeln");
       btDice.setActionCommand("dice");
       btDice.addActionListener(this);

    pnDice.add(btDice, null);
    pnDice.add(diceApplet, null);

    pnPieces.setBorder(new TitledBorder("  Spielsteine  "));
    pnPieces.setBounds(new Rectangle(525, 252, 235, 125));
    pnPieces.setLayout(null);
       
       lbPiece1 = new JLabel(Toolbox.loadImageIcon(clientColor + "1.gif", this.getClass()));   
	   lbPiece1.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPiece1.setHorizontalAlignment(SwingConstants.CENTER);
       //lbPiece1.setText("Piece1");
       lbPiece1.setBounds(new Rectangle(10, 20, 50, 65));
       lbPiece1.addMouseListener(this);
       
       lbPiece2 = new JLabel(Toolbox.loadImageIcon(clientColor + "2.gif", this.getClass()));   
       lbPiece2.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPiece2.setHorizontalAlignment(SwingConstants.CENTER);
       //lbPiece2.setText("Piece2");
       lbPiece2.setBounds(new Rectangle(65, 20, 50, 65));
       lbPiece2.addMouseListener(this);
		
       lbPiece3 = new JLabel(Toolbox.loadImageIcon(clientColor + "3.gif", this.getClass()));   
       lbPiece3.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPiece3.setHorizontalAlignment(SwingConstants.CENTER);
       //lbPiece3.setText("Piece3");
       lbPiece3.setBounds(new Rectangle(120, 20, 50, 65));
       lbPiece3.addMouseListener(this);
       
       lbPiece4 = new JLabel(Toolbox.loadImageIcon(clientColor + "4.gif", this.getClass()));   
       lbPiece4.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
       lbPiece4.setHorizontalAlignment(SwingConstants.CENTER);
       //lbPiece4.setText("Piece4");
       lbPiece4.setBounds(new Rectangle(175, 20, 50, 65));
       lbPiece4.addMouseListener(this);

       btMove.setBounds(new Rectangle(45, 90, 145, 25));
       btMove.setText("Rücken");
       btMove.setActionCommand("move");
       btMove.addActionListener(this);
       
    pnPieces.add(lbPiece1, null);
    pnPieces.add(lbPiece2, null);
    pnPieces.add(lbPiece3, null);
    pnPieces.add(lbPiece4, null);
    pnPieces.add(btMove, null);
    
    pnRadio.setBorder(new TitledBorder("  Radio  "));
    pnRadio.setBounds(new Rectangle(8, 567, 512, 72));
    pnRadio.setLayout(null);
    
       spRadio.setBounds(new Rectangle(10, 20, 492, 42));
       taRadio.setEditable(false);

    pnRadio.add(spRadio, null);
    
    this.getContentPane().add(boardPanel, null);
    this.getContentPane().add(pnPieces, null);
    this.getContentPane().add(pnDice, null);
    this.getContentPane().add(pnRadio, null);
    
    updateComponentEnabling();
    setVisible(true);
  }
  public static void main(String[] args) {
    
  	GameFrame f1, f2;
    
	try {
		
		Server s = new ServerImpl("Server");
		f1 = new GameFrame((Client)s);
		f1.setLocation(0,0);
		((Client)s).setClientListener(f1);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1){}
		
		Client c = s.newClient("Client");
		f2 = new GameFrame(c);
		c.setClientListener(f2);
		f2.setLocation(100,100);
		
		
		
	} catch (RemoteException e) {
		e.printStackTrace();
	}
	
  
  }

  private JMenuBar createMenuBar(){
    JMenuBar mb = new JMenuBar();
    JMenuItem mi = null;

    JMenu gameMenu = new JMenu("Spiel");
    gameMenu.setMnemonic('S');

       mi = new JMenuItem("Neues Spiel", Toolbox.loadRefreshIcon(this.getClass()));
       mi.addActionListener(this);
       mi.setActionCommand("newGame");
       mi.setEnabled(client instanceof Server);
       setCtrlAccelerator(mi, 'N');
       gameMenu.add(mi);

       gameMenu.addSeparator();

       mi = new JMenuItem("Beenden", Toolbox.loadExitIcon(this.getClass()));
       mi.addActionListener(this);
       mi.setActionCommand("dispose");
       setCtrlAccelerator(mi, 'E');
       gameMenu.add(mi);

//    JMenu settingsMenu = new JMenu("Optionen");
//    settingsMenu.setMnemonic('O');

    JMenu helpMenu = new JMenu("?");

      mi = new JMenuItem("Spielregeln", Toolbox.loadRulesIcon(this.getClass()));
      mi.addActionListener(this);
      mi.setActionCommand("showRules");
      setCtrlAccelerator(mi, 'R');
      helpMenu.add(mi);

      helpMenu.addSeparator();

      mi = new JMenuItem("Über...", Toolbox.loadInfoIcon(this.getClass()));
      mi.addActionListener(this);
      mi.setActionCommand("showInfo");
      setCtrlAccelerator(mi, 'I');
      helpMenu.add(mi);

    mb.add(gameMenu);
    mb.add(helpMenu);


    return mb;
  }

  private void setCtrlAccelerator(JMenuItem mi, char acc){
    KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
    mi.setAccelerator(ks);
  }

  private void updateComponentEnabling(){
  	try {
		btMove.setEnabled(client.getStatus() > Constants.INACTIVE);
		btDice.setEnabled((client.getStatus() == Constants.ACTIVE) && client.getAttempts() > 0);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public void actionPerformed(ActionEvent e){
    String cmd = e.getActionCommand();

    if (cmd.equals("newGame")){
    	try {
			client.getServer().startNewGame();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }else if (cmd.equals("dispose")){
        if (boardApplet!=null){
        	boardApplet.stop();
        	boardApplet.destroy();
        }
        
    	this.dispose();
    
    }else if (cmd.equals("showRules")){

    }else if (cmd.equals("showInfo")){

    }else if (cmd.equals("dice")){
       	try {
			dice = client.throwTheDice();
			tfDiceResult.setText("Würfelergebnis: " + dice);
			btDice.setEnabled(client.getAttempts()>0);
		} catch (RemoteException e1) {
			// e1.printStackTrace();
			tfDiceResult.setText("Würfelergebnis: n.a.");
		}
    }else if (cmd.equals("move")){
    	if ((selectedPiece >= 0)&&(selectedPiece < 4) && (dice > 0)){
    		try {
				
				client.getServer().move(client.getColor(), selectedPiece, dice);
				dice = 0;
				tfDiceResult.setText("");
    		} catch (InvalidMoveException e1) {
					if (e1.getErrorCode() == Constants.NO_MOVEABLE_PIECE){
						dice = 0;
						tfDiceResult.setText("");
					}
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	}else{
    		// TODO Message: kein Spielstein ausgewählt
    	}
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

public void showDocument(URL url) {}

public void showDocument(URL url, String target) {}

public void showStatus(String status) {}

public void setStream(String key, InputStream stream) throws IOException {
	streams.put(key, stream);
	
}

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
	if ((selectedPiece == 0)&& !e.getSource().equals(lbPiece1))
		lbPiece1.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	else if ((selectedPiece == 1)&& !e.getSource().equals(lbPiece2))
		lbPiece2.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	else if ((selectedPiece == 2)&& !e.getSource().equals(lbPiece3))
		lbPiece3.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	else if ((selectedPiece == 3)&& !e.getSource().equals(lbPiece4))
		lbPiece4.setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	
	// Neuen Spielstein wählen
	if (e.getSource().equals(lbPiece1)){
		selectedPiece = 0;
	}else if (e.getSource().equals(lbPiece2))
		selectedPiece = 1;
	else if (e.getSource().equals(lbPiece3))
		selectedPiece = 2;
	else if (e.getSource().equals(lbPiece4))
		selectedPiece = 3;
	
	// Umrahmung entsprechend der Spielerfarbe setzen
	((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(colors[clientColor],2));	
	
}

public void mouseReleased(MouseEvent e) {
}

public void mouseEntered(MouseEvent e) {
	
	((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0),2));	
}

public void mouseExited(MouseEvent e) {
	
	
	if ((e.getSource().equals(lbPiece1) && (selectedPiece != 0))
		||(e.getSource().equals(lbPiece2) && (selectedPiece != 1))
		||(e.getSource().equals(lbPiece3) && (selectedPiece != 2))
		||(e.getSource().equals(lbPiece4) && (selectedPiece != 3))
		){
		
		((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(this.getBackground(),2));
	}else if ((e.getSource().equals(lbPiece1) && (selectedPiece == 0))
			||(e.getSource().equals(lbPiece2) && (selectedPiece == 1))
			||(e.getSource().equals(lbPiece3) && (selectedPiece == 2))
			||(e.getSource().equals(lbPiece4) && (selectedPiece == 3))
			){
			
			((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(colors[clientColor],2));
		}

}

/* (non-Javadoc)
 * @see gui.ClientListener#boardConstellationChanged()
 */
public void boardConstellationChanged(Piece[][] pieces) {
	boardApplet.actualizePawnPositions(pieces);
	
}
/* (non-Javadoc)
 * @see gui.ClientListener#gameIsOver()
 */
public void gameIsOver() {
	// TODO Auto-generated method stub
	
}
/* (non-Javadoc)
 * @see gui.ClientListener#enablingChanged()
 */
public void enablingChanged() {
	updateComponentEnabling();
}
/* (non-Javadoc)
 * @see gui.ClientListener#showMessage()
 */
public void showMessage() {
	// TODO Auto-generated method stub
	
}

public void addRadioMessage(String msg) {
	taRadio.setText(msg + "\n" + taRadio.getText()) ;
	taRadio.setCaretPosition(0);
}

public void showMessage(String msg) {
	MessageDialogs.showInfoMessageDialog(this, "Server-Message", msg);
}
}