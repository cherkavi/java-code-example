
public class ControlObject {
	private String field_string;
	public ControlObject(String string){
		this.field_string=string;
	}
	public String getString(){
		return this.field_string;
	}
	public void setString(String value){
		this.field_string=value;
	}
}
