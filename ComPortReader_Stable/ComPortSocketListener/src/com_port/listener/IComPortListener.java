package com_port.listener;

/** слушатель COM-порта  */
public interface IComPortListener {
	/** оповщение о новых данных, которые пришли на порт */
	public void notifyDataFromPort(byte[] data);
}
