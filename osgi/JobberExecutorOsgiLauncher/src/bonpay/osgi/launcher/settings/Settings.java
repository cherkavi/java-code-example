package bonpay.osgi.launcher.settings;

import bonpay.osgi.launcher.settings.storage.IStorage;

/** настройки  */
public class Settings {
	/** место сохранения данных/чтения данных */
	private IStorage storage;
	
	public Settings(IStorage storage){
		this.storage=storage;
		storage.loadAllRecords();
	}
	
	/** установить значение */
	public void setInteger(String parameterName, Integer value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}

	/** установить значение */
	public void setString(String parameterName, String value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}

	/** установить значение */
	public void setFloat(String parameterName, Float value){
		this.storage.putRecord(parameterName, value);
		this.storage.saveAllRecords();
	}
	
	/** получить значение как Integer
	 * @param parameterName - имя параметра 
	 * @return null, если объект не найден или тип не соответствует  
	 */
	public Integer getInteger(String parameterName){
		try{
			return (Integer)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** получить значение как Float
	 * @param parameterName - имя параметра 
	 * @return null, если объект не найден или тип не соответствует  
	 */
	public Float getFloat(String parameterName){
		try{
			return (Float)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** получить значение как String
	 * @param parameterName - имя параметра 
	 * @return null, если объект не найден или тип не соответствует  
	 */
	public String getString(String parameterName){
		try{
			return (String)this.storage.getRecord(parameterName);
		}catch(Exception ex){
			return null;
		}
	}
	
}
