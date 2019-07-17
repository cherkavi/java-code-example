package com.ubs.omnia.research.activiti.images;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class ConsolePrinter implements JavaDelegate{

	private Expression message;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println(" >>> begin");
		System.out.println(" >>> "+execution.getParentId()+"  message: "+message.getExpressionText());
		System.out.println(" >>> "+execution.getParentId()+"  message: "+message.getValue(execution));
		System.out.println(" >>> end");
	}


}
