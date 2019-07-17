package database;

public class TableOne {
	private int field_one;
	private String field_two;
	private String field_three;
	private Double field_four;
	
	public TableOne(){
	}
	
	public int getField_one() {
		return field_one;
	}
	public void setField_one(int fieldOne) {
		field_one = fieldOne;
	}
	public String getField_two() {
		return field_two;
	}
	public void setField_two(String fieldTwo) {
		field_two = fieldTwo;
	}
	public String getField_three() {
		return field_three;
	}
	public void setField_three(String fieldThree) {
		field_three = fieldThree;
	}
	public Double getField_four() {
		return field_four;
	}
	public void setField_four(Double fieldFour) {
		field_four = fieldFour;
	}
	@Override
	public String toString() {
		return "TableOne [field_four=" + field_four + ", field_one="
				+ field_one + ", field_three=" + field_three + ", field_two="
				+ field_two + "]";
	}

	
}
