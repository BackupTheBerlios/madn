/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

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
	
	// Logisches Modell des Spiels
	private BoardModel bm;
	// Client-Liste
	private Client[] clients = {null, null, null, null};
	// ID/Farbe des Farbe des als nächstes ziehenden Spielers
	private int activeClient = -1; 
	// Liste der verfügbaren Spielerfarben
	private ArrayList colors; 
	// Wird aktuell gespielt?
	private boolean busy = false;  	

	public ServerImpl(String nickname) throws RemoteException {
		super(Constants.RED, nickname);
		setServer(this);
		clients[Constants.RED] = this;
		bm = new BoardModel();
		initColors();
		sendRadioMessage("Spieler " + nickname + " [" + Toolbox.colorToString(Constants.RED) + "] hat sich angemeldet.");
	}

	public Client newClient (String nickname) throws RemoteException{
		Client c = null;
		
		int color = getNextAvailableColor();
		
		if (color != -1){
			if (!busy){
				c = new ClientImpl(this, color, nickname);
				clients[color] = c;
				sendRadioMessage(nickname + " [" + Toolbox.colorToString(color) + "] hat sich angemeldet.");
			} else {
				// TODO Exception: Spiel läuft
			}
		}else{
			// TODO Exception: Server ausgelastet
		}
		
		return c;
	}
	
	public boolean existsClient (Client c){
		return true;
	}
	
	public void removeClient (int id){
		if ((id >= 0) && (id < 4)){
			if (clients[id] != null){
				try {
					// Serververbindung kappen
					clients[id].setServer(null);
					// Farbe wieder verfügbar machen
					colors.add(new Integer(id));
					// Aus Client-Liste löschen
					clients[id] = null;
					// TODO: Spielfarbe auf BoardModel zurücksetzen
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
	
	
	public void removeClient(Client c) throws RemoteException{
		
	}
	
	public void reset() throws RemoteException {
		
	}
	
	public void move (int color, int id, int distance) throws RemoteException, InvalidMoveException {
		
		if (busy){
			if (color == activeClient){
				try {
					bm.move(color, id, distance);
					sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] ist mit Spielstein " + (id+1) + " gezogen.");
					if (!bm.isGameOver()){
						if (distance==6){
							clients[activeClient].setStatus(Constants.ACTIVE);
							resetActiveClientAttempts();
							sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] ist abermals am Zug.");
						}else{ 
							setNextClientActive();
							sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");
						}
					}else{
						clients[activeClient].setStatus(Constants.INACTIVE);
						sendRadioMessage("Game Over!!! " + clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] ist der Gewinner.");
						clients[activeClient].recieveMessage("Game Over!!!\nHerzlichen Glückwunsch Sie haben gewonnen.");
						activeClient = -1;
					}
					refreshClients(true);
				} catch (InvalidMoveException e) {
					if (e.getErrorCode() == Constants.NO_MOVEABLE_PIECE){
						if (clients[activeClient].getAttempts() > 0){
							clients[activeClient].recieveMessage("Mit dem aktuellen Würfelergebnis ist kein Zug möglich.\nWürfeln Sie noch einmal!");
						}else{
							sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(color) + "] konnte nicht ziehen.");
							setNextClientActive();
							sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.");
						}
					}else{
						clients[activeClient].setStatus(Constants.ACTIVE_RESTRICTED);
						clients[activeClient].recieveMessage(e.getMessage());
					}
					refreshClients(false);
					throw (e);
				}
			}else{
				sendRadioMessage("Debug: du bist nicht dran");
				// TODO Exception: Anderer Spieler am Zug
			}
		}else{
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
			clients[activeClient].setStatus(Constants.INACTIVE);
		
			for (int i=activeClient+1; i<=activeClient+clients.length; i++){
				if (clients[i%4] != null){
					activeClient = i%4;
					clients[activeClient].setStatus(Constants.ACTIVE);
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
	
	public int dice(int client) throws RemoteException {
		int result = 0;
		if (client == activeClient){
			result = bm.throwTheDice();
			clients[client].decrementAttempts();
			sendRadioMessage(clients[client].getNickname() + " [" + Toolbox.colorToString(clients[client].getColor()) + "] hat eine " + result + " gewüfelt.");
		}else{
			// Exception: Anderer Spieler am Zug
		}
		return result;
	}
	
	public void sendRadioMessage(String msg) throws RemoteException {
		try {
			if (clients != null){
				for (int i = 0; i < clients.length; i++) {
					if (clients[i] != null)	clients[i].recieveRadioMessage(msg);			
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
		clients[activeClient].setStatus(Constants.ACTIVE);
		clients[activeClient].setAttempts(3);
		busy = true;
		
		refreshClients(true);
		sendRadioMessage(clients[activeClient].getNickname() + " [" + Toolbox.colorToString(clients[activeClient].getColor()) + "] am Zug.\nNeues Spiel gestartet.");
	}

	/* (non-Javadoc)
	 * @see gui.Server#getPieces()
	 */
	public Piece[][] getPieces() throws RemoteException {
		return bm.getPieces();
	}

}
