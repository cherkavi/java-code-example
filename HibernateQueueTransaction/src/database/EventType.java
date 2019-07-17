package database;
import javax.persistence.*;

@Entity
@Table(name="EVENT_TYPE")
public class EventType {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_EVENT_NAME_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="EVENT_NAME")
	private String eventName;
	
	public EventType(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	
}
