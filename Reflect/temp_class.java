package temp_package;

import java.net.*;
import java.lang.reflect.*;

class demo_reflect{
	demo_reflect(String class_name){
		try{
			Class c=Class.forName(class_name);
			java.lang.reflect.Constructor constructors[]=c.getConstructors();
			java.lang.reflect.Method methods[]=c.getMethods();
			java.lang.reflect.Field fields[]=c.getFields();

			System.out.println("Constructor:");
			for(int i=0;i<constructors.length;i++){
				System.out.println(i+":"+constructors[i]);
			}
			System.out.println("Methods:");
			for(int i=0;i<methods.length;i++){
				String string_add="";
				int modifiers=methods[i].getModifiers();
				if(java.lang.reflect.Modifier.isAbstract(modifiers)){
					string_add+="ABSTRACT ";
				}
				if(java.lang.reflect.Modifier.isFinal(modifiers)){
					string_add+="FINAL ";
				}
				if(java.lang.reflect.Modifier.isPublic(modifiers)){
					string_add+="PUBLIC ";
				}
				if(java.lang.reflect.Modifier.isSynchronized(modifiers)){
					string_add+="Synchronized";
				}
				System.out.println(i+":"+methods[i]+"  "+string_add);
			}
			System.out.println("Fields:");
			for(int i=0;i<fields.length;i++){
				System.out.println(i+":"+fields[i]);
			}
		}
		catch(Exception e){
			System.out.println("Error reflection\n"+e.getMessage());
		}
		
	}
}
public class temp_class {
	public static void main(String args[]){
		new demo_reflect("java.applet.Applet");
		
	}
	
}
