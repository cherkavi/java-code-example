import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class TestTimerTask {
	public static void main(String[] args){
		System.out.println("begin");
		Timer timer=new Timer();
		timer.schedule(new Task(), // Object for execute 
					   2000, // before first start delay
					   1000  // time period 
					   );
		System.out.println("-end-");
	}
}


class Task extends TimerTask{
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	@Override
	public void run() {
		System.out.println(this.toString()+"   timer ON: "+sdf.format(new Date()));
	}
	
}