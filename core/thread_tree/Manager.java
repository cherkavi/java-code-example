package thread_tree;

import java.util.concurrent.TimeUnit;

public class Manager {
	public static void main(String[] args) throws InterruptedException{
		System.out.println("main:begin");
		FirstLevel firstLevel=new FirstLevel();
		firstLevel.start();
		TimeUnit.SECONDS.sleep(2);
		System.out.println("main:-end-");
	}
}
