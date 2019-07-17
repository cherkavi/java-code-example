/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package copyfiles;
import java.io.*;
/**
 *
 * @author cherkashinv
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        copyFile("c:\\test.xml","c:\\2.xml");
    }

    private static boolean copyFile(String source, String destination){
        boolean return_value=false;
        try{
            File file_source = new File(source);
            File file_destination = new File(destination);
            InputStream in = new FileInputStream(file_source);

          //For Append the file.
    //      OutputStream out = new FileOutputStream(f2,true);
          //For Overwrite the file.
          OutputStream out = new FileOutputStream(file_destination);

            // buffer size
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return_value=true;
        }catch(FileNotFoundException ex){
          System.out.println("File ("+source+") not found: "+ex.getMessage());
        }catch(IOException e){
          System.out.println("IOException:"+e.getMessage());      
        }
        return return_value;
    }


}
