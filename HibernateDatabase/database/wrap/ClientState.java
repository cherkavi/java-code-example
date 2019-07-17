package pay.database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT_STATE")
public class ClientState {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_CLIENT_STATE_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int id;
	@Column(name="NAME",length=50)
	private String name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
