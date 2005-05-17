/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Client extends Remote {

	public int getColor() throws RemoteException;
	public void refresh(boolean all) throws RemoteException;
	public void throwTheDice() throws RemoteException;
	public void setServer(Server server) throws RemoteException;
	public Server getServer() throws RemoteException;
	public void recieveRadioMessage (String msg) throws RemoteException;
	public void recieveMessage (String msg) throws RemoteException;
	public void setStatus (int status) throws RemoteException;
	public int getStatus () throws RemoteException;
	public void setClientListener (ClientListener listener) throws RemoteException;
	public String getNickname() throws RemoteException;
	public void setAttempts(int attempts) throws RemoteException;
	public void decrementAttempts() throws RemoteException;
	public int getAttempts() throws RemoteException;
	public boolean hasAttemptsLeft () throws RemoteException;
	public void setColor(int color) throws RemoteException;
	public int getDiceResult () throws RemoteException;
	public void setDiceResult (int dice) throws RemoteException;
	public boolean existsServer() throws RemoteException;
}
