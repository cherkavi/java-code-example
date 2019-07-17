package cherkashin.vitaliy.db_loader.configurator.configuration.elements.format;

import java.util.Map;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public class Format_timestamp extends Format_date{

	public Format_timestamp(String name, Map<String, String> attr) throws EDbLoaderException{
		super(name, attr);
	}

}
