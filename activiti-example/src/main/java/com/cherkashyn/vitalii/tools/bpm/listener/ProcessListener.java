package com.cherkashyn.vitalii.tools.bpm.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessListener implements ExecutionListener {
	private static final long	serialVersionUID	= 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("ProcessEvent:" + execution.getEventName()
				+ "   instance: " + execution.getId());
	}

}
