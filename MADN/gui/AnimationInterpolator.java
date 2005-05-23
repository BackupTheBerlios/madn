package gui;

import javax.media.j3d.Alpha;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBRotPosScaleSplinePathInterpolator;

/**
 * @author Mario
 */
public class AnimationInterpolator extends KBRotPosScaleSplinePathInterpolator {
	
	float threshold;
	long duration;
	AnimationListener listener;
	
	public AnimationInterpolator(AnimationListener listener, long duration, TransformGroup target,
			Transform3D axisOfTransform, float threshold, KBKeyFrame[] keys) {
		
		super(new Alpha (-1,Alpha.INCREASING_ENABLE,1000,0,duration,0,0,0,0,0), target, axisOfTransform, keys);
		this.setEnable(false);
		this.listener = listener;
		this.duration = duration;
		this.threshold = threshold;
	}

	public void processStimulus(java.util.Enumeration criteria){
		if (getAlpha().value()>threshold){
			if (listener != null){
				listener.animationFinished();
			}
			this.setEnable(false);

		}
		super.processStimulus(criteria);
	}
	
	public void startAnimation(){
		if (listener != null){
			listener.animationStarted();
		}
		this.getAlpha().setStartTime(System.currentTimeMillis());
		this.setEnable(true);
	}
}
