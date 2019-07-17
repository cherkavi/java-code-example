package database;
import java.util.Date;
/** класс-обертка для таблицы INFORMATION*/
public class Information {
	/** ID */
	private int id;
	/** TEXT_VALUE */
	private String textValue;
	/** INT_VALUE */
	private int intValue;
	/** DATE_VALUE */
	private Date dateValue;
	
	public Information(){
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	
	
	
}
