
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		int value=100;
		for(int counter=0;counter<10;counter++){
			value+=counter;
			new ExtThread("Title:"+counter,value);
			try{
				Thread.sleep(5000);
			}catch(Exception ex){};
		}
		System.out.println("end");
	}
}

class ExtThread extends Thread{
	private int value;
	private boolean flagRun=true;
	private String title;
	
	public ExtThread(String title, int value){
		this.value=value;
		this.title=title;
		this.start();
	}

	public void stopThread(){
		this.flagRun=false;
	}
	
	@Override
	public void run(){
		while(flagRun){
			System.out.println("Title: "+title+"   Value:"+value);
			try{
				Thread.sleep(200);
			}catch(Exception ex){};
		}
	}
}
