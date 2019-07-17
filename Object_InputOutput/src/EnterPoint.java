import object.Child;
import object.NoSerializableObject;
import object.Parent;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("main: create object Child");
		Child object_child=new Child("Child information-Потомок информация");
		NoSerializableObject adding=new NoSerializableObject(12);
		object_child.calculateValue(adding);
		System.out.println("ChildInformation:"+object_child.getInformation());
		
		System.out.println("main: set object Parent");
		Parent object_parent=object_child;
		System.out.println("main: putting object:"+object_parent.getInformation());

		String path_to_file="c:\\object.bin";
		System.out.println("main: write object to "+path_to_file);
		// записываем объекты
		//writeObject_to_file(path_to_file,object_parent);
		byte[] data=Utility.writeObject_to_array(object_parent);
		System.out.println(">>> SAVE TO BLOB");
		Utility.write_to_database(data);
		object_parent=null;
		object_child=null;
		data=null;
		// записываем 
		//writeObject(path_to_file,null);
		
		System.out.println("main: read object");

		System.out.println(">>> READ FROM BLOB");
		data=Utility.read_from_database();
		System.out.println("Data:"+data);
		// читаем объекты
		//Parent restoreParent=Utility.readObject_from_file(path_to_file);
		Parent restoreParent=(Parent)Utility.readObject_from_array(data);
		
		//System.out.println("main: getting object:"+restoreParent.getInformation());
		System.out.println("main: execute method into ");
		try{
			Child restoreChild=(Child)restoreParent;
			System.out.println("main: getting object:"+restoreChild.getInformation());
			System.out.println("main: getting object:"+restoreChild.getDoubleInformation());
			System.out.println("main: getting value:"+restoreChild.getIntValue());
			System.out.println("main: getting NoSerializable:"+restoreChild.getNoSerializableValue());
		}catch(Exception ex){
			System.out.println("Error in execute Method:"+ex.getMessage());
		}
	}
	
}
