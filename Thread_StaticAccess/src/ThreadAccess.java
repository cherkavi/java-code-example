
/** поток, который */
public class ThreadAccess implements Runnable{
	private String treadPrefix;
	
	public ThreadAccess(String threadPrefix){
		this.treadPrefix=threadPrefix;
		Thread thread=new Thread(this);
		thread.start();
	}
	
	@Override
	public void run(){
		while(true){
			//DefaultResource.ActionOperation(this.treadPrefix, 10);
			DefaultResource.ActionMethod(this.treadPrefix, 10);
			try{
				Thread.sleep(300);
			}catch(Exception ex){};
		}
	}
}
