package thread_lifetime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Задача - создать поток от основного потока Level1
 * создать в Level1 другой поток - Level2
 * остановить поток Level1 
 * вызвать Garbagge Collector
 * Вывод 
 */
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		
		System.out.println("создать Level1");
		Level1 level1=new Level1();
		try{
			Thread.sleep(7000);
		}catch(Exception ex){};

		System.out.println("Остановить Level1");
		level1.stopThread();
		try{
			Thread.sleep(7000);
		}catch(Exception ex){};
		
		
		System.out.println(" Level1 Garbage collector ");
		System.gc();
		
		System.out.println("press ENTER to continue");
		try{
			// new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.in.read(); 
		}catch(Exception ex){};
		System.out.println("-end-");
	}
}
