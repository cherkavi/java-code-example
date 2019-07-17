package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Card implements IsSerializable{
	private int id;
	private Integer idUser;
	private String cardNumber;
	private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
