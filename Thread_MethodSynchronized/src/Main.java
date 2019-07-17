
public class Main {
	public static void main(String[] args){
		CommonResource commonResource=new CommonResource();
		new ThreadExecution(commonResource,true, 100);
		new ThreadExecution(commonResource,false, 1);
	}
}
