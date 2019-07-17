package gui.table;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** объект, который отвечает за получение изображения */
class ImageIdentifier{
	private Logger field_logger=Logger.getLogger(this.getClass());
	{
		field_logger.setLevel(Level.DEBUG);
	}
	private String field_path_to_image;
	//private ImageLabel field_label_full;
	private ImageLabel field_label_thumb;
	private AffineTransformOp field_transform=new AffineTransformOp(AffineTransform.getScaleInstance(0.25d, 0.25d), null);
	
	public ImageIdentifier(String path_to_image){
		field_logger.debug("constructor:begin");
		this.field_path_to_image=path_to_image;
		ImageLabel field_label_full=new ImageLabel(this.getImageFromPath(this.field_path_to_image));
		field_label_thumb=new ImageLabel(this.getThumbFromImage(field_label_full.getBufferedImage()));
		field_logger.debug("constructor:end");
	}
	
	private BufferedImage getImageFromPath(String path_to_image){
		try{
			field_logger.debug("getImageFromPath");
			return ImageIO.read(new File(path_to_image));
			//Toolkit.getDefaultToolkit().getImage()
		}catch(IOException ex){
			field_logger.error("Image not loaded:"+ex.getMessage());
			return null;
		}
	}
	
	private BufferedImage getThumbFromImage(BufferedImage full_image){
		return field_transform.filter(full_image, null);
	}
	
	public ImageLabel getLabel(){
		//return this.field_label_full;
		return new ImageLabel(this.getImageFromPath(this.field_path_to_image));
	}
	public ImageLabel getLabelThumb(){
		return this.field_label_thumb;
	}
	
	/* получить путь к изображению */
/*	private String getPathToImage(){
		return this.field_path_to_image;
	}
*/	
}
