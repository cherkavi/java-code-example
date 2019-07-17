package gui.editor.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="TARIFF")
public class Tariff {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="generator",sequenceName="GEN_TARIFF_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="ID_PAY_KIND")
	private int idPayKind;
	@Column(name="PRICE")
	private float price;
	@Column(name="IS_ACTIVE")
	private int isActive;
	@Column(name="MONTH_VALUE")
	private int month;
	@Column(name="YEAR_VALUE")
	private int year;
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
	 * @return the idPayKind
	 */
	public int getIdPayKind() {
		return idPayKind;
	}
	/**
	 * @param idPayKind the idPayKind to set
	 */
	public void setIdPayKind(int idPayKind) {
		this.idPayKind = idPayKind;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	
}
