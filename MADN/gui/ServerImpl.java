/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.BoardModel;
import model.Constants;
import model.InvalidMoveException;
import model.Piece;

/**
 * @author Mario
 *
 */
public class ServerImpl extends ClientImpl implements Server {
	
	// Konstanten
	private static int NO_ACTIVE_CLIENT = -1;
	// Logisches Modell des Spiels
	private BoardModel bm;
	// Client-Liste
	private Client[] clients = {null, null, null, null};
	// ID/Farbe des Farbe des als nächstes ziehenden Spielers
	private int activeClient = NO_ACTIVE_CLIENT; 
	// Liste der verfügbaren Spielerfarben
	private ArrayList colors; 
	// Wird aktuell gespielt?
	private boolean busy = false;
	// RMI-Prozess
	private Process rmiProcess = null;
	// Der Servername
	private String servername = "";

	public ServerImpl(String servername, String nickname) throws RemoteException, Exception {
		super(Constants.RED, nickname);
		this.servername = servername;
        File rmiFile = new File(System.getProperty("java.home") + File.separator + "bin" + File.separator + "rmiregistry.exe");
        File libDir = new File(System.getProperty("java.home") + File.separator + "lib");
        File jarFile = new File("madn.jar");
        if(rmiFile.exists() && libDir.exists()) {
            try {
                String classpath = jarFile.exists() ? libDir.getAbsolutePath() + ";" + jarFile.getAbsolutePath() : libDir.getAbsolutePath();
                rmiProcess = Runtime.getRuntime().exec(rmiFile.getAbsolutePath() + " -J-classpath -J\"" + classpath + "\"");
				Naming.rebind(servername, this);
            } catch(IOException exc) {
                if(rmiProcess != null)
                    rmiProcess.destroy();
                throw new Exception("RMI-Registry konnte nicht gestartet werden.");
            } catch(RuntimeException re) {
                rmiProcess.destroy();
                throw new Exception("Der Server konnte bei 'rmiregistry' nicht angemeldet werden.");
            }
        } else {
			throw new Exception("RMI-Registry wurde nicht gefunden.");
        }
		setServer(this);
		clients[Constants.RED] = this;
		bm = new BoardModel();
		initColors();
		sendRadioMessage("Spieler " + nickname + " [" + Toolbox.colorToString(Constants.RED) + "] hat sich angemeldet.");
	}

	public void newClient(Client newClient) throws RemoteException, Exception {
		
		int color = getNextAvailableColor();
		
		if (color != -1){
			if (!busy){
			    newClient.setColor(color);
				clients[color] = newClient;
				sendRadioMessage(newClient.getNickname() + " [" + Toolbox.colorToString(color) + "] hat sich angemeldet.");
			} else {
				colors.add(0, new Integer(color));
				throw new Exception("Spiel läuft bereits.");
			}
		} else {
			throw new Exception("Server ausgelastet.");
		}
	}

	private void removeServer (){
		for (int i=0; i<clients.length; i++){
			if (clients[i] != null){
				try {
					clients[i].setServer(null);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    try { Naming.unbind(servername); } catch(Exception exc) {}
	    rmiProcess.destroy();
	}

	public void removeClient (int id){
		if ((id >= 0) && (id < 4)){
			if (clients[id] != null){
				try {
					// Serververbindung kappen
					clients[id].setServer(null);
					
					// Farbe wieder verfügbar machen
					colors.add(new Integer(id));
					
					// Spielfarbe auf BoardModel zurücksetzen
					bm.reset(id);
					
					if (clients[id] instanceof Server){
						
						// Message an alle Spieler
						sendRadioMessage("Das Spiel kann nicht fortgesetzt werden!\nDer Server wurde beendet!");
						// Alle Spieler inaktiv setzen
						if (activeClient != NO_ACTIVE_CLIENT)
							clients[activeClient].setStatus(Constants.INACTIVE);
						// Server aus Client-Liste löschen
						clients[id] = null;
						// Aktualisiere alle Clients
						refreshClients(true);
						// Entferne Server in allen Clients
						removeServer();
						
					}else{
						
						// Message an alle Spieler
						sendRadioMessage(clients[id].getNickname() + " [" + Toolbox.colorToString(id) + "] hat sich abgemeldet.");
						
						// Client aus Client-Liste löschen
						clients[id] = null;
						
						// ggf. anderen Spieler aktivieren
						if (id == activeClient){
							setNextClientActive();
							sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");
						}
						
						// Aktualisiere alle Clients
						refreshClients(true);			
					}
					
				} catch (RemoteException e) {
					// TODO Exception: Client konnte nicht gelöscht werden
					e.printStackTrace();
				}
			}else{
				// TODO Exception: Client existiert nicht (mehr)
			}
		}else{
			// TODO Exception: Ungültige ID
		}
	}
	
	private int getNextAvailableColor(){
		if (colors.size() > 0){
			Integer i = (Integer)colors.remove(0);
			return i.intValue();
		}else return -1;
	}
	
	
	public void reset() throws RemoteException {
		
	}
	
	public void move (int color, int id, int distance) throws RemoteException, InvalidMoveException {
		
		if (busy){
			if (color == activeClient){
				if (clients[activeClient].getStatus() == Constants.ACTIVE_MOVE){
					try {
						bm.move(color, id, distance);
						sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] ist mit Spielstein " + (id+1) + " gezogen.");
						if (!bm.isGameOver()){
							if (distance==6){
								clients[activeClient].setStatus(Constants.ACTIVE_DICE);
								resetActiveClientAttempts();
								refreshClients(true);
								sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] ist abermals am Zug.");
							}else{ 
								setNextClientActive();
								refreshClients(true);
								sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");
							}
						}else{
							clients[activeClient].setStatus(Constants.INACTIVE);
							int c = activeClient;
							activeClient = NO_ACTIVE_CLIENT;
							refreshClients(true);
							sendRadioMessage("Game Over!!! " + clients[c].getNickname() + " [" + Toolbox.colorToString(color) + "] ist der Gewinner.");
							clients[c].recieveMessage("Game Over!!!\nHerzlichen Glückwunsch Sie haben gewonnen.");
						}
						
					} catch (InvalidMoveException e) {
						if (e.getErrorCode() == Constants.NO_MOVEABLE_PIECE){
							if (clients[activeClient].getAttempts() > 0){
								clients[activeClient].recieveMessage("Mit dem aktuellen Würfelergebnis ist kein Zug möglich.\nWürfeln Sie noch einmal!");
							}else{
								sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] konnte nicht ziehen.");
								setNextClientActive();
								refreshClients(false);
								sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");
							}
						}else{
							//clients[activeClient].setStatus(Constants.ACTIVE_MOVE);
							clients[activeClient].recieveErrorMessage(e.getMessage());
						}
						
						//throw (e);
					}
				}else{ // clients[activeClient].getStatus() == Constants.ACTIVE_MOVE
					sendRadioMessage("Debug: Du bist nicht am Zug.");
				}
			}else{ // End: color == activeClient
				sendRadioMessage("Debug: du bist nicht dran");
				// TODO Exception: Anderer Spieler am Zug
			}
		}else{ // End: busy
			sendRadioMessage("Debug: kein Spiel");
			// TODO Exception: Es läuft kein Spiel
		}
	}
	
	public boolean isActiveClientAllowedToDice3Times() throws RemoteException {
		return bm.isAllowedToDice3Times(clients[activeClient].getColor());
	}
	
	private void resetActiveClientAttempts(){
		try {
			if (isActiveClientAllowedToDice3Times())
				clients[activeClient].setAttempts(3);
			else
				clients[activeClient].setAttempts(1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setNextClientActive() {
		
		try {
			
			int tmpActive = activeClient;
			activeClient = NO_ACTIVE_CLIENT;
			
			if (clients[tmpActive] != null){
				clients[tmpActive].setStatus(Constants.INACTIVE);
			}
					
			for (int i=tmpActive+1; i<=tmpActive+clients.length; i++){
				if (clients[i%4] != null){
					activeClient = i%4;
					clients[activeClient].setStatus(Constants.ACTIVE_DICE);
					resetActiveClientAttempts();
					break;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initColors(){
		colors = new ArrayList();
		colors.add(new Integer(Constants.BLACK));
		colors.add(new Integer(Constants.BLUE));
		colors.add(new Integer(Constants.GREEN));
	}
	
	private void refreshClients(boolean all){
		try {
			if (clients != null){
				for (int i = 0; i < clients.length; i++) {
					if (clients[i] != null)	clients[i].refresh(all);			
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dice(int client) throws RemoteException {
		
		int distance = 0;
		
		if (client == activeClient){
			
			if (clients[client].getStatus() == Constants.ACTIVE_DICE){
				
				// Würfeln
				distance = bm.throwTheDice();
				// Resultat Client mitteilen
				clients[client].setDiceResult(distance);
				// Würfelversuche dekrementieren
				clients[client].decrementAttempts();
				// Message: Würfelergebnis an alle Spieler
				sendRadioMessage(clients[client].getNickname() + " [" + Toolbox.colorToString(clients[client].getColor()) + "] hat eine " + distance + " gewüfelt.", false);
				
				// Auswertung Wurfergebnis
				if (bm.existsMoveablePiece(clients[client].getColor(), distance)){
					// Spieler kann mit einer Figur ziehen
					clients[client].setStatus(Constants.ACTIVE_MOVE);
					// => Spieler muss Figur wählen und ziehen
				}else{ 
					if (!clients[client].hasAttemptsLeft()){
						// Spieler kann nicht ziehen und hat keine Wurfversuche mehr => der Nächste bitte!
						sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] konnte nicht ziehen.");
						setNextClientActive();
						sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");	
					}
				}
				
				// Aktualisiere Clients (nur Component-Enabling)
				refreshClients(false);
				
			}else{
				//	Exception: Nicht Würfeln sonderen Rücken
			}
		
		}else{
			// Exception: Anderer Spieler am Zug
		}
		
		//return distance;
	}
	
	public void sendRadioMessage(String msg) throws RemoteException {
		sendRadioMessage(msg, true);
	}
	
	public void sendRadioMessage(String msg, boolean inclActiveClient) throws RemoteException {
		try {
			if (clients != null){
				for (int i = 0; i < clients.length; i++) {
					if ((inclActiveClient || (i!=activeClient)) &&(clients[i] != null))
						clients[i].recieveRadioMessage(msg);			
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see gui.Server#startNewGame()
	 */
	public void startNewGame() throws RemoteException {
		bm = new BoardModel();
		
		if ((activeClient>=0) && (activeClient < clients.length))
			clients[activeClient].setStatus(Constants.INACTIVE);
		
		activeClient = Constants.RED;
		clients[activeClient].setStatus(Constants.ACTIVE_DICE);
		clients[activeClient].setAttempts(3);
		busy = true;
		
		refreshClients(true);
		sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.\nNeues Spiel gestartet.");
	}
	
	/* (non-Javadoc)
	 * @see gui.Server#startNewGame()
	 */
	public void quitGame() throws RemoteException {
		bm = new BoardModel();
		
		if ((activeClient>=0) && (activeClient < clients.length))
			clients[activeClient].setStatus(Constants.INACTIVE);
		
		activeClient = NO_ACTIVE_CLIENT;
		busy = false;
		
		refreshClients(true);
		sendRadioMessage("Das aktuelle Spiel wurde beendet.");
	}
	
	

	/* (non-Javadoc)
	 * @see gui.Server#getPieces()
	 */
	public Piece[][] getPieces() throws RemoteException {
		return bm.getPieces();
	}
}
