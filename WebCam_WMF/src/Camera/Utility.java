package Camera;

import java.awt.Image;

import javax.media.Buffer;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.Processor;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

public class Utility {
	private static Player player=null;
	
	public static boolean findCameraByURL(String url){
		boolean return_value=false;
        try {
            MediaLocator ml = new MediaLocator(url);
            Processor p = Manager.createProcessor(ml);
            System.out.println("Found camera at: " + url);
            return_value=true;
        } catch (Exception e) {
            System.out.println("Can not find camera at:" + url + ",or problem with JMF install.");
        } 
		return return_value;
	}
	
	public static void initCamera(String url){
		try{
			player=Manager.createRealizedPlayer( new MediaLocator(url));
			Thread.sleep(2500);
		}catch(Throwable ex){
			System.out.println("initCamera Exception:"+ex.getMessage());
		}
	}
	
	public static Image getAwtImage() throws Exception {
        FrameGrabbingControl frameGrabber = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
        Buffer buf = frameGrabber.grabFrame();
        Image img = (new BufferToImage((VideoFormat) buf.getFormat()).createImage(buf));
        
        if (img == null) {
            //throw new Exception("Image Null");
            System.exit(1);
        }
        
        return img;
    }	
}
