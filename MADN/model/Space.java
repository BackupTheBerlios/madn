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
public class Space {
	
	private int position;
	private Piece piece = null;
	
	public Space (int position){
		this.position = position;
	}
	
	public boolean isOccupied(){
		return (piece != null);
	}
	
	public boolean isVacant(){
		return (piece == null);
	}
	
	public int getPosition(){
		return position;
	}
	
	public Piece occupy(Piece newPiece){
		Piece oldPiece = this.piece;
		this.piece = newPiece;
		return oldPiece; 
	}
	
	public void leave(){
		this.piece = null;
	}
	
	public Piece getPiece(){
		return piece;
	}
	
}
