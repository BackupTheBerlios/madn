/*
 * Created on 17.05.2005
 */
package gui;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickTool;

public class TexturedRectangularSide extends Shape3D {
	
	public static float[][] signums = {{-1.0f, +1.0f}, {-1.0f, -1.0f}, {+1.0f, -1.0f}, {+1.0f, +1.0f}};
	public static int XY_PLANE = 2;
	public static int YZ_PLANE = 0;
	public static int ZX_PLANE = 1;
	
	private Point3f[] shape = null;
	
	public TexturedRectangularSide(Color3f color, String fname, int plane, float x, float y, float z, float gap){
		this.setGeometry(buildGeometry(plane, x, y, z, gap));
		this.setAppearance(buildAppearance(color, fname));
		this.setCapabilities();		
	}
			
	public TexturedRectangularSide(Color3f color, String fname, Point3f c0, Point3f c1, Point3f c2, Point3f c3){
		this.setGeometry(buildGeometry(c0, c1, c2, c3));
		this.setAppearance(buildAppearance(color, fname));
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
						coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * TexturedRectangularSide.signums[i][1-((j+plane)%2)];
					else{//Math.signum(coordinates[i][j]) == -1.0f
						coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * TexturedRectangularSide.signums[3-i][1-((j+plane)%2)];
					}
				}
			}
			shape[i] = new Point3f(coordinates[i][0], coordinates[i][1], coordinates[i][2]);
		}
		
		// GeometryInfo-Objekt erzeugen
		GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
		gi.setCoordinates(shape);
		
		// Texturkoordinaten beifügen
		gi.setTextureCoordinateParams(1, 2); // Texture_Coordinate_2
		TexCoord2f[] texCoords = {new TexCoord2f(0,1), new TexCoord2f(0,0), new TexCoord2f(1,0), new TexCoord2f(1,1)};
		gi.setTextureCoordinates(0, texCoords);
		
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
		// Shape-Punkte bzw. -Koordinaten erzeugen
		Point3f[] tmp = {c0, c1, c2, c3};
		shape = tmp;
		
		// GeometryInfo-Objekt erzeugen
		GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
		gi.setCoordinates(shape);
		
		// Texturkoordinaten beifügen
		gi.setTextureCoordinateParams(1, 2); // Texture_Coordinate_2
		TexCoord2f[] texCoords = {new TexCoord2f(0,1), new TexCoord2f(0,0), new TexCoord2f(1,0), new TexCoord2f(1,1)};
		gi.setTextureCoordinates(0, texCoords);
		
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
	
	private Appearance buildAppearance (Color3f color, String fname){
		float shading = 0.1f;
		Color3f white = new Color3f(1,1,1);
		Color3f specular = new Color3f(0.45f,0.4f,0.4f);
		Color3f black = new Color3f(0,0,0);
		Color3f darker = new Color3f(color.x * shading,color.y*shading,color.z*shading);
		
		Appearance a = new Appearance();
		
		TextureLoader loader = new NewTextureLoader(fname);
		ImageComponent2D img = loader.getImage();
		
		Texture2D tex = new Texture2D(Texture.BASE_LEVEL, Texture.RGB, img.getWidth(), img.getHeight());
		tex.setImage(0, img);
		a.setTexture(tex);

		TextureAttributes texAttr = new TextureAttributes();
 		texAttr.setTextureMode(TextureAttributes.MODULATE);
 		//texAttr.setCombineAlphaMode(TextureAttributes.COMBINE_ADD);
 		//texAttr.setCombineAlphaSource(TextureAttributes.COMBINE_OBJECT_COLOR, TextureAttributes.COMBINE_TEXTURE_COLOR);
 		a.setTextureAttributes(texAttr);

 		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);
		a.setColoringAttributes(ca);
 		
		Material m = new Material(white, black, white, specular, 40f);
		a.setMaterial(m);
		
		
		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
		a.setPolygonAttributes(pa);
		
		return a;
	}
		
}