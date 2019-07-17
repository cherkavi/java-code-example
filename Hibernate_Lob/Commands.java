package temp;
import javax.persistence.*;
@Entity
@Table(name="COMMANDS")
public class Commands {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_COMMANDS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="NAME",length=30)
	private String name;
	@Column(name="DESCRIPTION",length=255)
	private String description;

	@Lob
	@Column(name="BODY")
	private byte[] body;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
	
	
	
	
}
