package gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface: Client
 * =================
 * Remote-Interface das alle notwendigen clientseitigen Methoden spezifiziert.   
 */
public interface Client extends Remote {

	/**
	 * Methode: getColor
	 * -----------------
	 * @return Spielfarbe des Clients
	 * @throws RemoteException
	 */
	public int getColor() throws RemoteException;
	/**
	 * Methode: refresh
	 * ----------------
	 * Veranlasst Client angeschlossene Listener �ber Ver�nderungen der Spielsituation zu benachrichtigen.
	 * @param animationInclusive = true: die Spielbrettkonstellation hat sich ge�ndert 
	 * @throws RemoteException
	 */
	public void refresh(boolean animationInclusive) throws RemoteException;
	/**
	 * Methode: throwTheDice
	 * ---------------------
	 * Fordert W�rfelergebnis vom Server an. 
	 * @throws RemoteException
	 */
	public void throwTheDice() throws RemoteException;
	/**
	 * Methode: setServer
	 * ------------------
	 * Weist dem Client einen Server zu.
	 * @param server = das Serverobjekt
	 * @throws RemoteException
	 */
	public void setServer(Server server) throws RemoteException;
	/**
	 * Methode: getServer
	 * ------------------
	 * @return Serverobjekt (=Kommunikationspartner) des Clients
	 * @throws RemoteException
	 */
	public Server getServer() throws RemoteException;
	/**
	 * Methode: recieveRadioMessage
	 * ----------------------------
	 * Leitet "Spielradiomeldung" an angeschlossene Listener weiter.
	 * @param msg = Radiomeldung
	 * @throws RemoteException
	 */
	public void recieveRadioMessage (String msg) throws RemoteException;
	/**
	 * Methode: recieveErroMessage
	 * ---------------------------
	 * Leitet Fehlermeldung an angeschlossene Listener weiter.
	 * @param msg = Fehlermeldung
	 * @throws RemoteException
	 */
	public void recieveErrorMessage (String msg) throws RemoteException;
	/**
	 * Methode: recieveRadioMessage
	 * ----------------------------
	 * Leitet (allgemeine) Nachricht/Information an angeschlossene Listener weiter.
	 * @param msg = Nachricht
	 * @throws RemoteException
	 */
	public void recieveMessage (String msg) throws RemoteException;
	/**
	 * Methode: setStatus
	 * ------------------
	 * Setzt Aktivit�tsstatus des Clients.
	 * @param status = Aktivit�tsstatus
	 * @throws RemoteException
	 */
	public void setStatus (int status) throws RemoteException;
	/**
	 * Methode: getStatus
	 * ------------------
	 * @return Aktivit�tsstatus des Clients
	 * @throws RemoteException
	 */
	public int getStatus () throws RemoteException;
	/**
	 * Methode: setClientListener
	 * --------------------------
	 * Registriert Listener f�r Client-Events.
	 * @param listener = das Listener-Objekt (hier: GameFrame)
	 * @throws RemoteException
	 */
	public void setClientListener (ClientListener listener) throws RemoteException;
	/**
	 * Methode getNickname
	 * -------------------
	 * @return Spielername
	 * @throws RemoteException
	 */
	public String getNickname() throws RemoteException;
	/**
	 * Methode: setAttempts
	 * --------------------
	 * Legt die dem Client verbliebenen W�rfelversuche fest. 
	 * @param attempts = Anzahl der W�rfelversuche
	 * @throws RemoteException
	 */
	public void setAttempts(int attempts) throws RemoteException;
	/**
	 * Methode: decrementAttempts
	 * --------------------------
	 * Verringert die aktuelle Anzahl an W�rfelversuchen um 1.
	 * @throws RemoteException
	 */
	public void decrementAttempts() throws RemoteException;
	/**
	 * Methode: getAttempts
	 * --------------------
	 * @return Anzahl der verbliebenen W�rfelversuche
	 * @throws RemoteException
	 */
	public int getAttempts() throws RemoteException;
	/**
	 * Methode: hasAttemptsLeft
	 * @return true, wenn attempts>0, ansonsten false
	 * @throws RemoteException
	 */
	public boolean hasAttemptsLeft () throws RemoteException;
	/**
	 * Methode: setColor
	 * -----------------
	 * Setzt die Spielfarbe des Clients
	 * @param color = Spielfarbe
	 * @throws RemoteException
	 */
	public void setColor(int color) throws RemoteException;
	/**
	 * Methode: getDiceResult
	 * ----------------------
	 * @return das letzte W�rfelergebnis des Clients
	 * @throws RemoteException
	 */
	public int getDiceResult () throws RemoteException;
	/**
	 * Methode: setDiceResult
	 * ----------------------
	 * Setzt das aktuelle W�rfelergebnis (wird i.d.R. vom Server geliefert)
	 * @param dice = W�rfelergebnis
	 * @throws RemoteException
	 */
	public void setDiceResult (int dice) throws RemoteException;
	/**
	 * Methode: existsServer
	 * ---------------------
	 * @return true, wenn Membervariable server gesetzt, ansonsten false
	 * @throws RemoteException
	 */
	public boolean existsServer() throws RemoteException;

		
}
