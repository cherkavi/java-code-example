package database.wrap;

import javax.persistence.*;

@Entity
@Table(name="POINTS")
public class Points {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_POINTS_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="NAME")
	private String name;
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
	
	
}
