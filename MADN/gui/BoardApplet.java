/*
 * Created on 09.05.2005
 */
package gui;



import java.applet.Applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Constants;
import model.Piece;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickMouseBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Mario
 */
public class BoardApplet extends Applet {

	
	private PawnPickingListener listener = null;
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
				
		Transform3D viewTranslation = new Transform3D();
		viewTranslation.setTranslation(new Vector3d(0,0,1));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewTranslation);
		
//		PointLight light = new PointLight();
//		light.setPosition(0f,0f,1f);
//		light.setInfluencingBounds(new BoundingSphere(new Point3d(0,0,0), 2.0));
//		scene.addChild(light);

		// Set up the global lights
		BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		Color3f lColor1 = new Color3f(0.7f, 0.7f, 0.7f);
		Vector3f lDir1  = new Vector3f(-1.0f, -1.0f, -1.0f);
		Color3f alColor = new Color3f(0.2f, 0.2f, 0.2f);

		AmbientLight aLgt = new AmbientLight(alColor);
		aLgt.setInfluencingBounds(bounds);
		
		DirectionalLight dLgt = new DirectionalLight(lColor1, lDir1);
		dLgt.setInfluencingBounds(bounds);
		
		PointLight pLgt = new PointLight();
		pLgt.setPosition(0f,1f,1.5f);
		pLgt.setInfluencingBounds(bounds);
		
		scene.addChild(aLgt);
		scene.addChild(dLgt);
		scene.addChild(pLgt);
		
//		OrbitBehavior orbit = new OrbitBehavior(c3d, OrbitBehavior.REVERSE_ALL|OrbitBehavior.STOP_ZOOM);
//		orbit.setRotateEnable(true);
//		orbit.setZoomEnable(true);
//		orbit.setTranslateEnable(false);
//		//BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
//		orbit.setSchedulingBounds(bounds);
//		orbit.setMinRadius(0.9);
		
		PickPawnBehavior pick = new PickPawnBehavior(c3d, scene, new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 2.0), PickTool.GEOMETRY, 2f);
		scene.addChild(pick);
		
//		ViewingPlatform view = universe.getViewingPlatform();
//		view.setViewPlatformBehavior(orbit);
		
		universe.addBranchGraph(scene);
		
		
		
	}
	
	public BranchGroup buildSceneGraph(){
		BranchGroup root = new BranchGroup();
		
		root.addChild(buildGeometry());
		
		Color3f color= new Color3f();
		color.set(new Color(220, 220, 255));
		Background background = new Background(color); 
		background.setApplicationBounds(new BoundingSphere());
		root.addChild(background);		
		
		return root;
	}
	
	public BranchGroup buildGeometry(){
		BranchGroup bgBoard = new BranchGroup();
		
		Color3f yellow = new Color3f(new Color(255,255,0).darker().darker());
		Color3f grey = new Color3f(0.2f,0.2f,0.2f);
		Color3f red = new Color3f(GameFrame.colors[0].darker().darker());
		
		float gap = 0.008f;
		float x = 0.256f;
		float y = 0.016f;
		float z = 0.256f;
		
		// Unterseite
		ColoredRectangularSide lowerSide = new ColoredRectangularSide(grey, ColoredRectangularSide.ZX_PLANE, x, -y, z, gap);
		//TexturedRectangularSide lowerSide = new TexturedRectangularSide("gui/images/back.gif", ColoredRectangularSide.ZX_PLANE, x, -y, z);
				
		// Oberseite
		TexturedRectangularSide upperSide = new TexturedRectangularSide(yellow, "gui/images/board.gif", TexturedRectangularSide.ZX_PLANE, x, y, z, gap);
				
		// Rand links
		ColoredRectangularSide leftSide = new ColoredRectangularSide(red, ColoredRectangularSide.YZ_PLANE, -x, y, z, gap);
				
		// Rand rechts
		ColoredRectangularSide rightSide = new ColoredRectangularSide(red, ColoredRectangularSide.YZ_PLANE, x, y, z, gap);
		//TexturedRectangularSide rightSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.ZY_PLANE, x, y, z);
				
		// Rand hinten
		ColoredRectangularSide backSide = new ColoredRectangularSide(red, ColoredRectangularSide.XY_PLANE, x, y, -z, gap);
		//TexturedRectangularSide backSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.XY_PLANE, x, y, -z);
				
		// Rand vorne
		ColoredRectangularSide frontSide = new ColoredRectangularSide(red, ColoredRectangularSide.XY_PLANE, x, y, z, gap);
		//TexturedRectangularSide frontSide = new TexturedRectangularSide("gui/images/border.gif", ColoredRectangularSide.XY_PLANE, x, y, z);

		// Verbindungsfläche Unterseite - Rand links
		ColoredRectangularSide lowerLeftSide = new ColoredRectangularSide(red, lowerSide.getCoordinate(2),lowerSide.getCoordinate(1), leftSide.getCoordinate(3),leftSide.getCoordinate(2) );
		
		// Verbindungsfläche Oberseite - Rand links
		ColoredRectangularSide upperLeftSide = new ColoredRectangularSide(red, upperSide.getCoordinate(2),upperSide.getCoordinate(1), leftSide.getCoordinate(1),leftSide.getCoordinate(0) );

		// Verbindungsfläche Unterseite - Rand vorne
		ColoredRectangularSide lowerFrontSide = new ColoredRectangularSide(red,lowerSide.getCoordinate(1),lowerSide.getCoordinate(0), frontSide.getCoordinate(2),frontSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand vorne
		ColoredRectangularSide upperFrontSide = new ColoredRectangularSide(red,upperSide.getCoordinate(3),upperSide.getCoordinate(2), frontSide.getCoordinate(0),frontSide.getCoordinate(3) );

		// Verbindungsfläche Unterseite - Rand rechts
		ColoredRectangularSide lowerRightSide = new ColoredRectangularSide(red,lowerSide.getCoordinate(0),lowerSide.getCoordinate(3), rightSide.getCoordinate(1),rightSide.getCoordinate(0) );
		
		// Verbindungsfläche Oberseite - Rand rechts
		ColoredRectangularSide upperRightSide = new ColoredRectangularSide(red,upperSide.getCoordinate(0),upperSide.getCoordinate(3), rightSide.getCoordinate(3),rightSide.getCoordinate(2) );

		// Verbindungsfläche Unterseite - Rand hinten
		ColoredRectangularSide lowerBackSide = new ColoredRectangularSide(red,lowerSide.getCoordinate(3),lowerSide.getCoordinate(2), backSide.getCoordinate(2),backSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand hinten
		ColoredRectangularSide upperBackSide = new ColoredRectangularSide(red,upperSide.getCoordinate(1),upperSide.getCoordinate(0), backSide.getCoordinate(0),backSide.getCoordinate(3) );

		// Verbindungsfläche Rand links - Rand vorne
		ColoredRectangularSide leftFrontSide = new ColoredRectangularSide(red,leftSide.getCoordinate(0),leftSide.getCoordinate(3), frontSide.getCoordinate(1),frontSide.getCoordinate(0) );

		// Verbindungsfläche Rand vorne - Rand rechts
		ColoredRectangularSide frontRightSide = new ColoredRectangularSide(red,frontSide.getCoordinate(3),frontSide.getCoordinate(2), rightSide.getCoordinate(0),rightSide.getCoordinate(3) );

		// Verbindungsfläche Rand rechts - Rand hinten
		ColoredRectangularSide rightBackSide = new ColoredRectangularSide(red,rightSide.getCoordinate(2),rightSide.getCoordinate(1), backSide.getCoordinate(1),backSide.getCoordinate(0) );

		// Verbindungsfläche Rand hinten - Rand links
		ColoredRectangularSide backLeftSide = new ColoredRectangularSide(red,backSide.getCoordinate(3),backSide.getCoordinate(2), leftSide.getCoordinate(2),leftSide.getCoordinate(1) );

		// Ecke oben, vorne, links
		ColoredTriangularSide corner1 = new ColoredTriangularSide(red,leftSide.getCoordinate(0),frontSide.getCoordinate(0), upperSide.getCoordinate(2) );

		// Ecke oben, vorne, rechts
		ColoredTriangularSide corner2 = new ColoredTriangularSide(red,frontSide.getCoordinate(3),rightSide.getCoordinate(3), upperSide.getCoordinate(3) );

		// Ecke oben, hinten, rechts
		ColoredTriangularSide corner3 = new ColoredTriangularSide(red,rightSide.getCoordinate(2),backSide.getCoordinate(0), upperSide.getCoordinate(0) );

		// Ecke oben, hinten, links
		ColoredTriangularSide corner4 = new ColoredTriangularSide(red,backSide.getCoordinate(3),leftSide.getCoordinate(1), upperSide.getCoordinate(1) );

		// Ecke unten, vorne, links
		ColoredTriangularSide corner5 = new ColoredTriangularSide(red,frontSide.getCoordinate(1),leftSide.getCoordinate(3), lowerSide.getCoordinate(1) );

		// Ecke unten, vorne, rechts
		ColoredTriangularSide corner6 = new ColoredTriangularSide(red,rightSide.getCoordinate(0),frontSide.getCoordinate(2), lowerSide.getCoordinate(0) );

		// Ecke unten, hinten, rechts
		ColoredTriangularSide corner7 = new ColoredTriangularSide(red,backSide.getCoordinate(1),rightSide.getCoordinate(1), lowerSide.getCoordinate(3) );

		// Ecke unten, hinten, links
		ColoredTriangularSide corner8 = new ColoredTriangularSide(red,leftSide.getCoordinate(2),backSide.getCoordinate(2), lowerSide.getCoordinate(2) );
		
		
		Transform3D rot = new Transform3D();
		rot.rotX(Math.PI * 40.0 / 180.0);
		Transform3D rotY = new Transform3D();
		rotY.rotY(Math.PI * 25.0 / 180.0);
		Transform3D rotZ = new Transform3D();
		
		rot.mul(rotY);
		
		TransformGroup tg = new TransformGroup(rot);
		
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		tg.addChild(lowerSide);
		tg.addChild(upperSide);
		tg.addChild(leftSide);
		tg.addChild(rightSide);
		tg.addChild(backSide);
		tg.addChild(frontSide);
		
		tg.addChild(lowerLeftSide);
		tg.addChild(upperLeftSide);
		tg.addChild(lowerFrontSide);
		tg.addChild(upperFrontSide);
		tg.addChild(lowerRightSide);
		tg.addChild(upperRightSide);
		tg.addChild(lowerBackSide);
		tg.addChild(upperBackSide);
		
		tg.addChild(leftFrontSide);
		tg.addChild(frontRightSide);
		tg.addChild(rightBackSide);
		tg.addChild(backLeftSide);
		
		tg.addChild(corner1);
		tg.addChild(corner2);
		tg.addChild(corner3);
		tg.addChild(corner4);
		tg.addChild(corner5);
		tg.addChild(corner6);
		tg.addChild(corner7);
		tg.addChild(corner8);		
		
		for (int i=0; i<pawns.length; i++){
			for (int j=0; j<pawns[i].length; j++){
				pawns[i][j] = new Pawn(i, j, y + gap, 0.01575f, 0.0405f);
				
				tg.addChild(pawns[i][j]);
			}
		}
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		MouseRotate rotBehavior = new MouseRotate();
		rotBehavior.setTransformGroup(tg);
		tg.addChild(rotBehavior);
		rotBehavior.setSchedulingBounds( bounds );
		
		MouseZoom zoomBehavior = new MouseZoom();
		zoomBehavior.setTransformGroup(tg);
		zoomBehavior.setSchedulingBounds(bounds);
		tg.addChild(zoomBehavior);
		
		
		
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
	
	public void setPawnPickingListener (PawnPickingListener listener){
		this.listener = listener;
	}
	
	public PawnPickingListener getPawnPickingListener (){
		return listener;
	}
	
//	protected Pawn getPawnOfShape (Shape3D shape){
//		Pawn p = null;
//		for (int i=0; i<pawns.length; i++){
//			for (int j=0; j<pawns[i].length; j++){
//				if ((pawns[i][j]!=null)&&(pawns[i][j].containsShape3D(shape))){
//					p = pawns[i][j];
//					break;
//				}
//				
//			}
//		}
//		return p;
//	}
	
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
			startTransforms[Constants.RED][1].setTranslation(new Vector3d(-0.1365,0,0.1345));
			startTransforms[Constants.RED][0].setTranslation(new Vector3d(-0.1365,0,0.1795));
			startTransforms[Constants.RED][2].setTranslation(new Vector3d(-0.1815,0,0.1345));
			startTransforms[Constants.RED][3].setTranslation(new Vector3d(-0.1815,0,0.1795));		
		
			// Schwarz
			startTransforms[Constants.BLACK][1].setTranslation(new Vector3d(-0.1365,0,-0.1365));
			startTransforms[Constants.BLACK][0].setTranslation(new Vector3d(-0.1815,0,-0.1365));
			startTransforms[Constants.BLACK][2].setTranslation(new Vector3d(-0.1365,0,-0.1815));
			startTransforms[Constants.BLACK][3].setTranslation(new Vector3d(-0.1815,0,-0.1815));
										
			// Blau
			startTransforms[Constants.BLUE][1].setTranslation(new Vector3d(0.1345,0,-0.1365));
			startTransforms[Constants.BLUE][0].setTranslation(new Vector3d(0.1345,0,-0.1815));
			startTransforms[Constants.BLUE][2].setTranslation(new Vector3d(0.1795,0,-0.1365));
			startTransforms[Constants.BLUE][3].setTranslation(new Vector3d(0.1795,0,-0.1815));			

			// Grün
			startTransforms[Constants.GREEN][1].setTranslation(new Vector3d(0.1345,0,0.1345));
			startTransforms[Constants.GREEN][0].setTranslation(new Vector3d(0.1795,0,0.1345));
			startTransforms[Constants.GREEN][2].setTranslation(new Vector3d(0.1345,0,0.1795));
			startTransforms[Constants.GREEN][3].setTranslation(new Vector3d(0.1795,0,0.1795));
			
			
			
			
		// Transformationen Zielbereich
		for (int i=0; i < destinationTransforms.length; i++)
			for (int j=0; j < destinationTransforms[i].length; j++)
				destinationTransforms[i][j] = new Transform3D();
			
			// Rot
		
			destinationTransforms[Constants.RED][0].setTranslation(new Vector3d(-0.0015,0,0.1795));
			destinationTransforms[Constants.RED][1].setTranslation(new Vector3d(-0.0015,0,0.1345));
			destinationTransforms[Constants.RED][2].setTranslation(new Vector3d(-0.0015,0,0.0895));
			destinationTransforms[Constants.RED][3].setTranslation(new Vector3d(-0.0015,0,0.0445));			

			// Schwarz
			destinationTransforms[Constants.BLACK][0].setTranslation(new Vector3d(-0.1815,0,-0.0015));
			destinationTransforms[Constants.BLACK][1].setTranslation(new Vector3d(-0.1365,0,-0.0015));
			destinationTransforms[Constants.BLACK][2].setTranslation(new Vector3d(-0.0915,0,-0.0015));
			destinationTransforms[Constants.BLACK][3].setTranslation(new Vector3d(-0.0465,0,-0.0015));

			// Blau
			destinationTransforms[Constants.BLUE][0].setTranslation(new Vector3d(-0.0015,0,-0.1815));
			destinationTransforms[Constants.BLUE][1].setTranslation(new Vector3d(-0.0015,0,-0.1365));
			destinationTransforms[Constants.BLUE][2].setTranslation(new Vector3d(-0.0015,0,-0.0915));
			destinationTransforms[Constants.BLUE][3].setTranslation(new Vector3d(-0.0015,0,-0.0465));			
	
			// Grün
			destinationTransforms[Constants.GREEN][0].setTranslation(new Vector3d(0.1795,0,-0.0005));
			destinationTransforms[Constants.GREEN][1].setTranslation(new Vector3d(0.1345,0,-0.0005));
			destinationTransforms[Constants.GREEN][2].setTranslation(new Vector3d(0.0895,0,-0.0005));
			destinationTransforms[Constants.GREEN][3].setTranslation(new Vector3d(0.0445,0,-0.0005));
			
		// Transformationen Rundstrecke
		for (int i=0; i<trackTransforms.length; i++)
			trackTransforms[i] = new Transform3D();
		
			// Rot
			trackTransforms[Constants.RED * 10    ].setTranslation(new Vector3d(-0.0465,0,0.2245));
			trackTransforms[Constants.RED * 10 + 1].setTranslation(new Vector3d(-0.0465,0,0.1795));
			trackTransforms[Constants.RED * 10 + 2].setTranslation(new Vector3d(-0.0465,0,0.1345));
			trackTransforms[Constants.RED * 10 + 3].setTranslation(new Vector3d(-0.0465,0,0.0895));
			trackTransforms[Constants.RED * 10 + 4].setTranslation(new Vector3d(-0.0465,0,0.0445));
			trackTransforms[Constants.RED * 10 + 5].setTranslation(new Vector3d(-0.0915,0,0.0445f));
			trackTransforms[Constants.RED * 10 + 6].setTranslation(new Vector3d(-0.1365,0,0.0445f));
			trackTransforms[Constants.RED * 10 + 7].setTranslation(new Vector3d(-0.181,0,0.0445f));
			trackTransforms[Constants.RED * 10 + 8].setTranslation(new Vector3d(-0.2265,0,0.0445f));
			trackTransforms[Constants.RED * 10 + 9].setTranslation(new Vector3d(-0.2265,0,-0.0015f));					
		
		
			// Schwarz
			trackTransforms[Constants.BLACK * 10    ].setTranslation(new Vector3d(-0.2265,0,-0.0465f));
			trackTransforms[Constants.BLACK * 10 + 1].setTranslation(new Vector3d(-0.181,0,-0.0465f));
			trackTransforms[Constants.BLACK * 10 + 2].setTranslation(new Vector3d(-0.1365,0,-0.0465f));
			trackTransforms[Constants.BLACK * 10 + 3].setTranslation(new Vector3d(-0.0915,0,-0.0465f));
			trackTransforms[Constants.BLACK * 10 + 4].setTranslation(new Vector3d(-0.0465,0,-0.0465));
			trackTransforms[Constants.BLACK * 10 + 5].setTranslation(new Vector3d(-0.0465,0,-0.0915));
			trackTransforms[Constants.BLACK * 10 + 6].setTranslation(new Vector3d(-0.0465,0,-0.1365));
			trackTransforms[Constants.BLACK * 10 + 7].setTranslation(new Vector3d(-0.0465,0,-0.1815));
			trackTransforms[Constants.BLACK * 10 + 8].setTranslation(new Vector3d(-0.0465,0,-0.2265));
			trackTransforms[Constants.BLACK * 10 + 9].setTranslation(new Vector3d(-0.0015,0,-0.2265));

			// Blau
			trackTransforms[Constants.BLUE * 10    ].setTranslation(new Vector3d(0.0445,0,-0.2265));
			trackTransforms[Constants.BLUE * 10 + 1].setTranslation(new Vector3d(0.0445,0,-0.1815));
			trackTransforms[Constants.BLUE * 10 + 2].setTranslation(new Vector3d(0.0445,0,-0.1365));
			trackTransforms[Constants.BLUE * 10 + 3].setTranslation(new Vector3d(0.0445,0,-0.0915));
			trackTransforms[Constants.BLUE * 10 + 4].setTranslation(new Vector3d(0.0445,0,-0.0455));
			trackTransforms[Constants.BLUE * 10 + 5].setTranslation(new Vector3d(0.0895,0,-0.0455));
			trackTransforms[Constants.BLUE * 10 + 6].setTranslation(new Vector3d(0.1345,0,-0.0455));
			trackTransforms[Constants.BLUE * 10 + 7].setTranslation(new Vector3d(0.1795,0,-0.0455));
			trackTransforms[Constants.BLUE * 10 + 8].setTranslation(new Vector3d(0.2245,0,-0.0455));
			trackTransforms[Constants.BLUE * 10 + 9].setTranslation(new Vector3d(0.2245,0,-0.0005));

			// Grün
			trackTransforms[Constants.GREEN * 10    ].setTranslation(new Vector3d(0.2245,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 1].setTranslation(new Vector3d(0.1795,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 2].setTranslation(new Vector3d(0.1345,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 3].setTranslation(new Vector3d(0.0895,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 4].setTranslation(new Vector3d(0.0445,0,0.0445));
			trackTransforms[Constants.GREEN * 10 + 5].setTranslation(new Vector3d(0.0445,0,0.0895));
			trackTransforms[Constants.GREEN * 10 + 6].setTranslation(new Vector3d(0.0445,0,0.1345));
			trackTransforms[Constants.GREEN * 10 + 7].setTranslation(new Vector3d(0.0445,0,0.1795));
			trackTransforms[Constants.GREEN * 10 + 8].setTranslation(new Vector3d(0.0445,0,0.2245));
			trackTransforms[Constants.GREEN * 10 + 9].setTranslation(new Vector3d(-0.0015,0,0.2245));
			
	}

	private class PickPawnBehavior extends PickMouseBehavior{
		
		public PickPawnBehavior (Canvas3D canvas, BranchGroup root, Bounds bounds, int mode, float tolerance){
			super(canvas, root, bounds);
			setSchedulingBounds(bounds);
			setMode(mode);
			setTolerance(tolerance);
		}

		public void updateScene(int xpos, int ypos){
			MouseEvent m = this.mevent;			
			
			if (m.getButton() == MouseEvent.BUTTON1 /*Left*/){
				
				pickCanvas.setShapeLocation(xpos, ypos);
				
				PickResult result = pickCanvas.pickClosest();
				
				if (result != null){
						
					Primitive pickedPrimitive = null;
					pickedPrimitive = (Primitive) result.getNode(PickResult.PRIMITIVE);
					
					if (pickedPrimitive != null){
						
						if (pickedPrimitive instanceof MyCone){
							MyCone c = (MyCone)pickedPrimitive;
							if (listener != null){
								listener.pawnClicked(c.getColor(), c.getID(), m.getClickCount());
							}
						}else if (pickedPrimitive instanceof MySphere){
							MySphere s = (MySphere)pickedPrimitive;
							if (listener != null){
								listener.pawnClicked(s.getColor(), s.getID(), m.getClickCount());
							}
						}
					}
				}
			}else if ((m.getButton() == MouseEvent.BUTTON3 /*Right*/) && (m.getClickCount()==1)){
				if (listener != null) listener.rightMouseButtonClicked();
			}
		}
	}
}

