package com.cherkashyn.vitalii.examples.springshell;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class PrintActivitiEnvironment implements JavaDelegate {

    @Autowired
    ApplicationContext context;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("spring-context: " + context);
        System.out.println("ProcessDefinitionId: " + delegateExecution.getProcessDefinitionId());
        System.out.println("ActivityId: " + delegateExecution.getCurrentActivityId());
        System.out.println("OrderId: " + delegateExecution.getVariable(ActivitiShellCommands.ORDER_KEY));
    }

    public void print(){
        System.out.println("this is test");
    }
}
