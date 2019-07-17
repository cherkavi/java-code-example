package com.cherkashin.vitaliy.rfid.timing;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.cherkashin.vitaliy.rfid.timing.core_implementation.InputDataImplementation;
import com.cherkashin.vitaliy.rfid.timing.core_interface.IInputData;

public class CoreBundle implements BundleActivator{
	private ServiceRegistration serviceRegistration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		serviceRegistration=context.registerService(IInputData.class.getName(), // уникальное имя в пределах системы для поиска данного сервиса  
													new InputDataImplementation(), // объект, который содержит реализацию данного сервиса 
													null
													);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// Felix will be do it automatically
		context.ungetService(serviceRegistration.getReference());
	}

}
