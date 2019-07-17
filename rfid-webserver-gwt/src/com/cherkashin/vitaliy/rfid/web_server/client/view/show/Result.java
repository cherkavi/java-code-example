package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import java.util.Date;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Result implements IsSerializable{
	private Date date;
	private String userName;
	private Date dateIn;
	private Date dateOut;
	private int minutes;

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	 
}
