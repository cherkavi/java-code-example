import java.io.*;


public class DecodeUtf8 {
	public static void main(String[] args){
		System.out.println("BEGIN");
		String value="\\192.168.15.21\\scan\\EXP_DATA\\BonCard1 ARMA_ÐÐ°ÐºÐµÑ2_4.tif";
		//String value="ÐÐ°ÐºÐµÑ.tif";
		
		
		System.out.println("Decode: "+decodeUtf(value));
		System.out.println("Decode: "+decodeUtf("Ð²Ð²Ð²Ð°Ð°Ð°"));
		
	}

	private static String decodeUtf(String value){
		String returnValue="";
		byte[] array=new byte[value.length()];
		for(int counter=0;counter<value.length();counter++){
			array[counter]=(byte)value.charAt(counter);
		}
		try {
			ByteArrayInputStream bais=new ByteArrayInputStream(array);
			InputStreamReader isr=new InputStreamReader(bais,"UTF-8");
			BufferedReader reader=new BufferedReader(isr);
			returnValue=reader.readLine();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(Exception ex){
			System.out.println("Exception: "+ex.getMessage());
		}
		return returnValue;
	}
}
