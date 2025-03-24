package database;


/** ���������� ������������� ���� */
public class Field {
	private int type;
	private String name;
	
	/** ���������� ������������� ���� 
	 * @param name - ��� ���� 
	 * @param sqlType - ��� ���� 
	 */
	public Field(String name, int sqlType){
		this.name=name;
		this.type=sqlType;
	}

	/** ���������� ������������� ���� 
	 * @param name - ��� ���� 
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
