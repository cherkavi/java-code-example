package private_constructor_reflect;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class CreatePrivateConstructorClass {
	/** this is example of create the object with private constructor */
	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		// try to create object with private constructor
		Constructor[] constructors=PrivateConstructor.class.getDeclaredConstructors(); // getConstructors() - not return any constructor
		constructors[0].setAccessible(true); // set accessible to private constructor  
		System.out.println("Constructors:"+Arrays.toString(constructors)); // print all constructors: 
		System.out.println(constructors[0].newInstance()); // print PrivateConstructor#toString
		System.out.println("-end-");
	}
	
}
