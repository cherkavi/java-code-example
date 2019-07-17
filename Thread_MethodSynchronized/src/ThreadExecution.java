
public class ThreadExecution implements Runnable{
	private CommonResource resource;
	private Integer startValue;
	private boolean isMethodOne;
	
	@Override
	public void run() {
		while(true){
			if(this.isMethodOne==true){
				resource.methodOne(++startValue);
			}else{
				resource.methodTwo(++startValue);
			}
			
		}
	}
	
	public ThreadExecution(CommonResource resource,
						   boolean isMethodOne,
						   Integer startValue){
		this.resource=resource;
		this.startValue=startValue;
		this.isMethodOne=isMethodOne;
		(new Thread(this)).start();
	}
}
