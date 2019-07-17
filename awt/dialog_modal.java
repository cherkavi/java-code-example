import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;
class dialogMain extends Dialog implements java.awt.event.WindowListener,java.awt.event.MouseListener{
	public dialogMain(Frame frame) {
		super(frame,"this is modal dialog",true);
		// TODO Auto-generated constructor stub
		this.setSize(100,100);
		this.addWindowListener(this);
		Button OK_button=new Button("OK");
		OK_button.addMouseListener(this);
		this.add(OK_button);
		this.setVisible(true);
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.dispose();
		System.out.println("close window");
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

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("You click to button OK");
		this.dispose();
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
class frameMain extends Frame implements java.awt.event.WindowListener,java.awt.event.MouseListener,java.awt.event.ActionListener{
	Button my_button;
	Panel panel;
	frameMain(Dimension d){
		super("hello frame");
		panel=new Panel();
		// create button
		my_button=new Button("my_button");
		// add Mouse Listener to button's
		my_button.addMouseListener(this);
		this.add(my_button);
		this.setSize(d);
		this.addWindowListener(this);
		//this.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	frameMain(){
		this(new Dimension(100,200));
	}
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.dispose();
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getComponent().getName());
		if(arg0.getComponent() instanceof Button){
			System.out.println(((Button)(arg0.getComponent())).getLabel());
			if(((Button)(arg0.getComponent())).getLabel().equalsIgnoreCase("my_button")){
				dialogMain dialog1=new dialogMain(this);
			}
		}
		
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getSource());
	}
}
public class main_class extends Applet{
	Frame fmMain;
	public void init(){
		fmMain=new frameMain();
		fmMain.setVisible(true);
	}
	public void destroy(){
		
	}
	public void start(){
		
	}
	public void stop(){
		
	}
}
