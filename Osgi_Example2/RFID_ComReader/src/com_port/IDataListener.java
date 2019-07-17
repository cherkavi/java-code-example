package com_port;

/** оповещение о наличии данных, прочитанных из порта */
public interface IDataListener {
	/** оповещение о наличии данных, прочитанных из порта */
	public void dataEvent(byte[] data);
}
