package com_port_controller;

/** оповещение о приходе данных с порта */
public interface IDataNotify {
	/** оповещение о наличии данных с порта */
	public void notifyDataFromPort(byte[] array);
}
