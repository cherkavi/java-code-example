package spring_another_property;

public class AnotherBean {
	private String dateCreate;

	public AnotherBean(){
		this.dateCreate=(new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
	}
	
	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public String toString() {
		return "AnotherBean [dateCreate=" + dateCreate + "]";
	}
	
	
}
