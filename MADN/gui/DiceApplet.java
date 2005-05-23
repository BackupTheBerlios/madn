package gui;

import java.applet.Applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import model.Constants;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.interpolators.KBKeyFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * @author Mario
 */
public class DiceApplet extends Applet {

	// Scene Graph
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
				private BranchGroup				 objBehaviorBranch = null;
			private TransformGroup				 tableTransformGroup = null;
			private Transform3D					 tableTransform = null;
	
	// Colors & Misc
	Color3f 			bgColor = new Color3f(new Color(220, 220, 255));			
    BoundingSphere 		bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	Color3f 			dLgtColor = new Color3f(0.7f, 0.7f, 0.7f);
	Vector3f 			dLgtDir  = new Vector3f(-1.0f, -1.0f, -1.0f);
	Color3f 			aLgtColor = new Color3f(0.2f, 0.2f, 0.2f);
	Point3f 			pLgtPos = new Point3f(0f,0f,1.5f);	
	Color3f 			sphereColor = new Color3f(0.1f, 1f, 0.9f);
	Color3f				tableColor = new Color3f(new Color(153,102,0));
	
	// Dimensions
    private float tableSemiHeight = 0.05f;
    private float tableSemiWidth  = 1.00f;
    private float tableSemiDepth  = 0.50f;
	private float diceSemiTexLength = 0.08f; 
	private float diceEdgeGap = 0.01f;
	private float diceSemiDiagonal = (float)Math.sqrt(2 * Math.pow(diceSemiTexLength+diceEdgeGap, 2.0));
    
	// Winkel um bestimtes Würfelergebnis zu erzielen
	float[][] rotations = {//X-ROT                Z-ROT              
							{0.0f, 				 (float)(Math.PI/2.0d)},	// Würfelergebnis = 1
							{(float)(Math.PI/2.0d), 0.0f},				// Würfelergebnis = 2
							{0.0f, 				 (float)Math.PI},		// Würfelergebnis = 3
							{0.0f, 				  0.0f},				// Würfelergebnis = 4
							{(float)(Math.PI*1.5d), 0.0f},				// Würfelergebnis = 5
							{0.0f, 				 (float)(Math.PI*1.5d)}	// Würfelergebnis = 6
						  };
	
	// Key Frames & Interpolator
    private int                              	 duration = 2750;
    private float								 threshold = 0.96f;
    private Alpha                                animAlpha;
    private Transform3D                          yAxis;
    private KBKeyFrame[]                         splineKeyFrames = new KBKeyFrame[12];
    //private KBRotPosScaleSplinePathInterpolator  splineInterpolator;
    private AnimationInterpolator  				splineInterpolator;
    
    //Knoten-Positionen
    Vector3f           pos0 =  new Vector3f(-0.75f, tableSemiHeight + 3.00f * diceSemiDiagonal,         0.0f);
    Vector3f           pos1 =  new Vector3f(-0.60f, tableSemiHeight +         diceSemiDiagonal,         0.025f);
    Vector3f           pos2 =  new Vector3f(-0.45f, tableSemiHeight + 2.00f * diceSemiDiagonal,         0.0f);
    Vector3f           pos3 =  new Vector3f(-0.30f, tableSemiHeight +         diceSemiDiagonal,        -0.025f);
    Vector3f           pos4 =  new Vector3f(-0.15f, tableSemiHeight + 1.75f * diceSemiDiagonal,         0.0f);
    Vector3f           pos5 =  new Vector3f( 0.00f, tableSemiHeight +         diceSemiDiagonal,         0.025f);
    Vector3f           pos6 =  new Vector3f( 0.15f, tableSemiHeight + 1.50f * diceSemiDiagonal,         0.0f);
    Vector3f           pos7 =  new Vector3f( 0.30f, tableSemiHeight +         diceSemiDiagonal,        -0.025f);
    Vector3f           pos8 =  new Vector3f( 0.45f, tableSemiHeight + 1.25f * diceSemiDiagonal,         0.0f);
    Vector3f           pos9 =  new Vector3f( 0.60f, tableSemiHeight +         diceSemiDiagonal,         0.025f);
    Vector3f           pos10 = new Vector3f( 0.75f, tableSemiHeight + diceSemiTexLength + diceEdgeGap,  0.0f);
    Vector3f           pos11 = new Vector3f( 0.75f, tableSemiHeight + diceSemiTexLength + diceEdgeGap,  0.0f);

	public DiceApplet(AnimationListener listener){
		
		setLayout(new BorderLayout());
		
		// Canvas erzeugen
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c3d = new Canvas3D(config);
		add(c3d, BorderLayout.CENTER);
		
		// SceneGraph aufbauen
		scene = new BranchGroup();
		buildSceneGraph(listener);
		scene.addChild(sceneTransformGroup);
		scene.compile();
		
		// Universum erzeugen
		this.universe = new SimpleUniverse(c3d);
		
		// Beobachtungsstandpunkt festlegen
		ViewingPlatform viewingPlatform = universe.getViewingPlatform();
		viewingPlatform.setNominalViewingTransform();

		// add orbit behavior to the ViewingPlatform
//		OrbitBehavior orbit = new OrbitBehavior(c3d, OrbitBehavior.REVERSE_ALL);
//		orbit.setSchedulingBounds(bounds);
//		viewingPlatform.setViewPlatformBehavior(orbit);		
		
		universe.addBranchGraph(scene);
	}

	private void createInterpolator (AnimationListener listener) {

      objBehaviorBranch = new BranchGroup();
      splineInterpolator = new AnimationInterpolator(listener, duration, objTransformGroup, new Transform3D(), threshold, splineKeyFrames); 
      splineInterpolator.setSchedulingBounds(bounds);
      
      objBehaviorBranch.addChild(splineInterpolator);
      objTransformGroup.addChild(objBehaviorBranch); 
     
    }
	
	public void startAnimation(int diceResult){
		
		if ((diceResult>=1)&&(diceResult<=6)){
		
			float pitch = rotations[diceResult-1][0];
			float initialBank = rotations[diceResult-1][1];
			
			splineKeyFrames[0].pitch = pitch;
			splineKeyFrames[0].bank = initialBank;
			
			for (int i=1; i < splineKeyFrames.length-1; i++){
				
					if (i<6){
						pitch += (float)(Math.PI/5d) ;
					}else{
						pitch =  splineKeyFrames[10-i].pitch;
					}
									
				splineKeyFrames[i].pitch = pitch;
				splineKeyFrames[i].bank = initialBank - (float)(Math.PI * i * 0.2500000000d);
			}
			
			splineKeyFrames[11].pitch = splineKeyFrames[0].pitch;
			splineKeyFrames[11].bank = splineKeyFrames[10].bank;
			
					
			splineInterpolator.setKeyFrames(splineKeyFrames);
			splineInterpolator.startAnimation();
		}
		
	}
    
    
    private void setupSplineKeyFrames () {
           
      // Prepare spline keyframe data
      Point3f p   = new Point3f (pos0);     // position
      float head  = 0.0f ;                  // heading
      float pitch = 0.0f ;                 	// pitch 
      float bank  = 0.0f;					// bank 
      Point3f s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[0] = 
         new KBKeyFrame(0.0f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f); 

      p = new Point3f (pos1);
      head  = 0.0f ;//= (float)Math.PI/8 * 2*(randomizer.nextFloat() - 0.5f) ;	// heading
      pitch = 0.0f ;                    								// pitch 
      bank  = 0.0f;              										// bank  
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[1] = 
         new KBKeyFrame(0.1f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f); 

      p = new Point3f (pos2);
      head  = 0.0f ;                               // heading
      pitch = 0.0f ;                                  // pitch 
      bank  = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[2] = 
         new KBKeyFrame(0.2f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f); 

      p = new Point3f (pos3);
      head  = 0.0f ;//= (float)Math.PI/8 * 2*(randomizer.nextFloat() - 0.5f) ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank  = 0.0f;              				  // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[3] = 
         new KBKeyFrame(0.3f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f); 

      p = new Point3f (pos4);
      head  = 0.0f ;                               // heading
      pitch = 0.0f ;                               // pitch 
      bank  = 0.0f;              				  // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[4] = 
         new KBKeyFrame(0.4f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f); 

      p = new Point3f (pos5);
      head  = 0.0f ;//= (float)Math.PI/8 * 2*(randomizer.nextFloat() - 0.5f) ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank  = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[5] = 
         new KBKeyFrame(0.5f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);
      
      p = new Point3f (pos6);
      head  = 0.0f ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[6] = 
         new KBKeyFrame(0.6f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);

      p = new Point3f (pos7);
      head = 0.0f ;// = (float)Math.PI/8 * 2*(randomizer.nextFloat() - 0.5f) ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank  = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[7] = 
         new KBKeyFrame(0.7f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);
    
      p = new Point3f (pos8);
      head  = 0.0f ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank  = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[8] = 
         new KBKeyFrame(0.8f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);      
  
      p = new Point3f (pos9);
      head  = 0.0f ;//= (float)Math.PI/8 * 2*(randomizer.nextFloat() - 0.5f) ;                               // heading
      pitch = 0.0f ;                                   // pitch 
      bank  = 0.0f;                               // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);          // uniform scale
      splineKeyFrames[9] = 
         new KBKeyFrame(0.9f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);
  
      p = new Point3f (pos10);
      head  = 0.0f;                         // heading
      pitch = 0.0f ;                    	// pitch 
      bank  = 0.0f;                         // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);    // uniform scale
      splineKeyFrames[10] = 
         new KBKeyFrame(threshold, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);    
    
      p = new Point3f (pos11);
      head  = 0.0f;                         // heading
      pitch = 0.0f;                        // pitch 
      bank  = 0.0f;                         // bank 
      s = new Point3f(1.0f, 1.0f, 1.0f);    // uniform scale
      splineKeyFrames[11] = 
         new KBKeyFrame(1.0f, 0, p, head, pitch, bank, s, 0.0f, 0.0f, 0.0f);    
      
      
    }	
	
	
	public void buildSceneGraph(AnimationListener listener){
		
		sceneTransform = new Transform3D();
		Transform3D rotZ = new Transform3D();
		Transform3D rotX = new Transform3D();
		
		rotX.rotX(Math.PI/8);
		
		rotZ.rotZ(-Math.PI/7);
		rotZ.mul(rotX);
		
		sceneTransform.rotY(-Math.PI/2.5);
		sceneTransform.mul(rotZ);
		sceneTransform.setTranslation(new Vector3f(-0.1f,0.15f,0.9f));
		
		sceneTransformGroup = new TransformGroup(sceneTransform);
	    sceneTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    sceneTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);		
		
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

		// Würfeltischobjekt erstellen
		createTable();
		
		// Würfelobjekt erstellen
		createDice();
		
		// Animationsframes erstellen
		setupSplineKeyFrames();
		
		// Animationsinterpolator erstellen
		createInterpolator(listener);
	}
	
	public void createDice(){
		objTransform = new Transform3D();
		objTransform.setTranslation(new Vector3f(-0.75f, tableSemiHeight + diceSemiTexLength + diceEdgeGap,  0.0f));
		objTransformGroup = new TransformGroup(objTransform);
		
		objTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
		objTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
		objTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		
		// Dimensionen festlegen
		float x = diceSemiTexLength;
		float y = diceSemiTexLength;
		float z = diceSemiTexLength;
		float gap = diceEdgeGap;
		
		// Grundfarbe festlegen
		Color3f baseColor = new Color3f(GameFrame.colors[Constants.RED]);
		
		// Alle Grundelemente erstellen
		
		// Unterseite
		TexturedRectangularSide lowerSide = new TexturedRectangularSide(baseColor, "gui/images/d_lower.gif", TexturedRectangularSide.ZX_PLANE, x, -y, z, gap);
				
		// Oberseite
		TexturedRectangularSide upperSide = new TexturedRectangularSide(baseColor, "gui/images/d_upper.jpg", TexturedRectangularSide.ZX_PLANE, x, y, z, gap);
		// Seite links
		TexturedRectangularSide leftSide = new TexturedRectangularSide(baseColor, "gui/images/d_left.gif", TexturedRectangularSide.YZ_PLANE, -x, y, z, gap);
				
		// Seite rechts
		TexturedRectangularSide rightSide = new TexturedRectangularSide(baseColor, "gui/images/d_right.gif", TexturedRectangularSide.YZ_PLANE, x, y, z, gap);
				
		// Seite hinten
		TexturedRectangularSide backSide = new TexturedRectangularSide(baseColor, "gui/images/d_back.gif", TexturedRectangularSide.XY_PLANE, x, y, -z, gap);
				
		// Seite vorne
		TexturedRectangularSide frontSide = new TexturedRectangularSide(baseColor, "gui/images/d_front.gif", TexturedRectangularSide.XY_PLANE, x, y, z, gap);
		
		// Verbindungsfläche Unterseite - Rand links
		ColoredRectangularSide lowerLeftSide = new ColoredRectangularSide(baseColor, lowerSide.getCoordinate(2),lowerSide.getCoordinate(1), leftSide.getCoordinate(3),leftSide.getCoordinate(2) );
		
		// Verbindungsfläche Oberseite - Rand links
		ColoredRectangularSide upperLeftSide = new ColoredRectangularSide(baseColor, upperSide.getCoordinate(2),upperSide.getCoordinate(1), leftSide.getCoordinate(1),leftSide.getCoordinate(0) );

		// Verbindungsfläche Unterseite - Rand vorne
		ColoredRectangularSide lowerFrontSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(1),lowerSide.getCoordinate(0), frontSide.getCoordinate(2),frontSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand vorne
		ColoredRectangularSide upperFrontSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(3),upperSide.getCoordinate(2), frontSide.getCoordinate(0),frontSide.getCoordinate(3) );

		// Verbindungsfläche Unterseite - Rand rechts
		ColoredRectangularSide lowerRightSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(0),lowerSide.getCoordinate(3), rightSide.getCoordinate(1),rightSide.getCoordinate(0) );
		
		// Verbindungsfläche Oberseite - Rand rechts
		ColoredRectangularSide upperRightSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(0),upperSide.getCoordinate(3), rightSide.getCoordinate(3),rightSide.getCoordinate(2) );

		// Verbindungsfläche Unterseite - Rand hinten
		ColoredRectangularSide lowerBackSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(3),lowerSide.getCoordinate(2), backSide.getCoordinate(2),backSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand hinten
		ColoredRectangularSide upperBackSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(1),upperSide.getCoordinate(0), backSide.getCoordinate(0),backSide.getCoordinate(3) );

		// Verbindungsfläche Rand links - Rand vorne
		ColoredRectangularSide leftFrontSide = new ColoredRectangularSide(baseColor,leftSide.getCoordinate(0),leftSide.getCoordinate(3), frontSide.getCoordinate(1),frontSide.getCoordinate(0) );

		// Verbindungsfläche Rand vorne - Rand rechts
		ColoredRectangularSide frontRightSide = new ColoredRectangularSide(baseColor,frontSide.getCoordinate(3),frontSide.getCoordinate(2), rightSide.getCoordinate(0),rightSide.getCoordinate(3) );

		// Verbindungsfläche Rand rechts - Rand hinten
		ColoredRectangularSide rightBackSide = new ColoredRectangularSide(baseColor,rightSide.getCoordinate(2),rightSide.getCoordinate(1), backSide.getCoordinate(1),backSide.getCoordinate(0) );

		// Verbindungsfläche Rand hinten - Rand links
		ColoredRectangularSide backLeftSide = new ColoredRectangularSide(baseColor,backSide.getCoordinate(3),backSide.getCoordinate(2), leftSide.getCoordinate(2),leftSide.getCoordinate(1) );

		// Ecke oben, vorne, links
		ColoredTriangularSide corner1 = new ColoredTriangularSide(baseColor,leftSide.getCoordinate(0),frontSide.getCoordinate(0), upperSide.getCoordinate(2) );

		// Ecke oben, vorne, rechts
		ColoredTriangularSide corner2 = new ColoredTriangularSide(baseColor,frontSide.getCoordinate(3),rightSide.getCoordinate(3), upperSide.getCoordinate(3) );

		// Ecke oben, hinten, rechts
		ColoredTriangularSide corner3 = new ColoredTriangularSide(baseColor,rightSide.getCoordinate(2),backSide.getCoordinate(0), upperSide.getCoordinate(0) );

		// Ecke oben, hinten, links
		ColoredTriangularSide corner4 = new ColoredTriangularSide(baseColor,backSide.getCoordinate(3),leftSide.getCoordinate(1), upperSide.getCoordinate(1) );

		// Ecke unten, vorne, links
		ColoredTriangularSide corner5 = new ColoredTriangularSide(baseColor,frontSide.getCoordinate(1),leftSide.getCoordinate(3), lowerSide.getCoordinate(1) );

		// Ecke unten, vorne, rechts
		ColoredTriangularSide corner6 = new ColoredTriangularSide(baseColor,rightSide.getCoordinate(0),frontSide.getCoordinate(2), lowerSide.getCoordinate(0) );

		// Ecke unten, hinten, rechts
		ColoredTriangularSide corner7 = new ColoredTriangularSide(baseColor,backSide.getCoordinate(1),rightSide.getCoordinate(1), lowerSide.getCoordinate(3) );

		// Ecke unten, hinten, links
		ColoredTriangularSide corner8 = new ColoredTriangularSide(baseColor,leftSide.getCoordinate(2),backSide.getCoordinate(2), lowerSide.getCoordinate(2) );

		// Grundelemente an TransformGroup anhängen
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
		
		// Objekt dem Szenengraphen hinzufügen
		sceneTransformGroup.addChild(objTransformGroup);
	}
	
	private void createTable(){
		tableTransform = new Transform3D();
		tableTransformGroup = new TransformGroup(tableTransform);
		
		tableTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tableTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
		
		// Dimensionen festlegen
		float x = tableSemiWidth  - tableSemiHeight / 5;
		float y = tableSemiHeight - tableSemiHeight / 5;
		float z = tableSemiDepth  - tableSemiHeight / 5;
		float gap = tableSemiHeight/5;
		
		// Grundfarbe festlegen
		Color3f baseColor = tableColor;
		
		// Alle Grundelemente erstellen
		
		// Unterseite
		ColoredRectangularSide lowerSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.ZX_PLANE, x, -y, z, gap);
				
		// Oberseite
		ColoredRectangularSide upperSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.ZX_PLANE, x, y, z, gap);
		// Seite links
		ColoredRectangularSide leftSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.YZ_PLANE, -x, y, z, gap);
				
		// Seite rechts
		ColoredRectangularSide rightSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.YZ_PLANE, x, y, z, gap);
				
		// Seite hinten
		ColoredRectangularSide backSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.XY_PLANE, x, y, -z, gap);
				
		// Seite vorne
		ColoredRectangularSide frontSide = new ColoredRectangularSide(baseColor, ColoredRectangularSide.XY_PLANE, x, y, z, gap);
		
		// Verbindungsfläche Unterseite - Rand links
		ColoredRectangularSide lowerLeftSide = new ColoredRectangularSide(baseColor, lowerSide.getCoordinate(2),lowerSide.getCoordinate(1), leftSide.getCoordinate(3),leftSide.getCoordinate(2) );
		
		// Verbindungsfläche Oberseite - Rand links
		ColoredRectangularSide upperLeftSide = new ColoredRectangularSide(baseColor, upperSide.getCoordinate(2),upperSide.getCoordinate(1), leftSide.getCoordinate(1),leftSide.getCoordinate(0) );

		// Verbindungsfläche Unterseite - Rand vorne
		ColoredRectangularSide lowerFrontSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(1),lowerSide.getCoordinate(0), frontSide.getCoordinate(2),frontSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand vorne
		ColoredRectangularSide upperFrontSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(3),upperSide.getCoordinate(2), frontSide.getCoordinate(0),frontSide.getCoordinate(3) );

		// Verbindungsfläche Unterseite - Rand rechts
		ColoredRectangularSide lowerRightSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(0),lowerSide.getCoordinate(3), rightSide.getCoordinate(1),rightSide.getCoordinate(0) );
		
		// Verbindungsfläche Oberseite - Rand rechts
		ColoredRectangularSide upperRightSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(0),upperSide.getCoordinate(3), rightSide.getCoordinate(3),rightSide.getCoordinate(2) );

		// Verbindungsfläche Unterseite - Rand hinten
		ColoredRectangularSide lowerBackSide = new ColoredRectangularSide(baseColor,lowerSide.getCoordinate(3),lowerSide.getCoordinate(2), backSide.getCoordinate(2),backSide.getCoordinate(1) );
		
		// Verbindungsfläche Oberseite - Rand hinten
		ColoredRectangularSide upperBackSide = new ColoredRectangularSide(baseColor,upperSide.getCoordinate(1),upperSide.getCoordinate(0), backSide.getCoordinate(0),backSide.getCoordinate(3) );

		// Verbindungsfläche Rand links - Rand vorne
		ColoredRectangularSide leftFrontSide = new ColoredRectangularSide(baseColor,leftSide.getCoordinate(0),leftSide.getCoordinate(3), frontSide.getCoordinate(1),frontSide.getCoordinate(0) );

		// Verbindungsfläche Rand vorne - Rand rechts
		ColoredRectangularSide frontRightSide = new ColoredRectangularSide(baseColor,frontSide.getCoordinate(3),frontSide.getCoordinate(2), rightSide.getCoordinate(0),rightSide.getCoordinate(3) );

		// Verbindungsfläche Rand rechts - Rand hinten
		ColoredRectangularSide rightBackSide = new ColoredRectangularSide(baseColor,rightSide.getCoordinate(2),rightSide.getCoordinate(1), backSide.getCoordinate(1),backSide.getCoordinate(0) );

		// Verbindungsfläche Rand hinten - Rand links
		ColoredRectangularSide backLeftSide = new ColoredRectangularSide(baseColor,backSide.getCoordinate(3),backSide.getCoordinate(2), leftSide.getCoordinate(2),leftSide.getCoordinate(1) );

		// Ecke oben, vorne, links
		ColoredTriangularSide corner1 = new ColoredTriangularSide(baseColor,leftSide.getCoordinate(0),frontSide.getCoordinate(0), upperSide.getCoordinate(2) );

		// Ecke oben, vorne, rechts
		ColoredTriangularSide corner2 = new ColoredTriangularSide(baseColor,frontSide.getCoordinate(3),rightSide.getCoordinate(3), upperSide.getCoordinate(3) );

		// Ecke oben, hinten, rechts
		ColoredTriangularSide corner3 = new ColoredTriangularSide(baseColor,rightSide.getCoordinate(2),backSide.getCoordinate(0), upperSide.getCoordinate(0) );

		// Ecke oben, hinten, links
		ColoredTriangularSide corner4 = new ColoredTriangularSide(baseColor,backSide.getCoordinate(3),leftSide.getCoordinate(1), upperSide.getCoordinate(1) );

		// Ecke unten, vorne, links
		ColoredTriangularSide corner5 = new ColoredTriangularSide(baseColor,frontSide.getCoordinate(1),leftSide.getCoordinate(3), lowerSide.getCoordinate(1) );

		// Ecke unten, vorne, rechts
		ColoredTriangularSide corner6 = new ColoredTriangularSide(baseColor,rightSide.getCoordinate(0),frontSide.getCoordinate(2), lowerSide.getCoordinate(0) );

		// Ecke unten, hinten, rechts
		ColoredTriangularSide corner7 = new ColoredTriangularSide(baseColor,backSide.getCoordinate(1),rightSide.getCoordinate(1), lowerSide.getCoordinate(3) );

		// Ecke unten, hinten, links
		ColoredTriangularSide corner8 = new ColoredTriangularSide(baseColor,leftSide.getCoordinate(2),backSide.getCoordinate(2), lowerSide.getCoordinate(2) );

		// Grundelemente an TransformGroup anhängen

		tableTransformGroup.addChild(lowerSide);
		tableTransformGroup.addChild(upperSide);
		tableTransformGroup.addChild(leftSide);
		tableTransformGroup.addChild(rightSide);
		tableTransformGroup.addChild(backSide);
		tableTransformGroup.addChild(frontSide);

		tableTransformGroup.addChild(lowerLeftSide);
		tableTransformGroup.addChild(upperLeftSide);
		tableTransformGroup.addChild(lowerFrontSide);
		tableTransformGroup.addChild(upperFrontSide);
		tableTransformGroup.addChild(lowerRightSide);
		tableTransformGroup.addChild(upperRightSide);
		tableTransformGroup.addChild(lowerBackSide);
		tableTransformGroup.addChild(upperBackSide);
		
		tableTransformGroup.addChild(leftFrontSide);
		tableTransformGroup.addChild(frontRightSide);
		tableTransformGroup.addChild(rightBackSide);
		tableTransformGroup.addChild(backLeftSide);
		
		tableTransformGroup.addChild(corner1);
		tableTransformGroup.addChild(corner2);
		tableTransformGroup.addChild(corner3);
		tableTransformGroup.addChild(corner4);
		tableTransformGroup.addChild(corner5);
		tableTransformGroup.addChild(corner6);
		tableTransformGroup.addChild(corner7);
		tableTransformGroup.addChild(corner8);
		
		// Objekt dem Szenengraphen hinzufügen
		sceneTransformGroup.addChild(tableTransformGroup);
	}
	
	
	public static void main(String[] args) {
		DiceApplet da  = new DiceApplet(null);
		new MainFrame(da, 512, 512);
		new DiceAppletTest(da);

	}
}
