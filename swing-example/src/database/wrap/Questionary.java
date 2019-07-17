package database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="QUESTIONARY") 
public class Questionary {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_QUESTIONARY_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_CUSTOMER")
    	private Integer id_customer;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id_customer
	 */
	public Integer getId_customer() {
		return id_customer;
	}

	/**
	 * @param idCustomer the id_customer to set
	 */
	public void setId_customer(Integer idCustomer) {
		id_customer = idCustomer;
	}

	
}
