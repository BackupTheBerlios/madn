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

/**
 * Klasse ColoredRectangularSide
 * =============================
 * Erweiterung der Klasse Shape3D (@see javax.media.j3d.Shape3D), die eine farbige, lichtsensible (=> Farbverlauf auf Oberfl�che ist abh�ngig von
 * Lichteinstrahlung) Dreiecksfl�che auf Basis dreier Eckpunkte realisiert.   
 */
public class ColoredTriangularSide extends Shape3D{

	// Array der Fl�cheneckpunkte
	private Point3f[] shape = null;

	/**
	 * Konstruktor: ColoredTriangularSide
	 * ----------------------------------
	 * Erstellt Dreiecksfl�che auf Basis der �bergebenen Eckpunkte => Dreieck liegt nicht zwingend in einer bestimmten Ebene.
	 * Wichtig: Ist die Dreieckfl�che Bestandteil eines komplexeren Objekts, m�ssen die Eckpunkte im Gegenuhrzeigersinn (bei Au�enansicht)
	 * angegeben werden, damit ein korrekter Farbverlauf bei Lichteinfall gew�hrleistet ist (Stichtwort: Normale)  
	 * @param color	= Basisfarbe der Fl�che
	 * @param c0	= Eckpunkt
	 * @param c1	= Eckpunkt
	 * @param c2	= Eckpunkt
	 */
	public ColoredTriangularSide(Color3f color, Point3f c0, Point3f c1, Point3f c2){
		// Erstelle Geometrie
		this.setGeometry(buildGeometry(c0, c1, c2));
		// Bestimme �u�eres Erscheinungsbild
		this.setAppearance(buildAppearance(color));
		// Weise dediziert F�higkeiten zu
		this.setCapabilities();			

	}
	
	/**
	 * Methode: setCapabilities
	 * ------------------------
	 * Setzt alle F�higkeiten, die in Bezug auf die Selektion der Spielfiguren (Picking) ben�tigt werden.
	 */	
	private void setCapabilities(){
		this.setCapability(Shape3D.ALLOW_BOUNDS_READ);
		this.setCapability(Shape3D.ALLOW_BOUNDS_WRITE);
		this.setCapability(Shape3D.ALLOW_PICKABLE_READ);
		this.setCapability(Shape3D.ALLOW_PICKABLE_WRITE);
		this.setCapability(Shape3D.ENABLE_PICK_REPORTING);
		PickTool.setCapabilities(this, PickTool.INTERSECT_FULL);	
	}

	/**
	 * Methode: getCoordinate
	 * ----------------------
	 * @param c = Index des Fl�cheneckpunkts [0;2]
	 * @return einen bestimmten Eckpunkt oder null.
	 */
	public Point3f getCoordinate(int c){
		Point3f coordinate = new Point3f();
		
		if ((c>=0)&&(c<=2)){
			coordinate = shape[c];
		}
		
		return coordinate;
	}
	
	/**
	 * Methode: buildGeometry
	 * ----------------------
	 * Erstellt die komplette Geometrie der Fl�che (Ergebnis: Dreieckslinienz�ge), berechnet die Normalen.
	 * Wichtig: Eckpunkte m�ssen im Gegenuhrzeigersinn angegeben werden.
	 * @param c0 = Eckpunkt
	 * @param c1 = Eckpunkt
	 * @param c2 = Eckpunkt
	 * @return Geometry-Objekt der Fl�che
	 */	
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
		
		// Umwandlung in Dreieckslinienz�e (=> Performance)
		Stripifier st = new Stripifier();
		st.stripify(gi);
		gi.recomputeIndices();
		
				
		return gi.getGeometryArray();
	}
	
	/**
	 * Methode: buildAppearance
	 * ------------------------
	 * Erstellt Erscheinungsbild f�r die Rechteckfl�che.
	 * @param color = Basisfarbe der Fl�che
	 * @return Appearance-Objekt
	 */
	private Appearance buildAppearance (Color3f color){
		// Schattierungsfaktor => Abdunklung der Basisfarbe
		float shading = 0.1f;
		// Erstelle Farben f�r verschiendene Beleuchtungsvarianten
		Color3f white = new Color3f(1,1,1);
		Color3f black = new Color3f(0,0,0);
		Color3f darker = new Color3f(color.x * shading,color.y*shading,color.z*shading);
		Color3f specular = new Color3f(0.45f,0.4f,0.4f);
		
		// Erzeuge Appearance-Objekt
		Appearance a = new Appearance();
		
		// Setze Basisfarbe
 		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_GOURAUD);
		a.setColoringAttributes(ca);
 		
		// Setze Materialeigenschaften (=Farben f�r verschiendene Beleuchtungsvarianten)
 		Material m = new Material(color, darker, color, specular, 80f);
		a.setMaterial(m);
		
		// Setze Polygoneigenschaften
		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
		a.setPolygonAttributes(pa);
		      
		return a;
	}

}	
