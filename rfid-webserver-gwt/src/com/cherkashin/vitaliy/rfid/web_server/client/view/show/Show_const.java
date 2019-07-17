package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import com.google.gwt.i18n.client.Constants;

public interface Show_const extends Constants{
	/** заголовок для панели */
	@DefaultStringValue(value="Данные по сотрудникам")
	public String title();
	
	/** кнопка "отобразить" */
	@DefaultStringValue(value="Отобразить")
	public String buttonShow();
	
	/** надпись над датой начала */
	@DefaultStringValue(value="Дата начала")
	public String dateBegin();

	/** надпись над датой окончания*/
	@DefaultStringValue(value="Дата окончания")
	public String dateEnd();
}
