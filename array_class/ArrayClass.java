package array_class;

import java.lang.reflect.Type;

public class ArrayClass {
	public static void main(String[] args){
		System.out.println("begin");
		int[] array=new int[10];
		System.out.println("CnonicalClass: "+array.getClass().getCanonicalName());
		System.out.println("SimpleClass: "+array.getClass().getSimpleName());
		System.out.println("Name of Class: "+array.getClass().getName());
		System.out.println("ClassLoader: "+array.getClass().getClassLoader());
		System.out.println();
		
		Type superClass=(array).getClass().getGenericSuperclass();
		System.out.println("SuperClass: begin");
		printTypes(superClass);
		System.out.println("SuperClass: end\n");
		
		Type[] interfaces=(array).getClass().getGenericInterfaces();
		System.out.println("Interfaces begin: "+interfaces.length);
		printTypes(interfaces);
		System.out.println("Interfaces end:\n");
	
		System.out.println("Null parameters");
		printTypes(null);
		System.out.println();

		System.out.println("Empty parameters");
		printTypes();
		System.out.println();

		System.out.println("-end-");
	}
	
	/** вывести на консоль все типы 
	 * @param arrays - элемент или массив элементов, которые предоставл€ют тип 
	 * */
	private static void printTypes(Type ... arrays){
		if(arrays!=null){
			System.out.println("Parameters is NOT null ");
			for(int counter=0;counter<arrays.length;counter++){
				System.out.println("   #"+counter+" : "+arrays[counter]); // +"  ("+getAllSubclasses(arrays[counter],null)+")");
			}
		}else{
			System.out.println("Parameters is NULL ");
		}
	}
	
}
