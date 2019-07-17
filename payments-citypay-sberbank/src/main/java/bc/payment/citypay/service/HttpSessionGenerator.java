package bc.payment.citypay.service;

import java.util.Random;

public class HttpSessionGenerator {
	
	public static String generateHttpSessionId(){
		return Integer.toString(new Random().nextInt());
	}
}
