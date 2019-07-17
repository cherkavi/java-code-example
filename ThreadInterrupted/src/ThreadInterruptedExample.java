
/*
 * Пример реализации метода Thread.interrupted()
 * Вывод:
 * interrupted бросает исключение InterruptedException если поток Thread.sleep, но флаг isInterrupted = false
 * interrupted изменяет флаг isInterrupted = true
 * Резюме: нельзя использовать проверку isInterrupted - т.к. поток мог заснуть и выйдя из Sleep не получится узнать штатными средствами о проявлении isInterrupted,
 * поэтому следует использовать   
 * 
 */
public class ThreadInterruptedExample {
	public static void main(String[] args){
		System.out.println("begin");
		/*TempThread tempThread=new TempThread();
		tempThread.start();
		try{
			Thread.sleep(2000);
		}catch(Exception ex){};
		tempThread.interrupt();*/
		
		StrongThread strongThread=new StrongThread();
		strongThread.start();
		try{
			Thread.sleep(2000);
		}catch(Exception ex){};
		strongThread.stopThread();
		System.out.println("-end-");
	}
}


class TempThread extends Thread{
	public void run(){
		int counter=0;
		while(true){
			System.out.println(Thread.currentThread().getName()+" : "+(++counter)+"   isInterrrupted:"+this.isInterrupted());
			
			// вариант с использованием счетчика 
			//for(int index=0;index<999999999;index++){}
			
			// вариант с использованием Thread.sleep 
			/*try{
				Thread.sleep(500);
			}catch(Exception ex){
				ex.printStackTrace();
				System.err.println("TempThread sleep Example");
				System.err.println("IsInterrupted: "+this.isInterrupted());
			}*/
		}
	}
}

class StrongThread extends Thread{
	private boolean flagStop=false;
	private int counter=0;
	
	public void stopThread(){
		this.flagStop=true;
	}
	
	public void run(){
		while(flagStop==false){
			System.out.println("Counter: "+(++counter));
			try{
				Thread.sleep(500);
			}catch(Exception ex){};
		}
	}
}
