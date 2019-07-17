/*
 */

package text_parser;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.*;
/**
 * класс, который служит для разбора текста HTML
 */
public class Parser extends ParserCallback{
    
    /** Creates a new instance of Parser */
    public Parser() {
    }

    public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
        System.out.println("--- Start tag:"+t.toString()+" on position:"+pos);
    }

    public void handleEndTag(Tag t, int pos) {
        System.out.println("--- END tag:"+t.toString()+" on position:"+pos);
    }


}
