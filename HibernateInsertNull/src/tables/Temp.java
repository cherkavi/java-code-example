package tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="TEMP")
public class Temp {
	@Id
	@SequenceGenerator(name="temp_generator", sequenceName="TEMP_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="temp_generator")
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME",length=50)
	private String name;
	@Column(name="DATE_WRITE")
	private Date timeStamp;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Temp [id=" + id + ", name=" + name + ", timeStamp=" + timeStamp
				+ "]";
	}

	
}
