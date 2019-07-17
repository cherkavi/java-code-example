package bc.data_terminal.editor.transfer;

/** класс, которым обменивается клиент JavaScript и сервер J2EE*/
public class Data {
	private String field_key;
	private String field_value;
	private String field_value2;
	private String field_value3;
	private String field_value4;
	
	public Data(){
		this.field_key="";
		this.field_value="";
		this.field_value2="";
		this.field_value3="";
		this.field_value4="";
	}
	
	public String getKey(){
		return this.field_key;
	}
	public void setKey(String value){
		this.field_key=value;
	}
	
	public String getValue(){
		return field_value;
	}
	
	public void setValue(String value){
		this.field_value=value;
	}
	
	public String getValue2(){
		return field_value2;
	}
	
	public void setValue2(String value){
		this.field_value2=value;
	}

	public String getValue3(){
		return field_value3;
	}
	
	public void setValue3(String value){
		this.field_value3=value;
	}

	public String getValue4(){
		return field_value4;
	}
	
	public void setValue4(String value){
		this.field_value4=value;
	}
	
}
