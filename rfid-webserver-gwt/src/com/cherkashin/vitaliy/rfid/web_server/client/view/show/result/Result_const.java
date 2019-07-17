package com.cherkashin.vitaliy.rfid.web_server.client.view.show.result;

import com.google.gwt.i18n.client.Constants;

public interface Result_const extends Constants{
	/** заголовок для панели */
	@DefaultStringValue(value="Result of Query:")
	public String title();

	@DefaultStringValue(value="Date")
	public String columnDate();

	@DefaultStringValue(value="User name")
	public String columnUserName();

	@DefaultStringValue(value="Date In")
	public String columnDateIn();

	@DefaultStringValue(value="Date Out")
	public String columnDateOut();

	@DefaultStringValue(value="Minutes")
	public String columnMinutes();
	
}
