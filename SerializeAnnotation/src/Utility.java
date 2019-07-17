import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class Utility {
	
	private static void debug(String information){
		System.out.println("Utility: "+information);
	}
	private static void error(String information){
		System.out.println("Utility:  "+information);
	}
	
	/** <b>записать объект в byte[] и вернуть его</b> 
	 * @param object - объект, который нужно записать
	 * @return возвращает массив из байт, который содержит данный объект
	 * */
	public static byte[] writeObject_to_array(Object object){
		byte[] return_value=null;
		try{
			ByteArrayOutputStream byte_array_output_stream=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(byte_array_output_stream);
			oos.writeObject(object);
			return_value=byte_array_output_stream.toByteArray();
			System.out.println("Object write to byte_array is OK");
		}catch(Exception ex){
			System.out.println("ObjectOutputStream Error:"+ex.getMessage());
			return_value=null;
		}
		return return_value;
	}
	
	/** <b>прочитать объект из byte[] и вернуть объект</b>
	 * @param массив из байт, который содержит объект 
	 * @return прочитанный из данной последовательности объект
	 * */
	public static Object readObject_from_array(byte[] array_of_byte){
		Object return_value=null;
		try{
			debug("ByteInputStream");
			if(array_of_byte==null){
				error("readObject_from_array parameter is null ");
			}
			ByteArrayInputStream byte_array_input_stream=new ByteArrayInputStream(array_of_byte);
			debug("ObjectInputStream");
			ObjectInputStream ois=new ObjectInputStream(byte_array_input_stream);
			debug("readObject");
			return_value=ois.readObject();
			System.out.println("Object read from byte_array is OK");
		}catch(Exception ex){
			System.out.println("ObjectInputStream Error:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** <b>записать объект в файл</b> 
	 * @param path_to_file - путь к файлу, куда нужно записать данный объект
	 * @param parent - объект, который должен быть записан 
	 * */
	public static void writeObject_to_file(String path_to_file,Object parent){
		try{
			System.out.println("writeObject: create file");
			File f=new File(path_to_file);
			System.out.println("writeObject: create FileOutputStream");
			FileOutputStream fos=new FileOutputStream(f);
			System.out.println("writeObject: create ObjectOutputStream");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			System.out.println("writeObject: write Object");
			oos.writeObject(parent);
			System.out.println("writeObject: ObjectOutputStream close");
			oos.close();
			System.out.println("writeObject: FileOuputStream close");
			fos.close();
		}catch(Exception ex){
			System.out.println("writeObject: Exception:"+ex.getMessage());
		}
	}
	
	/** <b>прочитать объект из файла</b>
	 * @param path_to_file - путь к файлу, в котором записан объект
	 * @return прочитанный объект 
	 * */
	public static Object readObject_from_file(String path_to_file){
		Object return_value=null;
		try{
			System.out.println("readObject: create file");
			File f=new File(path_to_file);
			System.out.println("readObject: create FileInputStream");
			FileInputStream fos=new FileInputStream(f);
			System.out.println("readObject: create ObjectInputStream");
			ObjectInputStream oos=new ObjectInputStream(fos);
			System.out.println("readObject: read Object");
			return_value=oos.readObject();
			System.out.println("readObject: ObjectInputStream close");
			oos.close();
			System.out.println("readObject: FileInputStream close");
			fos.close();
		}catch(Exception ex){
			System.out.println("readObject: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	


	
	
	public final static String hexChars[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static int unsignedInt(int a) {
		if (a < 0) {
			return a + 256;
		}
		return a;
	}

	public static int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

	
public static byte stringCharToBCDByte(String data, int pos) {
	return (byte) (Integer.parseInt(data.substring(pos, pos + 2), 16));
}

@SuppressWarnings("unused")
private final static String nullString = "null";

/** получить в виде HEX текста */
public static String hexDump(BigInteger big) {
	return hexDump(big.toByteArray());
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data) {
	return hexDump(data, 0, data.length);
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data, int length) {
	return hexDump(data, 0, length);
}
/** получить в виде HEX текста */
public static String hexDump(byte[] data, int offset, int length) {
	String result = "";
	String part = "";
	for (int i = 0; i < min(data.length, length); i++) {
		part = ""
				+ hexChars[(byte) (unsignedInt(data[offset + i]) / 16)]
				+ hexChars[(byte) (unsignedInt(data[offset + i]) % 16)];
		result = result + part;
	}
	return result;
}






final static public int tagLen = 1;

public static int getTagIdentifier(byte[] data, int pos) {
	return data[pos];
}

public static int getDataLen(byte[] data, int pos) {
	int i = 0;
	int len = 0;
	while (unsignedInt(data[pos + i]) == 255) {
		len = len * 255 + 255;
		i++;
	}
	len += unsignedInt(data[pos + i]);
	return len;
}

public static int getLenLen(byte[] data, int pos) {
	int i = 0;
	while (unsignedInt(data[pos + i]) == 255) {
		i++;
	}
	return i + 1;
}

public static String getString(byte[] data, int pos, int len)
		throws Exception{
	return new String(data, pos, len);
}

public static byte[] selectBytes(byte[] data, int pos, int len)
		throws Exception {
	byte[] res = new byte[len];
	for (int i = 0; i < len; i++)
		res[i] = data[pos + i];
	return res;
}
// -- Вспомогательные методы
// формирует строку hex определенной длины, добавлят слева нулями
public static String intToHexStringF(int data, int len) {
	String Indata = Integer.toHexString(data).toUpperCase();
	String Outdata = "";
	for (int i = 0; i < (len - Indata.length()); i++) {
		Outdata = Outdata + "0";
	}
	Outdata = Outdata + Indata;
	return Outdata;
};

	
}
