package thread_executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestCallable {
	public static void main(String[] args){
		ExecutorService executorService=Executors.newCachedThreadPool();
		/** объект, который контроллирует получение результата из Thread(Callable)*/
		Future<String> returnedValue1= executorService.submit(new TestReturned("value1"));
		Future<String> returnedValue2= executorService.submit(new TestReturned("value2"));
		Future<String> returnedValue3= executorService.submit(new TestReturned("value3"));
		
		try{
			System.out.println("Value1: "+returnedValue1.get());
			System.out.println("Value1: "+returnedValue1.get());
			System.out.println("Value2: "+returnedValue2.get());
			System.out.println("Value3: "+returnedValue3.get());
		}catch(Exception ex){
			System.err.println("TestCallable Exception: "+ex.getMessage());
		}
		
		//if(returnedValue3.isDone()){}
	}
}

/** объект, который содержит в методе call возвращаемое значение */
class TestReturned implements Callable<String>{
	private String value=null;
	
	public TestReturned(String value){
		this.value=value;
	}
	
	@Override
	public String call() throws Exception {
		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception ex){}
		return value;
	}
	
}
