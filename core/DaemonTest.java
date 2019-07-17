import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class DaemonTest extends Thread{
	public static void main(String[] args) throws InterruptedException {
		System.out.println("begin");
		Object sharedObject=new Object();
		DaemonTest daemon=new DaemonTest(sharedObject);
		daemon.setName("daemon");
		daemon.setDaemon(false);
		daemon.start();
	
		Map<Thread, StackTraceElement[]> map=Thread.getAllStackTraces();
		Iterator<Thread> iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			Thread thread=iterator.next();
			if(thread.getName().equals("daemon")){
				if(Thread.State.WAITING== thread.getState()){
					System.out.println("interrupt the daemon");
					thread.interrupt();
				}
			}
		}
		
		System.out.println("-end-");
	}
	
	
	private Object sharedObject=null;
	
	public DaemonTest(Object value){
		this.sharedObject=value;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("try to wait");
			synchronized (sharedObject) {
				this.sharedObject.wait();
			}
		} catch (InterruptedException ex) {
			System.out.println("Exception in Thread:"+ex.getMessage());
		}
	}
}
