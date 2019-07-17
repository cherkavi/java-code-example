package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Обертка для объекта People
 * <table border=1>
 * 	<tr>
 * 		<td>id</td><td>уникальный номер в базе </td>
 * 	</tr>
 * 	<tr>
 * 		<td>name</td><td>Имя</td>
 * 	</tr>
 * 	<tr>
 * 		<td>surname</td><td>Фамилия</td>
 * 	</tr>
 * 	<tr>
 * 		<td>fatherName</td><td>Отчество</td>
 * 	</tr>
 * 	<tr>
 * 		<td>cardNumber</td><td>Номер карты</td>
 * 	</tr>
 * </table>
 */
public class People implements IsSerializable{
	private int id;
	private String name;
	private String surname;
	private String fatherName;
	private String cardNumber;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
}
