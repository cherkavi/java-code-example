import java.text.SimpleDateFormat;
import java.util.Date;


public class Destination {
	private final static SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
	private int id;
	private String name;
	private Date createDate;
	
	public Destination(){
		this.createDate=new Date();
	}

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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override 
	public String toString(){
		try{
			return " Id:"+this.getId()+"  Name:"+this.getName()+"  CreateDate:"+sdf.format(this.createDate);
		}catch(Exception ex){
			return "";
		}
	}
	
}
