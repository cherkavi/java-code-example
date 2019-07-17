
public class ThreadControl extends Thread{
	private int counter=0;
	private boolean flagRun=false;
	
	public ThreadControl(){
		this.flagRun=true;
		start();
	}
	 
	public void setRun(boolean flagRun){
		this.flagRun=flagRun;
	}
	
	 @Override
	public void run() {
		 try{
			 while(this.flagRun){
				 System.out.println(this.getName()+" : "+(++counter));
				 try{
					 Thread.sleep(500);
				 }catch(Exception ex){};
			 }
		 }finally{
			 System.out.println("ThreadControl#run finally");
		 }
	}
	 
	public void finalize(){
		System.out.println(this.getName()+" : finalize");
	}
}
