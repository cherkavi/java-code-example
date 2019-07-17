package com.cherkashyn.vitalii.settings.connector;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;

/**
 * read JDBC connector from different types of file:
 * <ul>
 * 	<li>XML</li>
 * 	<li>Properties</li>
 * </ul>
 * 
 * 
 */
public class App {

	public static void main(String[] args) throws ConfigurationException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		File configFile=parseFile(args);
		if(configFile==null){
			System.err.println("can't read file from input arguments ( or file doesn't exists )");
			System.exit(1);
		}

		JdbcConfiguration.Data data=JdbcConfiguration.readData(configFile);
		System.out.println(data);
		
	}

	/**
	 * read file from first argument of command line 
	 * @param args
	 * @return
	 */
	private static File parseFile(String[] args) {
		if(args.length==0){
			return null;
		}
		
		File file=new File(args[0]);
		if(!file.exists()){
			return null;
		}
		
		return file;
	}

}
