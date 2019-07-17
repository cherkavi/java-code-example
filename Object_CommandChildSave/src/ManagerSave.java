import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ManagerSave {
	public static void main(String[] args){
		System.out.println("ManagerSave: BEGIN");
		System.out.println("create object");
		Command command=new CommandInformation();
		System.out.println("save object into disc:"+saveObjectIntoFileName(command,"c:\\object.bin"));
		System.out.println("ManagerSave: END");
	}
	
	private static boolean saveObjectIntoFileName(Object object, String filename){
		boolean returnValue=false;
		try{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(filename)));
			oos.writeObject(object);
			oos.close();
			returnValue=true;
		}catch(Exception ex){
			System.out.println("saveObjectIntoFileName Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
