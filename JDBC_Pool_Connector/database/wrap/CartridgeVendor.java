package database.wrap;
import javax.persistence.*;

@Entity
@Table(name="CARTRIDGE_VENDOR")
public class CartridgeVendor {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_CARTRIDGE_VENDOR_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="NAME",length=100)
	private String name;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
