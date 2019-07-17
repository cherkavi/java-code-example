package test.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodExecuteTest {
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	public void printOneString(){
		System.out.println("Object was executed ("+this+"): "+sdf.format(new Date()));
	}
}
