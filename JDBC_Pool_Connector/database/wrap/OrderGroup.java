package database.wrap;

import javax.persistence.*;

@Entity
@Table(name="ORDER_GROUP")
public class OrderGroup {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_ORDER_GROUP_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="ID_CUSTOMER")
	private Integer idCustomer;
	@Column(name="DESCRIPTION",length=255)
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
