package copy_properties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CopyProperty {
	private final static Map<Class<?>, List<String>> excludeProperties=new HashMap<Class<?>, List<String>>();
	{
		excludeProperties.put(Object.class, Arrays.asList("class"));
		excludeProperties.put(ParentBean.class, Arrays.asList("excludeProperties"));
		
	}

	@SuppressWarnings("unchecked")
	public static <T> T createNew(TestBean testBean, 
								  Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object returnValue=clazz.newInstance();
		List<String> fields=filterField(getFieldNames(testBean), testBean.getClass());
		setProperties(testBean, fields, returnValue);
		return (T)returnValue;
	}
	
	/**
	 * set properties from source to destionation ( from/to get/set method by field(s) )
	 * @param source - source of data
	 * @param fields - field for generate get and set method
	 * @param destination - object for set
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	static void setProperties(TestBean source, List<String> fields, Object destination) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method[] sourceMethods=source.getClass().getMethods();
		Method[] destinationMethods=destination.getClass().getMethods();
		
		for(String eachField:fields){
			Object objectForSet=getMethodByName(sourceMethods, generateMethodGet(eachField)).invoke(sourceMethods);
			getMethodByName(destinationMethods, generateMethodSet(eachField)).invoke(destination, objectForSet);
		}
	}

	static Method getMethodByName(Method[] methods, String name){
		for(Method method:methods){
			if(method.getName().equals(name)){
				return method;
			}
		}
		throw new IllegalArgumentException(String.format("can't find method by name %s",name));
	}
	
	static List<String> filterField(List<String> fields, Class<?> clazz) {
		List<String> listOfFields=new ArrayList<String>(fields);
		for(Class<?> eachClass:excludeProperties.keySet()){
			if(clazz.isAssignableFrom(eachClass)){
				removeFieldsByName(listOfFields, excludeProperties.get(eachClass));
			}
		}
		return listOfFields;
	}


	/**
	 * filter for fields by name 
	 * @param listOfFields - fields for remove 
	 * @param names - list of field
	 */
	static void removeFieldsByName(List<String> listOfFields, List<String> names) {
		listOfFields.removeAll(names);
	}


	/**
	 * get all fields from Bean
	 * @param value
	 * @return
	 */
	static List<String> getFieldNames(Object value) {
		if(value==null){
			throw new IllegalArgumentException("value is NULL !!!");
		}
		Method[] methods=value.getClass().getMethods();
		List<String> returnValue=new ArrayList<String>(methods.length);
		for(Method method:methods){
			returnValue.add(method.getName());
		}
		return returnValue;
	}


	private static String PREFIX_GET="get";
	
	/**
	 * generate name of method Get
	 * @param fieldName
	 * @return
	 */
	static String generateMethodGet(String fieldName){
		return PREFIX_GET+StringUtils.capitalize(fieldName);
	}
	
	
	private static String PREFIX_SET="set";
	
	/**
	 * generate name of method Set
	 * @param fieldName
	 * @return
	 */
	static String generateMethodSet(String fieldName){
		return PREFIX_SET+StringUtils.capitalize(fieldName);
	}
	
}
