import java.io.*;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class resource_class{
	private String s;
	resource_class(String s){
		this.s=s;
	}
	public String toString(){
		return this.s;
	}
}
class thread_class implements Runnable{
	private Thread t;
	private resource_class resource;
	private int state;
	thread_class(int state,resource_class resource){
		this.state=state;
		this.t=new Thread(this);
		this.resource=resource;
		this.t.start();
	}
	public void run() {
		System.out.println(this.state+":Input into method Run");
		synchronized(this.resource){
			try{
				if(this.state==1){
					System.out.println("thread "+this.state+" is wait:");
					this.resource.wait();// приостановка потока и ожидание вызова над этим объектом метода notify()
				}
				else{
					System.out.println("thread "+this.state+" is notify:");
					this.resource.notify();// вызов notify над объектом, для возобновления потоков, которые были остановлены методом wait();
					Thread.sleep(1500);
				}
			}
			catch(Exception e){
				System.out.println(" Interrupted wait into Run:"+this.state+"   \n"+e.getMessage());
			}
		}// только после выхода отсюда другие потоки получат возможность получить сообщение Notify() и продолжить выполнение после метода wait()
		System.out.println(this.state+": Exit from method Run");
	}
}
public class main_class extends JApplet implements java.awt.event.ActionListener,java.awt.event.MouseListener{
	JButton button_wait,button_notify;
	thread_class t;
	thread_class t2;
	resource_class resource=new resource_class("resource");
	public void init(){
		JPanel panel=new JPanel();
		button_wait=new JButton("wait");
		button_notify=new JButton("notify");
		Container container=this.getContentPane();
		container.add(panel);
		panel.add(button_wait);
		panel.add(button_notify);
		panel.setLayout(new FlowLayout());
		button_wait.addActionListener(this);
		button_notify.addActionListener(this);
		button_wait.setActionCommand("button_wait");
		button_notify.setActionCommand("button_notify");
	}
	public void destroy(){
		
	}
	public void start(){
	}
	public void stop(){
		
	}
	public void paint(Graphics g){
	}
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("button_wait")){
			this.t=new thread_class(1,this.resource);
		}
		else{
			// если хотим дать возможность продолжить выполнение потоку, который ожидает 
			// вызвать метода resource.notify() в другом потоке
			//this.t2=new thread_class(2,this.resource);
			// вызвать метод resource.notify() в главном потоке
			try{// обязательно ловить ошибки
				synchronized(this.resource){// обязательно в synchronized
					this.resource.notify();
				}
			}
			catch(Exception e){
				System.out.println("Error when this.resource.notify():\n"+e.getMessage());
			}
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		
	}
	public void mouseEntered(MouseEvent arg0) {
		
	}
	public void mouseExited(MouseEvent arg0) {
		
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
}
