package mapping;

public class TableOneInnerJoinTableTwo extends TableOne{
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TableOneInnerJoinTableTwo [description=" + description
				+ ", getId()=" + getId() + ", getId_table_two()="
				+ getId_table_two() + ", getName()=" + getName()
				+ ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
