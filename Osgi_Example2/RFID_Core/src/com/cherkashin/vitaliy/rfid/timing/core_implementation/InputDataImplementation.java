package com.cherkashin.vitaliy.rfid.timing.core_implementation;

import com.cherkashin.vitaliy.rfid.timing.core_interface.IInputData;

public class InputDataImplementation implements IInputData{

	@Override
	public void notifyAboutInputData(byte[] data) {
		System.out.println("RFID_Core get Data:"+new String(data));
	}
	
}
