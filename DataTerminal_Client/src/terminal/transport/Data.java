package terminal.transport;

import java.io.*;

public class Data implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	/** Уникальное имя данных в контексте Task */
	private String field_DataName="";
	/** имя класса, которое мы получаем из ().getClass().getName() */
	private String field_ClassName=null;
	/** данные, которые содержат сериализованный объект */
	private byte[] field_data=null; 
	
	
	/** Данные для объекта Task
	 * @param DataName возможно, уникальное имя данных в контексте объекта Task
	 * @param object_for_send объект, которые нужно передавать/принимать
	 * */
	public Data(String DataName, Object object_for_send){
		this.field_DataName=DataName;
		if(object_for_send!=null){
			this.field_ClassName=object_for_send.getClass().getName();
			field_data=writeObjectToByteArray(object_for_send);
		}
	}

	/** Данные для объекта Task
	 * @param object_for_send объект, которые нужно передавать/принимать
	 * */
	public Data(Object object_for_send){
		this("",object_for_send);
	}
	
	/** получить объект */
	public Object getObject(){
		Object return_value=null;
		return_value=readObjectFromByteArray(field_data);
		return return_value;
	}
	/** получить имя класса для сохраненных данных */
	public String getClassName(){
		return this.field_ClassName;
	}
	
	/** получить имя для данных ( возможно, уникальное в контексте Task) */
	public String getDataName(){
		return this.field_DataName;
	}
	
	
	/** записать/сериализовать объект в byte[] */
	private byte[] writeObjectToByteArray(Object object_for_write){
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
    private Object readObjectFromByteArray(byte[] data){
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
