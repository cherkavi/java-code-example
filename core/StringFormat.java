import java.text.SimpleDateFormat;


public class StringFormat {
	public static void main(String[] args){
		System.out.println("begin");
		System.out.println(getValue());
		System.out.println("-end-");
	}
	
	private static String getValue(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return(String.format("String: (%s)   Integer:%d", sdf.format(new java.util.Date()),25));
	}
	
	
}
