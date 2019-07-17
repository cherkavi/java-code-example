package test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import main_interface.IValues;

public class EnterPoint {
	public static void main(String[] args){	
		System.out.println("begin");
		// 
		// IValues<String> value=getObject("C:\\","main_implementation.StringValue");
		IValues<String> value=getObject("C:\\main_implementation.zip","main_implementation.StringValue");
		if(value!=null){
			value.setValue("this is value from program");
			System.out.println(value.getValue());
		}
		System.out.println("-end-");
	}
	
 	/** *
 	 * 
 	 * @param path 
 	 * <ul>
 	 * 	<li> путь к корню каталога, из которого будет прочитан данный файл </li>
 	 * 	<li> путь к Zip архиву (jar файлу) </li>
 	 * </ul>  
 	 * @param className - полное имя класса, при чем если есть пакеты и файл не в архиве - нужно чтобы пакеты были в виде каталогов
 	 * @return
 	 */
	@SuppressWarnings("unchecked")
	private static IValues<String> getObject(String path, String className){
		try{
			// File file=new File(path);
			// URL[] urls=new URL[]{file.toURL()};
			
			URL[] urls=new URL[]{new URL("file:/"+path.replaceAll("\\\\","/"))};
			ClassLoader classLoader=new URLClassLoader(urls);
			
			Class<?> clazz=classLoader.loadClass(className);
			return (IValues<String>)clazz.newInstance();
		}catch(Exception ex){
			System.err.println("getObject Exception:"+ex.getMessage());
			return null;
		}
	}
}
