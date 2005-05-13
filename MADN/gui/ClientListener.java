/*
 * Created on 12.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.util.EventListener;

import model.Piece;

/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ClientListener extends EventListener {
	public void boardConstellationChanged(Piece[][] pieces);
	public void enablingChanged ();
	public void showMessage (String msg);
	public void addRadioMessage (String msg);
}
