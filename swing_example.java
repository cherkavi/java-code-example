import java.io.*;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main_class extends JApplet implements java.awt.event.ActionListener,java.awt.event.MouseListener{
	private static final long serialVersionUID = 1L;
	JButton button_1=new JButton("this is my button");
	JButton button_2=new JButton("this is my button");
	JButton button_3=new JButton("this is my button");
	JPanel panel=new JPanel(new GridLayout(1,1));
	Label label1=new Label("my label");
	ImageIcon icon=new ImageIcon("d:\\snapshot.jpg");
	JLabel jlabel=new JLabel("my label",icon,JLabel.LEFT);
	JScrollPane scrollpanel=new JScrollPane(panel);
	public void init(){
		Container container=this.getContentPane();
		button_1.setActionCommand("button1 is active");
		button_1.addActionListener(this);
		button_2.setActionCommand("button2 is active");
		button_2.addActionListener(this);
		button_3.setActionCommand("button3 is active");
		button_3.addActionListener(this);
		container.setLayout(new GridLayout(2,2));
		container.add(scrollpanel);
		panel.setLayout(new GridLayout(2,1));
		panel.add(button_1);
		panel.add(label1);
		container.add(button_2);
		container.add(button_3);
		container.add(jlabel);
		jlabel.addMouseListener(this);
		repaint();
	}
	public void destroy(){
		
	}
	public void start(){
		repaint();
	}
	public void stop(){
		
	}
	public void paint(Graphics g){
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() instanceof JButton){
			System.out.println(((JButton)(arg0.getSource())).getActionCommand());
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("you pressed into image");
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
