package com.cherkashin.vitaliy.rfid.web_server.client.utility;

/** интерфейс, который реагирует на события, произошедшие в таблице (into Grid ) */
public interface ITableDataSelected {
	/** выделение объекта */
	public void selectedValue(String value);
}
