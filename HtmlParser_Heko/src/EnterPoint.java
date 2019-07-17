
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Node;


public class EnterPoint {
	public static void main(String[] argv){
		System.out.println("begin");
        DOMParser parser = new DOMParser();
        for (int i = 0; i < argv.length; i++) {
            parser.parse(argv[i]);
            print(parser.getDocument(), "");
        }
		System.out.println("end");
	}
}
