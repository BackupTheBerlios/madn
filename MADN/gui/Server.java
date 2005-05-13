/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.InvalidMoveException;
import model.Piece;

/**
 * @author Mario
 *
 */
public interface Server extends Remote {
	
	public Client newClient(String nickName) throws RemoteException;
	
	public void removeClient(int id) throws RemoteException;
	
	public void reset() throws RemoteException;
	
	public void move (int color, int id, int distance) throws RemoteException, InvalidMoveException;
	
	public boolean isActiveClientAllowedToDice3Times() throws RemoteException;
	
	public void dice(int client) throws RemoteException;
	
	public void sendRadioMessage(String msg) throws RemoteException;
	
	public void startNewGame() throws RemoteException;
	
	public Piece[][] getPieces() throws RemoteException;
}
