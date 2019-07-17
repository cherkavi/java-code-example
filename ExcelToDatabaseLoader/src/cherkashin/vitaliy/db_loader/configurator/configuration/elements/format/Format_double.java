package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class Format_double extends Format{
	private static final String ATTR_DELIMETER="delimiter";
	private String delimiter=".";
	
	public Format_double(String name, Map<String, String> attr) {
		super(name, attr);
		String tempValue=attr.get(ATTR_DELIMETER);
		if(tempValue!=null){
			delimiter=tempValue;
		}
	}
	
	@Override
	public Object getValue(String externalValue) throws EDbLoaderException{
		if(isValueEmpty(externalValue))return null;
		String valueForConvert=externalValue.replaceAll("[^0-9^"+this.delimiter+"]", "");
		valueForConvert=externalValue.replaceAll(this.delimiter, ".");
		try{
			return Double.parseDouble(valueForConvert);
		}catch(Exception ex){
			throw new EDbLoaderException("can't convert to Double value: "+externalValue);
		}
	}
	
}
