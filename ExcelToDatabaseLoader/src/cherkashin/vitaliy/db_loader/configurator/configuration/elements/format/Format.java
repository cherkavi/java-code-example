package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.util.List;
import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** column format  */
public abstract class Format {
	
	protected final static String EMPTY_STRING="";
	private String name;
	
	public Format(String name, Map<String,String> attr){
		this.name=name;
	}
	
	/** get format name */
	public String getName(){
		return name;
	}
	
	/** get Object from text by format  */
	public abstract Object getValue(String externalValue) throws EDbLoaderException;
	
	/** check value for empty */
	protected boolean isValueEmpty(String value){
		return ((value==null)||(value.trim().equals(EMPTY_STRING)));
	}
	
	/**
	 * get format from list
	 * @param list - list of element 
	 * @param name - name of format
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - if format does not found </li>
	 * 	<li><b>value</b> - if format does not found </li>
	 * </ul>
	 */
	public static Format getFormatByName(List<Format> list, String name){
		if((list==null)||(name==null))return null;
		for(Format format:list){
			if(format.getName().equals(name)){
				return format;
			}
		}
		return null;
	}
	
	@Override 
	public String toString(){
		return this.getName();
	}
}
