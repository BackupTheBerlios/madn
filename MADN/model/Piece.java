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
public class Piece {

	private int color;
	private int id;
	private int position;
	private boolean moveable;
	private int evalErrorCode;

	public Piece (int color, int id, int position){
		this.color = color;
		this.id = id;
		this.position = position;
		this.moveable = true;
		this.evalErrorCode = -1;
	}
	
	public Piece (int color, int id){
		this(color, id, Constants.START);
	}
	
	public boolean isAtStartingPoint(){
		return (position == Constants.START);
	}
	
	public boolean isOnTrack(){
		return ((position >= 0) && (position < 40));
	}
	
	public boolean isAtHome(){
		return ((position >= 40) && (position < 44));
	}
	
	public boolean hasFinished(){
		return (position >= 40);
	}
		
	public int getColor(){
		return color;
	}
	
	public int getId(){
		return id;
	}
	
	public void setPosition(int position){
		this.position = position;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int getRealPosition(){
		return ((position>=0)&&(position<40)) ? (color*10 + position) % 40 : position; 			
	}
	
	public boolean isMoveable(){
		return moveable;
	}
	
	public void setMoveable(boolean moveable){
		this.moveable = moveable;
	}
	
	public void setEvaluationError(int errorCode){
		if (errorCode >= 0){
			this.evalErrorCode = errorCode;
			this.moveable = false;
		}else{
			this.evalErrorCode = -1;
			this.moveable = true;			
		}
	}
	
	public int getEvaluationError(){
		return evalErrorCode;
	}
	
	public void resetMoveableStatus(){
		this.moveable = true;
		this.evalErrorCode = -1;
	}
	
	public boolean equals (Object obj){
		boolean eq = false;
		
		if ((obj != null) && (obj instanceof Piece)){
			Piece p = (Piece)obj;
			eq = (this.color == p.color)&&(this.id == p.id)&&(this.position == p.position);
		}
		
		return eq;
	}
	
}
