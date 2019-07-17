package bonpay.osgi.launcher.settings.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesFileStorage implements IStorage{
	/** полный путь к файлу */
	private String pathToFile;
	private Properties storage;
	private String remark;
	
	public PropertiesFileStorage(String pathToFile, String remark){
		this.pathToFile=pathToFile;
		this.remark=remark;
		this.loadAllRecords();
	}
	
	@Override
	public Object getRecord(String name) {
		return this.storage.get(name);
	}

	@Override
	public boolean loadAllRecords() {
		this.storage=new Properties();
		try{
			this.storage.load(new FileInputStream(this.pathToFile));
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	@Override
	public boolean putRecord(String name, Object value) {
		try{
			this.storage.put(name, (String)value);
			return true;
		}catch(Exception ex){
			return false;
		}
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
		try{
			FileOutputStream fos=new FileOutputStream(this.pathToFile);
			this.storage.store(fos, this.remark);
			fos.flush();
			fos.close();
			return true;
		}catch(Exception ex){
			return false;
		}
	}

}
