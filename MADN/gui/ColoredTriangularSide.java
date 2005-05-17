package gui;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.picking.PickTool;

public class ColoredTriangularSide extends Shape3D{

	public static int XY_PLANE = 0;
	public static int ZY_PLANE = 1;
	public static int ZX_PLANE = 2;
	
	private Point3f[] shape = null;
	
	public ColoredTriangularSide(Color3f color, Point3f c0, Point3f c1, Point3f c2){
		this.setGeometry(buildGeometry(c0, c1, c2));
		this.setAppearance(buildAppearance(color));
		this.setCapabilities();

	}
	
	private void setCapabilities(){
		this.setCapability(Shape3D.ALLOW_BOUNDS_READ);
		this.setCapability(Shape3D.ALLOW_BOUNDS_WRITE);
		this.setCapability(Shape3D.ALLOW_PICKABLE_READ);
		this.setCapability(Shape3D.ALLOW_PICKABLE_WRITE);
		this.setCapability(Shape3D.ENABLE_PICK_REPORTING);
		PickTool.setCapabilities(this, PickTool.INTERSECT_FULL);	
	}
	
	public Point3f getCoordinate(int c){
		Point3f coordinate = new Point3f();
		
		if ((c>=0)&&(c<=2)){
			coordinate = shape[c];
		}
		
		return coordinate;
	}
	
	
	private Geometry buildGeometry (Point3f c0, Point3f c1, Point3f c2){
		int i=0;
		Point3f[] tmp = {c0, c1, c2};
		shape = tmp;
		
		// GeometryInfo-Objekt erzeugen
		GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
		gi.setCoordinates(shape);
		
		// Umwandlung Polygon => Dreieck
		gi.convertToIndexedTriangles();
		gi.recomputeIndices();
		
		// Normalen generieren (=> Schatten)
		NormalGenerator ng = new NormalGenerator();
		ng.generateNormals(gi);
		gi.recomputeIndices();
		
		// Umwandlung in Dreieckslinienzüe (=> Performance)
		Stripifier st = new Stripifier();
		st.stripify(gi);
		gi.recomputeIndices();
		
				
		return gi.getGeometryArray();
	}
	
	private Appearance buildAppearance (Color3f color){
		float shading = 0.1f;
		Color3f white = new Color3f(1,1,1);
		Color3f black = new Color3f(0,0,0);
		Color3f darker = new Color3f(color.x * shading,color.y*shading,color.z*shading);
		Color3f specular = new Color3f(0.45f,0.4f,0.4f);
		Appearance a = new Appearance();
		

 		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_GOURAUD);
		a.setColoringAttributes(ca);
 		
 		Material m = new Material(color, darker, color, specular, 80f);
		a.setMaterial(m);
		
		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
		a.setPolygonAttributes(pa);
		
		
		      
		return a;
	}

}	
