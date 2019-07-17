import java.io.Serializable;
import java.io.*;

/** проверка на вызов конструктора при десириализации объекта */
public class SerializableConstructor {
	public static void main(String[] args){
		System.out.println("Begin:");
		Value value=new Value("temp_object",87);
		System.out.println("Записать объект в byte[]");
		byte[] byteArray=writeObjectToByteArray(value);
		System.out.println("Прочесть объект из byte[]");
		Value valueSerializable=(Value)readObjectFromByteArray(byteArray);
		System.out.println("Create Value:"+valueSerializable.getStringValue()+" intValue:"+valueSerializable.getIntValue());
		System.out.println("End:");
	}
	
	/** записать/сериализовать объект в byte[] */
	private static byte[] writeObjectToByteArray(Object object_for_write){
        try{
            ByteArrayOutputStream byte_array=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(byte_array);
            oos.writeObject(object_for_write);
            oos.close();
            byte_array.close();
            return byte_array.toByteArray();
        }catch(IOException ex){
            System.out.println("writeObjectToByteArray: ERROR :"+ex.getMessage());
            return null;
        }
	}
    /** прочитать/сериализовать объект из byte[]*/
    private static Object readObjectFromByteArray(byte[] data){
        Object return_value=null;
        try{
            ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
            ObjectInputStream ois=new ObjectInputStream(inputStream);
            return_value=ois.readObject();
            ois.close();
            inputStream.close();
        }catch(IOException ex){
            System.out.println("readObjectFromByteArray ERROR:"+ex.getMessage());
        }catch(ClassNotFoundException ex){
            System.out.println("Class not found exception:"+ex.getMessage());
        }
        return return_value;
    }
	
	
}

class Value implements Serializable{
	private String stringValue;
	private int intValue;

	public Value(String stringValue, int intValue){
		System.out.println("Value constructor: ");
		this.stringValue=stringValue;
		this.intValue=intValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
}
