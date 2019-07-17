package test.stream;

import java.util.Arrays;

public class ParallelExecution {
	public static void main(String[] args){
		System.out.println("begin:"+Thread.currentThread().getName());
		Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7).stream().parallel().forEach(x->System.out.println(Thread.currentThread().getName()+" : "+x));
		System.out.println("-end-");
	}
}
