import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;


public class PropertiesExample {
	public static void main(String[] args){
		float[] x=new float[]{10f,20f,30f};
		float[] y=new float[]{5f,7f,15f};
		Properties properties=new Properties();
		for(int counter=0;counter<x.length;counter++){
			properties.put(Float.toString(x[counter]), Float.toString(y[counter]));
		}
		try{
			properties.store(new FileWriter("c:\\temp.out"), "array");
			System.out.println("Store OK");
		}catch(Exception ex){
			System.out.println("Store Error:"+ex.getMessage());
		}
		
		try{
			properties.load(new FileReader("c:\\temp.out"));
			System.out.println("Load OK");
		}catch(Exception ex){
			System.out.println("Load Error:"+ex.getMessage());
		}
	}
}
