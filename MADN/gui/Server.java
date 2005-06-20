package gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.InvalidMoveException;
import model.Piece;

/**
 * Interface: Server
 * =================
 * Remote-Interface das alle notwendigen Methoden f�r Zugriffe von Clients auf Server-Dienste
 * spezifiziert.   
 */
public interface Server extends Remote {
	
	/**
	 * Methode newClient:
	 * ------------------
	 * Registriert, falls freie Teilnehmerpl�tze vorhanden, Client-Objekt bei Server  
	 * @param newCient = Stub-Objekt des �bergebenen Clients (Konversion Client => Stub implizit, weil RMI-Call
	 * @throws RemoteException
	 * @throws Exception
	 */
	public void newClient(Client newCient) throws RemoteException, Exception;
	
	/**
	 * Methode removeClient:
	 * ---------------------
	 * Entfernt �bergebenen Client aus Teilnehmerliste.
	 * @param id = Color-Code des zu entfernenden Clients
	 * @throws RemoteException
	 */
	public void removeClient(int id) throws RemoteException;
	
	/**
	 * Methode reset:
	 * --------------
	 * Deprecated.
	 * @throws RemoteException
	 */
	public void reset() throws RemoteException;
	
	/**
	 * Methode move:
	 * -------------
	 * R�ckt einen bestimmten Spielkegel, einer bestimmten Farbe 
	 * um eine bestimmte Distanz (Augenzahl) vor.
	 * @param color    = Spielfarbe
	 * @param id       = Nr. Spielkegel
	 * @param distance = Augenzahl W�rfelversuch
	 * @throws RemoteException
	 * @throws InvalidMoveException
	 */
	public void move (int color, int id, int distance) throws RemoteException, InvalidMoveException;
	
	/**
	 * Methode isActiveClientAllowedToDice3Times:
	 * ------------------------------------------
	 * @return true, wenn aktivem Clients drei W�rfelversuche zur Verf�gung stehen, ansonsten false.
	 * @throws RemoteException
	 */
	public boolean isActiveClientAllowedToDice3Times() throws RemoteException;
	
	/**
	 * Methode dice:
	 * -------------
	 * Generiert zuf�lliges, in [1;6] diskret gleichm��ig verteiltes W�rfelergebnis und
	 * weist es einem bestimmten Client zu.
	 * @param client = Color-Code des Clients
	 * @throws RemoteException
	 */
	public void dice(int client) throws RemoteException;
	
	/**
	 * Methode sendRadioMessage:
	 * -------------------------
	 * Versendet Nachricht an registrierte Clients.
	 * @param msg              = Nachricht 
	 * @param inclActiveClient = true => Versand auch an aktiven Client
	 * @throws RemoteException
	 */
	public void sendRadioMessage(String msg, boolean inclActiveClient) throws RemoteException;
	
	/**
	 * Methode sendRadioMessage:
	 * -------------------------
	 * Versendet Nachricht an registrierte Clients.
	 * @param msg = Nachricht 
	 * @throws RemoteException
	 */
	public void sendRadioMessage(String msg) throws RemoteException;
	
	/**
	 * Methode startNewGame:
	 * ---------------------
	 * Beendet aktuelles Spiel und startet ein neues.
	 * @throws RemoteException
	 */
	public void startNewGame() throws RemoteException;
	
	/**
	 * Methode quitGame:
	 * -----------------
	 * Beendet aktuelles Spiel.
	 * @throws RemoteException
	 */
	public void quitGame() throws RemoteException;
	
	public Piece[][] getPieces() throws RemoteException;

}
