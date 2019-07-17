import junit.framework.Assert;

import org.junit.Test;


public class Temp {
	public static void main(String[] args){
		System.out.println("begin");
		System.out.println("-end-");
	}
	
	@Test
	public void test(){
		Assert.assertEquals(true, 2==2);
		System.out.println("This is the test");
	}
}
