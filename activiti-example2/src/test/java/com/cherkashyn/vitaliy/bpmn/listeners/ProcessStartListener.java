package com.cherkashyn.vitaliy.bpmn.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessStartListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		System.out.println(">>> ProcessStart:"+delegateExecution.getId());
	}


}
