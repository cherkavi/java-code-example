import java.lang.reflect.Method;


/** вызвать у объекта определнного класса метод по имени, с параметрами и без параметров */
public class CallMethodByName {
	public static void main(String[] args){
		TempClass temp=new TempClass();
		callMethodOfObject(temp,"setStringValue",new Class[]{String.class},new Object[]{"this is dynamic value"});
		System.out.println(callMethodOfObject(temp,"getStringValue",null,null));
	}
	
	/** вызвать у объекта метод по его(метода) имени 
	 * @param object - объект, у которого вызывается метод
	 * @param methodName - имя метода, который вызывается
	 * @param argumentClasses - классы объектов, на основании которых нужно получить метод
	 * @param argumentObjects - объекты, которые будут переданы в качестве аргументов
	 * */
	public static Object callMethodOfObject(Object object, 
											String methodName,
											Class<?>[] argumentClasses,
											Object[] argumentObjects){
		Object returnValue=null;
		try{
			if(argumentClasses==null){
				argumentClasses=new Class[]{};
			}
			if(argumentObjects==null){
				argumentObjects=new Object[]{};
			}
			Class<?> objectClass=object.getClass();
			Method method=objectClass.getMethod(methodName,argumentClasses);
			returnValue=method.invoke(object,argumentObjects);
		}catch(Exception ex){
			System.err.println("callMethodOfObject Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
}

class TempClass{
	private String stringValue;
	private int intValue;
	
	public TempClass(){
		System.out.println("TempClass");
	}
	
	public String getStringValue(){
		System.out.println("getStringValue");
		return stringValue;
	}
	public void setStringValue(String value){
		System.out.println("setStringValue");
		this.stringValue=value;
	}
	public int getIntValue(){
		System.out.println("getIntValue");
		return intValue;
	}
	public void setIntValue(int intValue){
		System.out.println("setIntValue");
		this.intValue=intValue;
	}
}
