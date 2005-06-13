package gui;

import javax.media.j3d.Appearance;

import com.sun.j3d.utils.geometry.Cone;

/**
 * Klasse: MyCone
 * ==============
 * Erweiterung des Java3D-Primitives Cone um die Membervariablen color und id.
 * Verwendung: Erzeugung von Spielfiguren (@see gui.Pawn) 
 */
public class MyCone extends Cone {

	// Spielsteinfarbe
	private int color;
	// Spielstein-ID
	private int id;
	
	/**
	 * Konstruktor: MyCone
	 * -------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Cone#Cone();
	 */
	public MyCone(int color, int id) {
		super();
		this.color = color;
		this.id = id;
	}
	
	/**
	 * Konstruktor: MyCone
	 * -------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Cone#Cone(float, float)
	 */
	public MyCone(int color, int id, float arg0, float arg1) {
		super(arg0, arg1);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MyCone
	 * -------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Cone#Cone(float, float, javax.media.j3d.Appearance)
	 */
	public MyCone(int color, int id, float arg0, float arg1, Appearance arg2) {
		super(arg0, arg1, arg2);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MyCone
	 * -------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Cone#Cone(float, float, int, javax.media.j3d.Appearance)
	 */
	public MyCone(int color, int id, float arg0, float arg1, int arg2, Appearance arg3) {
		super(arg0, arg1, arg2, arg3);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MyCone
	 * -------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Cone#Cone(float, float, int, int, int, javax.media.j3d.Appearance)
	 */
	public MyCone(int color, int id, float arg0, float arg1, int arg2, int arg3, int arg4,
			Appearance arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		this.color = color;
		this.id = id;
	}
	
	/**
	 * Methode: setColor
	 * -----------------
	 * Set-Methode der Membervariable color
	 * @param color = Spielsteinfarbe
	 */
	public void setColor(int color){
		this.color = color;
	}
	
	/**
	 * Methode: getColor
	 * -----------------
	 * Get-Methode der Membervariable color
	 * @return Spielsteinfarne
	 */
	public int getColor(){
		return color;
	}
	/**
	 * Methode: setID
	 * --------------
	 * Set-Methode der Membervariable id
	 * @param color = Spielstein-ID
	 */	
	public void setID(int id){
		this.id = id;
	}

	/**
	 * Methode: getID
	 * -----------------
	 * Get-Methode der Membervariable id
	 * @return Spielstein-ID
	 */
	public int getID(){
		return id;
	}
}
