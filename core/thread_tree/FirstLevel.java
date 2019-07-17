package thread_tree;

public class FirstLevel extends Thread{
	
	@Override
	public void run() {
		System.out.println(this.getName()+":begin");
		// start Daemon
		ThreadCounter t1=new ThreadCounter();
		t1.setName("FirstLevel.Daemon ");
		t1.setDaemon(true);
		t1.start();
		// start Regular
		ThreadCounter t2=new ThreadCounter();
		t2.setName("FirstLevel.Regular ");
		t2.start();
		
		System.out.println(this.getName()+":end");
	}
}
