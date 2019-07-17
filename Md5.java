import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5 {
	public static void main(String[] args){
		String value="hello";
		String value2="hello";
		// печать HashCode для выражения
		printArray(getMD5Hash(value.getBytes()));
		// печать HashCode для выражения
		printArray(getMD5Hash(value2.getBytes()));
	}
	
	protected static byte[] getMD5Hash(byte[] value){
    	MessageDigest m;
    	byte[] returnValue=null;
    	try {
    		m = MessageDigest.getInstance("MD5");
    		//m.reset();
    		m.update(value,0,value.length);
    		returnValue=m.digest();
    		//returnValue = new BigInteger(1,m.digest()).toByteArray();
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
    	return returnValue;
	}
	
	public static void printArray(byte[] array){
		for(int counter=0;counter<array.length;counter++){
			if(counter==0){
				System.out.print(" ");				
			}else{
				System.out.print(", ");
			}

			if(array[counter]<0){
				System.out.print(array[counter]+256);
			}else{
				System.out.print(array[counter]);
			}
		}
		System.out.println();
	}
	
}
