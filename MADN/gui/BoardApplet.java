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
import javax.vecmath.Point3f;
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
 * Klasse: BoardApplet
 * ===================
 * Spezialiserung der Klasse Applet, zur Anzeige einer Java3D-basierten
 * dreidimensionalen graphischen Umsetzung des Mensch-Ärgere-Dich-Nicht-Spielbretts  
 */
public class BoardApplet extends Applet {

	// Bei Auswahl der Spielfiguren per Maus zu benachrichtigender Listener
	private PawnPickingListener listener = null;
	
	// Transformationen die die einzelnen Positionen der Felder der Startbereiche festlegen	
	private Transform3D[][] destinationTransforms = new Transform3D[4][4];
	// Transformationen die die einzelnen Positionen der Felder der Zielbereiche festlegen
	private Transform3D[][] startTransforms = new Transform3D[4][4];
	// Transformationen die die einzelnen Positionen der Felder auf der Rundstrecke festlegen
	private Transform3D[]   trackTransforms = new Transform3D[40];
	
	// Objekte des Szenengraphs
	private SimpleUniverse 						 universe = null;
	private BranchGroup							 scene = null; // ROOT
		private TransformGroup 					 sceneTransformGroup = null;
		private Transform3D						 sceneTransform = null;
			private Background 					 background = null;
			private AmbientLight 				 aLgt = null;
			private DirectionalLight			 dLgt = null;
			private PointLight					 pLgt = null;
			private TransformGroup				 objTransformGroup = null;
			private Transform3D					 objTransform = null;
				private Pawn[][] 				 pawns = new Pawn[4][4];
				private BranchGroup				 objBehaviorBranch = null;
					private MouseRotate 		 rotBehavior = null;
					private MouseZoom 		 	 zoomBehavior = null;
		private PickPawnBehavior 				 pick = null;

	// Colors & Misc
	private Color3f 			bgColor = new Color3f(new Color(220, 220, 255));
	private Color3f 			yellow = new Color3f(new Color(255,255,0).darker().darker());
	private Color3f 			grey = new Color3f(0.2f,0.2f,0.2f);
	private Color3f 			red = new Color3f(GameFrame.colors[0].darker().darker());
	private BoundingSphere 		bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	private Color3f 			dLgtColor = new Color3f(0.7f, 0.7f, 0.7f);
	private Vector3f 			dLgtDir  = new Vector3f(-1.0f, -1.0f, -1.0f);
	private Color3f 			aLgtColor = new Color3f(0.2f, 0.2f, 0.2f);
	private Point3f 			pLgtPos = new Point3f(0f,0f,1.5f);	
	
	// Dimensionen Spielbrett
	private float gap = 0.008f;
	private float x = 0.256f;
	private float y = 0.016f;
	private float z = 0.256f;
	
	// Dimensionen Spielfiguren
	private float radius = 0.01575f;
	private float height = 0.0405f;
	
	/**
	 * Konstruktor: BoardApplet
	 * ------------------------
	 */
	public BoardApplet(){
		
		setLayout(new BorderLayout());
		
		// Canvas erzeugen
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c3d = new Canvas3D(config);
		add(c3d, BorderLayout.CENTER);
	
		// Szenengraph aufbauen
		scene = new BranchGroup();
		buildSceneGraph();
		scene.addChild(sceneTransformGroup);
		
		// Erstelle Basistransformationen
		buildTransformations();
		
		// Setze Spielfiguren in den jeweiligen Startbereich
		resetPawns();
		
		// Erstelle Selektionsverhalten
		pick = new PickPawnBehavior(c3d, scene, new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 2.0), PickTool.GEOMETRY, 2f);
		scene.addChild(pick);
		
		// Kompiliere Szenengraph
		scene.compile();
		
		// Universum erzeugen
		this.universe = new SimpleUniverse(c3d);
		
		// Beobachtungsstandpunkt festlegen		
		Transform3D viewTranslation = new Transform3D();
		viewTranslation.setTranslation(new Vector3d(0,0,1));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewTranslation);
		
		universe.addBranchGraph(scene);
	}
	
	/**
	 * Methode: buildSceneGraph
	 * ------------------------
	 * Erstellt den kompletten Szenengraphen (Lichter, Spielbrett, ...).
	 */
	private void buildSceneGraph(){
		
		// Erstelle Transformationsgruppe der Szene
		sceneTransform = new Transform3D();
		sceneTransformGroup = new TransformGroup(sceneTransform);
	    
		// Setze Fähigkeiten
		sceneTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    sceneTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		sceneTransformGroup.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		// Hintergund setzen
		background = new Background(bgColor); 
		background.setApplicationBounds(bounds);
		sceneTransformGroup.addChild(background);
		
		// Lichter setzen
		aLgt = new AmbientLight(aLgtColor);
		aLgt.setInfluencingBounds(bounds);
		sceneTransformGroup.addChild(aLgt);
		
		dLgt = new DirectionalLight(dLgtColor, dLgtDir);
		dLgt.setInfluencingBounds(bounds);
		sceneTransformGroup.addChild(dLgt);
		
		pLgt = new PointLight();
		pLgt.setPosition(pLgtPos);
		pLgt.setInfluencingBounds(bounds);		
		sceneTransformGroup.addChild(pLgt);		

		// Erstelle Spielbrett
		createBoard();
	}
	
	/**
	 * Methode: createBoard
	 * --------------------
	 * Erstellt das Spielbrett inklusive Spielfiguren und Zoom- bzw. Rotationsverhalten.
	 */
	private void createBoard(){
		
		/*
		 * Erstelle Spielbrettflächen
		 */ 

		// Unterseite
		ColoredRectangularSide lowerSide = new ColoredRectangularSide(grey, ColoredRectangularSide.ZX_PLANE, x, -y, z, gap);
				
		// Oberseite
		TexturedRectangularSide upperSide = new TexturedRectangularSide(yellow, "board.gif", TexturedRectangularSide.ZX_PLANE, x, y, z, gap);
				
		// Rand links
		ColoredRectangularSide leftSide = new ColoredRectangularSide(red, ColoredRectangularSide.YZ_PLANE, -x, y, z, gap);
				
		// Rand rechts
		ColoredRectangularSide rightSide = new ColoredRectangularSide(red, ColoredRectangularSide.YZ_PLANE, x, y, z, gap);
				
		// Rand hinten
		ColoredRectangularSide backSide = new ColoredRectangularSide(red, ColoredRectangularSide.XY_PLANE, x, y, -z, gap);
				
		// Rand vorne
		ColoredRectangularSide frontSide = new ColoredRectangularSide(red, ColoredRectangularSide.XY_PLANE, x, y, z, gap);

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
		
		/*
		 * Erstelle Transformationsgruppe für Brettflächen und Spielfiguren
		 */
		objTransform = new Transform3D();
		objTransform.rotX(Math.PI * 40.0 / 180.0);
			Transform3D rotY = new Transform3D();
			rotY.rotY(Math.PI * 25.0 / 180.0);
		objTransform.mul(rotY);
		
		objTransformGroup = new TransformGroup(objTransform);
		
		// Setze Berechtigungenen
		objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objTransformGroup.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		// Weise Brettflächen der Transformationsgruppe zu
		objTransformGroup.addChild(lowerSide);
		objTransformGroup.addChild(upperSide);
		objTransformGroup.addChild(leftSide);
		objTransformGroup.addChild(rightSide);
		objTransformGroup.addChild(backSide);
		objTransformGroup.addChild(frontSide);
		
		objTransformGroup.addChild(lowerLeftSide);
		objTransformGroup.addChild(upperLeftSide);
		objTransformGroup.addChild(lowerFrontSide);
		objTransformGroup.addChild(upperFrontSide);
		objTransformGroup.addChild(lowerRightSide);
		objTransformGroup.addChild(upperRightSide);
		objTransformGroup.addChild(lowerBackSide);
		objTransformGroup.addChild(upperBackSide);
		
		objTransformGroup.addChild(leftFrontSide);
		objTransformGroup.addChild(frontRightSide);
		objTransformGroup.addChild(rightBackSide);
		objTransformGroup.addChild(backLeftSide);
		
		objTransformGroup.addChild(corner1);
		objTransformGroup.addChild(corner2);
		objTransformGroup.addChild(corner3);
		objTransformGroup.addChild(corner4);
		objTransformGroup.addChild(corner5);
		objTransformGroup.addChild(corner6);
		objTransformGroup.addChild(corner7);
		objTransformGroup.addChild(corner8);		
		
		// Erstelle Spielfiguren und weise sie der Transformationsgruppe zu
		for (int i=0; i<pawns.length; i++){
			for (int j=0; j<pawns[i].length; j++){
				pawns[i][j] = new Pawn(i, j, y + gap, radius, height);
				
				objTransformGroup.addChild(pawns[i][j]);
			}
		}
		
		// Setze Zoom- und Rotationsverhalten (mausgesteuert)
		objBehaviorBranch = new BranchGroup();
			
			rotBehavior = new MouseRotate();
			rotBehavior.setTransformGroup(objTransformGroup);
			rotBehavior.setSchedulingBounds( bounds );
			objBehaviorBranch.addChild(rotBehavior);
		
			zoomBehavior = new MouseZoom();
			zoomBehavior.setTransformGroup(objTransformGroup);
			zoomBehavior.setSchedulingBounds(bounds);
			objBehaviorBranch.addChild(zoomBehavior);
		
		objTransformGroup.addChild(objBehaviorBranch);
		
		sceneTransformGroup.addChild(objTransformGroup);
	}
	
	/**
	 * Methode: actualizePawnPositions
	 * -------------------------------
	 * Aktualisiert die Positionen der einzelnen Spielfiguren dadurch, dass die Transformation
	 * der Transformationsgruppe des jeweiligen Spielsteins entsprechend angepasst wird.
	 * @param pieces = Array der logischen Repräsentanten (@see model.Piece) der Spielsteine (nach Farben)
	 */
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
	
	/**
	 * Methode: setPawnPickingListener
	 * -------------------------------
	 * Set-Methode der Membervariable listener
	 * @param listener
	 */
	public void setPawnPickingListener (PawnPickingListener listener){
		this.listener = listener;
	}

	/**
	 * Methode: getPawnPickingListener
	 * -------------------------------
	 * Get-Methode der Membervariable listener
	 */
	public PawnPickingListener getPawnPickingListener (){
		return listener;
	}
	
	/**
	 * Methode: resetPawns
	 * -------------------
	 * Setzt alle Spielfiguren in ihren jeweilgen Startbereich 
	 */
	private void resetPawns(){
		for (int i=0; i<pawns.length; i++){
			for (int j=0; j<pawns[i].length; j++){
				pawns[i][j].setTransform(startTransforms[i][j]);
			}
		}
	}
	
	/**
	 * Methode: buildTransformations
	 * -----------------------------
	 * Erstellt Transformationen, die alle möglichen Spielsteinpositionen auf dem 
	 * Spielbrett repräsentieren.
	 */
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

	/**
	 * Klasse: PickPawnBehavior
	 * ========================
	 * Private Spezialisierung der Klasse PickMouseBehavior, die eine adäquate Reaktion auf die 
	 * Auwahl einer Spielfigur (@see gui.Pawn) per Mausklick ermöglicht. 
	 */
	private class PickPawnBehavior extends PickMouseBehavior{
		
		/**
		 * Konstruktor: PickPawnBehavior
		 * -----------------------------
		 * @param canvas	= Canvas des Applets, dem das 3D-Universum des Szenengraphen zugeordnet
		 * @param root      = Wurzel des Szenengraphen
		 * @param bounds	= Wirkungsgrenzen des Behaviors
		 * @param mode		= @see PickTool
		 * @param tolerance	= Selektionstoleranz [0.0f; 0.8f]
		 */
		public PickPawnBehavior (Canvas3D canvas, BranchGroup root, Bounds bounds, int mode, float tolerance){
			super(canvas, root, bounds);
			setSchedulingBounds(bounds);
			setMode(mode);
			setTolerance(tolerance);
		}

		/**
		 * Methode: updateScene
		 * --------------------
		 * Wird bei jeder Mausaktion/jedem MouseEvent aufgerufen.
		 * @param xpos = horizontale Position des Mauscursors
		 * @param ypos = vertikale Position des Mauscursors
		 */
		public void updateScene(int xpos, int ypos){
			MouseEvent m = this.mevent;			
			
			// Wenn die linke Maustaste geclickt wurde...
			if (m.getButton() == MouseEvent.BUTTON1 /*Left*/){
				// Setze Mausposition im Universum
				pickCanvas.setShapeLocation(xpos, ypos);
				// Versuche Objekte zu selektieren
				PickResult result = pickCanvas.pickClosest();
				
				if (result != null){
						
					Primitive pickedPrimitive = null;
					pickedPrimitive = (Primitive) result.getNode(PickResult.PRIMITIVE);
					
					if (pickedPrimitive != null){
						// Falls Spielfigur selektiert wurde, benachrichtiger Listener						
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
			// Wenn die rechte Maustaste 1x geclickt wurde...
			}else if ((m.getButton() == MouseEvent.BUTTON3 /*Right*/) && (m.getClickCount()==1)){
				// Benachrichtige Listener
				if (listener != null) listener.rightMouseButtonClicked();
			}
		}
	}
}

