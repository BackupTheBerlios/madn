package gui;

import java.util.EventListener;

import model.Piece;

/**
 * Interface: ClientListener
 * =========================
 * Von Klassen, die von Status�nderungen einer Instanz der Klasse Client
 * benachrichtigt werden wollen, zu implementierendes Interface (hier: GameFrame)  
 */
public interface ClientListener extends EventListener {
	/**
	 * Methode: boardConstellationChanged
	 * ----------------------------------
	 * Benachrichtigt Listener bei �nderungen der Spielbrettkonstellation.
	 * @param pieces = Array der logischen Repr�sentanten (@see model.Piece) der Spielsteine (nach Farben)
	 */
	public void boardConstellationChanged(Piece[][] pieces);
	/**
	 * Methode: statusChanged
	 * ----------------------
	 * Benachrichtigt Listener �ber �nderungen des Client-Aktivit�tsstatus.
	 */
	public void statusChanged ();
	/**
	 * Methode: showMessage
	 * --------------------
	 * L�sst Listener Nachricht/Information anzeigen
	 * @param msg = Nachricht
	 */
	public void showMessage (String msg);
	/**
	 * Methode: showRadioMesssage
	 * --------------------------
	 * L�sst Listener "Spielradiomeldung" anzeigen
	 * @param msg = Radiomeldung
	 */
	public void showRadioMessage (String msg);
	/**
	 * Methode: showErrorMesssage
	 * --------------------------
	 * L�sst Listener Fehlermeldung anzeigen
	 * @param msg = Fehlermeldung
	 */
	public void showErrorMessage(String msg); 
}
