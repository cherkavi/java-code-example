import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;
public class FrameMain  extends JFrame {
	
	public static void main(String[] args){
		new FrameMain();
	}
	
	public FrameMain(){
		super("Вращение текста на кнопках");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350,250);
		initComponents();
		this.setVisible(true);
	}
	
	private void initComponents(){
		// create component's
/*		JButton button_test=new JButton("Это текст на кнопке"){
			private int field_height=200;
			private int field_width=250;
			public Dimension getSize(){
				return new Dimension(field_width, field_height);
			}
			public int getHeight(){
				return field_height;
			}
			public int getWidth(){
				return field_width;
			}
		};
*/
		JButton button_test=new JButton("это текст на кнопке");
		Font button_font=button_test.getFont();
		// size
		button_test.setFont(button_font.deriveFont(15f));
		// style
		button_test.setFont(button_font.deriveFont((Font.BOLD+Font.ITALIC)));
		// transform
		AffineTransform transform=new AffineTransform();
		transform.quadrantRotate(45,25,-30);
		button_test.setFont(button_font.deriveFont(transform));
		// add listener's
		button_test.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_test_click();
			}
		});
		// placing
		JPanel panel_main=new JPanel(new BorderLayout());
		panel_main.add(button_test,BorderLayout.WEST);
		this.getContentPane().add(panel_main);
	}
	
	/** reaction on striking button */
	private void on_button_test_click(){
		JOptionPane.showMessageDialog(this, "Click");
	}
}
