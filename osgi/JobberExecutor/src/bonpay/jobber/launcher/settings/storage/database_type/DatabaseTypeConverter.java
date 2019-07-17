package bonpay.jobber.launcher.settings.storage.database_type;

/** базовый тип для преобразования из объекта в строку и обратно */
public abstract class DatabaseTypeConverter {
	/** имя типа */
	private String type;

	public DatabaseTypeConverter(String type){
		this.type=type;
	}
	
	/** получить тип данных в виде строки */
	public String getType(){
		return type;
	}
	
	/** получить строковое представление типа, если объект имеет отношение к данному типу */
	public abstract String getTypeFromObject(Object object);
	
	/** проверить тип на соответствие */
	public boolean typeEquals(String controlType){
		boolean returnValue=false;
		try{
			String tempType=controlType.trim().substring(0,1);
			String currentType=type.trim().substring(0,1);
			if(tempType.equalsIgnoreCase(currentType)){
				returnValue=true;
			}else{
				returnValue=false;
			}
		}catch(Exception ex){
			System.err.println("DatabaseTypeConverter#typeEquals Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
	/** получить объект из строки */
	public abstract Object getObjectFromString(String value) throws Exception;
	
	/** получить строку из объекта  */
	public abstract String getStringFromObject(Object value) throws Exception;
}
