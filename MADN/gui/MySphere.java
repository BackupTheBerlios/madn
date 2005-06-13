package gui;

import javax.media.j3d.Appearance;

import com.sun.j3d.utils.geometry.Sphere;

/**
 * Klasse: MySphere
 * ================
 * Erweiterung des Java3D-Primitives Sphere um die Membervariablen color und id.
 * Verwendung: Erzeugung von Spielfiguren (@see gui.Pawn) 
 */
public class MySphere extends Sphere {

	// Spielsteinfarbe
	private int color;
	// Spielstein-ID
	private int id;
	
	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere(float)
	 */	
	public MySphere(int color, int id, float arg0) {
		super(arg0);
		this.color = color;
		this.id = id;
	}
	
	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere()
	 */	
	public MySphere(int color, int id) {
		super();
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere(float, javax.media.j3d.Appearance)
	 */	
	public MySphere(int color, int id, float arg0, Appearance arg1) {
		super(arg0, arg1);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere(float, int, javax.media.j3d.Appearance)
	 */	
	public MySphere(int color, int id, float arg0, int arg1, Appearance arg2) {
		super(arg0, arg1, arg2);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere(float, int, int)
	 */	
	public MySphere(int color, int id, float arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		this.color = color;
		this.id = id;
	}

	/**
	 * Konstruktor: MySphere
	 * ---------------------
	 * Erweitert Superklassen-Konstruktor um die Initilisierung der Membervaribalen color und id.
	 * @param color	= Spielsteinfarbe
	 * @param id	= Spielstein-ID
	 * @see Sphere#Sphere(float, int, int, javax.media.j3d.Appearance)
	 */	
	public MySphere(int color, int id, float arg0, int arg1, int arg2, Appearance arg3) {
		super(arg0, arg1, arg2, arg3);
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
