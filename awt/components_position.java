import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;
class mouse_event implements java.awt.event.MouseListener,java.awt.event.WindowListener{
	private Frame frame;
	mouse_event(Frame frame){
		this.frame=frame;
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key:"+arg0.getButton()+" Clickcount:"+arg0.getClickCount());
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("window.close");
	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("window.closing");
		this.frame.dispose();
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
public class main_class extends Applet{
	Button my_button;
	Button my_button2;
	Button my_button3;
	Frame f;
	Panel p1;
	Panel p2;
	Panel p3;
	mouse_event mouseevent;
	public void init(){
		f=new Frame();
		p1=new Panel();
		p2=new Panel();
		p3=new Panel();
		mouseevent=new mouse_event(f);
		my_button=new Button("my_button");
		my_button2=new Button("my_button2");
		my_button3=new Button("my_button3");
		my_button.addMouseListener(mouseevent);
		my_button2.addMouseListener(mouseevent);
		my_button3.addMouseListener(mouseevent);
		p1.setLayout(new GridLayout(3,3));
		p2.setLayout(new GridLayout(3,3));
		p3.setLayout(new GridLayout(3,3));

		for(int i=0;i<9;i++){
			p1.add(new Button("one"+i));
		}
		for(int i=0;i<9;i++){
			p2.add(new Button("two"+i));
		}
		for(int i=0;i<9;i++){
			p3.add(new Button("three"+i));
		}
		//p1.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		//p2.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		//p3.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		f.addWindowListener(new mouse_event(f));
		f.setSize(400,300);
		//f.setLayout(new GridLayout(2,3));
		f.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		f.add(p1);
		f.add(p2);
		f.add(p3);
		f.setVisible(true);
		//this.add(my_button,BorderLayout.SOUTH);
		//this.add(my_button2,BorderLayout.EAST);
		//this.add(my_button3,BorderLayout.NORTH);
		//this.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
	}
	public void destroy(){
		
	}
	public void start(){
		
	}
	public void stop(){
		
	}
}
