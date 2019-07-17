import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


public class EnterPoint {
	public static void main(String[] args) throws Exception{
		String pathToFile="c:\\outBinData.properties";
		//storeToFile(pathToFile);
		loadFromFile(pathToFile);
	}
	
	private static void loadFromFile(String pathToFile)  throws IOException{
		Properties properties=new Properties();
		properties.load(new FileReader(pathToFile));
		Enumeration<Object> enumeration=properties.keys();
		while(enumeration.hasMoreElements()){
			Object key=enumeration.nextElement();
			Object value=properties.get(key);
			System.out.println("Key:"+key+"   Value:"+value);
		}
	}
	
	private static void storeToFile(String pathToFile) throws IOException{
		Properties properties=new Properties();
		properties.put(Float.toString(25f), Float.toString(35.9f));
		properties.put(Float.toString(35f), Float.toString(45f));
		properties.put(Float.toString(45.1f), Float.toString(55.5f));
		properties.store(new FileWriter(pathToFile), "");
	}
}
