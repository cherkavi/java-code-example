package jmx.utility.server.test;

import java.util.Arrays;

import jmx.utility.server.description_decorator.AMBeanNotification;
import jmx.utility.server.description_decorator.AMBeanNotificationSkip;

public class TestNotification implements ITestNotification{

	@AMBeanNotificationSkip
	@Override
	public void notifyArrayValue(String[] values) {
		System.out.println("notifyArrayValue:"+Arrays.toString(values));
	}

	@AMBeanNotification(description="simple heart beat ")
	@Override
	public void notifyIntValue(int counter) {
		System.out.println("notifyIntValue:"+counter);
	}

	@AMBeanNotification(description="time from source, may be different from destination")
	@Override
	public void notifyStringValue(String tempValue) {
		System.out.println("notifyStringValue:"+tempValue);
	}

}
