/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
	
	public ClientImpl(Server server, int color, String nickName) throws RemoteException{
		this(color, nickName);
		this.server = server;
		
	}
	
	public ClientImpl(int color, String nickName) throws RemoteException{
		this.color = color;
		this.nickName = nickName;
	}
	
	public void setServer(Server server) throws RemoteException{
		this.server = server;
	}

	public int getColor() throws RemoteException{
		return color;
	}
	
	public Server getServer() throws RemoteException{
		return server;
	}
	
	public void refresh(boolean animationInclusive) throws RemoteException{
		/*
		 * TODO:
		 * - Spielfeldkostellation aktualisieren (all = true)
		 * - Check: Spielende (all = true)
		 * - Aktiver Spieler (always)
		 */
		if (listener != null){
			if (animationInclusive){
				listener.boardConstellationChanged(server.getPieces());
				//if (server.isGameOver()) listener.gameIsOver();
			}
			
			listener.enablingChanged();
		}
	}
	
	public static void main(String[] args) {
	}

	public void setStatus (int status) throws RemoteException{
		if ((status>=0)&&(status<=2)){
			this.status = status;
		}else status = 0;
	}
	
	public int getStatus () throws RemoteException{
		return status;		
	}
	
	public void throwTheDice() throws RemoteException {
		server.dice(this.getColor());
	}

	public void recieveRadioMessage(String msg) throws RemoteException {
		if (listener != null){
			listener.addRadioMessage(msg);
		}
	}
	
	public void setClientListener (ClientListener listener) throws RemoteException{
		this.listener = listener;
	}

	public String getNickname() throws RemoteException {
		return nickName;
	}

	public void setAttempts(int attempts) throws RemoteException {
		this.attempts = attempts;	
	}

	public void decrementAttempts() throws RemoteException {
		attempts--;
	}

	public int getAttempts() throws RemoteException {
		return attempts;
	}

	public void recieveMessage(String msg) throws RemoteException {
		listener.showMessage(msg);
		
	}
	
	public boolean hasAttemptsLeft () throws RemoteException{
		return attempts > 0;
	}


	public int getDiceResult() throws RemoteException {
		return dice;
	}

	public void setDiceResult(int dice) throws RemoteException {
		this.dice = dice;
		
	}
	
	public boolean existsServer() throws RemoteException{
		return (server != null);
	}
}
