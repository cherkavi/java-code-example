package com.cherkashyn.vitaliy.bpmn.diagram_scaner;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:application-test.xml"})
public class DiagramLoaderTest {

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	RuntimeService runtimeService;
	
	@Test
	public void test(){
		// List<ProcessDefinition> processList=repositoryService.createProcessDefinitionQuery().list();
		// ProcessInstance processInstance=runtimeService.startProcessInstanceById("PROCESS_1");
		Assert.assertTrue(repositoryService.createDeploymentQuery().list().size()>0);
		System.out.println("OK");
	}
}
