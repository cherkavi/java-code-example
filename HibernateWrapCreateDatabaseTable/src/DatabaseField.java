
/** поле из таблицы в базе данных */
public class DatabaseField {
	private String fieldName;
	private String typeName;
	private int size;
	private boolean primaryKey=false;
	
	public DatabaseField(String fieldName, String typeName, int size){
		this.fieldName=fieldName;
		this.typeName=typeName;
		this.size=size;
	}

	public void setPrimaryKey(boolean value){
		this.primaryKey=value;
	}

	public boolean isPrimaryKey(){
		return this.primaryKey;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String toString(){
		if(this.isPrimaryKey()){
			return "N pk: "+this.fieldName+" T:"+this.typeName+" S:"+this.size;
		}else{
			return "N: "+this.fieldName+" T:"+this.typeName+" S:"+this.size;
		}
	}
	
}
