/*
 * Created on 11.05.2005
 *
 */
package gui;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;

/**
 * @author Mario
 *
 */
public class Pawn extends TransformGroup {

	private int color = -1;
	private int id = -1;
	private Cone cone = null;
	private Sphere sphere = null;
	private float radius = 0f;
	private float height = 0f;
	
	public Pawn(int color, int id, float boardHeight, float radius, float height){
		super(new Transform3D());
		
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		this.radius = radius;
		this.height = height;
		this.color = color;
		this.id = id;
		
		sphere = new Sphere(radius * 0.6f); 
		cone = new Cone(radius, height - sphere.getRadius() * 0.5f);
		
		Color3f col3f = new Color3f(GameFrame.colors[color]);
		float shading = 0.3f;
		ColoringAttributes ca = new ColoringAttributes(col3f, ColoringAttributes.SHADE_GOURAUD);
		Material m = new Material(col3f, new Color3f(col3f.x * shading, col3f.y * shading, col3f.z * shading), col3f, col3f, 50f);
		
		Appearance ap = new Appearance();
		ap.setColoringAttributes(ca);
		ap.setMaterial(m);
		
		sphere.setAppearance(ap);
		cone.setAppearance(ap);
		
		Transform3D transform = new Transform3D();
		transform.setTranslation(new Vector3f(0f, boardHeight + cone.getHeight() * 0.5f, 0f));
		TransformGroup tgPutOnBoard = new TransformGroup(transform); 
			
			transform = new Transform3D();
			transform.setTranslation(new Vector3f(0f, (cone.getHeight()- sphere.getRadius()) * 0.5f, 0f));
			TransformGroup tgLiftSphere = new TransformGroup(transform);
				
			tgLiftSphere.addChild(sphere);
			
		tgPutOnBoard.addChild(cone);
		tgPutOnBoard.addChild(tgLiftSphere);
			
		this.addChild(tgPutOnBoard);
	}
}
