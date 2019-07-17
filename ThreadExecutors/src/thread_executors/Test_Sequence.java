package thread_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Test_Sequence {
	public static void main(String[] args){
		System.out.println("main.begin демонстрация последовательного выполнения потоков из SingleThreadExecutor");
		ExecutorService executorService=Executors.newSingleThreadExecutor(new ThreadFactory(){
			@Override
			public Thread newThread(Runnable r) {
				Thread thread=new Thread(r);
				// если установить setDaemon(true) то при завершении основного потока потоки-демоны будут прерваны 
				// thread.setDaemon(true);
				return thread;
			}
		});
		executorService.execute(new TestSingle(1,10));
		executorService.execute(new TestSingle(2,10));
		executorService.execute(new TestSingle(3,10));
		executorService.shutdown();
		System.out.println("main.end");
	}
}


class TestSingle implements Runnable{
	private int number;
	private int quantity;
	
	public TestSingle(int number,int quantity){
		this.number=number;
		this.quantity=quantity;
	}
	
	public void run(){
		System.out.println("TestThread#"+number+" .begin");
		for(int counter=0;counter<this.quantity;counter++){
			try{
				System.out.println("TestThread#"+number+"  pulse");
				TimeUnit.MILLISECONDS.sleep(300);
			}catch(InterruptedException ex){};
			
		}
		System.out.println("TestThread#"+number+" .end");
	}
}
