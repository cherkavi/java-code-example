package bc;

import java.util.*;

public class BanksXML extends allXML{
	private static String field_path="D:\\eclipse_workspace\\PrivateConstructor\\src\\xml\\banks.xml";
	private static Map<String,String> field_map=new HashMap<String,String>();
	private static BanksXML instance=null;
    
	public static BanksXML getInstance(){
    	if(instance==null){
    		instance=new BanksXML(field_map, field_path);
    	}
    	return instance;
    }

	protected BanksXML(Map<String,String> myFields, String myXMLFile) {
		super(myFields,myXMLFile);
    }
    
    
    
}
