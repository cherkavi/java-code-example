import java.net.URLEncoder;


public class TempUrlEncoder {
	public static void main(String[] args){
		String value="Ð²Ð²Ð²Ð°Ð°Ð°";
		try{
			System.out.println(value+"  :  "+URLEncoder.encode(value));
			System.out.println(value+"  :  "+URLEncoder.encode(value,"ISO-8859-5"));
			System.out.println(value+"  :  "+URLEncoder.encode(value,"WINDOWS-1251"));
		}catch(Exception ex){
			System.out.println();
		}
		
	}
}
