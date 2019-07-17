package image.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelJPEG_Scale extends JPanel{
	/** */
	private static final long serialVersionUID = 1L;
	private String field_path_to_file;
	private ImageIcon field_icon;
	
	
	public PanelJPEG_Scale(String path_to_file){
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
			//BufferedImage buffered_image=ImageIO.read(new File(path_to_file));
			
			this.field_icon=new ImageIcon(image);
			initComponents();
			
		}catch(Exception ex){
			
		}
	}
	public String getPath(){
		return this.field_path_to_file;
	}

	
	
	private void initComponents(){
		// create components
		JLabel label_icon=new JLabel(this.field_icon);
		JButton button_rescal_min=new JButton("Min");
		JButton button_rescal_max=new JButton("Max");
		JButton button_kernel=new JButton("Kernel");
		JButton button_crop=new JButton("Crop");
		JButton button_render=new JButton("Render");
		// add listener
		button_rescal_min.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_rescale_min();
			}
		});
		button_rescal_max.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_rescale_max();
			}
		});
		button_kernel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_kernel();
			}
		});
		button_crop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_crop();
			}
		});
		button_render.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_render();
			}
		});
		
		// placing components
		JPanel panel_main=new JPanel(new BorderLayout());
		JPanel panel_manager=new JPanel(new FlowLayout());

		panel_manager.add(button_rescal_min);
		panel_manager.add(button_rescal_max);
		panel_manager.add(button_kernel);
		panel_manager.add(button_crop);
		panel_manager.add(button_render);
		
		panel_main.add(label_icon,BorderLayout.CENTER);
		panel_main.add(panel_manager,BorderLayout.SOUTH);
		this.add(panel_main);
	}
	
	private void on_button_render(){
		BufferedImage source=getBufferedImageFromImage(this.field_icon.getImage());
		Graphics gr=source.getGraphics();
		Graphics2D gr2d=(Graphics2D)gr;
		//gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//gr2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gr2d.drawImage(this.field_icon.getImage(), 50,50,200,200,this);
		this.field_icon.setImage(source);
		this.repaint();
	}

	
	private void on_button_crop(){
		Image image=this.field_icon.getImage();
		this.field_icon.setImage(
							     createImage(new FilteredImageSource(image.getSource(),
							    		     new CropImageFilter(100,100,400,400))
							                 )
							     );
		this.repaint();
	}
	
	private void on_button_rescale_min(){
		// first method
		/*
		AffineTransform transform=AffineTransform.getScaleInstance(0.5, 0.5);
		AffineTransformOp op=new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
		this.field_icon.setImage(op.filter(this.getBufferedImageFromImage(this.field_icon.getImage()), null));
		*/
		// second method - small Memory
		//this.field_icon.setImage(this.field_icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		
		// third method
		BufferedImage source=getBufferedImageFromImage(this.field_icon.getImage());
		BufferedImage destination=new BufferedImage(source.getWidth()/2,source.getHeight()/2,BufferedImage.TYPE_INT_RGB);
		destination.getGraphics().drawImage(source, 0, 0, destination.getWidth(), destination.getHeight(), this);
		this.field_icon.setImage(destination);
		this.repaint();
	}

	private void on_button_rescale_max(){
		// first method 
		/*
		AffineTransform transform=AffineTransform.getScaleInstance(2, 2);
		AffineTransformOp op=new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
		this.field_icon.setImage(op.filter(this.getBufferedImageFromImage(this.field_icon.getImage()), null));
		*/
		
		// second method - small Memory 
		//this.field_icon.setImage(this.field_icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
		
		// third method
		BufferedImage source=getBufferedImageFromImage(this.field_icon.getImage());
		BufferedImage destination=new BufferedImage(source.getWidth()*2,source.getHeight()*2,BufferedImage.TYPE_INT_RGB);
		destination.getGraphics().drawImage(source, 0, 0, destination.getWidth(), destination.getHeight(), this);
		this.field_icon.setImage(destination);
		
		this.repaint();
	}
	
	private BufferedImage getBufferedImageFromImage(Image imageIn){
	    BufferedImage bufferedImageOut = new BufferedImage(imageIn.getWidth(null), imageIn.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = bufferedImageOut.getGraphics();
		g.drawImage(imageIn, 0, 0, null);
		return bufferedImageOut;
	}
	
	private void on_button_kernel(){
		BufferedImage image=this.getBufferedImageFromImage(this.field_icon.getImage());
		float[] SHARP = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f,-1.0f, 0.0f, -1.0f, 0.0f };		
		Kernel kernel = new Kernel(3, 3, SHARP);
	    ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,null);
	    BufferedImage out=new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_ARGB);
	    convolveOp.filter(image, out);
	    this.field_icon.setImage(out);
	    this.repaint();
	}
	
	
}
