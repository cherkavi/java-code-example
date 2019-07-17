package serializable_arrayofbyte;

import java.io.*;


/** 
 * проект, который выводит на консоль имена сериализуемых объектов
 * byte[] - [B
 * int[] - [I
 * serializable_arrayofbyte.ObjectSerializable
 * String[] - [Ljava.lang.String;
 */
public class Main {

    public static void main(String[] args) {
        /*Object object_for_write=get_byte_array();
        System.out.println("Object for write Data:"+Utility.hexDump((byte[])object_for_write));
         */
        /*
        Object object_for_write=new ObjectSerializable("value for save");
        System.out.println("Object for write Data:"+object_for_write);
         */

        /*Object object_for_write=get_string_array();
        System.out.println("Object for write Data:"+object_for_write);*/
        
        //Object object_for_write=new int[]{1,2,3};
        Object object_for_write=new int[]{1,2,3};
        
        String object_for_write_class_name=object_for_write.getClass().getName().toString();
        System.out.println("Object for write ClassName:"+object_for_write_class_name);
        
        
        if(object_for_write.getClass()==(new int[]{}).getClass()){
            System.out.println("Class is equals :"+object_for_write.getClass()); 
        }

        try{
            if(Class.forName(object_for_write_class_name)==(new int[]{}).getClass()){
                System.out.println("Class is equals by name ");
            }
        }catch(Exception ex){};
        
        byte[] transport=writeObjectToByteArray(object_for_write);
        if(transport!=null){
            System.out.println("Restore object");
            
            Object reader_object=readObjectFromByteArray(transport);
            System.out.println("Object for read ClassName:"+reader_object.getClass().getName());
            
            /*byte[] value=(byte[])reader_object;
            System.out.println("Object for read Data:"+Utility.hexDump(value));*/
            
            /*ObjectSerializable value=(ObjectSerializable)reader_object;
            System.out.println("Object for read ClassName:"+value);*/
            
            try{
                Object value=reader_object;
                System.out.println("Object for read :"+value);
            }catch(Exception ex){};
            
        }else{
            System.out.println("ERROR: data not writed");
        }
    }
    
    /** получить объект byte[] для записи */
    private static byte[] get_byte_array(){
        return new byte[]{0x00, 0x01, 0x02, 0x03,0x02,0x01,0x00};
    }
    
    
    /** получить объект String[] для записи */
    private static String[] get_string_array(){
        return new String[]{"one","two","three","four"};
    }
    /** записать объект в byte[] */
    private static byte[] writeObjectToByteArray(Object object_for_write){
        try{
            ByteArrayOutputStream byte_array=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(byte_array);
            oos.writeObject(object_for_write);
            oos.close();
            byte_array.close();
            System.out.println("Write OK");
            return byte_array.toByteArray();
        }catch(IOException ex){
            System.out.println("writeObjectToByteArray: ERROR :"+ex.getMessage());
            return null;
        }
    }
    
    /** получить объект из byte[]*/
    private static Object readObjectFromByteArray(byte[] data){
        Object return_value=null;
        try{
            ByteArrayInputStream inputStream=new ByteArrayInputStream(data);
            ObjectInputStream ois=new ObjectInputStream(inputStream);
            return_value=ois.readObject();
            ois.close();
            inputStream.close();
            System.out.println("Read OK");
        }catch(IOException ex){
            System.out.println("readObjectFromByteArray ERROR:"+ex.getMessage());
        }catch(ClassNotFoundException ex){
            System.out.println("Class not found exception:"+ex.getMessage());
        }
        return return_value;
    }
    
}
