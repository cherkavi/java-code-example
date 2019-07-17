package settings;
import java.awt.Color;

public abstract class Settings{
	
	public void setInteger(String path, Integer value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value.toString());
	}
	public void setDouble(String path, Double value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value.toString());
	}

	public void setString(String path, String value){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), value);
	}
	
	
	public Integer getInteger(String path, Integer defaultValue){
		String value=this.getStringFromStorage(this.convertUserPathToStoragePath(path), null);
		if(value==null){
			return defaultValue;
		}else{
			return new Integer(value);
		}
	}
	
	public Double getDouble(String path, Double defaultValue){
		String value=this.getStringFromStorage(this.convertUserPathToStoragePath(path), null);
		if(value==null){
			return defaultValue;
		}else{
			return new Double(value);
		}
		
	}
	public String getString(String path, String defaultValue){
		return this.getStringFromStorage(this.convertUserPathToStoragePath(path), defaultValue);
	}
	
	/** преобразовать пользовательский путь в универсальный путь хранилища 
	 * @param value - строка которая может быть следующего вида:
	 *  "//parent/child" - XPath формат <br>
	 *  "parent.child"
	 * */
	protected String convertUserPathToStoragePath(String path){
		path=path.replaceAll("/", ".");
		while(path.startsWith(".")==true){
			path=path.substring(1);
		}
		return path;
	}
	
	
	/** сохранить цвет 
	 * @param path - путь 
	 * @param color - цвет, который нужно сохранить 
	 */
	public void setColor(String path, Color color){
		this.setStringIntoStorage(this.convertUserPathToStoragePath(path), getStringValueFromColor(color));
	}
	
	/** прочитать цвет 
	 * @param path путь к переменной
	 * @param defaultColor значение по умолчанию, если не найден цвет по указанному пути 
	 * */
	public Color getColor(String path, Color defaultColor){
		Color returnValue=defaultColor;
		try{
			returnValue=Color.decode(getStringFromStorage(this.convertUserPathToStoragePath(path),null));
		}catch(Exception ex){
		}
		return returnValue;
	}

	/** получить строковое значение из Color 
	 * @param color - цвет, который нужно сохранить 
	 * */
	private String getStringValueFromColor(Color color){
		return "0x"+Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1); 
	}
	
	
	/** сохранить строку по указанному пути 
	 * @param path путь, по которому будет сохранен данный объект (Example:   "root.parent.value" )
	 * @param value значение, которое необходимо сохранить для данного объекта
	 * */
	protected abstract void setStringIntoStorage(String path, String value);

	
	/** получить строку из указанного пути 
	 * @param path путь, по которому будет происходить чтение объекта (Example:   "root.parent.value" )
	 * @param defaultValue значение, которое будет возвращено в случае не нахождения указанного пути 
	 * */
	protected abstract String getStringFromStorage(String path, String defaultValue);
	
	/** зафиксировать все сделанные изменения */
	public abstract boolean commitChange();
	
}
