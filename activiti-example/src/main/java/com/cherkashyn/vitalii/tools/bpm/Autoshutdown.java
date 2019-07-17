package com.cherkashyn.vitalii.tools.bpm;

import java.util.concurrent.TimeUnit;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Autoshutdown {

	public static void main(String[] args) {
		System.out.println("---begin---");

		String processId = "autoshutdown";

		// create Context
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring-context.xml");

		// get Runtime service
		RuntimeService service = context.getBean(RuntimeService.class);
		startProcessByName(service, processId);

		ProcessInstance processInstance = service.createProcessInstanceQuery()
				.processDefinitionKey(processId).active().singleResult();
		System.out.println("Instance: " + processInstance);

		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("message", "shutdown222");
		// service.signal(processInstance.getId(), params);

		service.signalEventReceived("shutdown",
				processInstance.getProcessInstanceId());

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
		}

		System.out.println("--- end ---");

	}

	private static void startProcessByName(RuntimeService service,
			String processId) {
		// build process
		ProcessInstanceBuilder processBuilder = service
				.createProcessInstanceBuilder().processDefinitionKey(processId);
		// add variables
		processBuilder.addVariable("var1", "variable1");

		// start process
		processBuilder.start();
		// service.startProcessInstanceByKey("photo-processing");
	}

}
