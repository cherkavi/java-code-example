import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/** автоматическое управление курсором мыши */
public class RobotExample {
	  public static void main(String[] args) throws Exception {
		    Robot r = new Robot();
		    r.mouseMove(35, 30);
		    r.mousePress(InputEvent.BUTTON1_MASK);
		    r.mouseRelease(InputEvent.BUTTON1_MASK);
		    Thread.sleep(1250);
		    r.mousePress(InputEvent.BUTTON1_MASK);
		    r.mouseRelease(InputEvent.BUTTON1_MASK);
		    
		    BufferedImage image=r.createScreenCapture(new Rectangle(100,100,300,300));
		    ImageIO.write(image, "BMP", new File("c:\\out.bmp"));
		    System.out.println("Ok");
		  }
}
