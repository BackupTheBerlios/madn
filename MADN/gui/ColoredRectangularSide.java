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
 * Erweiterung der Klasse Shape3D (@see javax.media.j3d.Shape3D), die eine farbige, lichtsensible (=> Farbverlauf auf Oberfläche ist abhängig von
 * Lichteinstrahlung) Rechteckfläche in einer bestimmten Ebenen (XY-, YZ- oder ZX-Ebene) realisiert.
 * Der Flächenschwerpunkt befindet sich im Nullpunkt der die Ebene aufspannenden Koordinatenachsen.   
 */
public class ColoredRectangularSide extends Shape3D{

		// Vorzeichen der Koordinaten der Flächeneckpunkte auf den Koordinatenachsen, die die Ebene aufspannen
		public static float[][] signums = {{-1.0f, +1.0f}, {-1.0f, -1.0f}, {+1.0f, -1.0f}, {+1.0f, +1.0f}};
		// Konstante Ebenenbezeichner
		public static int XY_PLANE = 2;
		public static int YZ_PLANE = 0;
		public static int ZX_PLANE = 1;
		// Array der Flächeneckpunkte
		private Point3f[] shape = null;
		
		/**
		 * Konstruktor: ColoredRectangularSide
		 * -----------------------------------
		 * Berechnet Eckpunkte selbstständig anhand der übergebenen Parameter.
		 * @param color	= Basisfarbe der Fläche
		 * @param plane = Ebene in der die Fläche liegt
		 * @param x		= Halbkantenlänge oder Scherpunktslage auf X-Achse
		 * @param y		= Halbkantenlänge oder Scherpunktslage auf Y-Achse
		 * @param z		= Halbkantenlänge oder Scherpunktslage auf Z-Achse
		 * @param gap	= Zusätzliche Schwerpunktslageverschiebung
		 */
		public ColoredRectangularSide(Color3f color, int plane, float x, float y, float z, float gap){
			// Erstelle Geometrie
			this.setGeometry(buildGeometry(plane, x, y, z, gap));
			// Bestimme äußeres Erscheinungsbild
			this.setAppearance(buildAppearance(color));
			// Weise dediziert Fähigkeiten zu
			this.setCapabilities();	
		}
		
		/**
		 * Konstruktor: ColoredRectangularSide
		 * -----------------------------------
		 * Erstellt Rechteckfläche auf Basis der übergebenen Eckpunkte => Rechteck liegt nicht zwingend in einer bestimmten Ebene.
		 * Wichtig: Ist die Rechteckfläche Bestandteil eines komplexeren Objekts, müssen die Eckpunkte im Gegenuhrzeigersinn (bei Außenansicht)
		 * angegeben werden, damit ein korrekter Farbverlauf bei Lichteinfall gewährleistet ist (Stichtwort: Normale)  
		 * @param color	= Basisfarbe der Fläche
		 * @param c0	= Eckpunkt
		 * @param c1	= Eckpunkt
		 * @param c2	= Eckpunkt
		 * @param c3	= Eckpunkt
		 */
		public ColoredRectangularSide(Color3f color, Point3f c0, Point3f c1, Point3f c2, Point3f c3){
			// Erstelle Geometrie
			this.setGeometry(buildGeometry(c0, c1, c2, c3));
			// Bestimme äußeres Erscheinungsbild
			this.setAppearance(buildAppearance(color));
			// Weise dediziert Fähigkeiten zu
			this.setCapabilities();			
		}

		/**
		 * Methode: setCapabilities
		 * ------------------------
		 * Setzt alle Fähigkeiten, die in Bezug auf die Selektion der Spielfiguren (Picking) benötigt werden.
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
		 * @param c = Index des Flächeneckpunkts [0;3]
		 * @return einen bestimmten Eckpunkt oder null.
		 */
		public Point3f getCoordinate(int c){
			Point3f coordinate = null;
			
			if ((c>=0)&&(c<=3)){
				coordinate = shape[c];
			}
			
			return coordinate;
		}
		
		/**
		 * Methode: buildGeometry
		 * ----------------------
		 * Erstellt die komplette Geometrie der Fläche (Ergebnis: Dreieckslinienzüge), berechnet die Normalen
		 * @param plane = Ebene in der die Fläche liegt
		 * @param x		= Halbkantenlänge oder Scherpunktslage auf X-Achse
		 * @param y		= Halbkantenlänge oder Scherpunktslage auf Y-Achse
		 * @param z		= Halbkantenlänge oder Scherpunktslage auf Z-Achse
		 * @param gap	= Zusätzliche Schwerpunktslageverschiebung
		 * @return Geometry-Objekt der Fläche
		 */
		private Geometry buildGeometry (int plane, float x, float y, float z, float gap){
			
			// Shape-Punkte bzw. -Koordinaten erzeugen
			float[][] coordinates = {{x,y,z},{x,y,z},{x,y,z},{x,y,z}};
			shape = new Point3f[4];
						
			for (int i=0; i<4; i++){
				for (int j = plane; j < (plane + 3); j++){
					if (j == plane){
						coordinates[i][j] = coordinates[i][j] + (coordinates[i][j] < 0 ? -1.0f : 1.0f) * Math.abs(gap);
					}else if (coordinates[i][j%3] != 0){
						if (coordinates[i][plane] > 0.0f)
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

		/**
		 * Methode: buildGeometry
		 * ----------------------
		 * Erstellt die komplette Geometrie der Fläche (Ergebnis: Dreieckslinienzüge), berechnet die Normalen.
		 * Wichtig: Eckpunkte müssen im Gegenuhrzeigersinn angegeben werden (@see ColoredRectangularSide#ColoredRectangularSide(Color3f, Point3f, Point3f, Point3f, Point3f)
		 * @param c0 = Eckpunkt
		 * @param c1 = Eckpunkt
		 * @param c2 = Eckpunkt
		 * @param c3 = Eckpunkt
		 * @return Geometry-Objekt der Fläche
		 */
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
	
		/**
		 * Methode: buildAppearance
		 * ------------------------
		 * Erstellt Erscheinungsbild für die Rechteckfläche.
		 * @param color = Basisfarbe der Fläche
		 * @return Appearance-Objekt
		 */
		private Appearance buildAppearance (Color3f color){
			// Schattierungsfaktor => Abdunklung der Basisfarbe
			float shading = 0.1f;
			// Erstelle Farben für verschiendene Beleuchtungsvarianten
			Color3f white = new Color3f(1,1,1);
			Color3f black = new Color3f(0,0,0);
			Color3f darker = new Color3f(color.x * shading,color.y*shading,color.z*shading);
			Color3f specular = new Color3f(0.45f,0.4f,0.4f);
			
			// Erzeuge Appearance-Objekt
			Appearance a = new Appearance();
			
			// Setze Basisfarbe
	 		ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.SHADE_GOURAUD);
			a.setColoringAttributes(ca);
	 		
			// Setze Materialeigenschaften (=Farben für verschiendene Beleuchtungsvarianten)
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