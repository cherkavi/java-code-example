package database.wrap;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="ADDRESS") 
public class Address {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_ADDRESS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="POST_KOD")
    	private String post_kod;

	@Column(name="CITY_NAME")
    	private String city_name;

	@Column(name="STREET_NAME")
    	private String street_name;

	@Column(name="HOME_NUMBER")
    	private String home_number;

	@Column(name="HOME_NUMBER_ADD")
    	private String home_number_add;

	@Column(name="APPARTMENT_NUMBER")
    	private String appartment_number;

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
	 * @return the post_kod
	 */
	public String getPost_kod() {
		return post_kod;
	}

	/**
	 * @param postKod the post_kod to set
	 */
	public void setPost_kod(String postKod) {
		post_kod = postKod;
	}

	/**
	 * @return the city_name
	 */
	public String getCity_name() {
		return city_name;
	}

	/**
	 * @param cityName the city_name to set
	 */
	public void setCity_name(String cityName) {
		city_name = cityName;
	}

	/**
	 * @return the street_name
	 */
	public String getStreet_name() {
		return street_name;
	}

	/**
	 * @param streetName the street_name to set
	 */
	public void setStreet_name(String streetName) {
		street_name = streetName;
	}

	/**
	 * @return the home_number
	 */
	public String getHome_number() {
		return home_number;
	}

	/**
	 * @param homeNumber the home_number to set
	 */
	public void setHome_number(String homeNumber) {
		home_number = homeNumber;
	}

	/**
	 * @return the home_number_add
	 */
	public String getHome_number_add() {
		return home_number_add;
	}

	/**
	 * @param homeNumberAdd the home_number_add to set
	 */
	public void setHome_number_add(String homeNumberAdd) {
		home_number_add = homeNumberAdd;
	}

	/**
	 * @return the appartment_number
	 */
	public String getAppartment_number() {
		return appartment_number;
	}

	/**
	 * @param appartmentNumber the appartment_number to set
	 */
	public void setAppartment_number(String appartmentNumber) {
		appartment_number = appartmentNumber;
	}

	
}
