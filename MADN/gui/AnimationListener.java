package gui;

import java.util.EventListener;

/**
 * Interface: AnimationListener
 * ============================
 * Von Klassen, die von Statusänderungen einer Instanz der Klasse AnimationInterpolator
 * benachrichtigt werden wollen, zu implementierendes Interface 
 */
public interface AnimationListener extends EventListener {
	
	/**
	 * Methode: animationStarted
	 * -------------------------
	 * Benachrichtigung über Animationsstart
	 */
	public void animationStarted();

	/**
	 * Methode: animationFinished
	 * --------------------------
	 * Benachrichtigung über Animationsende
	 */
	public void animationFinished();
	
}
