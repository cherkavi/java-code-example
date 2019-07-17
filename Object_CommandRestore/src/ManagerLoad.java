import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class ManagerLoad {
	public static void main(String[] args){
		System.out.println("ManagerLoad BEGIN:");
		
		ClassLoader loader=new FileClassLoader("c:\\CommandInformation.class");
		try{
			System.out.println("Try to load class");
			Command command=(Command)loader.loadClass("CommandInformation").newInstance();
			System.out.println("loader.loadClass is done :"+command);
			System.out.println(command.getResult());
			System.out.println(command.action());
			System.out.println("Class is load");
		}catch(Exception ex){
			System.out.println("Load class: Exception:"+ex.getMessage());
			ex.printStackTrace();
		}
		
		System.out.println("ManagerLoad -END-:");
	}

	/** return object from file or return null, when throw Exception */
	private static Object getObjectFromFileName(String pathToFile){
		Object returnValue=null;
		try{
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File(pathToFile)));
			returnValue=ois.readObject();
		}catch(Exception ex){
			System.out.println("getObjectFromFileName: Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}


class FileClassLoader extends ClassLoader{
	private String pathToFile;

	public FileClassLoader(String pathToFile){
		this.pathToFile=pathToFile;
	}
	
	public Class<?> findClass(String className){
		try{
			byte[] buffer=loadClassData(className);
			return this.defineClass(className, buffer, 0, buffer.length);
		}catch(Exception ex){
			return null;
		}
	}
	 
	public byte[] loadClassData(String className){
		try{
			File file=new File(this.pathToFile);
			byte[] buffer=new byte[(int) file.length()];
			FileInputStream fis=new FileInputStream(new File(this.pathToFile));
			fis.read(buffer);
			return buffer;
		}catch(Exception ex){
			return null;
		}
	}
}