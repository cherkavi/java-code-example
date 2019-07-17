import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;


public class TestProperties {
	public static void main(String[] args){
		String pathToFile="c:\\out.properties";
		
		// block for save data
		Properties properties=new Properties();
		properties.setProperty("key1", "value1");
		properties.setProperty("key2", "value2");
		properties.setProperty("key3", "value3");
		properties.setProperty("key4", "value4");
		properties.setProperty("key5", "value5");
		try{
			properties.store(new FileOutputStream(pathToFile), "this is properties example");
		}catch(Exception ex){
			System.out.println("Save Exception: "+ex.getMessage());
		}
		
		// block for load data 
		Properties load=new Properties();
		try{
			load.load(new FileInputStream(pathToFile));
			Set<Object> set=load.keySet();
			for(Object key:set){
				System.out.println("Key:"+key.toString()+"      Value:"+load.getProperty(key.toString()));
			}
		}catch(Exception ex){
			System.out.println("Load Exception: "+ex.getMessage());
		}
	}
	
	
}
