
/** объект-наблюдатель, который может брать из указанного объекта его значение */
public class Watcher implements Runnable{
	private boolean flagRun=false;
	private Core core;
	private boolean isInc;
	
	public Watcher(Core core, boolean isInc){
		this.flagRun=true;
		this.core=core;
		this.isInc=isInc;
		(new Thread(this)).start();
	}
	
	public void stop(){
		this.flagRun=false;
	}
	
	public void run(){
		while(flagRun){
			if(isInc){
				core.incValue();
			}else{
				core.decValue();
			}
		}
	}
}
