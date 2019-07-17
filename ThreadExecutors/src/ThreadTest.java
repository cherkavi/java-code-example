import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class ThreadTest {
	
	
	public static void main(String[] args){
		System.out.println("program begin ");
		Callable<Object> call=new Callable<Object>(){
			
			@Override
			public Object call() throws Exception {
				System.out.println("        Callable#call begin:");
				try{
					Thread.sleep(3000);
				}catch(Exception ex){
				}
				System.out.println("        Callable#call end: ");
				return new Integer(1);
			}
		};
		FutureTask<Object> task=new FutureTask<Object>(call);
		System.out.println("        task RUN");
		task.run();
		
		System.out.println("program end");
		try{
			System.out.println("	Result:"+task.get());
		}catch(Exception ex){};
	}
	
	
}
