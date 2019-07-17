package com.cherkashyn.vitalii.examples.springshell;

import org.activiti.bpmn.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
public class ActivitiShellCommands {
    public final static String ORDER_KEY="orderId";
    private final static String ORDER_NUMBER="11111";

    @Autowired
    ProcessEngine activiti;

    @ShellMethod("start Activiti process")
    public Date startProcess(String name){
        Map<String, Object> variables = new HashMap<>();
        variables.put(ORDER_KEY, ORDER_NUMBER);
        ProcessInstance instance = activiti.getRuntimeService().startProcessInstanceByKey(name, variables);
        return ((ProcessInstance) instance).getStartTime();
    }

    @ShellMethod("print all processes")
    public List<String> declaredProcesses(){
        return activiti.getRepositoryService().createProcessDefinitionQuery()
                // .processDefinitionKey("invoice")
                .orderByProcessDefinitionId()
                .asc()
                .list().stream()
                .map(processDefinition -> String.format("id: %s, key: %s, name:%s ", processDefinition.getId(), processDefinition.getKey(), processDefinition.getName()))
                .collect(Collectors.toList());
    }

    @ShellMethod("print all deployments")
    public List<String> deployedProcesses(){
        return activiti.getRepositoryService().createDeploymentQuery().list().stream()
                .map(processDefinition -> String.format("id: %s, key: %s, name:%s ", processDefinition.getId(), processDefinition.getKey(), processDefinition.getName()))
                .collect(Collectors.toList());
    }

    @ShellMethod("print all executions with 'OrderKey=OrderNumber' ")
    public List<String> runtimeProcesses(){
        return getRuntimeProcessByVariable(ORDER_KEY, ORDER_NUMBER)
                .stream().map(execution->
                        String.format("id: %s, name: %s,  ", execution.getId(), execution.getName()))
                .collect(Collectors.toList());
    }

    @ShellMethod("print active tasks ")
    public void runtimeProcessActiveTask(){
        ExecutionEntity processExecution = (ExecutionEntity) getRuntimeProcessByVariable(ORDER_KEY, ORDER_NUMBER).get(0);
        BpmnModel model = getBpmnModelFromProcessExecution(processExecution);

        String processInstanceId =    processExecution.getProcessInstanceId();
        for(Task activeTask : activiti.getTaskService().createTaskQuery().processInstanceId(processInstanceId).active().list()){
            System.out.println(">>> Name: "+activeTask.getName() + " ExecutionId: " + activeTask.getExecutionId());
            System.out.println(">>> Assignee: "+activeTask.getAssignee() + " Category: " + activeTask.getCategory() +" Description: "+activeTask.getDescription());
            readUsers(model, activeTask);
        }
    }

    private void readUsers(BpmnModel model, Task activeTask) {
        FlowElement flowElement = model.getMainProcess().getFlowElement(activeTask.getTaskDefinitionKey());
        if(flowElement instanceof UserTask){
            System.out.println("CandidateGroups: "+((UserTask)flowElement).getCandidateGroups());
            System.out.println("CandidateUsers: "+((UserTask)flowElement).getCandidateUsers());
            System.out.println("CustomProperties: "+((UserTask)flowElement).getCustomProperties());
        }
    }

    @ShellMethod("complete UserTask")
    public void completeTask(){
        ExecutionEntity processExecution = (ExecutionEntity) getRuntimeProcessByVariable(ORDER_KEY, ORDER_NUMBER).get(0);
        String processInstanceId = processExecution.getProcessInstanceId();
        for(Task activeTask : activiti.getTaskService().createTaskQuery().processInstanceId(processInstanceId).active().list()){
            activiti.getTaskService().complete(activeTask.getId());
        }
    }

    private BpmnModel getBpmnModelFromProcessExecution(ExecutionEntity processExecution) {
        return activiti.getRepositoryService().getBpmnModel(processExecution.getProcessDefinitionId());
    }

    private void readExtensionElementFromBpmnModel(BpmnModel model, Task activeTask) {
        FlowElement flowElement = model.getMainProcess().getFlowElement(activeTask.getTaskDefinitionKey());
        for(Map.Entry<String, List<ExtensionElement>> each: flowElement.getExtensionElements().entrySet()){
            System.out.println(String.format(">>> key: %s ", each.getKey()));
            for(ExtensionElement element: each.getValue()){
                System.out.println(String.format("> > > name: %s    text: %s   child.string: %s ", element.getName(), element.getElementText(), element.getChildElements().get("string").get(0).getElementText()));
            }
        }
    }

    @ShellMethod("print image")
    public void image(){
        ExecutionEntity processInstance = (ExecutionEntity) getRuntimeProcessByVariable(ORDER_KEY, ORDER_NUMBER).get(0);
        BpmnModel model = getBpmnModelFromProcessExecution(processInstance);
        if(modelExists(model)){
            writeImageOnDisk(model, processInstance.getId(), "c:\\temp\\out.jpg");
        }
    }

    private List<Execution> getRuntimeProcessByVariable(String name, String value) {
        return activiti.getRuntimeService()
                .createExecutionQuery()
                .variableValueEquals(name, value)
                .list();
    }

    private void writeImageOnDisk(BpmnModel model, String processInstanceId, String path) {
        // ProcessDiagramGenerator diagramGenerator = activiti.getProcessEngineConfiguration().getProcessDiagramGenerator();
        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        try (InputStream diagram = generator.generateDiagram(
                model,
                "jpg",
                activiti.getRuntimeService().getActiveActivityIds(processInstanceId),
                getHighLightFlows(model, activiti.getRuntimeService().getActiveActivityIds(processInstanceId))
        )){
            FileCopyUtils.copy(diagram, new FileOutputStream(new File(path)));
        } catch (IOException e) {
            System.err.println("can't save into output JPG file: "+e.getMessage());
        }
    }

    private List<String> getHighLightFlows(BpmnModel model, List<String> activeActivityIds) {
        List<String> returnValue = new ArrayList<>();
        // executionId -> task
        for(String eachTaskModelId: activeActivityIds){
            FlowElement element = model.getFlowElement(eachTaskModelId);
            if(element instanceof FlowNode){
                returnValue.addAll(((FlowNode)element).getOutgoingFlows().stream().map(f->f.getId()).collect(Collectors.toList()));
            }
        }
        // task ->
        return returnValue;
    }

    private List<SequenceFlow> getAllSequenceFlow(BpmnModel model) {
        return model.getMainProcess().getFlowElements().stream()
                .filter(e->e instanceof SequenceFlow)
                .map(sf->(SequenceFlow)sf)
                .collect(Collectors.toList());
    }

    private boolean modelExists(BpmnModel model) {
        return (model != null && model.getLocationMap().size() > 0);
    }

}
