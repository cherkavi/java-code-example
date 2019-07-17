package database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name="CUSTOMER") 
public class Customer {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_CUSTOMER_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
    	private Integer id;

	@Column(name="ID_CUSTOMER_KIND")
    	private Integer id_customer_kind;

	@Column(name="ID_ADDRESS")
    	private Integer id_address;

	@Column(name="ID_ADDRESS_HOME")
    	private Integer id_address_home;

	@Column(name="PHONE_MOBILE")
    	private String phone_mobile;

	@Column(name="PHONE_HOME")
    	private String phone_home;

	@Column(name="BIRTH_DAY")
    	private Date birth_day;

	@Column(name="E_MAIL")
    	private String e_mail;

	@Column(name="ID_CUSTOMER_INFORMATION")
    	private Integer id_customer_information;

	@Column(name="ID_CUSTOMER_PARENT")
    	private Integer id_customer_parent;

	@Column(name="SURNAME")
    	private String surname;

	@Column(name="NAME")
    	private String name;

	@Column(name="PEN_NAME")
    	private String pen_name;

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
	 * @return the id_customer_kind
	 */
	public Integer getId_customer_kind() {
		return id_customer_kind;
	}

	/**
	 * @param idCustomerKind the id_customer_kind to set
	 */
	public void setId_customer_kind(Integer idCustomerKind) {
		id_customer_kind = idCustomerKind;
	}

	/**
	 * @return the id_address
	 */
	public Integer getId_address() {
		return id_address;
	}

	/**
	 * @param idAddress the id_address to set
	 */
	public void setId_address(Integer idAddress) {
		id_address = idAddress;
	}

	/**
	 * @return the id_address_home
	 */
	public Integer getId_address_home() {
		return id_address_home;
	}

	/**
	 * @param idAddressHome the id_address_home to set
	 */
	public void setId_address_home(Integer idAddressHome) {
		id_address_home = idAddressHome;
	}

	/**
	 * @return the phone_mobile
	 */
	public String getPhone_mobile() {
		return phone_mobile;
	}

	/**
	 * @param phoneMobile the phone_mobile to set
	 */
	public void setPhone_mobile(String phoneMobile) {
		phone_mobile = phoneMobile;
	}

	/**
	 * @return the phone_home
	 */
	public String getPhone_home() {
		return phone_home;
	}

	/**
	 * @param phoneHome the phone_home to set
	 */
	public void setPhone_home(String phoneHome) {
		phone_home = phoneHome;
	}

	/**
	 * @return the birth_day
	 */
	public Date getBirth_day() {
		return birth_day;
	}

	/**
	 * @param birthDay the birth_day to set
	 */
	public void setBirth_day(Date birthDay) {
		birth_day = birthDay;
	}

	/**
	 * @return the e_mail
	 */
	public String getE_mail() {
		return e_mail;
	}

	/**
	 * @param eMail the e_mail to set
	 */
	public void setE_mail(String eMail) {
		e_mail = eMail;
	}

	/**
	 * @return the id_customer_information
	 */
	public Integer getId_customer_information() {
		return id_customer_information;
	}

	/**
	 * @param idCustomerInformation the id_customer_information to set
	 */
	public void setId_customer_information(Integer idCustomerInformation) {
		id_customer_information = idCustomerInformation;
	}

	/**
	 * @return the id_customer_parent
	 */
	public Integer getId_customer_parent() {
		return id_customer_parent;
	}

	/**
	 * @param idCustomerParent the id_customer_parent to set
	 */
	public void setId_customer_parent(Integer idCustomerParent) {
		id_customer_parent = idCustomerParent;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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
	 * @return the pen_name
	 */
	public String getPen_name() {
		return pen_name;
	}

	/**
	 * @param penName the pen_name to set
	 */
	public void setPen_name(String penName) {
		pen_name = penName;
	}

	
	
}
