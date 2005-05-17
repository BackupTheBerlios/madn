package gui;

import java.applet.Applet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;

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
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Constants;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Mario
 */
public class DiceApplet extends Applet {

	
	private SimpleUniverse universe;
	
	
	public DiceApplet(){
		
		setLayout(new BorderLayout());
				
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c3d = new Canvas3D(config);
		
		add(c3d, BorderLayout.CENTER);
		
		BranchGroup scene = buildSceneGraph();
		
		this.universe = new SimpleUniverse(c3d);
				
		Transform3D viewTranslation = new Transform3D();
		viewTranslation.setTranslation(new Vector3d(0,0,0.7f));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewTranslation);

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
		pLgt.setPosition(0f,0f,1.5f);
		pLgt.setInfluencingBounds(bounds);
		
		scene.addChild(aLgt);
		scene.addChild(dLgt);
		scene.addChild(pLgt);
		
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
		BranchGroup bgDice = new BranchGroup();
		
		float x = 0.064f;
		float y = 0.064f;
		float z = 0.064f;
		float gap = 0.008f;
		
		Color3f baseColor = new Color3f(GameFrame.colors[Constants.RED]);
		
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

		
		TransformGroup tg = new TransformGroup(new Transform3D());
		
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
				
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(tg);
		tg.addChild(behavior);
		behavior.setSchedulingBounds( new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0));

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
		
		bgDice.addChild(tg);
		bgDice.compile();
		return bgDice;
	}
	
	
	public static void main(String[] args) {
		new MainFrame(new DiceApplet(), 145, 145);
		
//		float x = 1f, y = -1f, z = 1f, gap = 1f;
//		float[][] coordinates = {{x,y,z},{x,y,z},{x,y,z},{x,y,z}};
//		Point3f[] shape = new Point3f[4];
//		int plane = TexturedRectangularSide.ZX_PLANE;			
//		
//		for (int i=0; i<4; i++){
//			for (int j = plane; j < (plane + 3); j++){
//				if (j == plane){
//					coordinates[i][j] = coordinates[i][j] + Math.signum(coordinates[i][j]) * Math.abs(gap);
//				}else if (Math.signum(coordinates[i][j%3]) != 0){
//					if (Math.signum(coordinates[i][plane]) == 1.0f)
//						coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * TexturedRectangularSide.signums[i][1-((j+plane)%2)];
//					else{//Math.signum(coordinates[i][j]) == -1.0f
//						coordinates[i][j%3] = Math.abs(coordinates[i][j%3]) * TexturedRectangularSide.signums[3-i][1-((j+plane)%2)];
//					}
//				}
//			}
//			shape[i] = new Point3f(coordinates[i][0], coordinates[i][1], coordinates[i][2]);
//		}
//		
//		for (int j=0; j<3; j++){
//			for (int i = 0; i < 4; i++){
//				System.out.print(coordinates[i][j] + "\t");
//			}
//			System.out.println("\n");
//		}
	}
}
