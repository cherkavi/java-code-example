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
// класс следит за событием Action
class actionlistener implements ActionListener{
	Frame frame;
	actionlistener(Frame f){
		this.frame=f;
	}
	public void actionPerformed(ActionEvent e) {
		Button button;
		if(e.getSource() instanceof Button){
			button=(Button)e.getSource();
			Button temp_button=new Button("close");
			System.out.println("Height:"+button.getHeight()+"  Width:"+button.getWidth());
			Dialog dialog=new Dialog(this.frame);
			dialog.add(temp_button);
			dialog.addWindowStateListener(new windowstate());
			dialog.show();
		}
	}
}
// класс для отслеживания передвижения мыши
class mousemotion implements MouseMotionListener{
	Frame frame;
	mousemotion(Frame f){
		this.frame=f;
	}
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("X:"+arg0.getX()+"   Y:"+arg0.getY());
	}
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
// обработка событий мыши 
class mouselistener implements java.awt.event.MouseListener{

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse click");
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse entered");
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse exited");
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse pressed");
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouse released");
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
	awt_sample(){
		Dimension dimension=new Dimension();
		dimension.height=300;
		dimension.width=400;
		frame=new Frame("my frame");
		panel=new Panel();
		button=new Button("My button");
		frame.setSize(dimension);//frame.setSize(400,300);
		frame.add(panel);
		frame.addWindowListener(new windowstate());
		
		panel.add(button);

		button.addActionListener(new actionlistener(frame));
		button.addMouseMotionListener(new mousemotion(frame));
		button.addMouseListener(new mouselistener());
		button.addMouseWheelListener(new mousewheel());
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
