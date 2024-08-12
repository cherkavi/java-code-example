package com.cherkashyn.vitaliy.bpmn.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessEndListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		System.out.println(">>> ProcessEnd:"+delegateExecution.getId());
	}


}
