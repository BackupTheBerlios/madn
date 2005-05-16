/*
 * Created on 14.05.2005
 */
package gui;

import java.util.EventListener;

/**
 * @author Mario
 */
public interface PawnPickingListener extends EventListener {
	public void pawnClicked (int color, int id, int clickCount);
	public void rightMouseButtonClicked ();
}
