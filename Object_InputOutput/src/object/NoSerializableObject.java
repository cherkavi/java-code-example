package object;

/** данный класс не является сериализуемым, но учавствует в одном из методов клиента */
public class NoSerializableObject {
	private int field_value=0;
	
	public NoSerializableObject(int value){
		this.field_value=value;
	}
	
	public int getValue(){
		return this.field_value;
	}
	public void setValue(int value){
		this.field_value=value;
	}
}
