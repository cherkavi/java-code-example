package database;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="EVENT")
public class Event {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_EVENT_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="DATE_SENSOR")
	private Date dateSensor;
	@Column(name="DATE_CAMERA")
	private Date dateCamera;
	@Column(name="ID_EVENT_TYPE")
	private Integer idEventType;
	@Column(name="FILENAME")
	private String filename;
	@Column(name="ID_ACTION")
	private Integer idAction;

	public Event(){
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateSensor() {
		return dateSensor;
	}
	public void setDateSensor(Date dateSensor) {
		this.dateSensor = dateSensor;
	}
	public Date getDateCamera() {
		return dateCamera;
	}
	public void setDateCamera(Date dateCamera) {
		this.dateCamera = dateCamera;
	}
	public Integer getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(Integer idEventType) {
		this.idEventType = idEventType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getIdAction() {
		return idAction;
	}
	public void setIdAction(Integer idAction) {
		this.idAction = idAction;
	}
}
