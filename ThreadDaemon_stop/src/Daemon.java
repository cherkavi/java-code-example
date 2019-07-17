
public class Daemon implements Runnable{
	private Thread field_thread;
	private boolean field_flag_run=true;
	private String field_name;
	private int field_delay;
	

	public Daemon(String name, int delay){
		this.field_name=name;
		this.field_delay=delay;
		this.field_thread=new Thread(this);
		this.field_thread.setDaemon(true);
		this.field_thread.start();
	}

	public void stop(){
		this.field_flag_run=false;
	}
	
	@Override
	public void run() {
		int counter=0;
		while(this.field_flag_run){
			try{
				Thread.sleep(this.field_delay);
			}catch(InterruptedException ex){
				
			}
			counter++;
			System.out.println(this.field_name+"   counter:"+counter);
		}
	}
}
