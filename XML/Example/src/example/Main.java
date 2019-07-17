/*
 * Main.java
 *
 * Created on 29 квітня 2008, 13:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package example;

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
        try{
            String path_to_xml="database_structure.xml";
            java.io.File file=new java.io.File(path_to_xml);
            if(file.exists()){
                //new sax(path_to_xml);
                //new dom(path_to_xml,"c://copy.xml");
                new xpath(path_to_xml);
            }
        }catch(Exception ex){
            System.out.println("File open Exception :"+ex.getMessage());
        }
        
    }
    
}
