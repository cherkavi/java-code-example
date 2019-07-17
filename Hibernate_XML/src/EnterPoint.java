import org.apache.log4j.BasicConfigurator;

import gui.FrameMain;


public class EnterPoint {
	
	public static void main(String[] args){
		// configure Log4j
		BasicConfigurator.configure();
		new FrameMain();
	}
}
