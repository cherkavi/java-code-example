package private_class_public_interface.consumer;

import java.lang.reflect.Modifier;

import private_class_public_interface.producer.Producer;
/**
 * Вывод:
 * все классы, включая private, если реализуют интерфейс ( который
 * уже по определению public ) могут быть доступны вне видимости
 * самого класса, и это понятно - ведь интерфейс public.
 * ( а вот создать private класс на основании getClass().newInstance() 
 * не получится - Exception )
 * 
 * Интересная особенность анонимных классов, они имеют
 * default ( package ) видимость
 * @author Technik
 *
 */
public class Consumer {
	public static void main(String[] args) throws Exception, IllegalAccessException{
		IGetString holder=new Producer().getHolder();
		decodeModifiers(holder.getClass());
		System.out.println(holder.getString());
	}
	
	private static void decodeModifiers(Class clazz){
		if(Modifier.isPrivate(clazz.getModifiers())){
			System.out.println("private");
		}
		if(Modifier.isProtected(clazz.getModifiers())){
			System.out.println("protected");
		}
		if(Modifier.isPublic(clazz.getModifiers())){
			System.out.println("public");
		}
		if(Modifier.isAbstract(clazz.getModifiers())){
			System.out.println("abstract");
		}
		if(Modifier.isStatic(clazz.getModifiers())){
			System.out.println("static");
		}
		if(Modifier.isFinal(clazz.getModifiers())){
			System.out.println("final");
		}
		System.out.println("Modifiers:"+clazz.getModifiers());
	}
	
}
