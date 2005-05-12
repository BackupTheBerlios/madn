/*
 * Created on 11.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.awt.Image;
import java.awt.Toolkit;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;


/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Toolbox {

	/**
	 * Laden eines Info-Icons(24*24).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadInfo24Icon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","info24.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Stop-Icons(24*24).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadStop24Icon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","stop24.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Tip-Icons(24*24).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadTip24Icon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","tip24.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Up-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadUpIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","up.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Down-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadDownIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","down.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines StepForward-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadStepForwardIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","step_forward.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	

	/**
	 * Laden eines Rules-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadRulesIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","rules.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Info-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadInfoIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","info.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Refresh-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadRefreshIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","refresh.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Exit-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadExitIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","exit.jpg", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Pawn-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadPawnIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","pawn.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Icons.
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadImageIcon(String fname, Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images",fname, clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Dice-Icons (16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadDiceIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","dice.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Den InputStream einer Datei ermitteln. 
	 * @param pkgname = Der Name vom Package, in dme sich eine Datei befindet. 
	 * @param fname = Der Name der Datei.
	 * @param clazz = Class einer beliebigen Componente.
	 * @return InputStream das ermittelt wurde. 
	 */	
	static public InputStream getResourceStream (String pkgname, String fname, Class clazz){
		String resname = "/" + pkgname.replace('.','/')+ "/" + fname;
		InputStream is = clazz.getResourceAsStream(resname);
		return is;
	}
	
	/**
	 * Das angegebene Image laden. 
	 * @param pkgname = Der Name vom Package, in dme sich eine Datei befindet. 
	 * @param fname = Der Name der Datei.
	 * @param clazz = Class einer beliebigen Componente.
	 * @return Image das geladen wurde. 
	 * @throws IOException
	 */
	static public Image loadImageResource(String pkgname, String fname, Class clazz) throws IOException{
		Image ret = null;
		InputStream is = getResourceStream(pkgname, fname, clazz);

		if (is != null)	{
			byte[] buffer = new byte[0];
			byte[] tmpbuf = new byte[1024];
			
			while (true){
				int len = is.read(tmpbuf);
				if (len<=0){
					break;
				}
				byte[] newbuf = new byte[buffer.length + len];
				System.arraycopy(buffer, 0, newbuf, 0, buffer.length);
				System.arraycopy(tmpbuf, 0, newbuf, buffer.length, len);
				buffer = newbuf;
			}
			// create image
			ret = Toolkit.getDefaultToolkit().createImage(buffer);
			is.close();
		}
		return ret;
	}
	
	static public String colorToString (int color){
		String s = "";
		
		switch (color){
			case 0: s = "ROT";	 	break;  
			case 1: s = "SCHWARZ"; 	break;
			case 2: s = "BLAU"; 	break;
			case 3: s = "GRÜN"; 	break;
		}
		
		return s;
	}
}
