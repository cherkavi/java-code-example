package bonpay.jobber.launcher.settings.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class FileStorage implements IStorage {
	String fileSeparator=System.getProperty("file.separator");
	Logger logger=Logger.getLogger(this.getClass());
	
	private HashMap<String, Object> storage=new HashMap<String, Object>();
	
	/** путь к корневой папке */
	private String rootPath;
	
	
	public FileStorage(String rootPath){
		String tempPath=rootPath.trim();
		if(tempPath.endsWith(fileSeparator)){
			this.rootPath=tempPath;
		}else{
			this.rootPath=tempPath+fileSeparator;
		}
	}
	
	@Override
	public Object getRecord(String name) {
		return this.storage.get(name);
	}

	/** прочесть объект из файла */
	private void readObjectFromFile(File file){
		if(file.isFile()&&file.canRead()){
			try{
				ObjectInputStream input=new ObjectInputStream(new FileInputStream(file)) ;
				Object object=input.readObject();
				input.close();
				if(object instanceof Wrap){
					Wrap source=(Wrap)object;
					this.storage.put(source.getKey(), source.getValue());
				}else{
					logger.error("readObjectFormFile is not Wrap: "+object.getClass().toString());
				}
			}catch(Exception ex){
				logger.error("readObjectFromFile Exception: "+ex.getMessage());
			}
		}else{
			logger.error("is not file: "+file.getAbsolutePath());
		}
	}
	
	@Override
	public boolean loadAllRecords() {
		boolean returnValue=false;
		// прочесть все файлы из каталога и заполнить объекты
		File directory=new File(this.rootPath);
		if(directory.isDirectory()){
			this.storage.clear();
			// загрузить объекты
			File[] files=directory.listFiles();
			for(int counter=0;counter<files.length;counter++){
				readObjectFromFile(files[counter]);
			}
			returnValue=true;
		}else{
			// не директория
			logger.error("loadAllRecords this is not directory "+directory.getAbsolutePath());
		}
		return returnValue;
	}

	@Override
	public boolean putRecord(String name, Object value) {
		this.storage.put(name, value);
		return true;
	}

	@Override
	public void removeRecord(String name) {
		this.storage.remove(name);
	}

	@Override
	public boolean replaceRecord(String name, Object value) {
		this.storage.put(name, value);
		return true;
	}

	@Override
	public boolean saveAllRecords() {
		boolean returnValue=false;
		File directory=new File(this.rootPath);
		if(directory.isDirectory()){
			// удалить все файлы в каталоге
			File[] files=directory.listFiles();
			for(int counter=0;counter<files.length;counter++){
				files[counter].delete();
			}
			// создать новые файлы на основании объектов
			int counter=0;
			Iterator<String> iterator=this.storage.keySet().iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				Object value=this.storage.get(key);
				String fileName=this.rootPath+Integer.toString(++counter)+".bin";
				if(saveFile(new Wrap(key,value),fileName)){
					// file saved
				}else{
					logger.error("saveAllRecords file not Save "+fileName);
				}
			}
			returnValue=true;
		}else{
			logger.error("saveAllRecords is not Directory "+this.rootPath);
		}
		return returnValue;
	}
	
	private boolean saveFile(Wrap wrap, String path){
		boolean returnValue=false;
		try{
			File file=new File(path);
			ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(wrap);
			output.flush();
			output.close();
			returnValue=true;
		}catch(Exception ex){
			logger.error("saveFile Exception: "+ex.getMessage());
			returnValue=false;
		}
		return returnValue;
	}
	
	public static void main(String[] args){
		System.out.println("begin");
		FileStorage fileStorage=new FileStorage("c:\\temp\\");
		if(fileStorage.loadAllRecords()){
			System.out.println("load OK");
			Date date=(Date)fileStorage.getRecord("current_date");
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			if(date!=null){
				System.out.println("Date: "+sdf.format(date));
			}
			Integer integerValue=(Integer)fileStorage.getRecord("integer value");
			System.out.println("IntegerValue: "+integerValue);
			Float floatValue=(Float)fileStorage.getRecord("float");
			System.out.println("FloatValue: "+floatValue);
		}else{
			System.out.println("load Error ");
		};
		fileStorage.putRecord("current_date", new Date());
		fileStorage.putRecord("integer value", new Integer(10));
		fileStorage.putRecord("float", new Float(2.3f));
		if(fileStorage.saveAllRecords()){
			System.out.println("save OK");
		}else{
			System.out.println("save ERROR");
		}
		System.out.println("end");
	}
}




/** класс-обертка для одного значения */
class Wrap implements Serializable{
	private final static long serialVersionUID=1L;
	private String key;
	private Object value;
	public Wrap(String key, Object value){
		this.key=key;
		this.value=value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}