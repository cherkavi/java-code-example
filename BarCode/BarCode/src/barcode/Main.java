/*
 * Main.java
 *
 * Created on 18 ќкт€брь 2007 г., 17:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package barcode;
import java.io.*;
import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;
import com.idautomation.linear.BarCode;
import com.idautomation.linear.encoder.barCodeEncoder;

/**
 *
 * @author root
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		  // create barcode:
              //Runtime.getRuntime().gc();
              BarCode bc=new BarCode();
	      bc.code="123456789aaa";
	      bc.barType=bc.CODE39;
	      bc.setRotationAngle(90);
	      bc.resolution=600;
	      
              barCodeEncoder bce = new barCodeEncoder(bc, "JPEG", "newfile.jpeg");
	      // To create a GIF instead, use
	      //barCodeEncoder bce = new barCodeEncoder(bc, "GIF", "c:\\newfile.gif");
	      /*GifEncoder ge=new GifEncoder()
	      java.awt.Image image_source=Toolkit.getDefaultToolkit().createImage("c:\\newfile.gif");
	      try{
	    	  File f=new File("c:\\out.gif");
		      OutputStream os=new FileOutputStream(f);
	    	  GifEncoder ge=new GifEncoder(image_source,os);
	    	  ge.setDimensions(1000, 1000);
	    	  ge.setHints(0);
	    	  ge.encode();
	    	  os.flush();
	    	  os.close();
	      }
	      catch(Exception e){
	    	  System.out.println("Error in write to OutputStream");
	      }*/
	      System.out.println("done.");
	      System.exit(0);
    }
    
}
