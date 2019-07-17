import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;

public class Main {
	public static void main(String[] args){
		// создание объекта 
		CurrentBean bean=new CurrentBean();
			// наполнение объекта данными 
		bean.setIntegerValue(3);bean.setName("temp bean");
		/*HashMap map=new HashMap();
		map.put("name_1","value_1");
		map.put("name_2","value_2");
		map.put("name_3","value_3");
		bean.setValues(map);*/
		// получение объекта на основании имен его членов 
		try{
			/*PropertyUtilsBean propertyUtilsBean=new PropertyUtilsBean();
			Map propertiesMap=propertyUtilsBean.describe(bean);
			System.out.println("PropertiesMap:"+propertiesMap);*/
			System.out.println("Value:"+PropertyUtils.getSimpleProperty(bean, "integerValue"));
			System.out.println("Type of Value:"+PropertyUtils.getPropertyType(bean, "integerValue").getName().toString());
		}catch(Exception ex){
			System.err.println("Get Attributes Exception:"+ex.getMessage());
		}
	}
}

