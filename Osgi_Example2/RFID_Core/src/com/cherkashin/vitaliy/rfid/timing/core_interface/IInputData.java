package com.cherkashin.vitaliy.rfid.timing.core_interface;

/** интерфейс для входящих данных из внешних источников */
public interface IInputData {
	/** оповестить слушателей о входящих данных из различных источников */
	public void notifyAboutInputData(byte[] data);
}
