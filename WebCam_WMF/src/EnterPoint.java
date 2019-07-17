import Camera.Utility;

import javax.swing.*;

public class EnterPoint {
	
	    public static void main(String[] args) {
	    	String camera_url="vfw://0";
	    	try{
	    		Utility.initCamera(camera_url)		    		;
		    		JFrame frame=new JFrame();
		    		frame.setSize(640,480);
		    		frame.setVisible(true);
		    		System.out.println("end");
	    	}finally{
		        // IMPORTANT !!!
		        System.exit(0);
	    	}
	    }
}
