package gui;

import javax.media.j3d.Appearance;

import com.sun.j3d.utils.geometry.Cone;

/**
 * @author Mario
 */
public class MyCone extends Cone {

	private int color;
	private int id;
	
	
	public MyCone(int color, int id) {
		super();
		this.color = color;
		this.id = id;
	}

	public MyCone(int color, int id, float arg0, float arg1) {
		super(arg0, arg1);
		this.color = color;
		this.id = id;
	}

	public MyCone(int color, int id, float arg0, float arg1, Appearance arg2) {
		super(arg0, arg1, arg2);
		this.color = color;
		this.id = id;
	}

	public MyCone(int color, int id, float arg0, float arg1, int arg2, Appearance arg3) {
		super(arg0, arg1, arg2, arg3);
		this.color = color;
		this.id = id;
	}

	public MyCone(int color, int id, float arg0, float arg1, int arg2, int arg3, int arg4,
			Appearance arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		this.color = color;
		this.id = id;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public int getColor(){
		return color;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}

}
