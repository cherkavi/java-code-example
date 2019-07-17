package bonpay.osgi.launcher.settings.storage;

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
		logger.debug("создано хранилище на основании файла: "+this.rootPath);
	}
	
	@Override
	public Object getRecord(String name) {
		return this.storage.get(name);
	}

	/** прочесть объект из файла */
	private void readObjectFromFile(File file){
		logger.debug("прочесть объект из файла");
		if(file.isFile()&&file.canRead()){
			try{
				ObjectInputStream input=new ObjectInputStream(new FileInputStream(file)) ;
				Object object=input.readObject();
				input.close();
				if(object instanceof Wrap){
					Wrap source=(Wrap)object;
					this.storage.put(source.getKey(), source.getValue());
					logger.debug(" объект из файла успешно прочитан ");
				}else{
					logger.error("демаршалинг невозможен  is not Wrap: "+object.getClass().toString());
				}
			}catch(Exception ex){
				logger.error("ошибка демаршалинга Exception: "+ex.getMessage());
			}
		}else{
			logger.error("файл не найден, или не может быть прочитан: "+file.getAbsolutePath());
		}
	}
	
	@Override
	public boolean loadAllRecords() {
		logger.debug("загрузить все записи из persistance storage для данного хранилища ( из файловой системы )");
		boolean returnValue=false;
		logger.debug("прочесть все файлы из каталога и заполнить объекты");
		File directory=new File(this.rootPath);
		if(directory.isDirectory()){
			logger.debug("очистить хранилище");
			this.storage.clear();
			logger.debug("загрузить объекты");
			File[] files=directory.listFiles();
			for(int counter=0;counter<files.length;counter++){
				readObjectFromFile(files[counter]);
			}
			returnValue=true;
			logger.debug("данные успешно загружены");
		}else{
			logger.error("loadAllRecords не директория (persistance storage должно быть каталогом в файловой системе) "+directory.getAbsolutePath());
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
		logger.debug("сохранить все записи ");
		boolean returnValue=false;
		File directory=new File(this.rootPath);
		if(directory.isDirectory()){
			logger.debug("удалить все файлы в каталоге");
			File[] files=directory.listFiles();
			for(int counter=0;counter<files.length;counter++){
				files[counter].delete();
			}
			logger.debug("создать новые файлы на основании объектов");
			int counter=0;
			Iterator<String> iterator=this.storage.keySet().iterator();
			while(iterator.hasNext()){
				String key=iterator.next();
				Object value=this.storage.get(key);
				String fileName=this.rootPath+Integer.toString(++counter)+".bin";
				if(saveFile(new Wrap(key,value),fileName)){
					// file saved
					logger.debug("очередной файл: "+fileName+"  успешно сохранен ");
				}else{
					logger.error("ошибка сохранения очередного файла: "+fileName);
				}
			}
			returnValue=true;
		}else{
			logger.error("saveAllRecords is not Directory "+this.rootPath);
		}
		return returnValue;
	}
	
	private boolean saveFile(Wrap wrap, String path){
		logger.debug("маршалинг объекта в файл");
		boolean returnValue=false;
		try{
			File file=new File(path);
			ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(wrap);
			output.flush();
			output.close();
			returnValue=true;
			logger.debug("маршалинг объекта в файл успешно завершен ");
		}catch(Exception ex){
			logger.error("saveFile Exception: "+ex.getMessage(), ex);
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