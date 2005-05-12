package model;

import java.util.Random;

/*
 * Created on 03.05.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


/**
 * @author Mario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BoardModel {
	
	private Piece[][] pieces = new Piece[4][4];
	private Space[] track = new Space[40];
	private Random diceGenerator;
	
	
	public BoardModel(){
		initialize();
	}
	
	private void initialize(){
		
		for (int color=Constants.RED/*0*/ ; color<=Constants.GREEN/*3*/ ; color++){
			for (int id=0 ; id<4 ; id++){
				pieces[color][id] = new Piece(color, id);
			}
		}
		
		for (int i=0 ; i<40 ; i++){
			track[i] = new Space(i);
		}
		
		diceGenerator = new Random();
	}
	
	public void reset(){
		initialize();
	}
	
	private void evaluate (int color, int distance){
		for (int i=0; i<4; i++){
			evaluate(pieces[color][i], distance);
		}
	}
	
	private void evaluate (Piece p, int distance){
		if (p.isAtStartingPoint()){ // Spielstein befindet sich auf Startfeld 
			
			if (distance==6){
				if ( isMoveBlocked(p, distance) == Constants.OCCUPATION ){
					// Feld durch eigenen Spielstein besetzt
					p.setMoveable(false);
					p.setEvaluationError(Constants.OCCUPATION);	
				}else{
					// Zug erlaubt
					p.setMoveable(true);
				}
			}else{
				// Spielstein nur bei Würfeln einer 6 ansetzbar
				p.setEvaluationError(3);						
			}
		
		}else{ // Spielstein befindet sich auf Spielfeldstrecke oder im Zielbereich
			
			int newPos = p.getPosition() + distance;
			
			if ((distance==6) && hasPiecesAtStartingPoint(p.getColor()) && ((!track[p.getColor()*10].isOccupied()) || (track[p.getColor()*10].getPiece().getColor()!=p.getColor()))){
				
				// 6 gewürfelt, aber noch Spielsteine auf dem Startfeld die angesetzt werden können
				p.setEvaluationError(7);		
									
			} else if (hasPiecesAtStartingPoint(p.getColor()) && (track[p.getColor()*10].isOccupied()) 
					&& (track[p.getColor()*10].getPiece().getColor()==p.getColor() && !track[p.getColor()*10].getPiece().equals(p)) 
					&& (isMoveBlocked(track[p.getColor()*10].getPiece(), distance)==Constants.NO_BLOCKING)){
				
				// Spielsteine auf dem Startfeld und ein eigener anderer Spielstein auf Ansatzfeld mit dem ein gültiger Zug möglich ist
				p.setEvaluationError(8); 
						
			} else	if (newPos > 43){
				
				// Distanz zu groß, Zielbereich überschritten
				p.setEvaluationError(5);
			
			} else if (newPos >= 40){ // neue Position befindet sich im Zielbereich
				
				int status;
				if ((status=isMoveBlocked(p, distance)) != Constants.NO_BLOCKING){
					// Eigener Spielstein übersprungen oder Zielfeld durch eigenen Spielstein besetzt
					p.setEvaluationError(status);
				}else{
					// Zug erlaubt
					p.setMoveable(true);
				}
				
				
			} else { // neue Position befindet sich auf Spielfeldstrecke
				
				if ( isMoveBlocked(p, distance) == Constants.OCCUPATION ){
					
					// Feld durch eigenen Spielstein besetzt
					p.setEvaluationError(Constants.OCCUPATION);
									
				}else{
					// Zug erlaubt
					p.setMoveable(true);
				}	
			}
		}		
	}
	
	private void resetMoveableStatus (int color){
		for (int i=0; i<4; i++){
			pieces[color][i].resetMoveableStatus();
		}		
	}
	
	private int move(Piece p, int distance){
		
		int newPos;
		
		// Ermittle neue Position	
		if (p.isAtStartingPoint()){ // Spielstein befindet sich auf Startfeld 
			newPos = 0;
		} else { 
			newPos = p.getPosition() + distance;
		}
		
		// Räume ggf. bisherige Position auf Spielfeldstrecke
		if (p.isOnTrack()) track[p.getRealPosition()].leave();
		
		// Setze neue Position
		p.setPosition(newPos);
		
		// Besetze ggf. neue Position auf Spielfeldstrecke
		if (p.isOnTrack()){
			Piece oldPiece;
			if ( (oldPiece = track[p.getRealPosition()].occupy(p)) != null){
				// fremden Spielstein auf Startfeld zurücksetzen
				oldPiece.setPosition(Constants.START);	
			}
		}	
	
		return p.getRealPosition();
	}
	
	public int move(int color, int id, int distance) throws InvalidMoveException{
		
		int newPosition = -1;
		
		if ( (color>=Constants.RED) && (color<=Constants.GREEN) && (id>=0) && (id<=3) ){
			if ((distance >= 0) && (distance <= 6)){//evtl. größere Distanzen als 6 zulässig?!
				
				// Setze Zugfähigkeitsstatus der Steine einer bestimmten Farbe zurück
				resetMoveableStatus(color);
				
				// Bestimme Zugfähigkeit der Spielsteine einer bestimmten Farbe
				evaluate(color, distance);
				
				// Führe Zug durch oder werfe Exception
				if (existsMoveablePiece(color)){
					Piece p = pieces[color][id];
					if (p.isMoveable()){
						newPosition = move(p, distance);
					}else{
						throw new InvalidMoveException(p.getEvaluationError());
					}
				}else{
					//System.out.println(""+pieces[color][id].getEvaluationError());
					throw new InvalidMoveException(Constants.NO_MOVEABLE_PIECE);
				}
			
			}else{
				// Ungültige Distanz
				throw new InvalidMoveException(2);
			}
		}else{
			// Spielstein nicht vorhanden
			throw new InvalidMoveException(1);
		}

		return newPosition;
	}
	
	
	private int isMoveBlocked(Piece p, int distance){
		
		int status = Constants.NO_BLOCKING;
		
		if (p.isAtStartingPoint()){ // Spielstein befindet sich auf Startfeld
		
			int newRealPos = p.getColor() * 10;
			if (track[newRealPos].isOccupied() && (track[newRealPos].getPiece().getColor()== p.getColor())){
				// Feld durch eigenen Spielstein besetzt
				status = Constants.OCCUPATION;
			}
		
		}else{ // Spielstein ist bereits auf Spielfeldstrecke oder im Zielbereich
		
			int newPos = p.getPosition() + distance;
			int newRealPos = ((newPos>=0)&&(newPos<40)) ? (p.getColor()*10 + newPos) % 40 : newPos;
			
			if (newPos >= 40){ // neue Position im Zielbereich
			
				for (int i=0; i<4; i++){
					Piece cmp = pieces[p.getColor()][i];
					if (!p.equals(cmp) && (cmp.getPosition() >= 40)){
						if ((cmp.getPosition() < newPos)&&(cmp.getPosition()>p.getPosition())){
							// Spielstein im Zielbereich übersprungen
							status = Constants.HOPPING;
							break;
						}
						if (cmp.getPosition() == newPos){
							// Feld durch eigenen Spielstein besetzt
							status = Constants.OCCUPATION;
							break;
						}
					}
				}
			
			}else{// neue Position auf Spielfeldstrecke
				
				if (track[newRealPos].isOccupied() && (track[newRealPos].getPiece().getColor()== p.getColor())){
					// Feld durch eigenen Spielstein besetzt
					status = Constants.OCCUPATION;
				}				
			
			}
			 
		}

		return status;

	}
	
	private boolean hasPiecesAtStartingPoint(int color){
		boolean result = false;
		
		for (int i=0; i<4; i++){
			if (pieces[color][i].isAtStartingPoint()){
				result = true;
				break;				
			}
		}
		
		return result;
	}
	
	private boolean hasPiecesOnTrack(int color){
		boolean result = false;
		
		for (int i=0; i<4; i++){
			if (pieces[color][i].isOnTrack()){
				result = true;
				break;				
			}
		}
		
		return result;
	}
	
	private boolean existsMoveablePiece (int color){
		boolean exists = false;
		
		for (int i=0; i<4; i++){
			if (pieces[color][i].isMoveable()){
				exists = true;
				break;
			}
		}
		
		return exists;
	}
	
	public boolean isAllowedToDice3Times (int color){
		return (!hasPiecesOnTrack(color) && hasPiecesAtStartingPoint(color));
	}
	
	public boolean isGameOver(){
		
		boolean gameOver = false;
		
		for (int color=Constants.RED/*0*/; color<=Constants.GREEN/*3*/ && !gameOver ; color++){
			gameOver = pieces[color][0].hasFinished() && pieces[color][1].hasFinished() && pieces[color][2].hasFinished() && pieces[color][3].hasFinished();
		}
		
		return gameOver;	
	}
	
	public int throwTheDice(){
		
		return diceGenerator.nextInt(6) + 1;
	}
	
	public Piece[][] getPieces(){
		return pieces;
	}
	
}
