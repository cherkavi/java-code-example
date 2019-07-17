package thread_local;

import java.util.concurrent.TimeUnit;

public class ThreadLocalExample extends Thread{
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("begin");
		KeyValue key=new KeyValue();
		ThreadLocal<KeyValue> local=new ThreadLocal<KeyValue>();
		local.set(key);
		new ThreadLocalExample(local).start();
		new ThreadLocalExample(local).start();
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Key:"+key);
		System.out.println("-end-");
	}
	
	private ThreadLocal<KeyValue> local;
	public ThreadLocalExample(ThreadLocal<KeyValue> local) {
		this.local=local;
	}
	
	@Override
	public void run() {
		this.local.set(new KeyValue());
		System.out.println(Thread.currentThread().getName()+" : "+this.local.get());
	}
}
