package image.example;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;

public class EnterPoint extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		new EnterPoint();
	}
	
	public EnterPoint(){
		super("image example");
		initComponents();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200,100);
		this.setVisible(true);
	}

	private void initComponents(){
		// create component's
		JButton button_jpg=new JButton("JPEG example ");
		JButton button_scale=new JButton("Scale");
		// add listener's
		button_jpg.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_jpg();
			}
		});
		button_scale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_scale();
			}
		});
		// placing component's
		
		this.getContentPane().setLayout(new FlowLayout());
		add(button_jpg);
		add(button_scale);
	}
	
	
	private void on_button_jpg(){
		JOptionPane.showMessageDialog(this, new PanelJPEG("D:\\eclipse_workspace\\Image\\Image\\image_1.jpg"));
	}
	
	private void on_button_scale(){
		JOptionPane.showMessageDialog(this, new PanelJPEG_Scale("D:\\eclipse_workspace\\Image\\Image\\image_1.jpg"));
	}
}
