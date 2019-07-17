package com.ubs.omnia.research.activiti.images;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.h2.util.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
	private final static String CONTEXT="classpath:/context.xml";
	private final static String PATH_PROCESS="TempProcess.bpmn";
	private final static String PROCESS_KEY="TempProcess";
	private final static String PATH_OUTPUT_IMAGE="d:\\temp\\out.png";
	private final static String PATH_OUTPUT_IMAGE_ACTIVE="d:\\temp\\out_active.png";
	
    public static void main( String[] args ) throws IOException
    {
        System.out.println( " --- start ---" );

    	ApplicationContext context=new ClassPathXmlApplicationContext(CONTEXT);
    	System.out.println("Spring Context - "+context);
    	
    	ProcessEngineConfiguration processEngineConfiguration=context.getBean(ProcessEngineConfiguration.class);
        System.out.println("Activiti ProcessEngineConfiguration - "+processEngineConfiguration);
        
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
        System.out.println("Activiti ProcessEngineConfiguration.processEngine - "+processEngine);
        
        Deployment deployment=processEngine.getRepositoryService().createDeployment().addClasspathResource(PATH_PROCESS).deploy();
        System.out.println("Activiti ProcessEngineConfiguration.repositoryService.createDeployment.id - "+deployment.getId());

        ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_KEY);
        System.out.println("Activiti ProcessEngineConfiguration.repositoryService.startProcess - "+processInstance);
        
        System.out.println("Instance count: "+processEngine.getRuntimeService().createProcessInstanceQuery().count());
        
        generateImage(processEngine, processInstance);
        generateImageActive(processEngine, processInstance);
        
        System.out.println( " --- end ---" );
    }
    
    private static void generateImageActive(ProcessEngine processEngine, ProcessInstance processInstance) throws IOException{
        BpmnModel bpmnModel=processEngine.getRepositoryService().getBpmnModel(processInstance.getProcessDefinitionId());

        List<FlowElement> flowElements=bpmnModel.getProcesses().get(0).findFlowElementsOfType(org.activiti.bpmn.model.FlowElement.class);
        for(FlowElement eachElement:flowElements){
        	System.out.println(" - "+eachElement.getClass().getSimpleName()+"  :  "+eachElement.getName());
        }
        
        OutputStream outputStream=null;
        InputStream inputStream=null;
        try{
            outputStream=new FileOutputStream(PATH_OUTPUT_IMAGE_ACTIVE);
            List<String> activeIds=processEngine.getRuntimeService().getActiveActivityIds(processInstance.getId());
            // generate diagram with highlighting of current task 
            inputStream=ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeIds);
            IOUtils.copy(inputStream, outputStream);
        }finally{
            IOUtils.closeSilently(inputStream);
            IOUtils.closeSilently(outputStream);
        }
    }
    
    
    private static void generateImage(ProcessEngine processEngine, ProcessInstance processInstance) throws IOException{
        BpmnModel bpmnModel=processEngine.getRepositoryService().getBpmnModel(processInstance.getProcessDefinitionId());

        OutputStream outputStream=null;
        InputStream inputStream=null;
        try{
            outputStream=new FileOutputStream(PATH_OUTPUT_IMAGE);
            // generate diagram with highlighting of current task 
            inputStream=ProcessDiagramGenerator.generatePngDiagram(bpmnModel);
            IOUtils.copy(inputStream, outputStream);
        }finally{
            IOUtils.closeSilently(inputStream);
            IOUtils.closeSilently(outputStream);
        }
    }
    
}
