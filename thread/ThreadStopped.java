import java.util.*;

public class TestThreadStop {
	private static ArrayList<TempThread> list=new ArrayList<TempThread>();
	
	public static void main(String[] args){
		init();
		for(int counter=0;counter<1000;counter++){
			if(list.size()>0){
				if(list.get(0)!=null){
					System.out.println("isAlive:"+list.get(0).isAlive());
					System.out.println("isInterrupted:"+list.get(0).isInterrupted());
					System.out.println("Thread:"+list.get(0));
				}else{
					System.out.println("null null null ");
				}
			}else{
				System.out.println("Size is null ");
			}
			try{
				Thread.sleep(1000);
			}catch(Exception ex){};
			if(counter==5){
				list.get(0).start();
			}
			
			if(counter==10){
				System.gc();
			}
		}
	}
	
	private static void init(){
		
		list.add(new TempThread());
	}
}

class TempThread extends Thread{
	public TempThread(){
		this.start();
	}
	
	public void run(){
		try{
			Thread.sleep(200);
		}catch(Exception ex){};
		System.out.println(">>> worked");
	}
}



// поток после своей остановки находится в "живом" состоянии - сборщик мусора его не убирает
// повторный запуск приводит к Exception in thread "main" java.lang.IllegalThreadStateException