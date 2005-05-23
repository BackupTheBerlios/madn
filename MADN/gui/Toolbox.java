/*
 * Created on 11.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gui;

import java.awt.Image;
import java.awt.Toolkit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.swing.ImageIcon;


/**
 * @author Mario
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Toolbox {

	/**
	 * Laden eines FH-Logo.
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadLogoIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","fhlogo.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
	/**
	 * Laden eines Close-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadCloseIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","close.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Zurueck-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadZurueckIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","zurueck.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Connector-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadConnectorIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","connector.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Add-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadAddIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","add.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines Open-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadOpenIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","Open.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}

	/**
	 * Laden eines New-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadNewIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","New.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}
	
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
	 * Laden eines Start-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadStartIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","start.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}		
	
	/**
	 * Laden eines Quit-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadQuitIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","quit.gif", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}	
	
	/**
	 * Laden eines Stop-Icons(16*16).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadStopIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","stop.gif", clazz);
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
	 * Laden eines Tip-Icons(12*12).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadPlayerIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","player.jpg", clazz);
		  if (img != null)
			  return  new ImageIcon(img);
	  }catch (IOException e){};
		return null;
	}	
	
	/**
	 * Laden eines Tip-Icons(12*12).
	 * @param clazz = Class von einer Componente.
	 * @return Das geladene ImageIcon.
	 */
	static public ImageIcon loadTipIcon(Class clazz){
		try{
		  Image img = Toolbox.loadImageResource("gui.images","tip.gif", clazz);
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

	/**
	 * Erstellen von allen nicht existierenden Verzeichnissen<br>
	 * vom aktuellen Verzeichnis bis zum angegebenem Verzeichnis.<br>
	 * author w.flat
	 * @param dir = Verzeichnis, bis einschließlich welchem man alle Verzeichnisse erstellen soll.
	 */
	static public void makeDirs(File dir) {
		if(dir.getAbsolutePath().equals((new File("")).getAbsolutePath()) || dir.getAbsolutePath().equals(""))
			return;
		makeDirs(dir.getParentFile());
		if(!dir.exists())
			dir.mkdir();
	}
	
	/**
	 * Methode zum Auslagern einer beliebigen Datei aus der Jar-Datei <br>
	 * in die gleiche Datei ausserhalb der Jar-Datei. <br>
	 * Wenn die Datei existiert wird sie nicht überschrieben.<br>
	 * author w.flat
	 * @param pkgname = Der Name vom Package, in dem sich eine Datei befindet. 
	 * @param fname = Der Name der Datei.
	 * @param clazz = Class einer beliebigen Componente.
	 * @throws IOException
	 */
	static public void restoreFile(String pkgname, String fname, Class clazz) throws IOException {
		File outFile = new File(pkgname.replace('.', File.separatorChar) + File.separator + fname);
		makeDirs(outFile.getAbsoluteFile().getParentFile());
		if(!outFile.exists()) {
			InputStream is = getResourceStream(pkgname, fname, clazz);
			FileOutputStream out = new FileOutputStream(outFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
			  out.write(buf, 0, len);
			}
			out.close();
			is.close();        		
		}
	}
	
	final public static int CODE = 1;
	final public static int DECODE = 2;
	final private static int DIVISOR = 123456;
	final private static String KEY = "OO2-DBA-Projekt";

	/**
	 * XOR-Verschlüsselung oder -Entschlüsselung eines übergebenen Textes.
	 * @param text = Text der verschlüsselt oder entschlüsselt werden soll.
	 * @param cORd = Flag zum Entscheiden ob verschlüsselt oder entschlüsselt werden soll. <br>
	 *               Hat Auswirkungen auf ob man die Prüfsumme überprüfen oder hinzufügen muss.
	 * @return Der entschlüsselte oder verschlüsselte Text.
	 */
	static public String xorText(String text, int cORd) {
		String result = text;
		StringBuffer buf = new StringBuffer();
		int summe = 0;
		try {
			if(cORd == CODE) {			// Wenn der String mit XOR kodieren werden soll
			    for(int i = 0; i < text.length(); i++)
			        summe += (int)text.charAt(i);
			    result = "" + (summe % DIVISOR) + "\n" + result;	// Prüfsumme vorne anhängen
			}
			for(int i = 0; i < result.length(); i++)				// XOR-Verfahren durchführen
			    buf.append((char)(result.charAt(i) ^ KEY.charAt(i % KEY.length())));
			if(cORd == DECODE) {		// Wenn der String mit XOR dekodiert werden soll
			    result = buf.substring(buf.indexOf("\n") + 1);
			    summe = 0;
			    for(int i = 0; i < result.length(); i++)
			        summe += (int)result.charAt(i);
			    if((summe % DIVISOR) != Integer.parseInt(buf.substring(0, buf.indexOf("\n"))))
			        throw new Exception("Keine Übereinstimmung bei der Prüfsumme !");
			} else {
			    result = buf.toString();
			}
		} catch(Exception exc) {
		    result = "";
		}
		return result;
	}

	/**
	 * Einlesen einer Datei und Ausgabe als String.
	 * @param fileName = Datei, die eingelesen werden soll.
	 * @return Der Datei-Inhlat als String.
	 */
	static public String readFile(String fileName) {
		StringBuffer result = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(fileName)));
			int ch;
			while((ch = in.read()) != -1) {
				result.append((char)ch);
			}
			in.close();
		} catch(Exception e) {
		}
		return result.toString();
	}

	/**
	 * Einen Text in die Datei reinschreiben.
	 * @param fileName = Datei, die eingelesen werden soll.
	 * @param text = Der neue Inhalt der Datei.
	 */
	static public void writeFile(String fileName, String text) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(fileName)));
			out.write(text);
			out.close();
		} catch(Exception e) {
		}
	}
	
	  /**
	   * This is the method which converts the any string value to MD5 format.
	   * @param str password
	   * @return encrypted password in MD5
	   */
	public String encryptMD5(String str) {
		StringBuffer retString = new StringBuffer();
		try {
			MessageDigest alg = MessageDigest.getInstance("MD5", "SUN");
			String myVar = str;
			byte bs[] = myVar.getBytes();
			byte digest[] = alg.digest(bs);
			
			for (int i = 0; i < digest.length; ++i)
				retString.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
		} catch (Exception e) {
			System.out.println("there appears to have been an error " + e);
		}
		return retString.toString();
	}

}
