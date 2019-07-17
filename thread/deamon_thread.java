import java.io.*;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class class_deamon extends Thread{
	private int counter=0;
	class_deamon(){
		
	}
	public void run(){
		while(true){
			try{
				System.out.println("deamon:"+this.counter++);
				sleep(500);
			}
			catch(InterruptedException e){
				System.out.println("Deamon is break");
			}
		}
	}
}
class class_runnable implements Runnable{
	private Thread thread=null;
	private String id;
	private int counter=0;
	class_runnable(String s){
		this.id=s;
		this.thread=new Thread(this);
		this.thread.setPriority(Thread.NORM_PRIORITY);
		this.thread.start();
	}
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try{
				System.out.println(this.id+":"+counter++);
				Thread.sleep(750);
			}
			catch(InterruptedException e){
				System.out.println("Error into Thread "+this.id);
			}
		}
	}
}
public class main_class extends JApplet implements java.awt.event.ActionListener,java.awt.event.MouseListener{
	private static final long serialVersionUID = 1L;
	private class_deamon deamon;
	private int thread_counter=0;
	public void init(){
		deamon=new class_deamon();
		deamon.setDaemon(true);
		deamon.setPriority(Thread.MIN_PRIORITY);
		deamon.start();
	}
	public void destroy(){
		
	}
	public void start(){
		Container this_container=this.getContentPane();
		JButton button=new JButton("my_button1");
		button.setActionCommand("This is my button");
		button.addMouseListener(this);
		this_container.add(button);
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
		System.out.println("run new other Thread");
		new class_runnable("Thread >"+(++this.thread_counter)+"<");
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
