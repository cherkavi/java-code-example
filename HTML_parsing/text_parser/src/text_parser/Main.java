/*
 * Main.java
 *
 * Created on 20 Август 2008 г., 19:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package text_parser;
import java.io.IOException;
import java.io.StringReader;
//import javax.swing.text.html.HTMLEditorKit.ParserCallback;
//import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.parser.ParserDelegator;
/**
 *
 * @author USER
 */
public class Main {
            
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("--- begin ---");
        try{
            ParserDelegator parser_delegator=new ParserDelegator();
            Parser parser=new Parser();
            StringReader string_reader=new StringReader("<H1> text <b> is </b> begin </H1>");
            parser_delegator.parse(string_reader,parser,true);
            
        }catch(IOException iox){
            System.out.println("catch IOException:"+iox.getMessage());
        }
        System.out.println("--- end  ---");
    }
    
}
