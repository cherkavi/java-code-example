package database.wrap;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.*;

@Entity
@PersistenceCapable(detachable="false")
@Table(name="rfid")
public class Rfid {
	@Id
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator",sequenceName="GEN_RFID_ID")
	@Column(name="id")
	private int id;
	
	@Column(name="id_user")
	private Integer idUser;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="time_write")
	private Date timeWrite;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getTimeWrite() {
		return timeWrite;
	}

	public void setTimeWrite(Date timeWrite) {
		this.timeWrite = timeWrite;
	}
}
