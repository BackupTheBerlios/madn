/* Created on 11.05.2005. */

package gui;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Klasse MadnStart.
 * @author w.flat
 */
public class MadnStart extends JFrame implements ActionListener {
    // Select Panel aufbauen
    JPanel selectPanel = new JPanel();
    JButton butBeenden = new JButton();
    JButton butJoinGame = new JButton();
    JButton butNewGame = new JButton();
    // Server Panel aufbauen
    JPanel serverPanel = new JPanel();
    JLabel labServernameServer = new JLabel();
    JLabel labNicknameServer = new JLabel();
    JTextField tfServernameServer = new JTextField();
    JTextField tfNicknameServer = new JTextField();
    JButton butZurueckServer = new JButton();
    JButton butStarten = new JButton();
    // ClientPanel aufbauen
    JPanel clientPanel = new JPanel();
    JLabel labServerHost = new JLabel();
    JLabel labServernameClient = new JLabel();
    JLabel labNicknameClient = new JLabel();
    JTextField tfServerHost = new JTextField();
    JTextField tfServernameClient = new JTextField();
    JTextField tfNicknameClient = new JTextField();
    JButton butZurueckClient = new JButton();
    JButton butJoin = new JButton();
    
    // Variablen zm Definieren der Größen der Panels und des Hauptfensters
    final static int PANEL_WIDTH = 270;
    final static int PANEL_HEIGHT = 135;
    final static int PANEL_XY = 0;
	final static int WND_WIDTH = 275;		// Breite des Login-Fensters
	final static int WND_HEIGHT = 160;		// Höhe des Login-Fensters
	
	// gespeichete Variablen des Servers
	static String SERVER_SERVERNAME = "madn";
	static String SERVER_NICKNAME = "";
	// gespeicherte Variablen des Clients
	static String CLIENT_SERVERHOST = "localhost";
	static String CLIENT_SERVERNAME = "madn";
	static String CLIENT_NICKNAME = "";
	// Verzeichnisse und Ordner für die Dateien, die ausgelagert werden müssen
	final static String xmlPackage = "xml";
	final static String xmlServer = "server.xml";
	final static String xmlClient = "client.xml";
	
	/**
	 * Klasse MadnStart.
	 */
    public MadnStart() {
        super("Mensch ärgere Dich nicht !");
        try {
            loadClientSettings();
            loadServerSettings();
            jbInitClientPanel();
            jbInitSelectPanel();
            jbInitServerPanel();
            jbInit();
            serverPanel.setVisible(false);
            clientPanel.setVisible(false);
            selectPanel.setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }
    
    /**
     * Programm-Start.
     */
    public static void main(String[] args) {
        MadnStart madnGui = new MadnStart();
    }
    
    /**
     * Verarbeitung der Button-Ereignisse.
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == butJoin) {
            if(checkClientSettings()) {
                try {
                    Client client = new ClientImpl(CLIENT_SERVERHOST, CLIENT_SERVERNAME, CLIENT_NICKNAME);
                    new GameFrame(client);
                    exit();
                } catch(Exception exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == butStarten) {
            if(checkServerSettings()) {
                try {
                    Server server = new ServerImpl(SERVER_SERVERNAME, SERVER_NICKNAME);
                    new GameFrame((Client)server);
                    exit();
                } catch(Exception exc) {
                    JOptionPane.showMessageDialog(this, exc.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == butZurueckClient || e.getSource() == butZurueckServer) {
            serverPanel.setVisible(false);
            clientPanel.setVisible(false);
            selectPanel.setVisible(true);
        } else if(e.getSource() == butNewGame) {
            serverPanel.setVisible(true);
            clientPanel.setVisible(false);
            selectPanel.setVisible(false);
        } else if(e.getSource() == butJoinGame) {
            serverPanel.setVisible(false);
            clientPanel.setVisible(true);
            selectPanel.setVisible(false);
        } else if(e.getSource() == butBeenden) {
            exit();
            System.exit(0);
        }
    }
    
    private void exit() {
        saveClientSettings();
        saveServerSettings();
        this.setVisible(false);
        this.dispose();
    }
    
    /**
     * Überprüfung der Server-Einstllungen.
     */
    private boolean checkServerSettings() {
        String error = "";
        if(tfServernameServer.getText().trim().length() == 0)
            error += " - Es wurde kein Servername eingegeben.\n";
        if(tfNicknameServer.getText().trim().length() == 0)
            error += " - Es wurde kein Nickname eingegeben.\n";
        
        if(error.length() == 0) {
            MadnStart.SERVER_SERVERNAME = tfServernameServer.getText();
            MadnStart.SERVER_NICKNAME = tfNicknameServer.getText();
            return true;
        } else {
			JOptionPane.showMessageDialog(this, "Es sind folgende Fehler aufgetreten : \n" + error, "Fehler !", JOptionPane.ERROR_MESSAGE);
			return false;
        }
    }
    
    /**
     * Überprüfung der Client-Einstllungen.
     */
    private boolean checkClientSettings() {
        String error = "";
        if(tfServerHost.getText().trim().length() == 0)
            error += " - Es wurde kein Serverhost eingegeben.\n";
        if(tfServernameClient.getText().trim().length() == 0)
            error += " - Es wurde kein Servername eingegeben.\n";
        if(tfNicknameClient.getText().trim().length() == 0)
            error += " - Es wurde kein Nickname eingegeben.\n";
        
        if(error.length() == 0) {
            MadnStart.CLIENT_SERVERHOST = tfServerHost.getText();
            MadnStart.CLIENT_SERVERNAME = tfServernameClient.getText();
            MadnStart.CLIENT_NICKNAME = tfNicknameClient.getText();
            return true;
        } else {
			JOptionPane.showMessageDialog(this, "Es sind folgende Fehler aufgetreten : \n" + error, "Fehler !", JOptionPane.ERROR_MESSAGE);
			return false;
        }
    }
    
	/**
	 * Die Server-Einstellungen aus der XML-Datei laden.
	 */
	private void loadServerSettings() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder  = factory.newDocumentBuilder();
			String text = Toolbox.readFile(xmlPackage.replace('.', File.separatorChar) + File.separator + xmlServer);
			Document document = builder.parse(new InputSource(new StringReader(text)));
			MadnStart.SERVER_SERVERNAME = document.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
			MadnStart.SERVER_NICKNAME = document.getElementsByTagName("nickname").item(0).getFirstChild().getNodeValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Client-Einstellungen aus der XML-Datei laden.
	 */
	private void loadClientSettings() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder  = factory.newDocumentBuilder();
			String text = Toolbox.readFile(xmlPackage.replace('.', File.separatorChar) + File.separator + xmlClient);
			Document document = builder.parse(new InputSource(new StringReader(text)));
			MadnStart.CLIENT_SERVERHOST = document.getElementsByTagName("serverhost").item(0).getFirstChild().getNodeValue();
			MadnStart.CLIENT_SERVERNAME = document.getElementsByTagName("servername").item(0).getFirstChild().getNodeValue();
			MadnStart.CLIENT_NICKNAME = document.getElementsByTagName("nickname").item(0).getFirstChild().getNodeValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Server-Einstellungen in die XML-Datei speichern.
	 */
	private void saveServerSettings() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder  = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.appendChild(document.createElement("server"));
			Node rootNode = document.getDocumentElement();
			Node tempNode = document.createElement("name");
			tempNode.appendChild(document.createTextNode(MadnStart.SERVER_SERVERNAME));
			rootNode.appendChild(tempNode);
			tempNode = document.createElement("nickname");
			tempNode.appendChild(document.createTextNode(MadnStart.SERVER_NICKNAME));
			rootNode.appendChild(tempNode);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource( document );
			StringWriter os = new StringWriter();
			StreamResult result = new StreamResult( os );
			transformer.transform( source, result );
			Toolbox.writeFile(xmlPackage.replace('.', File.separatorChar) + File.separator + xmlServer, os.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Die Client-Einstellungen in die XML-Datei speichern.
	 */
	private void saveClientSettings() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder  = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.appendChild(document.createElement("client"));
			Node rootNode = document.getDocumentElement();
			Node tempNode = document.createElement("serverhost");
			tempNode.appendChild(document.createTextNode(MadnStart.CLIENT_SERVERHOST));
			rootNode.appendChild(tempNode);
			tempNode = document.createElement("servername");
			tempNode.appendChild(document.createTextNode(MadnStart.CLIENT_SERVERNAME));
			rootNode.appendChild(tempNode);
			tempNode = document.createElement("nickname");
			tempNode.appendChild(document.createTextNode(MadnStart.CLIENT_NICKNAME));
			rootNode.appendChild(tempNode);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource( document );
			StringWriter os = new StringWriter();
			StreamResult result = new StreamResult( os );
			transformer.transform( source, result );
			Toolbox.writeFile(xmlPackage.replace('.', File.separatorChar) + File.separator + xmlClient, os.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Initialisierung des HauptFensters.
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setResizable(false);
		Dimension size = this.getToolkit().getScreenSize();		// Server in der Mitte des Bildschirms
		this.setBounds(new Rectangle((int)((size.width - WND_WIDTH) / 2), (int)((size.height - WND_HEIGHT) / 2), WND_WIDTH, WND_HEIGHT));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				butBeenden.doClick();
			}
		} );
        this.setLocale(java.util.Locale.getDefault());
        this.getContentPane().setLayout(null);
        this.getContentPane().add(selectPanel, null);
        this.getContentPane().add(clientPanel, null);
        this.getContentPane().add(serverPanel, null);
        selectPanel.setBounds(new Rectangle(PANEL_XY, PANEL_XY, PANEL_WIDTH, PANEL_HEIGHT));
        clientPanel.setBounds(new Rectangle(PANEL_XY, PANEL_XY, PANEL_WIDTH, PANEL_HEIGHT));
        serverPanel.setBounds(new Rectangle(PANEL_XY, PANEL_XY, PANEL_WIDTH, PANEL_HEIGHT));
    }
    
    /**
     * Aufbau des SelectPanels.
     * @throws Exception
     */
    void jbInitSelectPanel() throws Exception {
        butNewGame.setText("Neues Spiel Starten");
        butNewGame.setBounds(new Rectangle(10, 10, 250, 25));
        butNewGame.setFont(new java.awt.Font("SansSerif", 1, 11));
        butJoinGame.setText("Einem Spiel Anschließen");
        butJoinGame.setBounds(new Rectangle(10, 50, 250, 25));
        butJoinGame.setFont(new java.awt.Font("SansSerif", 1, 11));
        butBeenden.setText("Anwendung Beenden");
        butBeenden.setBounds(new Rectangle(10, 100, 250, 25));
        butBeenden.setFont(new java.awt.Font("SansSerif", 1, 11));
        selectPanel.setLayout(null);
        selectPanel.setFont(new java.awt.Font("Dialog", 0, 11));
        selectPanel.add(butNewGame, null);
        selectPanel.add(butJoinGame, null);
        selectPanel.add(butBeenden, null);
        selectPanel.setBounds(5, 5, 250, 130);
        
        butBeenden.addActionListener(this);
        butBeenden.setIcon(Toolbox.loadCloseIcon(getClass()));
        butNewGame.addActionListener(this);
        butNewGame.setIcon(Toolbox.loadNewIcon(getClass()));
        butJoinGame.addActionListener(this);
        butJoinGame.setIcon(Toolbox.loadAddIcon(getClass()));
    }
    
    /**
     * Aufbau des ServerPanels.
     * @throws Exception
     */
    void jbInitServerPanel() throws Exception {
        labServernameServer.setFont(new java.awt.Font("Dialog", 0, 11));
        labServernameServer.setToolTipText("");
        labServernameServer.setText("Server-Name");
        labServernameServer.setBounds(new Rectangle(10, 10, 90, 15));
        serverPanel.setLayout(null);
        labNicknameServer.setFont(new java.awt.Font("Dialog", 0, 11));
        labNicknameServer.setText("Nickname");
        labNicknameServer.setBounds(new Rectangle(10, 40, 90, 15));
        tfServernameServer.setFont(new java.awt.Font("Dialog", 0, 11));
        tfServernameServer.setText(SERVER_SERVERNAME);
        tfServernameServer.setBounds(new Rectangle(100, 10, 150, 21));
        tfNicknameServer.setFont(new java.awt.Font("Dialog", 0, 11));
        tfNicknameServer.setText(SERVER_NICKNAME);
        tfNicknameServer.setBounds(new Rectangle(100, 40, 150, 19));
        butZurueckServer.setBounds(new Rectangle(10, 100, 115, 25));
        butZurueckServer.setFont(new java.awt.Font("SansSerif", 1, 11));
        butZurueckServer.setText("Zurück");
        butStarten.setBounds(new Rectangle(135, 100, 115, 25));
        butStarten.setFont(new java.awt.Font("SansSerif", 1, 11));
        butStarten.setText("Starten");
        serverPanel.add(labServernameServer, null);
        serverPanel.add(labNicknameServer, null);
        serverPanel.add(tfServernameServer, null);
        serverPanel.add(tfNicknameServer, null);
        serverPanel.add(butZurueckServer, null);
        serverPanel.add(butStarten, null);

        butZurueckServer.addActionListener(this);
        butZurueckServer.setIcon(Toolbox.loadZurueckIcon(getClass()));
        butStarten.addActionListener(this);
        butStarten.setIcon(Toolbox.loadConnectorIcon(getClass()));
    }
    
    /**
     * Aufbau des ClientPanels.
     * @throws Exception
     */
    void jbInitClientPanel() throws Exception {
        labServerHost.setFont(new java.awt.Font("Dialog", 0, 11));
        labServerHost.setText("Server-Host");
        labServerHost.setBounds(new Rectangle(10, 10, 90, 15));
        clientPanel.setLayout(null);
        labServernameClient.setFont(new java.awt.Font("Dialog", 0, 11));
        labServernameClient.setText("Server-Name");
        labServernameClient.setBounds(new Rectangle(10, 40, 90, 15));
        labNicknameClient.setFont(new java.awt.Font("Dialog", 0, 11));
        labNicknameClient.setText("Nickname");
        labNicknameClient.setBounds(new Rectangle(10, 70, 90, 15));
        tfServerHost.setFont(new java.awt.Font("Dialog", 0, 11));
        tfServerHost.setText(CLIENT_SERVERHOST);
        tfServerHost.setBounds(new Rectangle(100, 10, 150, 21));
        tfServernameClient.setFont(new java.awt.Font("Dialog", 0, 11));
        tfServernameClient.setText(CLIENT_SERVERNAME);
        tfServernameClient.setBounds(new Rectangle(100, 40, 150, 21));
        tfNicknameClient.setFont(new java.awt.Font("Dialog", 0, 11));
        tfNicknameClient.setText(CLIENT_NICKNAME);
        tfNicknameClient.setBounds(new Rectangle(100, 70, 150, 19));
        butZurueckClient.setBounds(new Rectangle(10, 100, 115, 25));
        butZurueckClient.setFont(new java.awt.Font("SansSerif", 1, 11));
        butZurueckClient.setText("Zurück");
        butJoin.setBounds(new Rectangle(135, 100, 115, 25));
        butJoin.setFont(new java.awt.Font("SansSerif", 1, 11));
        butJoin.setText("Verbinden");
        clientPanel.add(labServerHost, null);
        clientPanel.add(labServernameClient, null);
        clientPanel.add(labNicknameClient, null);
        clientPanel.add(tfServerHost, null);
        clientPanel.add(tfServernameClient, null);
        clientPanel.add(tfNicknameClient, null);
        clientPanel.add(butZurueckClient, null);
        clientPanel.add(butJoin, null);

        butZurueckClient.addActionListener(this);
        butZurueckClient.setIcon(Toolbox.loadZurueckIcon(getClass()));
        butJoin.addActionListener(this);
        butJoin.setIcon(Toolbox.loadConnectorIcon(getClass()));
    }
}
