import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/** класс, который отвечает за сохранение объектов в базе и получение этих объектов из базы */
public class FunctionParameter implements Serializable{
	private final static long serialVersionUID=1L;
	
	private Class<?>[] classes;
	private Object[] objects;
	private String className;
	
	public FunctionParameter(String className, Class<?>[] classes, FunctionParameter[] objects){
		this.className=className;
		this.classes=classes;
		this.objects=objects;
	}

	public Class<?>[] getClasses() {
		return classes;
	}

	public void setClasses(Class<?>[] classes) {
		this.classes = classes;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/** возвращает массив из байт с двоичным представлением объекта
	 * @param Object - object for save
	 * @return null - возникла ошибка при сохранении  
	 * */
	public static byte[] getBytesFromObject(Serializable object){
		byte[] returnValue=null;
		try{
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			baos.close();
			return baos.toByteArray();
		}catch(Exception ex){
			System.err.println("getBytesFromObject:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** 
	 * Преобразовать двоичные данные в объект  
	 * @param bytes - двоичное представление
	 * @return - объект, либо же null, если что-либо не удалось сделать  
	 */
	public static Object getObjectFromBytes(byte[] bytes){
		Object returnValue=null;
		try{
			ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
			ObjectInputStream ois=new ObjectInputStream(bais);
			returnValue=ois.readObject();
			ois.close();
			bais.close();
		}catch(Exception ex){
			System.err.println("getObjectFromBytes");
		}
		return returnValue;
	}
}
