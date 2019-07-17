package method_by_name;

public class example {
	private String field_string_1=null;
	private String field_string_2=null;
	public example(String field_1,String field_2){
		this.field_string_1=field_1;
		this.field_string_2=field_2;
	}
	public String getField(Integer index){
		String return_value=null;
		if(index.intValue()==1){
			return_value=this.field_string_1;
		}
		if(index.intValue()==2){
			return_value=this.field_string_2;
		}
		return return_value;
	}
}
