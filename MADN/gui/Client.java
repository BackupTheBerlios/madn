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
	 * Veranlasst Client angeschlossene Listener über Veränderungen der Spielsituation zu benachrichtigen.
	 * @param animationInclusive = true: die Spielbrettkonstellation hat sich geändert 
	 * @throws RemoteException
	 */
	public void refresh(boolean animationInclusive) throws RemoteException;
	/**
	 * Methode: throwTheDice
	 * ---------------------
	 * Fordert Würfelergebnis vom Server an. 
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
	 * Setzt Aktivitätsstatus des Clients.
	 * @param status = Aktivitätsstatus
	 * @throws RemoteException
	 */
	public void setStatus (int status) throws RemoteException;
	/**
	 * Methode: getStatus
	 * ------------------
	 * @return Aktivitätsstatus des Clients
	 * @throws RemoteException
	 */
	public int getStatus () throws RemoteException;
	/**
	 * Methode: setClientListener
	 * --------------------------
	 * Registriert Listener für Client-Events.
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
	 * Legt die dem Client verbliebenen Würfelversuche fest. 
	 * @param attempts = Anzahl der Würfelversuche
	 * @throws RemoteException
	 */
	public void setAttempts(int attempts) throws RemoteException;
	/**
	 * Methode: decrementAttempts
	 * --------------------------
	 * Verringert die aktuelle Anzahl an Würfelversuchen um 1.
	 * @throws RemoteException
	 */
	public void decrementAttempts() throws RemoteException;
	/**
	 * Methode: getAttempts
	 * --------------------
	 * @return Anzahl der verbliebenen Würfelversuche
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
	 * @return das letzte Würfelergebnis des Clients
	 * @throws RemoteException
	 */
	public int getDiceResult () throws RemoteException;
	/**
	 * Methode: setDiceResult
	 * ----------------------
	 * Setzt das aktuelle Würfelergebnis (wird i.d.R. vom Server geliefert)
	 * @param dice = Würfelergebnis
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
