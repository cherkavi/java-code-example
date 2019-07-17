package database.wrap;
import javax.persistence.*;

@Entity
@Table(name="POINT_SETTINGS")
public class PointSettings {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_POINT_SETTINGS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="ID_POINTS")
	private Integer idPoints;
	@Column(name="PRINTER_NAME")
	private String printer;
	@Column(name="PRINTER_BARCODE_NAME")
	private String printerBarcode;
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
	 * @return the idPoints
	 */
	public Integer getIdPoints() {
		return idPoints;
	}
	/**
	 * @param idPoints the idPoints to set
	 */
	public void setIdPoints(Integer idPoints) {
		this.idPoints = idPoints;
	}
	/**
	 * @return the printer
	 */
	public String getPrinter() {
		return printer;
	}
	/**
	 * @param printer the printer to set
	 */
	public void setPrinter(String printer) {
		this.printer = printer;
	}
	/**
	 * @return the printerBarcode
	 */
	public String getPrinterBarcode() {
		return printerBarcode;
	}
	/**
	 * @param printerBarcode the printerBarcode to set
	 */
	public void setPrinterBarcode(String printerBarcode) {
		this.printerBarcode = printerBarcode;
	}
	
	
}
