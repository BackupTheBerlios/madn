package gui;

import java.util.EventListener;

/**
 * Interface: AnimationListener
 * ============================
 * Von Klassen, die von Status�nderungen einer Instanz der Klasse AnimationInterpolator
 * benachrichtigt werden wollen, zu implementierendes Interface 
 */
public interface AnimationListener extends EventListener {
	
	/**
	 * Methode: animationStarted
	 * -------------------------
	 * Benachrichtigung �ber Animationsstart
	 */
	public void animationStarted();

	/**
	 * Methode: animationFinished
	 * --------------------------
	 * Benachrichtigung �ber Animationsende
	 */
	public void animationFinished();
	
}
