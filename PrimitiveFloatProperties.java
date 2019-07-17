import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;


public class PrimitiveFloatProperties {
	public static void main(String[] args){
		HashMap<Float,Float> map=getPrimitiveFloatMap("D:\\eclipse_workspace\\TempAstronomy\\weight.txt");
		if(map!=null){
			float[] x=getKeyArray(map);
			float[] y=getValueArray(map);
			for(int counter=0;counter<x.length;counter++){
				System.out.println(x[counter]+"    "+y[counter]);
			}
		}else{
			System.out.println("Error");
		}
	}
	
	private static void printProperties(HashMap<Float, Float> map){
		Iterator<Float> keys=map.keySet().iterator();
		while(keys.hasNext()){
			Float key=keys.next();
			Float value=map.get(key);
			System.out.println("Key:"+key+"    Value:"+value);
		}
	}
	
	private static void putFloatToHashMapFromString(HashMap<Float,Float> map,String currentString){
		try{
			int indexEquals=currentString.indexOf('=');
			if(indexEquals>0){
				String firstString=currentString.substring(0,indexEquals);
				String secondString=currentString.substring(indexEquals+1);
				System.out.println("Main:"+currentString+"    1:"+firstString+"    2:"+secondString);
				Float key=Float.parseFloat(firstString);
				Float value=Float.parseFloat(secondString);
				System.out.println("Key:"+key+"   Value:"+value);
				if((key!=null)&&(value!=null)){
					map.put(key, value);
				}
			}else{
				throw new Exception("= is not found");
			}
		}catch(Exception ex){
			System.out.println("putFloatToPropertiesFromString Exception:"+ex.getMessage());
		}
	}
	
	/** прочитать пары <ключ><значение> */
	private static HashMap<Float,Float> getPrimitiveFloatMap(String pathToFile){
		HashMap<Float,Float> map=new HashMap<Float,Float>();
		try{
			BufferedReader reader=new BufferedReader(new FileReader(pathToFile));
			String currentString=null;
			while((currentString=reader.readLine())!=null){
				putFloatToHashMapFromString(map, currentString);
			}
		}catch(Exception ex){
			map=null;
			System.err.println("Load properties Exception:"+ex.getMessage());
		}
		return map;
	}
	
	/** получить из HashMap{@literal<Float,Float>} два массива */
	private static float[] getKeyArray(HashMap<Float,Float> map){
		Float[] array=map.keySet().toArray(new Float[]{});
		float[] primitive=new float[array.length];
		for(int counter=0;counter<array.length;counter++){
			primitive[counter]=array[counter];
		}
		return primitive;
	}
	
	private static float[] getValueArray(HashMap<Float,Float> map){
		Float[] array=map.keySet().toArray(new Float[]{});
		float[] primitive=new float[array.length];
		for(int counter=0;counter<array.length;counter++){
			primitive[counter]=map.get(array[counter]);
		}
		return primitive;
	}
	
}
