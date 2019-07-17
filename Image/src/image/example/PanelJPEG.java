package image.example;
import javax.swing.*;
import com.sun.image.codec.jpeg.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

/** image JPG example*/
public class PanelJPEG extends JPanel{
	/** */
	private static final long serialVersionUID = 1L;
	private String field_path_to_file;
	
	public PanelJPEG(String path_to_file){
		super();
		this.field_path_to_file=path_to_file;
		this.setLayout(new BorderLayout());
		try{
			
			/*FileInputStream fis=new FileInputStream(path_to_file);
			JPEGImageDecoder decoder=JPEGCodec.createJPEGDecoder(fis);
			BufferedImage image=decoder.decodeAsBufferedImage();
			fis.close();
			*/
			Image image=getToolkit().createImage(path_to_file);
			this.add(new JLabel(new ImageIcon(image)),BorderLayout.CENTER);
			
			
		}catch(Exception ex){
			
		}
	}
	public String getPath(){
		return this.field_path_to_file;
	}
}
