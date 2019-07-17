package cherkashin.vitaliy.db_loader.configurator;

import java.io.File;

import cherkashin.vitaliy.db_loader.configurator.configuration.Configuration;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

/** Factory Method for get Configuration - root Object*/
public abstract class ConfigurationFactoryMethod {
	/** get {@link Configuration} from file */
	public abstract Configuration getConfiguration(String pathToFile) throws EDbLoaderException;
	
	/** check file for not exists */
	protected boolean fileNotExists(String pathToFile){
		try{
			File file=new File(pathToFile);
			return !file.exists(); 
		}catch(Exception ex){
			return false;
		}
	}

	
}
