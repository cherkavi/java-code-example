package database;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ACTION")
public class Action {
	@Id
	@SequenceGenerator(name="generator",sequenceName="GEN_ACTION_ID")
	@GeneratedValue(generator="generator",strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="ACTION_DATE")
	private Date actionDate;
	@Column(name="ACTION_VALUE")
	private Short actionValue;
	@Column(name="FLAG")
	private Integer flag;
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Date getActionDate() {
		return actionDate;
	}



	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}



	public Short getActionValue() {
		return actionValue;
	}



	public void setActionValue(Short actionValue) {
		this.actionValue = actionValue;
	}



	public Integer getFlag() {
		return flag;
	}



	public void setFlag(Integer flag) {
		this.flag = flag;
	}



	public Action(){
		
	}
}
