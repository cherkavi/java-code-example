package find_in_map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FindInMapByName {
	public static void main(String[] args){
		// get from map list of elements with difference in last two digits
		System.out.println("begin");
		Map<String, Object> map=new HashMap<String,Object>();
		fillTheMap(map);
		Map<String, Object> submap=getMapByPreambula(map, "column_");
		System.out.println("Submap Size:"+submap.size()+"   Submap value:"+submap);
		System.out.println("MaxValue="+getMaxValueFromKeys(submap));
		System.out.println(" end ");
	}
	
	private static void fillTheMap(Map<String, Object> map){
		map.put("column_4", true);
		map.put("column_3", 5);
		map.put("column_2", 2.55);
		map.put("column_1", "TEMP");
		map.put("column_0", "another value");
		map.put("column_header_2", true);
	}
	
	private static Map<String, Object> getMapByPreambula(Map<String, Object> map, String header){
		HashMap<String, Object> returnValue=new HashMap<String, Object>();
		Iterator<String> iterator=map.keySet().iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			if(isValidKey(header, key)){
				returnValue.put(key, map.get(key));
			}
		}
		return returnValue;
	}
	
	/**
	 * get max value from key of Map 
	 * @param submap
	 * @return
	 */
	private static int getMaxValueFromKeys(Map<String, Object> submap){
		int defaultValue=(-1);
		int maxValue=defaultValue;
		Iterator<String> iterator=submap.keySet().iterator();
		while(iterator.hasNext()){
			int nextValue=getIntFromString(iterator.next(), defaultValue);
			if(nextValue>maxValue){
				maxValue=nextValue;
			}
		}
		return maxValue;
	}
	
	/**
	 * get Int value from String 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	private static int getIntFromString(String value, int defaultValue){
		try{
			return Integer.parseInt(value.replaceAll("[^0-9]", ""));
		}catch(Exception ex){
			return defaultValue;
		}
	}
	
	/**
	 * is Key start with pattern and consists 1 or 2 or 3 digits in tail ?
	 * @param pattern
	 * @param key
	 * @return
	 */
	private static boolean isValidKey(String pattern, String key){
		if((pattern==null)||(key==null))return false;
		if(pattern.length()+3>=key.length()){
			if(key.startsWith(pattern)){
				return isDigit(key.substring(pattern.length(), key.length()));
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private static boolean isDigit(String value){
		if(value==null)return false;
		if(value.trim().length()==0)return false;
		return value.replaceAll("[^0-9]", "").length()>0;
	}
}
