/*
 * Created on 09.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;


import com.sun.j3d.utils.image.TextureLoader;


public class NewTextureLoader extends TextureLoader {

	static Component observer;
	
	public static void setImageObserver(Component observer){
		NewTextureLoader.observer = observer;
	}
	
	public static Component getImageObserver(){
		return observer;
	}
	
	public NewTextureLoader(Image image){
		super(image, observer);
	}

	public NewTextureLoader(Image image, int flags){
		super(image, flags, observer);
	}
	
	public NewTextureLoader(Image image, String format){
		super(image, format, observer);
	}
	
	public NewTextureLoader(Image image, String format, int flags){
		super(image, format, flags, observer);
	}
	
	public NewTextureLoader(String fname){
		super(fname, observer);
	}
	
	public NewTextureLoader(String fname, int flags){
		super(fname, flags, observer);
	}
	
	public NewTextureLoader(String fname, String format){
		super(fname, format, observer);
	}
	
	public NewTextureLoader(String fname, String format, int flags){
		super(fname, format, flags, observer);
	}
	
	public NewTextureLoader(URL url){
		super(url, observer);
	}
	
	public NewTextureLoader(URL url, int flags){
		super(url, flags, observer);
	}
	
	public NewTextureLoader(URL url, String format){
		super(url, format, observer);
	}
	
	public NewTextureLoader(URL url, String format, int flags){
		super(url, format, flags, observer);
	}
}
