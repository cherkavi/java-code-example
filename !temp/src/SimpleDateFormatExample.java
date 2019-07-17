import java.text.SimpleDateFormat;


public class SimpleDateFormatExample {
	public static void main(String[] args){
		SimpleDateFormat sqlDateFormat=new SimpleDateFormat("dd.MM.yyyy");
		System.out.println("Date: "+sqlDateFormat.format(new java.util.Date()));
		Integer value=10;
		System.out.println("Value:"+value);
	}
}
