package object;
/**
 * ¬ потомке не об€зательно объ€вл€ть интерфейс Serializable, т.к. он объ€влен в предке
 * и распространа€етс€ на потомка тоже
 * то есть когда объект записываетс€ то записываетс€ полностью, а не на той точке 
 * где объ€влен маркер Serializable  
 * @author cherkashinv
 */
public class Child extends Parent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String field_information="Child information";
	private int field_value=0;
	private transient NoSerializableObject field_no_serializable;
	
	public Child(){
		super("");
	}
	public Child(String information){
		super(information);
		this.field_information=information;
	}
	public String getInformation(){
		return this.field_information;
	}
	
	public String getDoubleInformation(){
		return "|"+this.field_information+"|"+this.field_information+"|";
	}
	
	public int getIntValue(){
		return this.field_value;
	}

	public void calculateValue(NoSerializableObject source){
		this.field_no_serializable=source;
		this.field_value=source.getValue();
	}
	
	public int getNoSerializableValue(){
		if(this.field_no_serializable==null){
			System.out.println("it is NULL");
			return 0;
		}else{
			return this.field_no_serializable.getValue();
		}
		
	}
	
}
