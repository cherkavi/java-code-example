import java.io.*;
import java.awt.*;
import java.awt.event.*;

// класс следит за закрытием оконными сообщениями
class windowstate extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		System.out.println("close");
		((Window)(e.getSource())).dispose();
	}
}
class my_canvas extends Canvas{
	public Point point_begin;
	public Point point_end;
	my_canvas(){
		super();
	}
	public void update(Graphics g){
		g.drawLine((int)point_begin.getX(),(int)point_begin.getY(),(int)point_end.getX(),(int)point_end.getY());
	}
}

class mouse_event implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener{
	private boolean drag=false;
	private Point point=new Point();
	private Graphics graphics;
	private my_canvas canvas;
	mouse_event(my_canvas c){
		this.canvas=c;
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
		if(arg0.getButton()==1){
			this.drag=true;
			this.point=arg0.getPoint();
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton()==1){
			this.drag=false;
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//this.graphics.drawLine((int)this.point.getX(), (int)this.point.getY(), arg0.getX(), arg0.getY());
		//this.point.x=arg0.getX();
		//this.point.y=arg0.getY();
		canvas.point_begin=this.point;
		canvas.point_end=new Point(arg0.getX(),arg0.getY());
		canvas.repaint();
		this.point.x=arg0.getX();
		this.point.y=arg0.getY();
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
// класс для получения данных о прокручивании колеса мыши
class mousewheel implements java.awt.event.MouseWheelListener{
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Rotation:"+arg0.getWheelRotation());
	}
}
class awt_sample{
	Frame frame;
	Panel panel;
	Button button;
	mouse_event mouseevent;
	my_canvas canvas;
	awt_sample(){
		Dimension dimension=new Dimension();
		dimension.height=300;
		dimension.width=400;
		frame=new Frame("my frame");
		panel=new Panel();
		canvas=new my_canvas();
		button=new Button("My button");
		frame.setSize(dimension);//frame.setSize(400,300);
		//frame.add(panel);
		frame.addWindowListener(new windowstate());
		mouseevent=new mouse_event(canvas);
		canvas.addMouseListener(mouseevent);
		canvas.addMouseMotionListener(mouseevent);
		frame.add(canvas);
		//panel.addMouseMotionListener(mouseevent);
		//panel.addMouseListener(mouseevent);
	}
	public void setvisible(boolean state){
		this.frame.setVisible(state);
	}

}
public class main_class {
	
	public static void main(String args[]){
		(new awt_sample()).setvisible(true);
		
	}
}
