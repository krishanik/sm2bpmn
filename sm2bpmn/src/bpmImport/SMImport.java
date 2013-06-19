package bpmImport;

import java.util.HashMap;
import java.util.HashSet;

import bpmImport.smParser.DTO_ServiceObject;
import bpmnCore.Annotation;
import bpmnCore.Association;
import bpmnCore.Documentation;
import bpmnCore.EndEvent;
import bpmnCore.Gateway;
import bpmnCore.IdGenerator;
import bpmnCore.MemoryModel;
import bpmnCore.SequenceFlow;
import bpmnCore.ServiceGraph;
import bpmnCore.StartEvent;
import bpmnCore.Task;

/**
 * The Class SMImport produce the memory model by a given service graph.
 */
public class SMImport extends Import {
	
	/** The Constant CONN_TYPE_StartEvent2Task. */
	private static final int CONN_TYPE_StartEvent2Task = 1;
	
	/** The Constant CONN_TYPE_StartEvent2Gateway. */
	private static final int CONN_TYPE_StartEvent2Gateway = 2;
	
	/** The Constant CONN_TYPE_Task2EndEvent. */
	private static final int CONN_TYPE_Task2EndEvent = 3;
	
	/** The Constant CONN_TYPE_Task2Task. */
	private static final int CONN_TYPE_Task2Task = 4;
	
	/** The Constant CONN_TYPE_Task2Gateway. */
	private static final int CONN_TYPE_Task2Gateway = 5;
	
	/** The Constant CONN_TYPE_Gateway2EndEvent. */
	private static final int CONN_TYPE_Gateway2EndEvent = 6;
	
	/** The Constant CONN_TYPE_Gateway2Task. */
	private static final int CONN_TYPE_Gateway2Task = 7;
	
	/** The Constant CONN_TYPE_Gateway2Gateway. */
	private static final int CONN_TYPE_Gateway2Gateway = 8;

	/** The id gen. */
	private IdGenerator idGen;
	
	/** The mm. */
	private MemoryModel mm;
	
	/** The sg. */
	private ServiceGraph sg;

	/**
	 * Class constructor instantiates a new sM import.
	 *
	 * @param sg ServiceGraph Object
	 */
	public SMImport(ServiceGraph sg) {
		idGen = new IdGenerator();
		mm = new MemoryModel();
		this.sg = sg;
		
		buildMemoryModel();
	}
	
	/**
	 * Class constructor instantiates a new sM import.
	 *
	 * @param sg ServiceGraph Object
	 * @param idGen IdGenerator Object
	 */
	public SMImport(ServiceGraph sg, IdGenerator idGen) {
		this.idGen = idGen;
		mm = new MemoryModel();
		this.sg = sg;
		
		buildMemoryModel();
	}
	
	/**
	 * Gets the memory model.
	 *
	 * @return MemoryModel
	 */
	public MemoryModel getMemoryModel() {
		return this.mm;
	}
	
	/**
	 * Creates a directed connection between 2 Objects
	 * by inserting a SequenceFlow between them 
	 * and linking each of Objects with this SequenceFlow.
	 * @param conType indicates Class of each Object
	 * @param id1 id of first Object
	 * @param id2 id of second Object
	 */
	private void connect( int conType, String id1, String id2 ) {
		//create a sequence flow
		String sequenceFlowID = idGen.getNextId();
		mm.getSequenceFlows().put(sequenceFlowID, new SequenceFlow(sequenceFlowID, "", id1, id2));
		
		switch( conType ) {
			case CONN_TYPE_StartEvent2Task: {
				mm.getStartEvents().get(id1).setOutgoing(sequenceFlowID);
				mm.getTasks().get(id2).setIncoming(sequenceFlowID);
				break;
			}
			case CONN_TYPE_StartEvent2Gateway: {
				mm.getStartEvents().get(id1).setOutgoing(sequenceFlowID);
				mm.getGateways().get(id2).getIncoming().add(sequenceFlowID);
				break;				
			}
			case CONN_TYPE_Task2EndEvent: {
				mm.getTasks().get(id1).setOutgoing(sequenceFlowID);
				mm.getEndEvents().get(id2).setIncoming(sequenceFlowID);
				break;
			}
			case CONN_TYPE_Task2Task: {
				mm.getTasks().get(id1).setOutgoing(sequenceFlowID);
				mm.getTasks().get(id2).setIncoming(sequenceFlowID);
				break;
			}
			case CONN_TYPE_Task2Gateway: {
				mm.getTasks().get(id1).setOutgoing(sequenceFlowID);
				mm.getGateways().get(id2).getIncoming().add(sequenceFlowID);
				break;
			}
			case CONN_TYPE_Gateway2EndEvent: {
				mm.getGateways().get(id1).getOutgoing().add(sequenceFlowID);
				mm.getEndEvents().get(id2).setIncoming(sequenceFlowID);
				break;
			}
			case CONN_TYPE_Gateway2Task: {
				mm.getGateways().get(id1).getOutgoing().add(sequenceFlowID);
				mm.getTasks().get(id2).setIncoming(sequenceFlowID);
				break;
			}
			case CONN_TYPE_Gateway2Gateway: {
				mm.getGateways().get(id1).getOutgoing().add(sequenceFlowID);
				mm.getGateways().get(id2).getIncoming().add(sequenceFlowID);
				break;
			}
		}
	}
	
	/**
	 * Builds the memory model.
	 */
	private void buildMemoryModel() {
		buildLayer();
		setDocumentation();
		setAnnotation();
	}
	
	/**
	 * first Layer.
	 */
	private void buildLayer() {
	
		// nodes without predecessors
		HashSet<String> startNodes = new HashSet<String>();
		
		for(String nodeID: sg.getServiceNodeList().keySet()) {			
			if ( sg.getServiceNodeList().get(nodeID).getPredecessors().isEmpty() ) {
				startNodes.add(nodeID);
				mm.getTasks().put(nodeID, new Task(nodeID, sg.getServiceNodeList().get(nodeID).getLabel()));
			}
		}
		if( startNodes.isEmpty() ) return;
		
		String startEventID = idGen.getNextId();
		mm.getStartEvents().put(startEventID, new StartEvent(startEventID, "") );
		
		// if only 1 start node - connect StartEvent with this node through a SequenceFlow
		if( 1 == startNodes.size() ) {
			String taskID = startNodes.iterator().next();
			connect(CONN_TYPE_StartEvent2Task, startEventID, taskID);
		}
		// more than 1 start node - create a Gateway
		else {
			String gateID = idGen.getNextId();
			mm.getGateways().put(gateID, new Gateway(gateID, ""));
			connect(CONN_TYPE_StartEvent2Gateway, startEventID, gateID);
			// connect gate with start nodes through sequence flows
			for(String taskID: startNodes) {
				connect(CONN_TYPE_Gateway2Task, gateID, taskID);
			}
		}
		
		buildNextLayer(startNodes);
	}	
	
	/**
	 * Builds the next layer.
	 *
	 * @param preNodes the pre nodes
	 */
	private void buildNextLayer(HashSet<String> preNodes) {
		HashSet<String> succNodes = new HashSet<String>();
		
		// get successors of preNodes
		for(String preID: preNodes) {
			for(String succID: sg.getServiceNodeList().get(preID).getSuccessors()) {
				succNodes.add(succID);
				mm.getTasks().put(succID, new Task(succID, sg.getServiceNodeList().get(succID).getLabel()));
			}
		}
		
		// no successors left - create EndEvent and connect with preNodes
		if( succNodes.isEmpty() ) {
			String endEventID = idGen.getNextId();
			//EndEvent endEvent = new EndEvent(idGen.getNextId());
			mm.getEndEvents().put(endEventID, new EndEvent(endEventID, ""));
			
			// if only 1 preNode - connect EndEvent with this node
			if( 1 == preNodes.size() ) {
				String taskID = preNodes.iterator().next();
				connect(CONN_TYPE_Task2EndEvent, taskID, endEventID);				
			}
			// more than 1 preNode - create a Gateway
			else {
				String gateID = idGen.getNextId();
				mm.getGateways().put(gateID, new Gateway(gateID, ""));
				connect(CONN_TYPE_Gateway2EndEvent, gateID, endEventID);
				//connect Gateway with preNodes
				for(String taskID: preNodes) {
					connect(CONN_TYPE_Task2Gateway, taskID, gateID);
				}
			}
			return;
		}
		
		//succNodes not empty - connect preNodes and succNodes through gateways
		buildGateways(preNodes, succNodes);
		
		buildNextLayer(succNodes);
	}
	
	/**
	 * Builds the gateways.
	 *
	 * @param preNodes the pre nodes
	 * @param succNodes the succ nodes
	 */
	private void buildGateways(HashSet<String> preNodes, HashSet<String> succNodes) {
		HashMap<String, HashSet<String> > succNodeIncomingTasks = new HashMap<String, HashSet<String> >();
		HashMap<String, HashSet<String> > succNodeIncomingGates = new HashMap<String, HashSet<String> >();
		HashMap<String, HashSet<String> > gateOutgoings			= new HashMap<String, HashSet<String> >();
		
		for(String succNodeID: succNodes) {
			succNodeIncomingTasks.put(succNodeID, new HashSet<String>());
			succNodeIncomingGates.put(succNodeID, new HashSet<String>());
		}
		
		for( String preNodeID: preNodes) {
			if(sg.getServiceNodeList().get(preNodeID).getSuccessors().isEmpty()) continue;
			if(1 == sg.getServiceNodeList().get(preNodeID).getSuccessors().size()){
				String succNodeID = sg.getServiceNodeList().get(preNodeID).getSuccessors().iterator().next();
				
				/*if( null == succNodeIncomingTasks.get(succNodeID) ) {
					succNodeIncomingTasks.put(succNodeID, new HashSet<String>() );
				}*/
				succNodeIncomingTasks.get(succNodeID).add(preNodeID);
			}
			// create Gateways for preNodes with |successors| >= 2
			else {
				HashSet<String> successors = sg.getServiceNodeList().get(preNodeID).getSuccessors();
				
				String gateID = "";
				if( gateOutgoings.containsValue(successors) ) {
					for(String gateIDtemp: gateOutgoings.keySet() ) {
						if( gateOutgoings.get(gateID).containsAll(successors) && successors.containsAll(gateOutgoings.get(gateID)) ) {
							gateID = gateIDtemp;
							break;
						}
					}
				}
				else {
					gateID = idGen.getNextId();
					mm.getGateways().put(gateID, new Gateway(gateID, ""));
					gateOutgoings.put(gateID, successors);
				}
				connect(CONN_TYPE_Task2Gateway, preNodeID, gateID);
				
				for(String succID: successors) {
					/*if( null == succNodeIncomingGates.get(succID) ) {
						succNodeIncomingGates.put(succID, new HashSet<String>() );
					}*/
					succNodeIncomingGates.get(succID).add(gateID);
				}
			}
		}
		
		for(String succNodeID: succNodes) {
			if( succNodeIncomingTasks.get(succNodeID).isEmpty() && succNodeIncomingGates.get(succNodeID).isEmpty() ) continue;
			if( 1 == ( succNodeIncomingTasks.get(succNodeID).size() + succNodeIncomingGates.get(succNodeID).size() ) )
			{
				// connect Task to succNodeID
				if( succNodeIncomingTasks.containsKey(succNodeID) ) {
					String taskID = succNodeIncomingTasks.get(succNodeID).iterator().next();
					connect(CONN_TYPE_Task2Task, taskID, succNodeID);
				}
				//connect Gateway to succNodeID
				else {
					String gateID = succNodeIncomingGates.get(succNodeID).iterator().next();
					connect(CONN_TYPE_Gateway2Task, gateID, succNodeID);
				}
			}
			// create Gateway for this succNode
			else {
				String gateID = idGen.getNextId();
				mm.getGateways().put(gateID, new Gateway(gateID, ""));
				//connect Gateway with succNodeID
				connect(CONN_TYPE_Gateway2Task, gateID, succNodeID);
				// connect tasks and gateways with this gateway
				for(String taskID: succNodeIncomingTasks.get(succNodeID)) {
					connect(CONN_TYPE_Task2Gateway, taskID, gateID);
				}
				for(String gate0ID: succNodeIncomingGates.get(succNodeID)) {
					connect(CONN_TYPE_Gateway2Gateway, gate0ID, gateID);
				}
			}
		}
	}
	
	/**
	 * Sets the documentation.
	 */
	private void setDocumentation(){
		
		for(DTO_ServiceObject service : sg.getServiceObjectList()){
			
			if(service.getAttributes().get("SMtoBPMN_componentHierarchy").isEmpty()) continue;
			
			String content = "TaskHierarchy: ";
			for(String tier: service.getAttributes().get("SMtoBPMN_componentHierarchy")){
				content = content.concat(tier+"/");
			}
			content = content.substring(0, content.length()-1);
			mm.getTasks().get(service.getName()).setDocumentation(new Documentation(idGen.getNextId(), content));
			
		}
		
	}
	
	/**
	 * Sets the annotation.
	 */
	private void setAnnotation() {
		for(DTO_ServiceObject service : sg.getServiceObjectList())	{
			String annID = idGen.getNextId();
			String assID = idGen.getNextId();
			mm.getAnnotations().put(annID, new Annotation(annID, service.getDescription()));
			mm.getAssociations().put(assID, new Association(assID, annID, service.getName()));
		}	
	}
	
}















