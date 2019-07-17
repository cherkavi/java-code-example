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
class frameMain extends Frame implements java.awt.event.WindowListener,java.awt.event.MouseListener,java.awt.event.ActionListener{
	Button my_button;
	Button my_button2;
	Button my_button3;
	Panel panel;
	MenuBar menubar;
	Menu menu;
	MenuItem menuitem_1,menuitem_2,menuitem_3,menuitem_4;
	frameMain(Dimension d){
		super("hello frame");
		panel=new Panel();
		// create button
		my_button=new Button("my_button");
		my_button2=new Button("my_button2");
		my_button3=new Button("my_button3");
		// add Mouse Listener to button's
		my_button.addMouseListener(this);
		my_button2.addMouseListener(this);
		my_button3.addMouseListener(this);
		// add Panel to Frame
		this.add(panel);
		// add button's to Panel
		panel.add(my_button);
		panel.add(my_button2);
		panel.add(my_button3);
		this.setSize(d);
		this.addWindowListener(this);
		//this.setLayout(new FlowLayout(FlowLayout.LEFT));
		// create MenuBar
		menubar=new MenuBar();
		// create Menu
		menu=new Menu("menu main");
		// create MenuItem's
		menuitem_1=new MenuItem("menuitem_1");
		menuitem_2=new MenuItem("menuitem_2");
		menuitem_3=new MenuItem("menuitem_3");
		menuitem_4=new MenuItem("menuitem_4");
		// add Action Listener - this(Frame) for MenuItem
		menuitem_1.addActionListener(this);
		// add menuitem's to menu
		menu.add(menuitem_1);
		menu.add(menuitem_2);
		menu.add(menuitem_3);
		menu.add(menuitem_4);
		// add menu to MenuBar
		menubar.add(menu);
		this.setMenuBar(menubar);
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
