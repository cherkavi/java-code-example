package gui.editor.tn;

import gui.editor.database.HibernateConnection;

import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Utility;

public /** объект, который содержит необходимую информацию для вывода в заголовок */
class Information{
	private String address;
	private String name;
	private String number;
	private String date;
	private StringBuffer query;
	{
		query=new StringBuffer();
		query.append("select ul.naiu, npom.dm1, npom.lt1,arn.naim, npom.nd, npom.dd\n");
		query.append("from npom\n");
		query.append("inner join arn on arn.akod=npom.aren\n");
		query.append("inner join ul on ul.ul=npom.ul1\n");
		query.append("where npom.kod_pom=?\n");
	}
	
	public Information(HibernateConnection connection, Object numberNpom){
		try{
			this.clearObject();
			PreparedStatement statement=connection.getConnection().prepareStatement(query.toString());
			statement.setObject(1, numberNpom);
			ResultSet rs=statement.executeQuery();
			if(rs.next()){
				// data presend
				this.address=rs.getString("NAIU")+" "+rs.getString("DM1")+" "+rs.getString("LT1");
				this.name=rs.getString("NAIM");
				this.number=rs.getString("ND");
				this.date=rs.getString("DD");
			}else{
				// no data for get
			}
			rs.close();
		}catch(Exception ex){
			System.err.println("Information Exceptin:"+ex.getMessage());
		}
	}

	public JPanel getPanel(int monthValue, int yearValue){
		JPanel returnValue=new JPanel(new GridLayout(5,1));
		returnValue.add(new JLabel("Адрес: "+this.getAddress()));
		returnValue.add(new JLabel("Название: "+this.getName()));
		returnValue.add(new JLabel("Номер дог.: "+this.getNumber()));
		returnValue.add(new JLabel("Дата дог.: "+this.getDate()));
		returnValue.add(new JLabel("Период : "+Utility.monthName[monthValue-1]+"  "+yearValue));
		return returnValue;
	}
	
	private void clearObject(){
		this.address="";
		this.name="";
		this.number="";
		this.date="";
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	
}

