package com.ubs.omnia.research.activiti.images;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;

public class ProcessDiagramGeneratorProxy extends ProcessDiagramGenerator{
	
	  public static InputStream generatePngDiagram(BpmnModel bpmnModel) {
		  return ProcessDiagramGenerator.generatePngDiagram(bpmnModel);
	  }

	  public static InputStream generateJpgDiagram(BpmnModel bpmnModel) {
		  return ProcessDiagramGenerator.generateJpgDiagram(bpmnModel);
	  }

	  public static InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities) {
		    ProcessDiagramCanvas canvas = generateDiagram(bpmnModel, highLightedActivities, Collections.<String> emptyList());
		    return canvas.generateImage(imageType);
	  }
	  
	  

	  public static InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows) {
		  return ProcessDiagramGenerator.generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows);
	  }
	  
	  // IMPORTANT - override static method - HARD CODE
	  protected static ProcessDiagramCanvas generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities, List<String> highLightedFlows) {
		  ProcessDiagramCanvasDecorator processDiagramCanvas = new ProcessDiagramCanvasDecorator(initProcessDiagramCanvas(bpmnModel));
		    
//		    // Draw pool shape, if process is participant in collaboration
		    for (Pool pool : bpmnModel.getPools()) {
		      GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
		      processDiagramCanvas.drawPoolOrLane(pool.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), 
		              (int) graphicInfo.getWidth(),  (int) graphicInfo.getHeight());
		    }
		    
		    // Draw lanes
		    for (Process process : bpmnModel.getProcesses()) {
		      for (Lane lane : process.getLanes()) {
		        GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
		        processDiagramCanvas.drawPoolOrLane(lane.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), 
		                (int) graphicInfo.getWidth(),  (int) graphicInfo.getHeight());
		      }
		    }
		    
		    // Draw activities and their sequence-flows
		    for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)) {
		      drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows);
		    }
		    
		    // Draw artifacts
		    for (Process process : bpmnModel.getProcesses()) {
		      for (Artifact artifact : process.getArtifacts()) {
		        drawArtifact(processDiagramCanvas, bpmnModel, artifact);
		      }
		    }
		    
		    return processDiagramCanvas;
		  }
	  
}
