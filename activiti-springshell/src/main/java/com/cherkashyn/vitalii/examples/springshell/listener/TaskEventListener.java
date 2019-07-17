package com.cherkashyn.vitalii.examples.springshell.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskEventListener implements TaskListener {
    private static final long	serialVersionUID	= 1L;

    @Override
    public void notify(DelegateTask task) {
        System.out.println("   TaskEvent:" + task.getEventName());
    }

}
