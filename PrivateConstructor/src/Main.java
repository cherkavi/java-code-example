import bc.*;


import java.util.*;

public class Main {
	
	private static void getTranslate(allXML xml,String language, String section){
		System.out.println(xml.getfieldTransl(language, section));
	}
	
	public static void main(String[] args){
/*		BanksXML.getInstance(new HashMap<String,String>(),
							 "D:\\eclipse_workspace\\PrivateConstructor\\src\\xml\\banks.xml"
							 );
		clearingXML.getInstance(new HashMap<String,String>(), 
								"D:\\eclipse_workspace\\PrivateConstructor\\src\\xml\\clearing.xml");
*/		
		System.out.println(BanksXML.getInstance().getfieldTransl("RU", "general"));
		System.out.println(clearingXML.getInstance().getfieldTransl("RU", "general"));
		
		System.out.println("=================");
		
		getTranslate(BanksXML.getInstance(),"RU","general");
		getTranslate(clearingXML.getInstance(),"RU","general");
	}
}
