
package bpmnExport;

import bpmnCore.Annotation;
import bpmnCore.Association;
import bpmnCore.Bounds;
import bpmnCore.Edge;
import bpmnCore.EndEvent;
import bpmnCore.Gateway;
import bpmnCore.MemoryModel;
import bpmnCore.Point;
import bpmnCore.SequenceFlow;
import bpmnCore.Shape;
import bpmnCore.StartEvent;
import bpmnCore.Task;

/**
 * The Class BPMNSerializer serializes a memory model to BPMN output format.
 */
public class BPMNSerializer extends Serializer {
	
	/** The process prefix. */
	private String processPrefix;
	
	/** The diagramm prefix. */
	private String diagrammPrefix;
	
	/** The bound prefix. */
	private String boundPrefix;
	
	/** The waypoint prefix. */
	private String waypointPrefix;
	
	/** The resolution. */
	private double resolution;
	
	/**
	 * Class constructor instantiates a new BPMN serializer.
	 */
	public BPMNSerializer(){		
		super();
		processPrefix = "semantic";
		diagrammPrefix = "bpmndi";
		boundPrefix = "dc";
		waypointPrefix = "di";
		resolution = 96.00000267028808; //Visio standard value		
	}
	
	/* (non-Javadoc)
	 * @see bpmnExport.Serializer#toString(bpmnCore.MemoryModel)
	 */
	public String toString(MemoryModel mm)
	{
		String out = "";
		
		//xml header
		out = out.concat("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>"+System.lineSeparator());
		
		//definitions
		out = out.concat("<"+processPrefix+":definitions id=\""+mm.getDefinitions().getId()+"\" targetNamespace=\"http://www.trisotech.com/definitions/_1275500364427\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:semantic=\"http://www.omg.org/spec/BPMN/20100524/MODEL\">"+System.lineSeparator());
		
		//process-------------------------------------------------------------------------------------------------------------------------------------------------------
		out = out.concat("<"+processPrefix+":process isExecutable=\"false\" id=\""+mm.getProcess().getId()+"\">"+System.lineSeparator());
		
		//start events
		for(StartEvent se: mm.getStartEvents().values()){
			out = out.concat("<"+processPrefix+":startEvent name=\""+se.getName()+"\" id=\""+se.getId()+"\">"+System.lineSeparator());
			out = out.concat("\t<"+processPrefix+":outgoing>"+se.getOutgoing()+"</"+processPrefix+":outgoing>"+System.lineSeparator());
			out = out.concat("</"+processPrefix+":startEvent>"+System.lineSeparator());
		}
		
		//tasks
		for(Task t: mm.getTasks().values()){
			
			out = out.concat("<"+processPrefix+":task completionQuantity=\"1\" isForCompensation=\"false\" startQuantity=\"1\" name=\""+t.getName()+"\" id=\""+t.getId()+"\">"+System.lineSeparator());
			if(t.getDocumentation().getId() != null){				
				//out = out.concat("<documentation id=\""+t.getDocumentation().getId()+"\">"+t.getDocumentation().getContent()+"</documentation>"+System.lineSeparator());
				
				/*
				out = out.concat("<extensionElements>"+System.lineSeparator());
				out = out.concat("<signavio:signavioMetaData metaKey=\"bgcolor\" metaValue=\"#ffffcc\"/>"+System.lineSeparator());
				out = out.concat("<signavio:signavioMetaData metaKey=\"risklevel\" metaValue=\"\"/>"+System.lineSeparator());
				out = out.concat("<signavio:signavioMetaData metaKey=\"externaldocuments\" metaValue=\"\"/>"+System.lineSeparator());
				out = out.concat("</extensionElements>"+System.lineSeparator());
				*/
			}
			out = out.concat("\t<"+processPrefix+":incoming>"+t.getIncoming()+"</"+processPrefix+":incoming>"+System.lineSeparator());
	        out = out.concat("\t<"+processPrefix+":outgoing>"+t.getOutgoing()+"</"+processPrefix+":outgoing>"+System.lineSeparator());
	        out = out.concat("</"+processPrefix+":task>"+System.lineSeparator());			
		}
		
		//gateways
		for(Gateway gate: mm.getGateways().values()){			
			out = out.concat("<"+processPrefix+":parallelGateway gatewayDirection=\"Unspecified\" name=\""+gate.getName()+"\" id=\""+gate.getId()+"\">"+System.lineSeparator());
			
			for (String i: gate.getIncoming()){				
				out = out.concat("\t<"+processPrefix+":incoming>"+i+"</"+processPrefix+":incoming>"+System.lineSeparator());			
			}
			
			for (String o: gate.getOutgoing()){				
				out = out.concat("\t<"+processPrefix+":outgoing>"+o+"</"+processPrefix+":outgoing>"+System.lineSeparator());			
			}
			
			out = out.concat("</"+processPrefix+":parallelGateway>"+System.lineSeparator());			
		}
		
		//end events
		for(EndEvent ee: mm.getEndEvents().values()){			
			out = out.concat("<"+processPrefix+":endEvent name=\""+ee.getName()+"\" id=\""+ee.getId()+"\">"+System.lineSeparator());
			out = out.concat("\t<"+processPrefix+":incoming>"+ee.getIncoming()+"</"+processPrefix+":incoming>"+System.lineSeparator());
			out = out.concat("</"+processPrefix+":endEvent>"+System.lineSeparator());			
		}
		
		//sequence flows
		for(SequenceFlow sf: mm.getSequenceFlows().values()){			
			out = out.concat("<"+processPrefix+":sequenceFlow sourceRef=\""+sf.getSourceRef()+"\" targetRef=\""+sf.getTargetRef()+"\" name=\""+sf.getName()+"\" id=\""+sf.getId()+"\"/>"+System.lineSeparator());
		}
		
		//annotations
		for(Annotation an: mm.getAnnotations().values()){
			out = out.concat("<"+processPrefix+":textAnnotation id=\""+an.getId()+"\">"+System.lineSeparator());
			out = out.concat("<"+processPrefix+":text>"+an.getText()+"</"+processPrefix+":text>"+System.lineSeparator());
			out = out.concat("</"+processPrefix+":textAnnotation>"+System.lineSeparator());
		}
		
		//associations
		for(Association as: mm.getAssociations().values()){
			out = out.concat("<"+processPrefix+":association associationDirection=\"None\" sourceRef=\""+as.getSourceId()+"\" targetRef=\""+as.getTargetId()+"\" id=\""+as.getId()+"\"/>"+System.lineSeparator());
		}
		
		//process end
		out = out.concat("</"+processPrefix+":process>"+System.lineSeparator());
		
		//diagram-----------------------------------------------------------------------------------------------------------------------------------------------------
		out = out.concat("<"+diagrammPrefix+":BPMNDiagram documentation=\"\" id=\""+mm.getDiagram().getId()+"\" name=\""+mm.getDiagram().getName()+"\" resolution=\""+resolution+"\">"+System.lineSeparator());
		
		//plane
		out = out.concat("<"+diagrammPrefix+":BPMNPlane bpmnElement=\""+mm.getPlane().getRefId()+"\">"+System.lineSeparator());
		
		//shapes
		for(Shape sh: mm.getShapes().values()){
			
			out = out.concat("<"+diagrammPrefix+":BPMNShape bpmnElement=\""+sh.getRefId()+"\" id=\""+sh.getId()+"\">"+System.lineSeparator());
			
			//bounds
			for(Bounds b: sh.getBounds()){
				out = out.concat("<"+boundPrefix+":Bounds height=\""+b.getHeight()+"\" width=\""+b.getWidth()+"\" x=\""+b.getPosition().getX()+"\" y=\""+b.getPosition().getY()+"\"/>"+System.lineSeparator());
			}
			//shape end
			out = out.concat("</"+diagrammPrefix+":BPMNShape>"+System.lineSeparator());
			
		}
		
		//edges
		for(Edge ed: mm.getEdges().values()){			
			out = out.concat("<"+diagrammPrefix+":BPMNEdge bpmnElement=\""+ed.getRefId()+"\" id=\""+ed.getId()+"\">"+System.lineSeparator());
			for(Point p: ed.getPoints()){		
				out = out.concat("<"+waypointPrefix+":waypoint x=\""+p.getX()+"\" y=\""+p.getY()+"\"/>"+System.lineSeparator());				
			}
			//end edge
			out = out.concat("</"+diagrammPrefix+":BPMNEdge>"+System.lineSeparator());			
		}	
		//end tags
		out = out.concat("</"+diagrammPrefix+":BPMNPlane>"+System.lineSeparator());		
		out = out.concat("</"+diagrammPrefix+":BPMNDiagram>"+System.lineSeparator());		
		out = out.concat("</"+processPrefix+":definitions>");
		
		return out;
	}
	
}