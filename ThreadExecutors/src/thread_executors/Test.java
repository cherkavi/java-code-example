package thread_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
	public static void main(String[] args){
		ExecutorService executorService=Executors.newCachedThreadPool();
		executorService.execute(new TestThread(1));
		System.out.println("main Executor was created ");
		try{
			TimeUnit.SECONDS.sleep(5);
		}catch(InterruptedException ex){};
		System.out.println("ExecutorService.shutdown");
		executorService.shutdown();
		System.out.println("main. end");
	}
}

class TestThread implements Runnable{
	private int number;
	
	public TestThread(int number){
		this.number=number;
	}
	
	@Override
	public void run() {
		System.out.println("TestThread #"+number+" begin");
		try{
			TimeUnit.MILLISECONDS.sleep(750);
		}catch(InterruptedException ex){};
		System.out.println("TestThread #"+number+"  end");
	}
	
}
