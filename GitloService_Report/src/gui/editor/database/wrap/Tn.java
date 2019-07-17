package gui.editor.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="TN")
public class Tn {
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="generator",sequenceName="GEN_TN_ID")
	private int id;
	@Column(name="ID_NPOM")
	private Integer idNpom;
	@Column(name="ID_ARN")
	private Integer idArn;
	@Column(name="ID_PAY_KIND")
	private Integer idPayKind;
	@Column(name="ID_TARIFF")
	private Integer idTariff;
	@Column(name="PRICE")
	private float price;
	@Column(name="QUANTITY")
	private float quantity;
	@Column(name="AMOUNT")
	private float amount;
	@Column(name="MONTH_VALUE")
	private int monthValue;
	@Column(name="YEAR_VALUE")
	private int yearValue;
	@Column(name="MARK_BEGIN")
	private int markBegin;
	@Column(name="MARK_END")
	private int markEnd;
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
	 * @return the idNpom
	 */
	public Integer getIdNpom() {
		return idNpom;
	}
	/**
	 * @param idNpom the idNpom to set
	 */
	public void setIdNpom(Integer idNpom) {
		this.idNpom = idNpom;
	}
	/**
	 * @return the idArn
	 */
	public Integer getIdArn() {
		return idArn;
	}
	/**
	 * @param idArn the idArn to set
	 */
	public void setIdArn(Integer idArn) {
		this.idArn = idArn;
	}
	/**
	 * @return the idPayKind
	 */
	public Integer getIdPayKind() {
		return idPayKind;
	}
	/**
	 * @param idPayKind the idPayKind to set
	 */
	public void setIdPayKind(Integer idPayKind) {
		this.idPayKind = idPayKind;
	}
	/**
	 * @return the idTariff
	 */
	public Integer getIdTariff() {
		return idTariff;
	}
	/**
	 * @param idTariff the idTariff to set
	 */
	public void setIdTariff(Integer idTariff) {
		this.idTariff = idTariff;
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
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
	/**
	 * @return the monthValue
	 */
	public int getMonthValue() {
		return monthValue;
	}
	/**
	 * @param monthValue the monthValue to set
	 */
	public void setMonthValue(int monthValue) {
		this.monthValue = monthValue;
	}
	/**
	 * @return the yearValue
	 */
	public int getYearValue() {
		return yearValue;
	}
	/**
	 * @param yearValue the yearValue to set
	 */
	public void setYearValue(int yearValue) {
		this.yearValue = yearValue;
	}
	/**
	 * @return the markBegin
	 */
	public int getMarkBegin() {
		return markBegin;
	}
	/**
	 * @param markBegin the markBegin to set
	 */
	public void setMarkBegin(int markBegin) {
		this.markBegin = markBegin;
	}
	/**
	 * @return the markEnd
	 */
	public int getMarkEnd() {
		return markEnd;
	}
	/**
	 * @param markEnd the markEnd to set
	 */
	public void setMarkEnd(int markEnd) {
		this.markEnd = markEnd;
	}

	
}
