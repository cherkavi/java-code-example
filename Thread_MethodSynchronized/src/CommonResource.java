import java.util.concurrent.TimeUnit;


public class CommonResource {
	public CommonResource(){
	}

	public synchronized void methodTwo(Integer value){
		try{
			System.out.println("CommonResource#methodTwo:"+value);
			TimeUnit.MILLISECONDS.sleep(2000);
		}catch(Exception ex){};
	}
	
	public synchronized void methodOne(Integer value){
		try{
			System.out.println("CommonResource#methodOne:"+value);
			TimeUnit.MILLISECONDS.sleep(2000);
		}catch(Exception ex){};
	}
}
