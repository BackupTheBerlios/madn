package gui;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Klasse: ClientImpl
 * ==================
 * Spezialisierung der Klasse UnicastRemoteObject, die die komlpette während des Spielverlaufs 
 * notwendige clientseitige, RMI-basierte Kommunikation realisiert und dazu alle Methoden des
 * Remote-Interface @see gui.Client implementiert 
 */
public class ClientImpl extends UnicastRemoteObject implements Client {

	// Serverobjekt
	private Server server;
	// Nickname
	private String nickName = "";
	//	Spielsteinfarbe und Client-ID auf Server
	private int color; 
	// Status => TriState-Logik: 0 = Inaktiv, 1 = Würfeln (Aktiv), 2 = Ziehen (Aktiv) 
	private int status = 0; 
	// Listener => GameFrame
	private ClientListener listener = null;
	//Anzahl der Würfe
	private int attempts = 0;
	// Würfelergebnis
	private int dice = 0;
	
	/**
	 * Konstruktor: ClientImpl
	 * -----------------------
	 * Erzeugt eine voll funkionsfähige Client-Instanz 
	 * @param serverhost		= IP des Hosts der RMI-Registry, bei der sich der Spiele-Server registriert hat
	 * @param servername		= Name, unter dem der Server bei der RMI-Registry registriert ist
	 * @param nickName			= frei gewählter Spielername
	 * @throws RemoteException
	 * @throws Exception
	 */
	public ClientImpl(String serverhost, String servername, String nickName) throws RemoteException, Exception {
		// Hole Server-RemoteObject
		this.server = (Server)Naming.lookup("//" + serverhost + "/" + servername);
		// Registriere Client bei Server
		this.server.newClient(this);
	}
	
	/**
	 * Konstruktor: ClientImpl
	 * -----------------------
	 * Erzeugt eine minimalisierte Client-Instanz 
	 * @param color		= Spielfarbe des Clients
	 * @param nickName	= frei gewählter Speilername
	 * @throws RemoteException
	 */
	public ClientImpl(int color, String nickName) throws RemoteException{
		this.color = color;
		this.nickName = nickName;
	}
	
	/**
	 * Methode: setServer
	 * ------------------
	 * @see Client#setServer(Server)
	 */
	public void setServer(Server server) throws RemoteException{
		this.server = server;
	}

	/**
	 * Methode: getColor
	 * -----------------
	 * @see Client#getColor()
	 */
	public int getColor() throws RemoteException{
		return color;
	}
	
	/**
	 * Methode: getServer
	 * ------------------
	 * @see Client#getServer()
	 */
	public Server getServer() throws RemoteException{
		return server;
	}
	
	/**
	 * Methode: refresh
	 * ----------------
	 * @see Client#refresh(boolean)
	 */
	public void refresh(boolean animationInclusive) throws RemoteException{
		if (listener != null){
			if (animationInclusive){
				listener.boardConstellationChanged(server.getPieces());
			}
			
			listener.statusChanged();
		}
	}
	
	/**
	 * Methode: setColor
	 * -----------------
	 * @see Client#setColor(int)
	 */
	public void setColor(int color) throws RemoteException {
	    this.color = color;
	}

	/**
	 * Methode: setStatus
	 * ------------------
	 * @see Client#setStatus(int)
	 */
	public void setStatus (int status) throws RemoteException{
		if ((status>=0)&&(status<=2)){
			this.status = status;
		}else status = 0;
	}
	
	/**
	 * Methode: getStatus
	 * ------------------
	 * @see Client#getStatus()
	 */
	public int getStatus () throws RemoteException{
		return status;		
	}
	
	/**
	 * Methode: throwTheDice
	 * ---------------------
	 * @see Client#throwTheDice()
	 */
	public void throwTheDice() throws RemoteException {
		server.dice(this.getColor());
	}

	/**
	 * Methode: recieveRadioMessage
	 * ----------------------------
	 * @see Client#recieveRadioMessage(String)
	 */
	public void recieveRadioMessage(String msg) throws RemoteException {
		if (listener != null){
			listener.showRadioMessage(msg);
		}
	}
	
	/**
	 * Methode: setClientListener
	 * --------------------------
	 * @see Client#setClientListener(ClientListener)
	 */
	public void setClientListener (ClientListener listener) throws RemoteException{
		this.listener = listener;
	}

	/**
	 * Methode: getNickname
	 * --------------------
	 * @see Client#getNickname()
	 */
	public String getNickname() throws RemoteException {
		return nickName;
	}

	/**
	 * Methode: setAttempts
	 * --------------------
	 * @see Client#setAttempts(int)
	 */
	public void setAttempts(int attempts) throws RemoteException {
		this.attempts = attempts;	
	}

	/**
	 * Methode: decrementAttempts
	 * --------------------------
	 * @see Client#decrementAttempts()
	 */
	public void decrementAttempts() throws RemoteException {
		attempts--;
	}

	/**
	 * Methode: getAttempts
	 * --------------------
	 * @see Client#getAttempts()
	 */
	public int getAttempts() throws RemoteException {
		return attempts;
	}

	/**
	 * Methode: recieveMessage
	 * -----------------------
	 * @see Client#recieveMessage(String)
	 */
	public void recieveMessage(String msg) throws RemoteException {
		listener.showMessage(msg);
	}
	
	/**
	 * Methode: hasAttemptsLeft
	 * ------------------------
	 * @see Client#hasAttemptsLeft()
	 */
	public boolean hasAttemptsLeft () throws RemoteException{
		return attempts > 0;
	}

	/**
	 * Methode: getDiceResult
	 * ----------------------
	 * @see Client#getDiceResult()
	 */
	public int getDiceResult() throws RemoteException {
		return dice;
	}

	/**
	 * Methode: setDiceResult
	 * ----------------------
	 * @see Client#setDiceResult(int)
	 */
	public void setDiceResult(int dice) throws RemoteException {
		this.dice = dice;
	}
	
	/**
	 * Methode: existsServer
	 * ---------------------
	 * @see Client#existsServer()
	 */
	public boolean existsServer() throws RemoteException{
		return (server != null);
	}
	
	/**
	 * Methode: recieveErrorMessage
	 * ----------------------------
	 * @see Client#recieveErrorMessage(String)
	 */
	public void recieveErrorMessage(String msg) throws RemoteException {
		listener.showErrorMessage(msg);
	}
}
