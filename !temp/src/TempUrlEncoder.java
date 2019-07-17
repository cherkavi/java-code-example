import java.net.URLEncoder;


public class TempUrlEncoder {
	public static void main(String[] args){
		String value="Первый";
		try{
			System.out.println(value+"  :  "+URLEncoder.encode(value));
			System.out.println(value+"  :  "+URLEncoder.encode(value,"ISO-8859-5"));
			System.out.println(value+"  :  "+URLEncoder.encode(value,"WINDOWS-1251"));
		}catch(Exception ex){
			System.out.println();
		}
		
	}
}
