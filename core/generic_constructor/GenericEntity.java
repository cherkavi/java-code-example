package generic_constructor;

public class GenericEntity {
	private Object value;
	
	public <T> GenericEntity(T value){
		this.value=value;
	}
	
	public Object getValue(){
		return this.value;
	}
	
	public Void setValue() throws InstantiationException, IllegalAccessException{
		return Void.TYPE.newInstance();
	}
}
