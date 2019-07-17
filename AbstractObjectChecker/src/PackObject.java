import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import calc.AbstractChecker;
import calc_implementation.EQDoubleChecker;

public class PackObject {
	public static void main(String[] args){
		System.out.println("begin");
		AbstractChecker checker=new EQDoubleChecker(5000, " Double Alarm message ", 1.2345d, 5 );
		writeObjectToFile("c:\\out.bin", checker);
		System.out.println("end");
	}
	
	private static boolean writeObjectToFile(String pathToFile, Object object){
		boolean returnValue=false;
		try{
			ObjectOutputStream fos=new ObjectOutputStream(new FileOutputStream(pathToFile));
			fos.writeObject(object);
			fos.flush();
			fos.close();
			return true;
		}catch(Exception ex){
			System.err.println("writeObjectToFile Exception: "+ex.getMessage());
		}
		return returnValue;
	}
}
