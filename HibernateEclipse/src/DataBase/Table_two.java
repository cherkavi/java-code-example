package DataBase;
import javax.persistence.*;

@Entity
@Table (name="TABLE_TWO")
public class Table_two {
	@Id 
	@GeneratedValue
	@Column(name="KOD")
	private int field_kod;
	
	@Column(name="FIELD_STRING")
	private String field_string;
	
	public int getField_kod() {
		return field_kod;
	}
	public void setField_kod(int field_kod) {
		this.field_kod = field_kod;
	}
	public String getField_string() {
		return field_string;
	}
	public void setField_string(String field_string) {
		this.field_string = field_string;
	}

}
