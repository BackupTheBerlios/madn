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
public final class Constants {
	
	// Farbcodes
	public static int RED = 0;
	public static int BLACK = 1;
	public static int BLUE = 2;
	public static int GREEN = 3;
	
	// Startfeldindex
	public static int START = -1;
	
	// Blocking-Codes
	public static int NO_BLOCKING = 0;
	public static int OCCUPATION = 4;
	public static int HOPPING = 6;
	
	// Errorcode der "kein Zug möglich"-Fehlermeldung
	public static int NO_MOVEABLE_PIECE = 9;
	
	// Client-Stati
	public static int INACTIVE = 0; // => nichts
	public static int ACTIVE_RESTRICTED = 1; // => nicht würfeln, Figur wählen, rücken
	public static int ACTIVE = 2; // => würfeln, Figur wählen, rücken 

}
