package gui;

import java.util.EventListener;

import model.Piece;

/**
 * Interface: ClientListener
 * =========================
 * Von Klassen, die von Statusänderungen einer Instanz der Klasse Client
 * benachrichtigt werden wollen, zu implementierendes Interface (hier: GameFrame)  
 */
public interface ClientListener extends EventListener {
	/**
	 * Methode: boardConstellationChanged
	 * ----------------------------------
	 * Benachrichtigt Listener bei Änderungen der Spielbrettkonstellation.
	 * @param pieces = Array der logischen Repräsentanten (@see model.Piece) der Spielsteine (nach Farben)
	 */
	public void boardConstellationChanged(Piece[][] pieces);
	/**
	 * Methode: statusChanged
	 * ----------------------
	 * Benachrichtigt Listener über Änderungen des Client-Aktivitätsstatus.
	 */
	public void statusChanged ();
	/**
	 * Methode: showMessage
	 * --------------------
	 * Lässt Listener Nachricht/Information anzeigen
	 * @param msg = Nachricht
	 */
	public void showMessage (String msg);
	/**
	 * Methode: showRadioMesssage
	 * --------------------------
	 * Lässt Listener "Spielradiomeldung" anzeigen
	 * @param msg = Radiomeldung
	 */
	public void showRadioMessage (String msg);
	/**
	 * Methode: showErrorMesssage
	 * --------------------------
	 * Lässt Listener Fehlermeldung anzeigen
	 * @param msg = Fehlermeldung
	 */
	public void showErrorMessage(String msg); 
}
