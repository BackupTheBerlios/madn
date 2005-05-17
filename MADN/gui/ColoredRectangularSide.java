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

public class ColoredRectangularSide extends Shape3D{

		public static float[][] signums = {{-1.0f, +1.0f}, {-1.0f, -1.0f}, {+1.0f, -1.0f}, {+1.0f, +1.0f}};
		public static int XY_PLANE = 2;
		public static int YZ_PLANE = 0;
		public static int ZX_PLANE = 1;
		
		private Point3f[] shape = null;
		
		public ColoredRectangularSide(Color3f color, int plane, float x, float y, float z, float gap){
			this.setGeometry(buildGeometry(plane, x, y, z, gap));
			this.setAppearance(buildAppearance(color));
			this.setCapabilities();	
		}
		
		public ColoredRectangularSide(Color3f color, Point3f c0, Point3f c1, Point3f c2, Point3f c3){
			this.setGeometry(buildGeometry(c0, c1, c2, c3));
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
			Point3f coordinate = null;
			
			if ((c>=0)&&(c<=3)){
				coordinate = shape[c];
			}
			
			return coordinate;
		}
		
		private Geometry buildGeometry (int plane, float x, float y, float z, float gap){
			
			// Shape-Punkte bzw. -Koordinaten erzeugen
			
			float[][] coordinates = {{x,y,z},{x,y,z},{x,y,z},{x,y,z}};
			shape = new Point3f[4];
						
			for (int i=0; i<4; i++){
				for (int j = plane; j < (plane + 3); j++){
					if (j == plane){
						coordinates[i][j] = coordinates[i][j] + Math.signum(coordinates[i][j]) * Math.abs(gap);
					}else if (Math.signum(coordinates[i][j%3]) != 0){
						if (Math.signum(coordinates[i][plane]) == 1.0f)
							coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * ColoredRectangularSide.signums[i][1-((j+plane)%2)];
						else{//Math.signum(coordinates[i][j]) == -1.0f
							coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * ColoredRectangularSide.signums[3-i][1-((j+plane)%2)];
						}
					}
				}
				shape[i] = new Point3f(coordinates[i][0], coordinates[i][1], coordinates[i][2]);
			}
			
			// GeometryInfo-Objekt erzeugen
			GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
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
		
		private Geometry buildGeometry (Point3f c0, Point3f c1, Point3f c2, Point3f c3){
			
			int i=0;
			Point3f[] tmp = {c0, c1, c2, c3};
			shape = tmp;
			
			// GeometryInfo-Objekt erzeugen
			GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
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