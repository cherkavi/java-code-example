package md5_string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Instance {
	public static void main(String[] args) throws NoSuchAlgorithmException{
		System.out.println("begin");
		String source=new String("yfgbcfkfpjqrfvytgbcmvj");
		printBytes(getMd5(source.getBytes()));
		// printBytes2(getMd5(source.getBytes()));
		System.out.println("-end-");
	}

	
	private static void printBytes(byte[] message){
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<message.length;i++) {
			// hexString.append(Integer.toHexString(0xFF & message[i]));
			hexString.append(Integer.toString((message[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println(" md5: "+hexString.toString());
	}

	/*
	private static void printBytes2(byte[] message){
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<message.length;i++) {
			hexString.append(Integer.toHexString(0xFF & message[i]));
			// hexString.append(Integer.toString((message[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println(" md5: "+hexString.toString());
	}
	*/
	/** получить MD5 код на основании заданного значения  */
	private static byte[] getMd5(byte[] source) {
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(source);
			return algorithm.digest();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
			return null;
		}
	}
}
