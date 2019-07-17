/*
 * Main.java
 *
 * Created on 18 ќкт€брь 2007 г., 15:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package modal;

import java.io.*;
import java.io.FileInputStream;
import java.util.HashMap;
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
	public static void main(String[] args){
		main_frame temp_main_frame=new main_frame();
                temp_main_frame.setVisible(true);
                System.out.println("start test modal window");
		/*try {
			try{
				File f=new File("reports/"+xml_filename);
				FileInputStream fileinputstream=new FileInputStream(f);
				while(fileinputstream.available()>0){
					byte[] temp_byte=new byte[100];
					int length=fileinputstream.read(temp_byte);
					String temp_string=new String(temp_byte,0,length);
					System.out.println(temp_string);
				}
			}
			catch(Exception e){
				System.out.println("Error in open file output stream "+e.getMessage());
			}*/
		System.out.println("end test modal window");
    }
    
}
