package child_serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Manager {
	public static void main(String[] args) throws Exception {
		System.out.println("begin");
		Child child=new Child();
		child.changeValue("!!!new value for parent!!! ");
		System.out.println("getValue from Parent: "+child.getValue());
		String fileName="/tmp/temp/file.obj";
		writeToFile(child, fileName);
		System.out.println("--------------------");
		Child readedFile=(Child)readFile(fileName);
		System.out.println("getValue from Parent: "+readedFile.getValue());
		System.out.println("getValue from Child: "+readedFile.toString());
		System.out.println("-end-");
	}

	private static Object readFile(String fileName) throws Exception {
		ObjectInputStream input=null ;
		try{
			input=new ObjectInputStream(new FileInputStream(fileName));
			return input.readObject();
		}finally{
			try{
				input.close();
			}catch(Exception ex){};
		}
		
	}

	private static void writeToFile(Child child, String fileName) throws Exception {
		ObjectOutputStream output=null;
		try{
			output=new ObjectOutputStream(new FileOutputStream(fileName));
			output.writeObject(child);
		}finally{
			try{
				output.close();
			}catch(Exception ex){};
		}
		
	}
}
