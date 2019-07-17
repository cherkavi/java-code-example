package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.text.SimpleDateFormat;
import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class Format_date extends Format{
	private final static String ATTR_PATTERN="pattern";
	public Format_date(String name, Map<String, String> attr) throws EDbLoaderException{
		super(name, attr);
		sdf=new SimpleDateFormat(attr.get(ATTR_PATTERN));
	}

	private SimpleDateFormat sdf=null;
	
	@Override
	public Object getValue(String externalValue) throws EDbLoaderException {
		if(isValueEmpty(externalValue))return null;
		try{
			return sdf.parse(externalValue);
		}catch(Exception ex){
			throw new EDbLoaderException("parse value ("+externalValue+") throw Exception:"+ex.getMessage());
		}
	}

}
