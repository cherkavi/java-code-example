package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.google.gwt.i18n.client.Constants;

public interface Edit_const extends Constants{
	/** заголовок для панели */
	@DefaultStringValue(value="Редактировать данные по сотрудникам")
	public String title();
	
	/** кнопка "Добавить сотрудника" */
	@DefaultStringValue(value="Добавить")
	public String buttonAdd();

	/** кнопка "Редактировать сотрудника" */
	@DefaultStringValue(value="Редактировать")
	public String buttonEdit();

	/** заголовок для столбца Id */
	@DefaultStringValue(value="id")
	public String columnId();
	
	/** заголовок для столбца имя  */
	@DefaultStringValue(value="Name")
	public String columnName();
	
	/** заголовок для столбца фамилия  */
	@DefaultStringValue(value="Surname")
	public String columnSurname();

	/** заголовок для столбца Отчество  */
	@DefaultStringValue(value="FatherName")
	public String columnFatherName();

	/** заголовок для столбца номер карты */
	@DefaultStringValue(value="CardNumber")
	public String columnCardNumber();

	/** кнопка удалить  */
	@DefaultStringValue(value="Remove")
	public String buttonRemove();

	/** Заголовок для запроса удалить */
	public String titleRemove();

	/** сообщение для запроса удалить */
	public String messageRemove();

	/** кнопка отменить */
	public String buttonCancel();
}
