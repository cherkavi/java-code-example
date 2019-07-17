package com.cherkashyn.vitalii.tools.externalexecution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class ExternalScriptExecutor {

	/**
	 * pair of:
	 * <ul>
	 * 	<li><b>command name</b> - name of command </li>
	 * 	<li><b>OS command </b> - real command for execute on Operation System </li>
	 * </ul>
	 */
	private final Map<String, String> commands;
	
	public ExternalScriptExecutor(final String pathToProperties){
		if(StringUtils.trim(pathToProperties)==null){
			throw new IllegalArgumentException("path to file with commands is empty ");
		}
		
		File propertiesFile=new File(StringUtils.trim(pathToProperties));
		if(!propertiesFile.exists()){
			throw new IllegalArgumentException("can't read file by path: "+pathToProperties);
		}
		
		commands=convertTo(propertiesFile);
	}
	
	
	public void executeCommand(String commandName) throws IOException, InterruptedException{
		if(!commands.containsKey(commandName)){
			return;
		}
		String executeLine=commands.get(commandName);
		
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(executeLine);
		pr.waitFor();
		pr.destroy();
	}
	
	
	/**
	 * convert from Properties to Map
	 * @param propertiesFile
	 * @return
	 */
	private static Map<String, String> convertTo(File propertiesFile){
		Map<String, String> returnValue=new HashMap<String, String>();
		for(Entry<Object, Object> eachEntry: load(propertiesFile).entrySet()){
			String key=StringUtils.trimToNull((eachEntry.getKey()==null)? null: eachEntry.getKey().toString());
			String value=StringUtils.trimToNull((eachEntry.getValue()==null)? null: eachEntry.getValue().toString());
			if(key==null || value==null){
				continue;
			}
			returnValue.put(key, value);
		}
		return returnValue;
	}

	
	/**
	 * load data from file 
	 * @param file
	 * @return
	 */
	private static Properties load(File file){
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(file);
			Properties returnValue=new Properties();
			returnValue.load(fis);
			return returnValue;
		}catch(IOException ex){
			throw new IllegalArgumentException("can't read file:"+file.getAbsolutePath() );
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	
}
