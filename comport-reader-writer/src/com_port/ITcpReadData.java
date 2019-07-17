package com_port;


/** оповещение о прочитанных с порта TCP данных */
public interface ITcpReadData {
	/** оповещение о прочитанных с порта TCP данных  */
	public void readDataFromTcpPort(byte[] array);
}
