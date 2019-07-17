package jmx.utility.server.test;

import jmx.utility.server.interfaces.INotification;

public interface ITestNotification extends INotification{
	void notifyIntValue(int counter);
	void notifyStringValue(String tempValue);
	void notifyArrayValue(String[] values);
}
