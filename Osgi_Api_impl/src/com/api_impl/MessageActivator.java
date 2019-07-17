package com.api_impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.api.IMessage;

public class MessageActivator implements BundleActivator{
	private ServiceRegistration serviceRegistration;
	@Override
	public void start(BundleContext context) throws Exception {
		this.serviceRegistration=context.registerService(IMessage.class.getName(), new MessageImpl(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.serviceRegistration.unregister();
	}

}
