package com_port_controller.listener;

/** слушатель COM-порта  */
public interface ISocketPortListener {
	/** оповщение о новых данных, которые пришли на порт */
	public void notifyDataFromPort(byte[] data);
}
