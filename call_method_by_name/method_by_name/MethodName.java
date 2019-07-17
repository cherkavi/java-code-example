package method_by_name;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Класс, который служит для вызова методов по его, метода,
 * имени 
 */
public class MethodName {
	private Class field_class;
	private Object field_source_object;
	private Object[] field_arguments;
	private Method field_method;
	public MethodName(){
		this.field_class=null;
		this.field_source_object=null;
		this.field_arguments=null;
		this.field_method=null;
	}
	/**
	 * конструктор с необходимыми параметрами 
	 * @param object_with_method - объект у которого будет вызван метод
	 * @param method_name -имя метода, который будет вызван у объекта {@link}object_with_method
	 * @param arguments аргументы метода, который будет вызван
	 */
	public MethodName(Object object_with_method,String method_name,Object[] arguments) throws NoSuchMethodException{
			this.setClass(object_with_method);
			this.setMethod(method_name,arguments);
			this.setArguments(arguments);
	}
	/**
	 * Установить класс
	 */
	public void setClass(Class source_class){
		this.field_class=source_class;
	}
	/**
	 * установить класс, исходя из объекта
	 * @param source_class
	 */
	public void setClass(Object source_class){
		this.field_class=source_class.getClass();
		this.field_source_object=source_class;
	}
	/**
	 * Установить класс объекта, к методам которого будут обращения 
	 * @param class_name имя класса
	 * @throws ClassNotFoundException если класс не найден по имени
	 */
	public void setClass(String class_name) throws ClassNotFoundException{
		this.field_class=Class.forName(class_name);
	}
	/**
	 * установить метод по его имени
	 * @param method_name - имя метода
	 * @throws NoSuchMethodException - если метод не найден в ЗАРАНЕЕ установленном объекте, и с ЗАРАНЕЕ установленными параметрами
	 */
	public void setMethod(String method_name) throws NoSuchMethodException{
		this.field_method=null;
		Method method = this.field_class.getMethod(method_name, new Class[]{});
		this.field_method=method;
	}
	/**
	 * установка метода, который будет вызван по
	 * @param method_name имени метода
	 * @param argument_class аргументам в виде классов
	 * @throws NoSuchMethodException если данный метод не найден в ЗАРАНЕЕ устновленном объекте Class
	 */
	public void setMethod(String method_name,
						  Class[] argument_class) 
						  throws NoSuchMethodException{
		this.field_method=null;
		this.field_method=this.field_class.getMethod(method_name, argument_class);
	}
	/**
	 * установка метода, который будет вызван 
	 * @param method_name имя метода
	 * @param argument_object аргументы, которые участвуют в вызове
	 * @throws NoSuchMethodException если метод не найден
	 */
	public void setMethod(String method_name, Object[] argument_object) throws NoSuchMethodException{
		Class[] argument_classes=new Class[argument_object.length];
		for(int counter=0;counter<argument_object.length;counter++){
			argument_classes[counter]=argument_object[counter].getClass();
		}
		this.field_method=null;
		this.field_method=this.field_class.getMethod(method_name,argument_classes);
	}
	/**
	 * Установить аргументы, которые будут использованы в вызове методов
	 * @param arguments
	 */
	public void setArguments(Object[] arguments){
		this.field_arguments=arguments;
	}
	/**
	 * Установить объект, у которого будет вызываться данный метод
	 */
	public void setObjectWithMethod(Object source){
		this.field_source_object=source;
	}
	/**
	 * @return возвращает реальное значение объекта на основании введенных параметров
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Object getValue() throws InvocationTargetException,IllegalAccessException{
		return this.field_method.invoke(
						this.field_source_object,
						this.field_arguments
					);
	}
}
