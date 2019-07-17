package gui.editor.database.wrap;
import javax.persistence.*;

@Entity
@Table(name="PAY_KIND")
public class PayKind {
	@Id
	@Column(name="KOD")
	@SequenceGenerator(name="generator",sequenceName="GEN_PAY_KIND_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	private int id;
	@Column(name="NAME",length=50)
	private String name;
	@Column(name="UNIT",length=25)
	private String unit;
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
