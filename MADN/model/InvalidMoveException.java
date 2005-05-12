/*
 * Created on 03.05.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package model;

/**
 * @author Mario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvalidMoveException extends Exception {

	private static int NOM = 10;
	private static String[] messages = null;

	private int errorCode;

	public InvalidMoveException(int errorCode){
		super();

		if (messages == null) initializeErrorMessages();

		if ((errorCode >= 1) && (errorCode < messages.length))
			this.errorCode = errorCode;
		else this.errorCode = 0;
		
	}

	public String getMessage(){
		return messages[errorCode];
	}

	public int getErrorCode(){
		return errorCode;
	}

	public static void initializeErrorMessages(){

		messages = new String[NOM];

		for(int i = 0; i < messages.length; i++){
			messages[i] = null;
		}

		messages[0] = "Unbekannter Fehlercode.";
		messages[1] = "Spielstein nicht vorhanden.";
		messages[2] = "Ungültige Zugdistanz.";
		messages[3] = "Spielsteine aus dem Startfeld können nur bei Würfeln einer 6\nangesetzt werden!" +
					  	"\nWählen Sie einen Spielstein außerhalb des Startfelds.";
		messages[4] = "Zielfeld durch Spielstein gleicher Farbe besetzt." +
		  				"\nWählen Sie einen anderen Spielstein.";
		messages[5] = "Über's Ziel hinausgeschossen: Die gewürfelte Distanz kann\nnicht gerückt werden."+
		  				"\nWählen Sie einen anderen Spielstein.";
		messages[6] = "Im Zielbereich darf kein (eigener) Spielstein\nübersprungen werden."+
		  				"\nWählen Sie einen anderen Spielstein.";
		messages[7] = "Befinden sich noch Spielsteine auf dem Startfeld, muss bei\nWürfeln einer 6 einer dieser Steine angesetzt werden."+
		  				"\nWählen Sie einen Spielstein aus dem Startfeld.";
		messages[8] = "Es befindet sich einer Ihrer Spielsteine auf Ihrem Ansatzfeld.\nMit diesem Würfelergebnis kann/muss das Ansatzfeld\ngeräumt werden."+
		  				"\nWählen Sie den Spielstein des Ansatzfelds.";
		messages[9] = "Kein Zug möglich!!!";

	}
}