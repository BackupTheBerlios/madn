package gui;

import javax.media.j3d.Alpha;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

/**
 * Klasse: AnimationInterpolator
 * =============================
 * Spezialisierung der Klasse KBRotPosScaleSplinePathInterpolator, um eine Animation (hier: die W�rfelanimation)
 * flexibel terminierbar, mehrfach durchf�hren/ausl�sen zu k�nnen.  
 */
public class AnimationInterpolator extends KBRotPosScaleSplinePathInterpolator {
	
	// Alpha-Schwellwert [0;1] bei dem die Animation/Interpolation abgebrochen wird
	float threshold;
	// Animationsdauer in Millisekunden
	long duration;
	// Bei Animationsstart/-ende zu benachrichtigender Listener (hier: GameFrame)
	AnimationListener listener;
	
	/**
	 * Konstruktor: AnimationInterpolator
	 * ----------------------------------
	 * @param listener			= bei Animationsstart/-ende zu benachrichtigender Listener
	 * @param duration			= Animationsdauer in Millisekunden
	 * @param target			= Transformationsgruppe auf der die Animation ausgef�hrt wird (hier: objTransformGroup = W�rfel)
	 * @param axisOfTransform	= Basistransformation (legt die Rotationsachsen fest)
	 * @param threshold			= Alphaschwellwert bei dem die Animation abgebrochen wird
	 * @param keys				= Array der Schl�ssel-/Knotenframes (begr�nden Animationsetappen) der Animation
	 */
	public AnimationInterpolator(AnimationListener listener, long duration, TransformGroup target,
			Transform3D axisOfTransform, float threshold, KBKeyFrame[] keys) {
		// Konstruktoraufruf der �bergeordneten Klasse (Alphaobjekt wird generiert)		
		super(new Alpha (-1,Alpha.INCREASING_ENABLE,1000,0,duration,0,0,0,0,0), target, axisOfTransform, keys);
		// Initilisierung der Membervariablen
		this.setEnable(false);
		this.listener = listener;
		this.duration = duration;
		this.threshold = threshold;
	}

	/**
	 * Methode: processStimulus
	 * ------------------------
	 * @see javax.media.j3d.Behavior#processStimulus(java.util.Enumeration)
	 * 
	 * �berlagerte Methode, um den aktuellen Wert des Alphaobjekts gegen den
	 * Schwellwert zu pr�fen und die Animation ggf. zu stoppen.
	 */
	public void processStimulus(java.util.Enumeration criteria){
		// Bei �berschreiten des Schwellwerts => Abbruch Animation
		if (getAlpha().value()>threshold){
			if (listener != null){
				// Benachrichtigung des Listeners
				listener.animationFinished();
			}
			this.setEnable(false);
		}
		super.processStimulus(criteria);
	}
	
	/**
	 * Methode: startAnimation
	 * -----------------------
	 * Startet unmittelbar die Animation.
	 */
	public void startAnimation(){
		// Benacrichtigung des Listeners
		if (listener != null){
			listener.animationStarted();
		}
		// Animation unmittelbar starten
		this.getAlpha().setStartTime(System.currentTimeMillis());
		this.setEnable(true);
	}
}
