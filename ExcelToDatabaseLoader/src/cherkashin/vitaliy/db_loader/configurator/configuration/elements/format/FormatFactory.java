package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class FormatFactory {

	/** build format by
	 * @param name - format name 
	 * @param attr - attributes of format
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Format getFormat(String name, HashMap<String, String> attr) throws EDbLoaderException{
		try{
			String type=attr.get("type");
			if(type==null){
				throw new Exception("required attribute <type> does not found in format with name:"+name);
			}
			// get file name
			String fileName=getPackage()+".Format_"+type.toLowerCase();
			// get Class
			Class clazz=Class.forName(fileName);
			// get constructor
			Constructor constructor=clazz.getConstructor(String.class, Map.class);
			return (Format)constructor.newInstance(name, attr);
		}catch(Exception ex){
			throw new EDbLoaderException("Format#getFormat Exception:"+ex.getMessage());
		}
	}
	
	private static String getPackage(){
		String name=FormatFactory.class.getName();
		int lastIndexOfDot=name.lastIndexOf('.');
		return name.substring(0, lastIndexOfDot);
	}
}
