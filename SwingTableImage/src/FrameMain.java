import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FrameMain extends JFrame{
	public static void main(String[] args){
		new FrameMain();
	}
	
	private JTable field_table;
	public FrameMain(){
		super("table with image");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		try{
			initComponents();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "InitError");
		}
		
		this.setVisible(true);
	}
	private void initComponents() throws Exception {
		// create components
		JButton button=new JButton("load");
		JTable table=new JTable();
		BufferedImage image=ImageIO.read(new File("D:\\eclipse_workspace\\SwingTableImage\\image_1.jpg"));
		ImageIcon icon=new ImageIcon(image);
		JLabel label=new JLabel(icon){
			int field_width=200;
			int field_height=150;
			@Override
			public int getWidth() {
				// TODO Auto-generated method stub
				return field_width;
			}
			@Override
			public int getHeight() {
				// TODO Auto-generated method stub
				return field_height;
			}
			
			@Override
			public Dimension getSize() {
				// TODO Auto-generated method stub
				return new Dimension(field_width,field_height);
			}
		};
		//JLabel label=new JLabel("hello");
		// add listener's
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_load_click();
			}
		});
		
		// placing
		JPanel panel_main=new JPanel(new BorderLayout());
		panel_main.add(label,BorderLayout.NORTH);
		panel_main.add(table,BorderLayout.CENTER);
		panel_main.add(button, BorderLayout.SOUTH);
		this.getContentPane().add(panel_main);
	}

	
	private void on_button_load_click(){
		JOptionPane.showMessageDialog(this, "click");
	}
}
