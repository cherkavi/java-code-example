package database;

public class TableOne {
	private int kod;
	private String field_string;
	private double field_double;
	private java.util.Date field_date;
	private java.util.Date field_timestamp;

	public TableOne(){
		clear();
	}
	
	public void clear(){
		this.field_string="";
		this.field_double=0;
		this.field_date=new java.util.Date();
		this.field_timestamp=this.field_date;
	}
	
	public void setKod(int value){
		this.kod=value;
	}
	
	public int getKod(){
		return this.kod;
	}
	
	public String getField_string() {
		return field_string;
	}
	public void setField_string(String field_string) {
		this.field_string = field_string;
	}
	public double getField_double() {
		return field_double;
	}
	public void setField_double(double field_double) {
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
