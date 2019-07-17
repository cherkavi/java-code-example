/*
 * Main.java
 *
 * Created on 21 травня 2008, 10:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package file_wrap;

/**
 *
 * @author Technik
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String path="C:\\tempo.html";
        file_utility file=new file_utility(path);
        System.out.println("directory:"+file.get_directory());
        System.out.println("filename:"+file.get_filename());
        System.out.println("file ext:"+file.get_extension());
        System.out.println("file name only:"+file.get_filename_without_ext());
        System.out.println("NEXT File:"+file.get_next_filename(path,"_"));
    }
    
}
