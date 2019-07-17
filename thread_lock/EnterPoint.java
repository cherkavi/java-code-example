package thread_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		Control control=new Control();
		Executor e1=new Executor(control);
		Executor e2=new Executor(control);
		e1.start();
		e2.start();
		System.out.println("end");
	}
}


class Executor extends Thread{
	private Control control;
	public Executor(Control control){
		this.control=control;
	}
	
	public void run(){
		control.methodOne(Thread.currentThread().getName());
	}
}

class Control{
	private Object flag=new Object();
	private Lock lock=new ReentrantLock();
	
	public void methodOne(String value){
		// Lock lock=new ReentrantLock(); // Lock должен быть локальным, а не глобальным
		System.out.println(value+"methodOne>>>");
		lock.lock();// synchronized(flag){
			System.out.println(value+"locked block >>> ");
			try{
				Thread.sleep(5000);
			}catch(Exception ex){};
			System.out.println(value+"locked block <<< ");
		//}
		lock.unlock();
		System.out.println(value+"methodOne<<<");
	}
}
