package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class Format_integer extends Format{

	public Format_integer(String name, Map<String, String> attr) {
		super(name, attr);
	}

	@Override
	public Object getValue(String externalValue) throws EDbLoaderException {
		try{
			return Integer.parseInt(externalValue.replaceAll("[^0-9]",""));
		}catch(Exception ex){
			throw new EDbLoaderException("convert value to Integer Exception:"+ex.getMessage());
		}
	}

}
