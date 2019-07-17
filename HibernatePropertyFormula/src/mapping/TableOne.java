package mapping;

public class TableOne {
	private int id;
	private int id_table_two;
	private String name;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_table_two() {
		return id_table_two;
	}
	public void setId_table_two(int id_table_two) {
		this.id_table_two = id_table_two;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "TableOne [id=" + id + ", id_table_two=" + id_table_two
				+ ", name=" + name + ", description=" + description + "]";
	}
	
}
