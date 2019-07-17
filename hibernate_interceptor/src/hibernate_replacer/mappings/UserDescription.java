package hibernate_replacer.mappings;

import java.util.Date;

public class UserDescription {
	private int id;
	private String description;
	private Date dateWrite;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	
	@Override
	public String toString() {
		return "Users [id=" + id + ", description="
				+ description + ", dateWrite=" + dateWrite + "]";
	}
	
}
