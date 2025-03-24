package database.wrap;
import javax.persistence.*;

@Entity
@Table(name="CARTRIDGE_MODEL")
public class CartridgeModel {
	@Id
	@SequenceGenerator(name="generator", sequenceName="GEN_CARTRIDGE_MODEL_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="ID_VENDOR")
	private Integer idVendor;
	@Column(name="NAME",length=100)
	private String name;
	@Column(name="PRICE")
	private Float price;
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
	/**
	 * @return the idVendor
	 */
	public Integer getIdVendor() {
		return idVendor;
	}
	/**
	 * @param idVendor the idVendor to set
	 */
	public void setIdVendor(Integer idVendor) {
		this.idVendor = idVendor;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	
}
