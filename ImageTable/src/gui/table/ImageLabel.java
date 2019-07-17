package gui.table;
import java.awt.image.*;
import javax.swing.*;

public class ImageLabel extends JLabel{
	/** */
	private static final long serialVersionUID = 1L;
	private BufferedImage field_image;

	/** обертка для отображения изображения */
	public ImageLabel(BufferedImage image){
		field_image=image;
		this.setIcon(new ImageIcon(field_image));
		//this.setSize(field_image.getWidth(),field_image.getHeight());
	}

	public BufferedImage getBufferedImage(){
		return this.field_image;
	}
	
/*	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.drawImage(this.field_image, 0,0,this);
	}
*/	
}
