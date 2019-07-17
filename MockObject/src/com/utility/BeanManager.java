package com.utility;

public class BeanManager {
	
	/**
	 * получить все интерфейсы данного объекта
	 * @param object - объект для получения интерфейсов
	 * @return классы всех реализуемых интерфейсов 
	 */
	public static Class<?>[] getInterfaces(Object object){
		try{
			return object.getClass().getInterfaces();
		}catch(Exception ex){
			System.err.println("#getInterface Exception:"+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * получить класс родительского объекта 
	 * @param object
	 * @return
	 */
	public static Class<?> getParentObject(Object object){
		try{
			return object.getClass().getSuperclass();
		}catch(Exception ex){
			System.err.println("#getParentObject: Exception"+ex.getMessage());
			return null;
		}
	}

	public static void printParentObject(Object object){
		Class<?> parentClass=getParentObject(object);
		if(parentClass!=null){
			System.out.println("   Parent Class for object is:"+parentClass.getName());
		}else{
			System.out.println("   Parent Class is empty");
		}
	}
	
	public static void printObjectInterfaces(Object object){
		try{
			Class<?>[] interfaces=getInterfaces(object);
			System.out.println("   Interfaces Size:"+interfaces.length);
			for(int counter=0;counter<interfaces.length;counter++){
				if(interfaces[counter]!=null){
					System.out.println("   "+counter+"  "+interfaces[counter].getName());
				}else{
					System.out.println(counter+"  null");
				}
				
			}
		}catch(Exception ex){
			System.err.println("#printObjectInterfaces Exception:"+ex.getMessage());
		}
	}
}
