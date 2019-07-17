package com.cherkashyn.vitalii.tools.bpm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Autoshutdown {

	public static void main(String[] args) {
		System.out.println("---begin---");

		String processId = "autoshutdown";
		String processMessageId="shutdown";
		
		// create Context
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring-context.xml");

		// get Runtime service
		RuntimeService service = context.getBean(RuntimeService.class);
		startProcessByName(service, processId);

		ProcessInstance processInstance = service.createProcessInstanceQuery()
				.processDefinitionKey(processId).active().singleResult();
		System.out.println("Instance: " + processInstance);
		
		Execution processExecutionInstance=service.createExecutionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).messageEventSubscriptionName("shutdown").singleResult();
				// messageEventSubscriptionName(processMessageId).singleResult();

		 Map<String, Object> params = new HashMap<String, Object>();
		 // params.put("signal", "shutdown");
		 //service.signal(processInstance.getId(), params);

		service.signalEventReceived(processMessageId,processExecutionInstance.getId(), params);

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}

		System.out.println("--- end ---");

	}

	private static ProcessInstance startProcessByName(RuntimeService service,
			String processId) {
		// build process
		ProcessInstanceBuilder processBuilder = service
				.createProcessInstanceBuilder().processDefinitionKey(processId);
		// add variables
		processBuilder.addVariable("var1", "variable1");

		// start process
		return processBuilder.start();
		// service.startProcessInstanceByKey("photo-processing");
	}

}
