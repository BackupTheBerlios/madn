/*
 * Created on 09.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.applet.Applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Constants;
import model.Piece;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BoardApplet extends Applet {

	private Transform3D[][] destinationTransforms = new Transform3D[4][4];
	private Transform3D[][] startTransforms = new Transform3D[4][4];
	private Transform3D[]   trackTransforms = new Transform3D[40];
	
	private Pawn[][] pawns = new Pawn[4][4];
	
	private SimpleUniverse universe;
	
	public BoardApplet(){
		
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c3d = new Canvas3D(config);
		add(c3d, BorderLayout.CENTER);
		
		BranchGroup scene = buildSceneGraph();
		
		this.universe = new SimpleUniverse(c3d);
		universe.getViewingPlatform().setNominalViewingTransform();
		
		PointLight light = new PointLight();
		light.setPosition(0f,0.5f,0f);
		light.setInfluencingBounds(new BoundingSphere(new Point3d(0,0,0), 2.0));
		scene.addChild(light);
		
		OrbitBehavior orbit = new OrbitBehavior(c3d, OrbitBehavior.REVERSE_ALL|OrbitBehavior.STOP_ZOOM);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		orbit.setSchedulingBounds(bounds);
		orbit.setMinRadius(0.9);
		
		ViewingPlatform view = universe.getViewingPlatform();
		view.setViewPlatformBehavior(orbit);
		Transform3D t = new Transform3D();
		view.getViewPlatformTransform().getTransform(t);
		t.setTranslation(new Vector3f(0f,0f, 0.9f));
		
		view.getViewPlatformTransform().setTransform(t);
		
		universe.addBranchGraph(scene);
	}
	
	public BranchGroup buildSceneGraph(){
		BranchGroup root = new BranchGroup();
		
		root.addChild(buildGeometry());
		
		Color3f color= new Color3f();
		color.set(new Color(204, 204, 255));
		Background background = new Background(color); 
		background.setApplicationBounds(new BoundingSphere());
		root.addChild(background);		
		
		return root;
	}
	
	public BranchGroup buildGeometry(){
		BranchGroup bgBoard = new BranchGroup();
		
		float x = 0.256f;
		float y = 0.016f;
		float z = 0.256f;
		
		// Unterseite
		//ColoredRectangularSide lowerSide = new ColoredRectangularSide(new Color3f(0f, 0f, 0f), ColoredRectangularSide.ZX_PLANE, x, -y, z);
		TexturedRectangularSide lowerSide = new TexturedRectangularSide("gui/images/back.gif", ColoredRectangularSide.ZX_PLANE, x, -y, z);
		
		// Oberseite
		TexturedRectangularSide upperSide = new TexturedRectangularSide("gui/images/board.gif", ColoredRectangularSide.ZX_PLANE, x, y, z);
		// Rand links
		//ColoredRectangularSide leftSide = new ColoredRectangularSide(new Color3f(1f, 0f, 0f), ColoredRectangularSide.ZY_PLANE, -x, y, z);
		TexturedRectangularSide leftSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.ZY_PLANE, -x, y, z);
		
		// Rand rechts
		//ColoredRectangularSide rightSide = new ColoredRectangularSide(new Color3f(1f, 0f, 0f), ColoredRectangularSide.ZY_PLANE, x, y, z);
		TexturedRectangularSide rightSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.ZY_PLANE, x, y, z);
		
		// Rand hinten
		//ColoredRectangularSide backSide = new ColoredRectangularSide(new Color3f(1f, 0f, 0f), ColoredRectangularSide.XY_PLANE, x, y, -z);
		TexturedRectangularSide backSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.XY_PLANE, x, y, -z);
		// Rand vorne
		//ColoredRectangularSide frontSide = new ColoredRectangularSide(new Color3f(1f, 0f, 0f), ColoredRectangularSide.XY_PLANE, x, y, z);
		TexturedRectangularSide frontSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.XY_PLANE, x, y, z);
		
		Transform3D rot = new Transform3D();
		rot.rotX(Math.PI * 40.0 / 180.0);
		Transform3D rotY = new Transform3D();
		rotY.rotY(Math.PI * 25.0 / 180.0);
		Transform3D rotZ = new Transform3D();
		
		rot.mul(rotY);
		
		TransformGroup tg = new TransformGroup(rot);
		tg.addChild(lowerSide);
		tg.addChild(upperSide);
		tg.addChild(leftSide);
		tg.addChild(rightSide);
		tg.addChild(backSide);
		tg.addChild(frontSide);
		
		for (int i=0; i<pawns.length; i++){
			for (int j=0; j<pawns[i].length; j++){
				pawns[i][j] = new Pawn(i, j, y, 0.01575f, 0.0405f);
				tg.addChild(pawns[i][j]);
			}
		}
		
		buildTransformations();
		resetPawns();
		
		bgBoard.addChild(tg);
		bgBoard.compile();
		return bgBoard;
	}
	
	public void actualizePawnPositions(Piece[][] pieces){

		if (pieces != null){
			for (int i=0; i < pawns.length; i++){
				for (int j = 0; j < pawns[i].length; j++) {
					if ((i < pieces.length)&&(j < pieces[i].length)){
						int pos = pieces[i][j].getRealPosition();
						
						if (pos < 0){ //Figur im Startbereich
							pawns[i][j].setTransform(startTransforms[i][j]);
						}else if (pos < 40){ //Figur auf Strecke
							pawns[i][j].setTransform(trackTransforms[pos]);
						}else if (pos < 44){ // Figur im Zielbereich
							pawns[i][j].setTransform(destinationTransforms[i][pos - 40]);
						}
						
					}
				}
			}
		}
		
	}
	
	private void resetPawns(){
		for (int i=0; i<pawns.length; i++){
			for (int j=0; j<pawns[i].length; j++){
				pawns[i][j].setTransform(startTransforms[i][j]);
			}
		}
	}
	
	private void buildTransformations(){
		
		// Transformationen Startbereich
		for (int i=0; i < startTransforms.length; i++)
			for (int j=0; j < startTransforms[i].length; j++)
				startTransforms[i][j] = new Transform3D();
				
			// Rot
			startTransforms[Constants.RED][0].setTranslation(new Vector3d(-0.1365,0,-0.1365));
			startTransforms[Constants.RED][1].setTranslation(new Vector3d(-0.1815,0,-0.1365));
			startTransforms[Constants.RED][2].setTranslation(new Vector3d(-0.1365,0,-0.1815));
			startTransforms[Constants.RED][3].setTranslation(new Vector3d(-0.1815,0,-0.1815));
										
			// Schwarz
			startTransforms[Constants.BLACK][0].setTranslation(new Vector3d(0.1345,0,-0.1365));
			startTransforms[Constants.BLACK][1].setTranslation(new Vector3d(0.1345,0,-0.1815));
			startTransforms[Constants.BLACK][2].setTranslation(new Vector3d(0.1795,0,-0.1365));
			startTransforms[Constants.BLACK][3].setTranslation(new Vector3d(0.1795,0,-0.1815));			

			// Blau
			startTransforms[Constants.BLUE][0].setTranslation(new Vector3d(0.1345,0,0.1345));
			startTransforms[Constants.BLUE][1].setTranslation(new Vector3d(0.1795,0,0.1345));
			startTransforms[Constants.BLUE][2].setTranslation(new Vector3d(0.1345,0,0.1795));
			startTransforms[Constants.BLUE][3].setTranslation(new Vector3d(0.1795,0,0.1795));
			
			// Grün
			startTransforms[Constants.GREEN][0].setTranslation(new Vector3d(-0.1365,0,0.1345));
			startTransforms[Constants.GREEN][1].setTranslation(new Vector3d(-0.1365,0,0.1795));
			startTransforms[Constants.GREEN][2].setTranslation(new Vector3d(-0.1815,0,0.1345));
			startTransforms[Constants.GREEN][3].setTranslation(new Vector3d(-0.1815,0,0.1795));
			
		// Transformationen Zielbereich
		for (int i=0; i < destinationTransforms.length; i++)
			for (int j=0; j < destinationTransforms[i].length; j++)
				destinationTransforms[i][j] = new Transform3D();
			
			// Rot
			destinationTransforms[Constants.RED][0].setTranslation(new Vector3d(-0.1815,0,-0.0015));
			destinationTransforms[Constants.RED][1].setTranslation(new Vector3d(-0.1365,0,-0.0015));
			destinationTransforms[Constants.RED][2].setTranslation(new Vector3d(-0.0915,0,-0.0015));
			destinationTransforms[Constants.RED][3].setTranslation(new Vector3d(-0.0465,0,-0.0015));
			
			// Schwarz
			destinationTransforms[Constants.BLACK][0].setTranslation(new Vector3d(-0.0015,0,-0.1815));
			destinationTransforms[Constants.BLACK][1].setTranslation(new Vector3d(-0.0015,0,-0.1365));
			destinationTransforms[Constants.BLACK][2].setTranslation(new Vector3d(-0.0015,0,-0.0915));
			destinationTransforms[Constants.BLACK][3].setTranslation(new Vector3d(-0.0015,0,-0.0465));			
	
			// Blau
			destinationTransforms[Constants.BLUE][0].setTranslation(new Vector3d(0.1795,0,-0.0005));
			destinationTransforms[Constants.BLUE][1].setTranslation(new Vector3d(0.1345,0,-0.0005));
			destinationTransforms[Constants.BLUE][2].setTranslation(new Vector3d(0.0895,0,-0.0005));
			destinationTransforms[Constants.BLUE][3].setTranslation(new Vector3d(0.0445,0,-0.0005));
			
			// Grün
			destinationTransforms[Constants.GREEN][0].setTranslation(new Vector3d(-0.0015,0,0.1795));
			destinationTransforms[Constants.GREEN][1].setTranslation(new Vector3d(-0.0015,0,0.1345));
			destinationTransforms[Constants.GREEN][2].setTranslation(new Vector3d(-0.0015,0,0.0895));
			destinationTransforms[Constants.GREEN][3].setTranslation(new Vector3d(-0.0015,0,0.0445));		
		
		// Transformationen Rundstrecke
		for (int i=0; i<trackTransforms.length; i++)
			trackTransforms[i] = new Transform3D();
		
			// Rot
			trackTransforms[Constants.RED * 10    ].setTranslation(new Vector3d(-0.2265,0,-0.0465f));
			trackTransforms[Constants.RED * 10 + 1].setTranslation(new Vector3d(-0.181,0,-0.0465f));
			trackTransforms[Constants.RED * 10 + 2].setTranslation(new Vector3d(-0.1365,0,-0.0465f));
			trackTransforms[Constants.RED * 10 + 3].setTranslation(new Vector3d(-0.0915,0,-0.0465f));
			trackTransforms[Constants.RED * 10 + 4].setTranslation(new Vector3d(-0.0465,0,-0.0465));
			trackTransforms[Constants.RED * 10 + 5].setTranslation(new Vector3d(-0.0465,0,-0.0915));
			trackTransforms[Constants.RED * 10 + 6].setTranslation(new Vector3d(-0.0465,0,-0.1365));
			trackTransforms[Constants.RED * 10 + 7].setTranslation(new Vector3d(-0.0465,0,-0.1815));
			trackTransforms[Constants.RED * 10 + 8].setTranslation(new Vector3d(-0.0465,0,-0.2265));
			trackTransforms[Constants.RED * 10 + 9].setTranslation(new Vector3d(-0.0015,0,-0.2265));

			// Schwarz
			trackTransforms[Constants.BLACK * 10    ].setTranslation(new Vector3d(0.0445,0,-0.2265));
			trackTransforms[Constants.BLACK * 10 + 1].setTranslation(new Vector3d(0.0445,0,-0.1815));
			trackTransforms[Constants.BLACK * 10 + 2].setTranslation(new Vector3d(0.0445,0,-0.1365));
			trackTransforms[Constants.BLACK * 10 + 3].setTranslation(new Vector3d(0.0445,0,-0.0915));
			trackTransforms[Constants.BLACK * 10 + 4].setTranslation(new Vector3d(0.0445,0,-0.0455));
			trackTransforms[Constants.BLACK * 10 + 5].setTranslation(new Vector3d(0.0895,0,-0.0455));
			trackTransforms[Constants.BLACK * 10 + 6].setTranslation(new Vector3d(0.1345,0,-0.0455));
			trackTransforms[Constants.BLACK * 10 + 7].setTranslation(new Vector3d(0.1795,0,-0.0455));
			trackTransforms[Constants.BLACK * 10 + 8].setTranslation(new Vector3d(0.2245,0,-0.0455));
			trackTransforms[Constants.BLACK * 10 + 9].setTranslation(new Vector3d(0.2245,0,-0.0005));

			// Blau
			trackTransforms[Constants.BLUE * 10    ].setTranslation(new Vector3d(0.2245,0,0.0445));
			trackTransforms[Constants.BLUE * 10 + 1].setTranslation(new Vector3d(0.1795,0,0.0445));
			trackTransforms[Constants.BLUE * 10 + 2].setTranslation(new Vector3d(0.1345,0,0.0445));
			trackTransforms[Constants.BLUE * 10 + 3].setTranslation(new Vector3d(0.0895,0,0.0445));
			trackTransforms[Constants.BLUE * 10 + 4].setTranslation(new Vector3d(0.0445,0,0.0445));
			trackTransforms[Constants.BLUE * 10 + 5].setTranslation(new Vector3d(0.0445,0,0.0895));
			trackTransforms[Constants.BLUE * 10 + 6].setTranslation(new Vector3d(0.0445,0,0.1345));
			trackTransforms[Constants.BLUE * 10 + 7].setTranslation(new Vector3d(0.0445,0,0.1795));
			trackTransforms[Constants.BLUE * 10 + 8].setTranslation(new Vector3d(0.0445,0,0.2245));
			trackTransforms[Constants.BLUE * 10 + 9].setTranslation(new Vector3d(-0.0015,0,0.2245));
			
			// Grün
			trackTransforms[Constants.GREEN * 10    ].setTranslation(new Vector3d(-0.0465,0,0.2245));
			trackTransforms[Constants.GREEN * 10 + 1].setTranslation(new Vector3d(-0.0465,0,0.1795));
			trackTransforms[Constants.GREEN * 10 + 2].setTranslation(new Vector3d(-0.0465,0,0.1345));
			trackTransforms[Constants.GREEN * 10 + 3].setTranslation(new Vector3d(-0.0465,0,0.0895));
			trackTransforms[Constants.GREEN * 10 + 4].setTranslation(new Vector3d(-0.0465,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 5].setTranslation(new Vector3d(-0.0915,0,0.0445f));
			trackTransforms[Constants.GREEN * 10 + 6].setTranslation(new Vector3d(-0.1365,0,0.0445f));
			trackTransforms[Constants.GREEN * 10 + 7].setTranslation(new Vector3d(-0.181,0,0.0445f));
			trackTransforms[Constants.GREEN * 10 + 8].setTranslation(new Vector3d(-0.2265,0,0.0445f));
			trackTransforms[Constants.GREEN * 10 + 9].setTranslation(new Vector3d(-0.2265,0,-0.0015f));			
	}
	
	private static class TexturedRectangularSide extends Shape3D {
		
		public static int XY_PLANE = 0;
		public static int ZY_PLANE = 1;
		public static int ZX_PLANE = 2;
		
		public TexturedRectangularSide(String texFilename, int plane, float x, float y, float z){
			this.setGeometry(buildGeometry(plane, x, y, z));
			this.setAppearance(texFilename);
		}
				
		public void setAppearance (String fname){
			this.setAppearance(buildAppearance(fname));
		}
		
		public Geometry buildGeometry (int plane, float x, float y, float z){
			QuadArray side = new QuadArray(4, GeometryArray.COORDINATES|GeometryArray.TEXTURE_COORDINATE_2);
			
			Point3f uPoint = new Point3f();
			Point2f tPoint = new Point2f();
			
			// Universumskoordinaten erstellen
			uPoint.set((plane==XY_PLANE) ? -x : x , y, (plane==XY_PLANE) ? z : -z );
			side.setCoordinate(0, uPoint);
			
			uPoint.set((plane==ZY_PLANE) ? x : -x , (plane==ZX_PLANE) ? y : -y, (plane==XY_PLANE) ? z : -z );
			side.setCoordinate(1, uPoint);
			
			uPoint.set((plane==ZX_PLANE) ? -x : x , (plane==ZX_PLANE) ? y : -y, z);
			side.setCoordinate(2, uPoint);
			
			uPoint.set(x , y, z);
			side.setCoordinate(3, uPoint);
			
			// Texturkoordinaten generieren
			tPoint.set(0.0f, 1.0f);
			side.setTextureCoordinate(3, tPoint);
		
			tPoint.set(0.0f, 0.0f);
			side.setTextureCoordinate(0, tPoint);
			
			tPoint.set(1.0f, 0.0f);
			side.setTextureCoordinate(1, tPoint);
			
			tPoint.set(1.0f, 1.0f);
			side.setTextureCoordinate(2, tPoint);
		
			return side;
		}
		
		public Appearance buildAppearance (String fname){
			Appearance a = new Appearance();
			
			PolygonAttributes pa = new PolygonAttributes();
			pa.setCullFace(PolygonAttributes.CULL_NONE);
			a.setPolygonAttributes(pa);
			
			TextureLoader loader = new NewTextureLoader(fname);
			ImageComponent2D img = loader.getImage();
			
			Texture2D tex = new Texture2D(Texture.BASE_LEVEL, Texture.RGB, img.getWidth(), img.getHeight());
			tex.setImage(0, img);
			tex.setEnable(true);
			
			a.setTexture(tex);
			
			return a;
		}
			
	}

	private static class ColoredRectangularSide extends Shape3D{

		public static int XY_PLANE = 0;
		public static int ZY_PLANE = 1;
		public static int ZX_PLANE = 2;
		
		public ColoredRectangularSide(Color3f color, int plane, float x, float y, float z){
			this.setGeometry(buildGeometry(plane, x, y, z));
			this.setAppearance(color);
		}
				
		public void setAppearance (Color3f color){
			this.setAppearance(buildAppearance(color));
		}
		
		public Geometry buildGeometry (int plane, float x, float y, float z){
			QuadArray side = new QuadArray(4, GeometryArray.COORDINATES);
			
			Point3f uPoint = new Point3f();
			Point2f tPoint = new Point2f();
			
			// Universumskoordinaten erstellen
			uPoint.set((plane==XY_PLANE) ? -x : x , y, (plane==XY_PLANE) ? z : -z );
			side.setCoordinate(0, uPoint);
			
			uPoint.set((plane==ZY_PLANE) ? x : -x , (plane==ZX_PLANE) ? y : -y, (plane==XY_PLANE) ? z : -z );
			side.setCoordinate(1, uPoint);
			
			uPoint.set((plane==ZX_PLANE) ? -x : x , (plane==ZX_PLANE) ? y : -y, z);
			side.setCoordinate(2, uPoint);
			
			uPoint.set(x , y, z);
			side.setCoordinate(3, uPoint);
			
			
			return side;
		}
		
		public Appearance buildAppearance (Color3f color){
			Appearance a = new Appearance();
			
			ColoringAttributes ca = new ColoringAttributes();
			
			ca.setColor(color);
			//ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
			
			a.setColoringAttributes(ca);
			
			return a;
		}
	
	}
	
	public void test(){
		//universe.getCanvas().
		pawns[0][0].setTransform(trackTransforms[20]);
		//universe.getCanvas().postRender();
	}
	
}

