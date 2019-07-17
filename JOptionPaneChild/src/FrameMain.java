import javax.swing.*;
import java.awt.event.*;

public class FrameMain extends JFrame{
	public static void main(String[] args){
		new FrameMain();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameMain(){
		super("Show message");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton button_show=new JButton("show");
		button_show.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_show_click();
			}
		});
		this.getContentPane().add(button_show);
		this.setSize(300,200);
		this.setVisible(true);
	}
	
	private void on_button_show_click(){
		JOptionPane.showMessageDialog(this, new UserMessage("hello"));
	}
}
