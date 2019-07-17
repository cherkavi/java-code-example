import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.*;
import javax.media.jai.*;

import Utility.ImageUtils;

import com.sun.media.jai.codec.*;

public class FrameMain extends JFrame{
	public static void main(String[] args){
		new FrameMain();
	}

	
	public FrameMain(){
		super("JAI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800,600);
		initComponents();
		this.setVisible(true);
	}
	
	private Image field_image;
	
	
	private void initComponents(){
		// create component's
		this.field_image=getImage();
		JLabel label=new JLabel(new ImageIcon(this.field_image));
		// add listener's
		
		// placing component's
		JPanel panel_main=new JPanel(new BorderLayout());
		
		panel_main.add(label,BorderLayout.CENTER);
		this.getContentPane().add(panel_main);
	}
	
	private Image getImage(){
		Image return_value=null;
		try{
			ImageUtils image=new ImageUtils();
			return_value=image.load("D:\\eclipse_workspace\\SwingTableImage\\image_1.jpg");
			return_value=image.thumbnail(100f);
			//return_value=image.border(20, 150);
			//return_value=image.crop(200f);
		}catch(Exception ex){
			System.out.println("getImage ERROR:"+ex.getMessage());
		}
		return return_value; 
	}
	
}
