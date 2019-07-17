package com.test.jmx_controller;

import jmx.utility.server.interfaces.INotification;

public interface INotificationJobber extends INotification{
	/** started new jobber */
	public void changeJobberName(String name);
	/** start, stop, pause */
	public void changeJobberState(String state);
}
