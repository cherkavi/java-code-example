package database;

import javax.persistence.*;


@Entity
@Table(name="TABLE_ONE")
public class TableOne {
	@Id
	@SequenceGenerator(name="generator_1",sequenceName="GEN_TABLE_ONE_ID")	
	@GeneratedValue(strategy=GenerationType.AUTO,generator="generator_1")	
	@Column(name="KOD")
	private Integer kod;
	
	@Column(name="FIELD_STRING")
	private String field_string;
	
	@Column(name="FIELD_DOUBLE")
	private Double field_double;
	
	@Column(name="FIELD_DATE")
	private java.util.Date field_date;
	
	@Column(name="FIELD_TIMESTAMP")
	private java.util.Date field_timestamp;

	public TableOne(){
		clear();
	}
	
	public void clear(){
		this.kod=null;
		this.field_string="";
		this.field_double=new Double(0);
		this.field_date=new java.util.Date();
		this.field_timestamp=this.field_date;
	}
	
	public void setKod(int value){
		this.kod=value;
	}

	public Integer getKod(){
		return this.kod;
	}

	public String getField_string() {
		return field_string;
	}
	public void setField_string(String field_string) {
		this.field_string = field_string;
	}

	public Double getField_double() {
		return field_double;
	}
	public void setField_double(Double field_double) {
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
