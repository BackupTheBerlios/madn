/*
 * Created on 11.05.2005
 *
 */
package gui;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

/**
 * @author Mario
 *
 */
public class Pawn extends TransformGroup {

	private static boolean intersectCapabilitySet = false;
	
	private int color = -1;
	private int id = -1;
	private Cone cone = null;
	private Sphere sphere = null;
	private float radius = 0f;
	private float height = 0f;
	
	public Pawn(int color, int id, float boardHeight, float radius, float height){
		super(new Transform3D());
		
		this.setPickable(true);
		
		this.radius = radius;
		this.height = height;
		this.color = color;
		this.id = id;
		
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Color3f col3f = new Color3f(GameFrame.colors[color]);
//		float shading = 0.3f;
//		ColoringAttributes ca = new ColoringAttributes(col3f, ColoringAttributes.SHADE_GOURAUD);
//		Material m = new Material(col3f, new Color3f(col3f.x * shading, col3f.y * shading, col3f.z * shading), col3f, col3f, 50f);
//		
//		Appearance ap = new Appearance();
//		ap.setColoringAttributes(ca);
//		ap.setMaterial(m);
		float shading = 0.1f;
		Color3f white = new Color3f(1,1,1);
		Color3f black = new Color3f(0,0,0);
		Color3f darker = new Color3f(col3f.x * shading,col3f.y*shading,col3f.z*shading);
		Color3f specular = new Color3f(0.45f,0.4f,0.4f);
		Appearance a = new Appearance();
	
 		ColoringAttributes ca = new ColoringAttributes(col3f, ColoringAttributes.SHADE_GOURAUD);
		a.setColoringAttributes(ca);
 		
 		Material m = new Material(col3f, darker, col3f, specular, 80f);
		a.setMaterial(m);		
		
		sphere = new MySphere(color, id, radius * 0.6f);
		sphere.setAppearance(a);
		sphere.setPickable(true);
		
		sphere.setCapability(Primitive.ALLOW_BOUNDS_READ);
		sphere.setCapability(Primitive.ALLOW_BOUNDS_WRITE);
		sphere.setCapability(Primitive.ALLOW_PICKABLE_READ);
		sphere.setCapability(Primitive.ALLOW_PICKABLE_WRITE);
		sphere.setCapability(Primitive.ENABLE_GEOMETRY_PICKING);
		sphere.setCapability(Primitive.ENABLE_PICK_REPORTING);
		
		cone = new MyCone(color, id, radius, height - sphere.getRadius() * 0.5f);
		cone.setAppearance(a);
		cone.setPickable(true);
		cone.setCapability(Primitive.ALLOW_BOUNDS_READ);
		cone.setCapability(Primitive.ALLOW_BOUNDS_WRITE);
		cone.setCapability(Primitive.ALLOW_PICKABLE_READ);
		cone.setCapability(Primitive.ALLOW_PICKABLE_WRITE);
		cone.setCapability(Primitive.ENABLE_GEOMETRY_PICKING);
		cone.setCapability(Primitive.ENABLE_PICK_REPORTING);
		
		if (!Pawn.intersectCapabilitySet){
			
			sphere.getShape().getGeometry().setCapability(Geometry.ALLOW_INTERSECT);
			cone.getShape(Cone.BODY).getGeometry().setCapability(Geometry.ALLOW_INTERSECT);
			cone.getShape(Cone.CAP).getGeometry().setCapability(Geometry.ALLOW_INTERSECT);
			Pawn.intersectCapabilitySet = true;
		}
		
		Transform3D transform = new Transform3D();
		transform.setTranslation(new Vector3f(0f, boardHeight + cone.getHeight() * 0.5f, 0f));
		TransformGroup tgPutOnBoard = new TransformGroup(transform);
		tgPutOnBoard.setPickable(true);
		
			transform = new Transform3D();
			transform.setTranslation(new Vector3f(0f, (cone.getHeight()- sphere.getRadius()) * 0.5f, 0f));
			TransformGroup tgLiftSphere = new TransformGroup(transform);
			tgLiftSphere.setPickable(true);
				
			tgLiftSphere.addChild(sphere);
			
		tgPutOnBoard.addChild(cone);
		tgPutOnBoard.addChild(tgLiftSphere);
			
		this.addChild(tgPutOnBoard);
	}
	
	public int getColor(){
		return color;
	}
	
	public int getID(){
		return id;
	}

//	public boolean containsShape3D (Shape3D shape){
//		return cone.getShape(Cone.BODY).equals(shape) || cone.getShape(Cone.CAP).equals(shape) || sphere.getShape().equals(shape);	
//	}
}
