package DataBase;
import javax.persistence.*;

@Entity
@Table (name="TABLE_ONE")
public class Table_one {
	@Id 
	@GeneratedValue
	@Column(name="KOD")
	private int field_kod;
	
	@Column(name="FIELD_STRING")
	private String field_string;
	
	@Column(name="FIELD_DOUBLE")
	private float field_double;
	
	@Column(name="FIELD_DATE")
	private java.util.Date field_date;
	
	@Column(name="FIELD_TIMESTAMP")
	private java.util.Date field_timestamp;
	
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
	public float getField_double() {
		return field_double;
	}
	public void setField_double(float field_double) {
		this.field_double = field_double;
	}
	public java.util.Date getField_date() {
		return field_date;
	}
	public void setField_date(java.util.Date field_date) {
		this.field_date = field_date;
	}
	public java.util.Date getField_timestamp() {
		return field_timestamp;
	}
	public void setField_timestamp(java.util.Date field_timestamp) {
		this.field_timestamp = field_timestamp;
	}
	
}
