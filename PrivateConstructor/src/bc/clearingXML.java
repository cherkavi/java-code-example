package bc;

import java.util.*;

public class clearingXML extends allXML{
	private static clearingXML instance=null;
	private static String field_path="D:\\eclipse_workspace\\PrivateConstructor\\src\\xml\\clearing.xml";
	private static Map<String,String> field_map=new HashMap<String,String>();
	
    public static clearingXML getInstance(){
    	if(instance==null){
    		instance=new clearingXML(field_map, field_path);
    	}
    	return instance;
    }

	protected clearingXML(Map<String,String> myFields, String myXMLFile) {
		super(myFields,myXMLFile);
    }
}
