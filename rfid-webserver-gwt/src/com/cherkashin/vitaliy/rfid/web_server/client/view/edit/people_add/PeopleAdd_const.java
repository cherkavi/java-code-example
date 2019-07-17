package com.cherkashin.vitaliy.rfid.web_server.client.view.edit.people_add;

import com.google.gwt.i18n.client.Constants;

public interface PeopleAdd_const extends Constants{
	/** заголовок для панели */
	@DefaultStringValue(value="Редактировать данные по сотрудникам")
	public String title();
	
	/** заголовок "Фамилия" */
	@DefaultStringValue(value="Фамилия")
	public String surname();

	/** заголовок "Имя" */
	@DefaultStringValue(value="Имя")
	public String name();
	
	/** кнопка "Сохранить" */
	@DefaultStringValue(value="Сохранить")
	public String buttonSave();

	/** кнопка "Отменить" */
	@DefaultStringValue(value="Отменить")
	public String buttonCancel();

	/** сообщение о пустой фамилии */
	@DefaultStringValue(value="Input Surname")
	public String alertEmptySurname();
	
	/** ошибка сохранения */
	@DefaultStringValue(value="Ошибка сохранения")
	public String titleSaveError();

	/** сообщение о пустом имени */
	@DefaultStringValue(value="Input name")
	public String alertEmptyName();

	/** заголовок отчество */
	@DefaultStringValue(value="Father Name")
	public String fatherName();

	/** заголовок номер карты */
	@DefaultStringValue(value="Number of Card")
	public String cardNumber();
	
	/** уникальный номер карты */
	@DefaultStringValue(value="Card Number")
	public String alertEmptyCardNumber();
	
	/** заголовок для столбца Id */
	@DefaultStringValue(value="id")
	public String columnId();

	/** заголовок для столбца Номер карты */
	@DefaultStringValue(value="card number")
	public String columnCardNumber();

	/** заголовок для столбца даты */
	@DefaultStringValue(value="date")
	public String columnDate();
	
}
