package database;


/** уникальный идентификатор поля */
public class Field {
	private int type;
	private String name;
	
	/** уникальный идентификатор поля 
	 * @param name - имя поля 
	 * @param sqlType - тип поля 
	 */
	public Field(String name, int sqlType){
		this.name=name;
		this.type=sqlType;
	}

	/** уникальный идентификатор поля 
	 * @param name - имя поля 
	 */
	public Field(String name){
		this.name=name;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Field){
			Field destination=(Field)obj;
			try{
				return (destination.name.equals(name))&&(destination.type==type);
			}catch(NullPointerException npe){
				return false;
			}
		}else{
			return false;
		}
	}
	
}
