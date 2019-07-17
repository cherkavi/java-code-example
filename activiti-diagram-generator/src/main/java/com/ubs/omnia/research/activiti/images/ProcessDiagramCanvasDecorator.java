package com.ubs.omnia.research.activiti.images;

import java.awt.Image;
import java.awt.geom.Line2D.Double;
import java.io.InputStream;
import java.util.List;

import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas.SHAPE_TYPE;

public class ProcessDiagramCanvasDecorator extends ProcessDiagramCanvas {
	ProcessDiagramCanvas realObject;
	
	public ProcessDiagramCanvasDecorator(ProcessDiagramCanvas realObject){
		this.realObject=realObject;
		
	}

	public InputStream generateImage(String imageType) {
		return realObject.generateImage(imageType);
	}

	public void close() {
		realObject.close();
	}

	public void drawNoneStartEvent(int x, int y, int width, int height) {
		realObject.drawNoneStartEvent(x, y, width, height);
	}

	public void drawTimerStartEvent(int x, int y, int width, int height) {
		realObject.drawTimerStartEvent(x, y, width, height);
	}

	public void drawStartEvent(int x, int y, int width, int height, Image image) {
		realObject.drawStartEvent(x, y, width, height, image);
	}

	public void drawNoneEndEvent(int x, int y, int width, int height) {
		realObject.drawNoneEndEvent(x, y, width, height);
	}

	public void drawErrorEndEvent(String name, int x, int y, int width,
			int height) {
		realObject.drawErrorEndEvent(name, x, y, width, height);
	}

	public void drawErrorEndEvent(int x, int y, int width, int height) {
		realObject.drawErrorEndEvent(x, y, width, height);
	}

	public void drawErrorStartEvent(int x, int y, int width, int height) {
		realObject.drawErrorStartEvent(x, y, width, height);
	}

	public void drawCatchingEvent(int x, int y, int width, int height,
			boolean isInterrupting, Image image) {
		realObject
				.drawCatchingEvent(x, y, width, height, isInterrupting, image);
	}

	public void drawCatchingTimerEvent(String name, int x, int y, int width,
			int height, boolean isInterrupting) {
		realObject.drawCatchingTimerEvent(name, x, y, width, height,
				isInterrupting);
	}

	public void drawCatchingTimerEvent(int x, int y, int width, int height,
			boolean isInterrupting) {
		realObject.drawCatchingTimerEvent(x, y, width, height, isInterrupting);
	}

	public void drawCatchingErrorEvent(String name, int x, int y, int width,
			int height, boolean isInterrupting) {
		realObject.drawCatchingErrorEvent(name, x, y, width, height,
				isInterrupting);
	}

	public void drawCatchingErrorEvent(int x, int y, int width, int height,
			boolean isInterrupting) {
		realObject.drawCatchingErrorEvent(x, y, width, height, isInterrupting);
	}

	public void drawCatchingSignalEvent(String name, int x, int y, int width,
			int height, boolean isInterrupting) {
		realObject.drawCatchingSignalEvent(name, x, y, width, height,
				isInterrupting);
	}

	public void drawCatchingSignalEvent(int x, int y, int width, int height,
			boolean isInterrupting) {
		realObject.drawCatchingSignalEvent(x, y, width, height, isInterrupting);
	}

	public void drawThrowingSignalEvent(int x, int y, int width, int height) {
		realObject.drawThrowingSignalEvent(x, y, width, height);
	}

	public void drawThrowingNoneEvent(int x, int y, int width, int height) {
		realObject.drawThrowingNoneEvent(x, y, width, height);
	}

	public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY,
			boolean conditional) {
		realObject.drawSequenceflow(srcX, srcY, targetX, targetY, conditional);
	}

	public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY,
			boolean conditional, boolean highLighted) {
		realObject.drawSequenceflow(srcX, srcY, targetX, targetY, conditional,
				highLighted);
	}

	public void drawAssociation(int[] xPoints, int[] yPoints,
			AssociationDirection associationDirection, boolean highLighted) {
		realObject.drawAssociation(xPoints, yPoints, associationDirection,
				highLighted);
	}

	public void drawSequenceflow(int[] xPoints, int[] yPoints,
			boolean conditional, boolean isDefault, boolean highLighted) {
		realObject.drawSequenceflow(xPoints, yPoints, conditional, isDefault,
				highLighted);
	}

	public void drawConnection(int[] xPoints, int[] yPoints,
			boolean conditional, boolean isDefault, String connectionType,
			AssociationDirection associationDirection, boolean highLighted) {
		realObject.drawConnection(xPoints, yPoints, conditional, isDefault,
				connectionType, associationDirection, highLighted);
	}

	public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX,
			int targetY, boolean conditional) {
		realObject.drawSequenceflowWithoutArrow(srcX, srcY, targetX, targetY,
				conditional);
	}

	public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX,
			int targetY, boolean conditional, boolean highLighted) {
		realObject.drawSequenceflowWithoutArrow(srcX, srcY, targetX, targetY,
				conditional, highLighted);
	}

	public void drawArrowHead(Double line) {
		realObject.drawArrowHead(line);
	}

	public void drawDefaultSequenceFlowIndicator(Double line) {
		realObject.drawDefaultSequenceFlowIndicator(line);
	}

	public void drawConditionalSequenceFlowIndicator(Double line) {
		realObject.drawConditionalSequenceFlowIndicator(line);
	}

	public void drawTask(String name, int x, int y, int width, int height) {
		realObject.drawTask(name, x, y, width, height);
	}

	public void drawPoolOrLane(String name, int x, int y, int width, int height) {
		realObject.drawPoolOrLane(name, x, y, width, height);
	}

	public void drawUserTask(String name, int x, int y, int width, int height) {
		realObject.drawUserTask(name, x, y, width, height);
	}

	public void drawScriptTask(String name, int x, int y, int width, int height) {
		realObject.drawScriptTask(name, x, y, width, height);
	}

	public void drawServiceTask(String name, int x, int y, int width, int height) {
		realObject.drawServiceTask(name, x, y, width, height);
	}

	public void drawReceiveTask(String name, int x, int y, int width, int height) {
		realObject.drawReceiveTask(name, x, y, width, height);
	}

	public void drawSendTask(String name, int x, int y, int width, int height) {
		realObject.drawSendTask(name, x, y, width, height);
	}

	public void drawManualTask(String name, int x, int y, int width, int height) {
		realObject.drawManualTask(name, x, y, width, height);
	}

	public void drawBusinessRuleTask(String name, int x, int y, int width,
			int height) {
		realObject.drawBusinessRuleTask(name, x, y, width, height);
	}

	public void drawExpandedSubProcess(String name, int x, int y, int width,
			int height, Boolean isTriggeredByEvent) {
		realObject.drawExpandedSubProcess(name, x, y, width, height,
				isTriggeredByEvent);
	}

	public void drawCollapsedSubProcess(String name, int x, int y, int width,
			int height, Boolean isTriggeredByEvent) {
		realObject.drawCollapsedSubProcess(name, x, y, width, height,
				isTriggeredByEvent);
	}

	public void drawCollapsedCallActivity(String name, int x, int y, int width,
			int height) {
		realObject.drawCollapsedCallActivity(name, x, y, width, height);
	}

	public void drawCollapsedMarker(int x, int y, int width, int height) {
		realObject.drawCollapsedMarker(x, y, width, height);
	}

	public void drawActivityMarkers(int x, int y, int width, int height,
			boolean multiInstanceSequential, boolean multiInstanceParallel,
			boolean collapsed) {
		realObject.drawActivityMarkers(x, y, width, height,
				multiInstanceSequential, multiInstanceParallel, collapsed);
	}

	public void drawGateway(int x, int y, int width, int height) {
		realObject.drawGateway(x, y, width, height);
	}

	public void drawParallelGateway(int x, int y, int width, int height) {
		realObject.drawParallelGateway(x, y, width, height);
	}

	public void drawExclusiveGateway(int x, int y, int width, int height) {
		realObject.drawExclusiveGateway(x, y, width, height);
	}

	public void drawInclusiveGateway(int x, int y, int width, int height) {
		realObject.drawInclusiveGateway(x, y, width, height);
	}

	public void drawEventBasedGateway(int x, int y, int width, int height) {
		realObject.drawEventBasedGateway(x, y, width, height);
	}

	public void drawMultiInstanceMarker(boolean sequential, int x, int y,
			int width, int height) {
		realObject.drawMultiInstanceMarker(sequential, x, y, width, height);
	}

	public void drawHighLight(int x, int y, int width, int height) {
		realObject.drawHighLight(x, y, width, height);
	}

	public void drawTextAnnotation(String text, int x, int y, int width,
			int height) {
		realObject.drawTextAnnotation(text, x, y, width, height);
	}

	public void drawLabel(String text, int x, int y, int width, int height) {
		realObject.drawLabel(text, x, y, width, height);
	}

	public void drawLabel(String text, int x, int y, int width, int height,
			boolean centered) {
		realObject.drawLabel(text, x, y, width, height, centered);
	}

	public List<GraphicInfo> connectionPerfectionizer(
			SHAPE_TYPE sourceShapeType, SHAPE_TYPE targetShapeType,
			GraphicInfo sourceGraphicInfo, GraphicInfo targetGraphicInfo,
			List<GraphicInfo> graphicInfoList) {
		return realObject.connectionPerfectionizer(sourceShapeType,
				targetShapeType, sourceGraphicInfo, targetGraphicInfo,
				graphicInfoList);
	}

	public boolean equals(Object arg0) {
		return realObject.equals(arg0);
	}

	public int hashCode() {
		return realObject.hashCode();
	}

	public String toString() {
		return realObject.toString();
	}
	
	
}
