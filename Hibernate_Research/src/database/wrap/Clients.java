package database.wrap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTS")
public class Clients {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_CLIENTS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int id;
	@Column(name="ID_SATELLITE")
	private int id_satellite;
	@Column(name="UNIQUE_KEY_SATELLITE",length=50)
	private String unique_key_satellite;
	@Column(name="UNIQUE_KEY_SERVICE",length=50)
	private String unique_key_service;
	@Column(name="RETURN_URL",length=50)
	private String return_url;
	@Column(name="ID_CLIENT_STATE")
	private int id_client_state;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_satellite() {
		return id_satellite;
	}
	public void setId_satellite(int id_satellite) {
		this.id_satellite = id_satellite;
	}
	public String getUnique_key_satellite() {
		return unique_key_satellite;
	}
	public void setUnique_key_satellite(String unique_key_satellite) {
		this.unique_key_satellite = unique_key_satellite;
	}
	public String getUnique_key_service() {
		return unique_key_service;
	}
	public void setUnique_key_service(String unique_key_service) {
		this.unique_key_service = unique_key_service;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public int getId_client_state() {
		return id_client_state;
	}
	public void setId_client_state(int id_client_state) {
		this.id_client_state = id_client_state;
	}
	
	
}
