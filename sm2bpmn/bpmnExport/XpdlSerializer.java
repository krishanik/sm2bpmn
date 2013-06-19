package bpmnExport;

import java.util.ArrayList;

import bpmnCore.Annotation;
import bpmnCore.Association;
import bpmnCore.Bounds;
import bpmnCore.EndEvent;
import bpmnCore.Gateway;
import bpmnCore.MemoryModel;
import bpmnCore.Point;
import bpmnCore.SequenceFlow;
import bpmnCore.StartEvent;
import bpmnCore.Task;



// TODO: Auto-generated Javadoc
/**
 * The Class XpdlSerializer serializes a memory model to XPDL output format.
 */
public class XpdlSerializer extends Serializer{
	
	/** The xpdl prefix. */
	private String xpdlPrefix; //prefix name by signavio
	
	/** The xpd extension prefix. */
	private String xpdExtensionPrefix;
	
	/**
	 * Class constructor instantiates a new xpdl serializer.
	 */
	public XpdlSerializer(){
		
		super();
		xpdlPrefix = "zdef244184457";
		xpdExtensionPrefix = "xpdExt";
	
	}
	
	/* (non-Javadoc)
	 * @see bpmnExport.Serializer#toString(bpmnCore.MemoryModel)
	 */
	public String toString(MemoryModel mm){
	
		String out = "";
		
		//xml header
		out = out.concat("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+System.lineSeparator()+System.lineSeparator());
		
		//Package
		out = out.concat("<"+xpdlPrefix+":Package xmlns=\"\" xmlns:"+xpdExtensionPrefix+"=\"http://www.tibco.com/XPD/xpdExtension1.0.0\" xmlns:"+xpdlPrefix+"=\"http://www.wfmc.org/2008/XPDL2.1\" Language=\"English\" Id=\"canvas\">"+System.lineSeparator());
		
		//Artifacts
		out = out.concat("<Artifacts xmlns=\"http://www.wfmc.org/2008/XPDL2.1\">"+System.lineSeparator());
		
		for(Annotation a: mm.getAnnotations().values()){
			
			out = out.concat("<Artifact TextAnnotation=\""+a.getText()+"\" Id=\""+a.getId()+"\">"+System.lineSeparator());
			
			out = out.concat("<NodeGraphicsInfos>"+System.lineSeparator());
			
			for(Bounds b: mm.getShapes().get(mm.getShapeRef().get(a.getId())).getBounds()){
			
				out = out.concat("<NodeGraphicsInfo Height=\""+b.getHeight()+"\" Width=\""+b.getWidth()+"\" BorderColor=\"#0,0,0\" ToolId=\"Oryx\">"+System.lineSeparator());
				out = out.concat("<Coordinates XCoordinate=\""+b.getPosition().getX()+"\" YCoordinate=\""+b.getPosition().getY()+"\"/>"+System.lineSeparator());
				out = out.concat("</NodeGraphicsInfo>"+System.lineSeparator());
			
			}
			
			out = out.concat("</NodeGraphicsInfos>"+System.lineSeparator());
			
			out = out.concat("</Artifact>"+System.lineSeparator());
			
		}
		
		out = out.concat("</Artifacts>"+System.lineSeparator());
		
		//Associations
		out = out.concat("<Associations xmlns=\"http://www.wfmc.org/2008/XPDL2.1\">"+System.lineSeparator());
		
		for(Association as: mm.getAssociations().values()){
			
			out = out.concat("<Association Source=\""+as.getSourceId()+"\" Target=\""+as.getTargetId()+"\" Id=\""+as.getId()+">"+System.lineSeparator());
			out = out.concat("<ConnectorGraphicsInfos>"+System.lineSeparator());
			out = out.concat("<ConnectorGraphicsInfo BorderColor=\"#0,0,0\" ToolId=\"Oryx\">"+System.lineSeparator());
			
			for(Point b: mm.getEdges().get(mm.getEdgeRef().get(as.getId())).getPoints()){
				
				out = out.concat("<Coordinates XCoordinate=\""+b.getX()+"\" YCoordinate=\""+b.getY()+"\"/>"+System.lineSeparator());
				
			}
			
			out = out.concat("</ConnectorGraphicsInfo>"+System.lineSeparator());
			out = out.concat("</ConnectorGraphicsInfos>"+System.lineSeparator());
			out = out.concat("</Association>"+System.lineSeparator());
			
		}
		
		out = out.concat("</Associations>"+System.lineSeparator());
		
		//Pools
		out = out.concat("<Pools xmlns=\"http://www.wfmc.org/2008/XPDL2.1\">"+System.lineSeparator());
		out = out.concat("<Pool BoundaryVisible=\"false\" MainPool=\"true\" Process=\"MainPool-process\" Orientation=\"HORIZONTAL\" Id=\"MainPool\" Name=\"Main Pool\">"+System.lineSeparator());
		out = out.concat("<NodeGraphicsInfos>"+System.lineSeparator());
		out = out.concat("<NodeGraphicsInfo FillColor=\"#ffffff\" Height=\"0.0\" Width=\"0.0\" BorderColor=\"#0,0,0\" ToolId=\"Oryx\">"+System.lineSeparator());
		out = out.concat("<Coordinates XCoordinate=\"0.0\" YCoordinate=\"0.0\"/>"+System.lineSeparator());
		out = out.concat("</NodeGraphicsInfo>"+System.lineSeparator());
		out = out.concat("</NodeGraphicsInfos>"+System.lineSeparator());
		out = out.concat("</Pool>"+System.lineSeparator());
		out = out.concat("</Pools>"+System.lineSeparator());
		
		//Workflow
		out = out.concat("<WorkflowProcesses xmlns=\"http://www.wfmc.org/2008/XPDL2.1\">"+System.lineSeparator());
		out = out.concat("<WorkflowProcess AdhocOrdering=\"Sequential\" ProcessType=\"None\" Status=\"None\" SuppressJoinFailure=\"true\" Id=\""+mm.getProcess().getId()+"\" Name=\"MainProcess\">"+System.lineSeparator());
		
		//Activities start
		out = out.concat("<Activities>"+System.lineSeparator());
			
		//Start event
		for(StartEvent se: mm.getStartEvents().values()){
			
			out = out.concat("<Activity Id=\""+se.getId()+"\" Name=\""+se.getName()+"\" Status=\"None\" StartMode=\"Automatic\" FinishMode=\"Automatic\">"+System.lineSeparator());
			out = out.concat("<Event>"+System.lineSeparator());
			out = out.concat("<StartEvent Trigger=\"None\" />"+System.lineSeparator());
			out = out.concat("</Event>"+System.lineSeparator());
			
			out = out.concat( nodeGraphicsInfoToString(mm, mm.getShapes().get(mm.getShapeRef().get(se.getId())).getBounds()) );
			out = out.concat("</Activity>"+System.lineSeparator());
		}
		
		//End event
		for(EndEvent ee: mm.getEndEvents().values()){
			
			out = out.concat("<Activity CompletionQuantity=\"1\" StartQuantity=\"1\" Id=\""+ee.getId()+"\" Name=\""+ee.getName()+"\">"+System.lineSeparator());
			out = out.concat("<Event>"+System.lineSeparator());
			out = out.concat("<EndEvent Result=\"None\" />"+System.lineSeparator());
			out = out.concat("</Event>"+System.lineSeparator());
			
			out = out.concat( nodeGraphicsInfoToString(mm, mm.getShapes().get(mm.getShapeRef().get(ee.getId())).getBounds()) );
			out = out.concat("</Activity>"+System.lineSeparator());
		
		}
		
		//Tasks
		for(Task t: mm.getTasks().values()){
			
			out = out.concat("<Activity CompletionQuantity=\"1\" StartQuantity=\"1\" Id=\""+t.getId()+"\" Name=\""+t.getName()+"\">"+System.lineSeparator());
			out = out.concat("<Implementation>"+System.lineSeparator());
			out = out.concat("<No/>"+System.lineSeparator());
			out = out.concat("</Implementation>"+System.lineSeparator());
			out = out.concat("<Documentation>"+System.lineSeparator());
			out = out.concat(t.getDocumentation().getContent()+System.lineSeparator());
            out = out.concat("</Documentation>"+System.lineSeparator());
			out = out.concat( nodeGraphicsInfoToString(mm, mm.getShapes().get(mm.getShapeRef().get(t.getId())).getBounds()) );
			out = out.concat("</Activity>"+System.lineSeparator());
			
		}
		
		//Gateways
		for(Gateway g: mm.getGateways().values()){
			
			out = out.concat("<Activity CompletionQuantity=\"1\" StartQuantity=\"1\" Id=\""+g.getId()+"\" Name=\""+g.getName()+"\">"+System.lineSeparator());
			out = out.concat("<Route GatewayType=\"AND\" Instantiate=\"false\" MarkerVisible=\"false\" />"+System.lineSeparator());
			out = out.concat("<TransitionRestrictions>"+System.lineSeparator());
			out = out.concat("<TransitionRestriction>"+System.lineSeparator());
			out = out.concat("<Join Type=\"XOR\" />"+System.lineSeparator());
			out = out.concat("</TransitionRestriction>"+System.lineSeparator());
			out = out.concat("</TransitionRestrictions>"+System.lineSeparator());
			
			out = out.concat( nodeGraphicsInfoToString(mm, mm.getShapes().get(mm.getShapeRef().get(g.getId())).getBounds()) );
			out = out.concat("</Activity>"+System.lineSeparator());
			
		}
		
		//Activities end
		out = out.concat("</Activities>"+System.lineSeparator());
		
		//Transitions
		out = out.concat("<Transitions>"+System.lineSeparator());
		
		//Sequenceflows
		for(SequenceFlow sf: mm.getSequenceFlows().values()){
	
			out = out.concat("<Transition From=\""+sf.getSourceRef()+"\" To=\""+sf.getTargetRef()+"\" Id=\""+sf.getId()+"\">"+System.lineSeparator());
			out = out.concat("<ConnectorGraphicsInfos>"+System.lineSeparator());
			out = out.concat("<ConnectorGraphicsInfo BorderColor=\"#0,0,0\" ToolId=\"Oryx\">"+System.lineSeparator());
			
			for(Point p: mm.getEdges().get(mm.getEdgeRef().get(sf.getId())).getPoints()){
			
				out = out.concat("<Coordinates XCoordinate=\""+p.getX()+"\" YCoordinate=\""+p.getY()+"\"/>"+System.lineSeparator());
			
			}
			
			out = out.concat("</ConnectorGraphicsInfo>"+System.lineSeparator());
			out = out.concat("</ConnectorGraphicsInfos>"+System.lineSeparator());
			out = out.concat("<ExtendedAttributes>"+System.lineSeparator());
			out = out.concat("<ExtendedAttribute Name=\"showdiamondmarker\" Value=\"false\"/>"+System.lineSeparator());
			out = out.concat("</ExtendedAttributes>"+System.lineSeparator());
			out = out.concat("</Transition>"+System.lineSeparator());
			
		}
        
		//Transitions end
		out = out.concat("</Transitions>"+System.lineSeparator());
		
		//Workflow end
		out = out.concat("</WorkflowProcess>"+System.lineSeparator());
		out = out.concat("</WorkflowProcesses>"+System.lineSeparator());
		
		//Package end
		out = out.concat("</"+xpdlPrefix+":Package>"+System.lineSeparator());
		
		return out;
	
	}
	
	/**
	 * transform graphical information of a task to XPDL format.
	 *
	 * @param mm the memory model
	 * @param bounds the bounds
	 * @return the string
	 */
	private String nodeGraphicsInfoToString(MemoryModel mm, ArrayList<Bounds> bounds){
		
		String out = "";
		
		out = out.concat("<NodeGraphicsInfos>"+System.lineSeparator());
		
		for(Bounds b: bounds){
		
			out = out.concat("<NodeGraphicsInfo FillColor=\"#ffffcc\" Height=\""+b.getHeight()+"\" Width=\""+b.getWidth()+"\" BorderColor=\"#0,0,0\" ToolId=\"Oryx\">"+System.lineSeparator());
			out = out.concat("<Coordinates XCoordinate=\""+b.getPosition().getX()+"\" YCoordinate=\""+b.getPosition().getY()+"\"/>"+System.lineSeparator());
			out = out.concat("</NodeGraphicsInfo>"+System.lineSeparator());
			
		}
		
		out = out.concat("</NodeGraphicsInfos>"+System.lineSeparator());
		
		return out;
		
	}
	
}