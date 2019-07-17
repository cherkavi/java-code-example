package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Position {
    public static void setDialogToCenter(JDialog dialog,int offset_left,int offset_top){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setBounds(offset_left, offset_top, 
                        screenSize.width - offset_left*2, 
                        screenSize.height-offset_top*2);
    };
    public static void setDialogToCenterBySize(JDialog dialog,int width, int height){
    	Dimension dimension=new Dimension(width, height);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setBounds((int)((screenSize.getWidth()-dimension.getWidth())/2),
                        (int)((screenSize.getHeight()-dimension.getHeight())/2),
                        (int)dimension.getWidth(),
                        (int)dimension.getHeight());
    }

    public static void setFrameBySize(JFrame frame,int width, int height){
    	Dimension dimension=new Dimension(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int)((screenSize.getWidth()-dimension.getWidth())/2),
                        (int)((screenSize.getHeight()-dimension.getHeight())/2),
                        (int)dimension.getWidth(),
                        (int)dimension.getHeight());
    }
    
}
