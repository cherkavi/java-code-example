import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTemp {
	/**
	 * @param args
	 */
	public static void main(String[] args){
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy  HH.mm.ss.SSS");
		
		Calendar calendarBegin=Calendar.getInstance();
		Calendar calendarEnd=Calendar.getInstance();
		calendarEnd.add(Calendar.DAY_OF_MONTH, +2);
		
		Date dateBegin=calendarBegin.getTime();
		Date dateEnd=calendarEnd.getTime();
		
		System.out.println("Date begin:"+sdf.format(dateBegin));
		System.out.println("Date end:"+sdf.format(dateEnd));
	}
}
