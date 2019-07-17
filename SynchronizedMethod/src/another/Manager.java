
/** 
 * пример использования синхронизированного метода, 
 * вывод - при синхронизировании метода Lock устанавливается на целый объек
 * */
public class Manager {
	public static void main(String[] args){
		System.out.println("main.begin");
		SharedObject object=new SharedObject();
		(new One(object)).start();
		(new Two(object)).start();
		System.out.println("main.end");
	}
}


class One extends Thread{
	private SharedObject object;
	
	public One(SharedObject object){
		this.object=object;
	}
	
	public void run(){
		System.out.println("One.begin");
		try{
			object.printPerSecond(5);
			Thread.yield();
			//Thread.sleep(15000);
			object.printPerSecond(5);
			Thread.yield();
			//Thread.sleep(15000);
			object.printPerSecond(5);
			Thread.yield();
			//Thread.sleep(15000);
		}catch(Exception ex){
			System.err.println("One.run Exception: "+ex.getMessage());
		}
		System.out.println("One.end");
	}
}


class Two extends Thread{
	private SharedObject object;
	
	public Two(SharedObject object){
		this.object=object;
	}
	
	public void run(){
		System.out.println("Two.begin");
		try{
			object.printPerHalfSecond(10);
			Thread.yield();
			//Thread.sleep(10000);
			object.printPerHalfSecond(10);
			Thread.yield();
			//Thread.sleep(10000);
			object.printPerHalfSecond(10);
			Thread.yield();
			//Thread.sleep(10000);
		}catch(Exception ex){
			System.err.println("Two.run Exception: "+ex.getMessage());
		}
		System.out.println("Two.End");
	}
}
