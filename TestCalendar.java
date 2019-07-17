import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TestCalendar {

	public static void main(String[] args){
		Date dateBegin;
		Date dateEnd;
		Date timeBegin;
		Date timeEnd;
		SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSSS");
		
		Calendar calendar;
		calendar=Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_MONTH, 10);
		calendar.set(Calendar.MONTH, 01);
		calendar.set(Calendar.YEAR, 2008);
		dateBegin=calendar.getTime();
		
		calendar.set(Calendar.DAY_OF_MONTH, 12);
		calendar.set(Calendar.MONTH, 01);
		calendar.set(Calendar.YEAR, 2008);
		dateEnd=calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 15);
		calendar.set(Calendar.MINUTE, 10);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		timeBegin=calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 40);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		timeEnd=calendar.getTime();
		
		System.out.println("dateBegin:"+sdf.format(dateBegin));
		System.out.println("timeBegin:"+sdf.format(timeBegin));
		
		System.out.println("dateEnd:"+sdf.format(dateEnd));
		System.out.println("timeEnd:"+sdf.format(timeEnd));
		System.out.println("--------");
		
		Date now=new Date();
		System.out.println("check: "+sdf.format(getDateFromDateAndTime(now,timeEnd)));
	}
	
	
	private static Date getDateFromDateAndTime(Date date, Date time){
		Calendar dateValue=Calendar.getInstance();
		Calendar timeValue=Calendar.getInstance();
		dateValue.setTime(date);
		timeValue.setTime(time);
		dateValue.set(Calendar.HOUR_OF_DAY, timeValue.get(Calendar.HOUR_OF_DAY));
		dateValue.set(Calendar.MINUTE, timeValue.get(Calendar.MINUTE));
		dateValue.set(Calendar.SECOND, timeValue.get(Calendar.SECOND));
		dateValue.set(Calendar.MILLISECOND, timeValue.get(Calendar.MILLISECOND));
		return dateValue.getTime();
	}
	
}
