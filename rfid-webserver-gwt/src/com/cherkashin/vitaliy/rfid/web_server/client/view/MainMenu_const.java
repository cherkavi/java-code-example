package com.cherkashin.vitaliy.rfid.web_server.client.view;

import com.google.gwt.i18n.client.Constants;

public interface MainMenu_const extends Constants{
	/** заголовок для панели */
	@DefaultStringValue(value="Учет рабочего времени")
	public String title();
	
	/** кнопка "отобразить" */
	@DefaultStringValue(value="Отобразить")
	public String buttonShow();
	
	/** кнопка "редактировать"*/
	@DefaultStringValue(value="Редактировать")
	public String buttonEdit();
}
