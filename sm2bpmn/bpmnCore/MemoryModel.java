package bpmnCore;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The Class MemoryModel represents internal memory model to store bpmn model elements and their graphical informations.
 */
public class MemoryModel {
	
	/** The Constant TYPE_StartEvent. */
	private static final int TYPE_StartEvent = 1;
	
	/** The Constant TYPE_EndEvent. */
	private static final int TYPE_EndEvent = 2;
	
	/** The Constant TYPE_Gateway. */
	private static final int TYPE_Gateway = 3;
	
	/** The Constant TYPE_Task. */
	private static final int TYPE_Task = 4;
	
	/** The Constant TYPE_SequenceFlow. */
	private static final int TYPE_SequenceFlow = 5;
	
	/** The Constant TYPE_Annotation. */
	private static final int TYPE_Annotation = 6;
	
	/** The Constant TYPE_Association. */
	private static final int TYPE_Association = 7;
	
	/** The Constant TYPE_Shape. */
	private static final int TYPE_Shape = 8;
	
	/** The Constant TYPE_Edge. */
	private static final int TYPE_Edge = 9;
	
	/** The start events. */
	private HashMap<String, StartEvent>		startEvents;
	
	/** The end events. */
	private HashMap<String, EndEvent>		endEvents;
	
	/** The gateways. */
	private HashMap<String, Gateway>		gateways;
	
	/** The tasks. */
	private HashMap<String, Task>			tasks;
	
	/** The sequence flows. */
	private HashMap<String, SequenceFlow>	sequenceFlows;
	
	/** The annotations. */
	private HashMap<String, Annotation>		annotations;
	
	/** The associations. */
	private HashMap<String, Association>	associations;
	
	/** The shapes. */
	private HashMap<String, Shape>			shapes;
	
	/** The edges. */
	private HashMap<String, Edge>			edges;
	
	/** The shape ref. */
	private HashMap<String, String>			shapeRef;
	
	/** The edge ref. */
	private HashMap<String, String>			edgeRef;
	
	/** The association ref. */
	private HashMap<String, String>			associationRef;
	
	/** The types. */
	private HashMap<String, Integer>		types;
	
	/** The process. */
	private Process process;
	
	/** The diagram. */
	private Diagram diagram;
	
	/** The plane. */
	private Plane plane;
	
	/** The definitions. */
	private Definitions definitions;
	
	/**
	 * Class constructor instantiates a new memory model.
	 */
	public MemoryModel() {
		
		//bpmn model elements
		startEvents		= new HashMap<String, StartEvent>();
		endEvents		= new HashMap<String, EndEvent>();
		gateways		= new HashMap<String, Gateway>();
		tasks			= new HashMap<String, Task>();
		sequenceFlows	= new HashMap<String, SequenceFlow>();
		annotations		= new HashMap<String, Annotation>();
		associations	= new HashMap<String, Association>();
		
		process 		= new Process(); 
		diagram 		= new Diagram();
		plane 			= new Plane();
		definitions 	= new Definitions();
		
		//graphical elements
		shapes			= new HashMap<String, Shape>();
		edges			= new HashMap<String, Edge>();
		
		//reference maps <model element, graphical element>
		associationRef	= new HashMap<String, String>();
		shapeRef		= new HashMap<String, String>();
		edgeRef			= new HashMap<String, String>();
		
		//type map <model element, type>
		types			= new HashMap<String, Integer>();
		
	}
	
	/**
	 * Creates the shapes.
	 *
	 * @param idGen the id gen
	 */
	public void createShapes(IdGenerator idGen) {
		String shapeID;
		for(String id: getStartEvents().keySet() ) {
			shapeID = idGen.getNextId();
			getShapes().put(shapeID, new Shape(shapeID) );
			getShapes().get(shapeID).setRefId(id);
			getShapeRef().put(id, shapeID);
		}
		for(String id: getEndEvents().keySet() ) {
			shapeID = idGen.getNextId();
			getShapes().put(shapeID, new Shape(shapeID) );
			getShapes().get(shapeID).setRefId(id);
			getShapeRef().put(id, shapeID);
		}
		for(String id: getGateways().keySet() ) {
			shapeID = idGen.getNextId();
			getShapes().put(shapeID, new Shape(shapeID) );
			getShapes().get(shapeID).setRefId(id);
			getShapeRef().put(id, shapeID);
		}
		for(String id: getTasks().keySet() ) {
			shapeID = idGen.getNextId();
			getShapes().put(shapeID, new Shape(shapeID) );
			getShapes().get(shapeID).setRefId(id);
			getShapeRef().put(id, shapeID);
		}
		for(String id: getAnnotations().keySet() ) {
			shapeID = idGen.getNextId();
			getShapes().put(shapeID, new Shape(shapeID) );
			getShapes().get(shapeID).setRefId(id);
			getShapeRef().put(id, shapeID);
		}		
	}
	
	/**
	 * Creates the edges.
	 *
	 * @param idGen the id gen
	 */	
	public void createEdges(IdGenerator idGen) {
		for(String id: getSequenceFlows().keySet() ) {
			String edgeID = idGen.getNextId();
			getEdges().put(edgeID, new Edge(edgeID) );
			getEdges().get(edgeID).setRefId(id);
			getEdgeRef().put(id, edgeID);
		}
		for(String id: getAssociations().keySet() ) {
			String assID = idGen.getNextId();
			getEdges().put(assID, new Edge(assID) );
			getEdges().get(assID).setRefId(id);
			getEdgeRef().put(id, assID);
		}
	}
	
	/**
	 * Creates the association ref.
	 */		
	public void createAssociationRef() {
		for(Association a: getAssociations().values()) {
			associationRef.put(a.getTargetId(), a.getId());
		}		
	}

	/**
	 * Creates the type map.
	 */	
	public void createTypeMap() {
		for(String id: startEvents.keySet() ) types.put(id, TYPE_StartEvent);
		for(String id: endEvents.keySet() ) types.put(id, TYPE_EndEvent);
		for(String id: gateways.keySet() ) types.put(id, TYPE_Gateway);
		for(String id: tasks.keySet() ) types.put(id, TYPE_Task);
		for(String id: sequenceFlows.keySet() ) types.put(id, TYPE_SequenceFlow);
		for(String id: annotations.keySet() ) types.put(id, TYPE_Annotation);
		for(String id: associations.keySet() ) types.put(id, TYPE_Association);
		for(String id: shapes.keySet() ) types.put(id, TYPE_Shape);
		for(String id: edges.keySet() ) types.put(id, TYPE_Edge);
	}
	
	/**
	 * Gets the successors.
	 *
	 * @param id the id
	 * @return the successors
	 */
	public HashSet<String> getSuccessors(String id) {
		int type = types.get(id);
		HashSet<String> resultSet = new HashSet<String>();
		
		switch(type) {
		case TYPE_StartEvent: {
			if(! (null == getStartEvents().get(id).getOutgoing()) )
				resultSet.add(getStartEvents().get(id).getOutgoing());
			break;
			}
		case TYPE_EndEvent: return resultSet;
		case TYPE_Gateway: {
			if(! (null == getGateways().get(id).getOutgoing()) )
				return new HashSet<String>(getGateways().get(id).getOutgoing());
			break;
			}
		case TYPE_Task: {
			if(! (null == getTasks().get(id).getOutgoing()) )
				resultSet.add(getTasks().get(id).getOutgoing());
			break;
			}
		case TYPE_SequenceFlow: {
			if(! (null == getSequenceFlows().get(id).getTargetRef()) )
			resultSet.add(getSequenceFlows().get(id).getTargetRef());
			break;
			}
		case TYPE_Annotation: return new HashSet<String>();
		case TYPE_Association: {
			if(! (null == getAssociations().get(id).getTargetId()) )
			resultSet.add(getAssociations().get(id).getTargetId());
			break;
			}
		case TYPE_Shape: return getSuccessors(getShapes().get(id).getRefId());
		case TYPE_Edge: return getSuccessors(getEdges().get(id).getRefId());
		}
		return resultSet;
	}

	/**
	 * Sets the default values.
	 *
	 * @param idGen the new default values
	 */
	public void setDefaultValues(IdGenerator idGen) {
		getProcess().setId(idGen.getNextId()); 
		getDiagram().setId(idGen.getNextId());
		getPlane().setRefId(getProcess().getId());
		getDefinitions().setId(idGen.getNextId());
		
	}
	
	/**
	 * Gets the start events.
	 *
	 * @return startEvents
	 */
	public HashMap<String, StartEvent> getStartEvents() {
		return startEvents;
	}
	
	/**
	 * Sets the start events.
	 *
	 * @param startEvents startEvents
	 */
	public void setStartEvents(HashMap<String, StartEvent> startEvents) {
		this.startEvents = startEvents;
	}
	
	/**
	 * Gets the end events.
	 *
	 * @return endEvents
	 */
	public HashMap<String, EndEvent> getEndEvents() {
		return endEvents;
	}
	
	/**
	 * Sets the end events.
	 *
	 * @param endEvents endEvents
	 */
	public void setEndEvents(HashMap<String, EndEvent> endEvents) {
		this.endEvents = endEvents;
	}
	
	/**
	 * Gets the gateways.
	 *
	 * @return gateways
	 */
	public HashMap<String, Gateway> getGateways() {
		return gateways;
	}
	
	/**
	 * Sets the gateways.
	 *
	 * @param gateways gateways
	 */
	public void setGateways(HashMap<String, Gateway> gateways) {
		this.gateways = gateways;
	}
	
	/**
	 * Gets the tasks.
	 *
	 * @return tasks
	 */
	public HashMap<String, Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Sets the tasks.
	 *
	 * @param tasks tasks
	 */
	public void setTasks(HashMap<String, Task> tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * Gets the sequence flows.
	 *
	 * @return sequenceFlows
	 */
	public HashMap<String, SequenceFlow> getSequenceFlows() {
		return sequenceFlows;
	}
	
	/**
	 * Sets the sequence flows.
	 *
	 * @param sequenceFlows sequenceFlows
	 */
	public void setSequenceFlows(HashMap<String, SequenceFlow> sequenceFlows) {
		this.sequenceFlows = sequenceFlows;
	}
	
	/**
	 * Gets the annotations.
	 *
	 * @return annotations
	 */
	public HashMap<String, Annotation> getAnnotations() {
		return annotations;
	}
	
	/**
	 * Sets the annotations.
	 *
	 * @param annotations annotations
	 */
	public void setAnnotations(HashMap<String, Annotation> annotations) {
		this.annotations = annotations;
	}
	
	/**
	 * Gets the associations.
	 *
	 * @return assosiations
	 */
	public HashMap<String, Association> getAssociations() {
		return associations;
	}
	
	/**
	 * Sets the associations.
	 *
	 * @param associations the associations
	 */
	public void setAssociations(HashMap<String, Association> associations) {
		this.associations = associations;
	}
	
	/**
	 * Gets the process.
	 *
	 * @return process
	 */
	public Process getProcess() {
		return process;
	}
	
	/**
	 * Sets the process.
	 *
	 * @param process process
	 */
	public void setProcess(Process process) {
		this.process = process;
	}
	
	/**
	 * Gets the diagram.
	 *
	 * @return diagram
	 */
	public Diagram getDiagram() {
		return diagram;
	}
	
	/**
	 * Sets the diagram.
	 *
	 * @param diagram diagram
	 */
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
	
	/**
	 * Gets the plane.
	 *
	 * @return plane
	 */
	public Plane getPlane() {
		return plane;
	}
	
	/**
	 * Sets the plane.
	 *
	 * @param plane plane
	 */
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	
	/**
	 * Gets the definitions.
	 *
	 * @return definitions
	 */
	public Definitions getDefinitions() {
		return definitions;
	}
	
	/**
	 * Sets the definitions.
	 *
	 * @param definitions definitions
	 */
	public void setDefinitions(Definitions definitions) {
		this.definitions = definitions;
	}
	
	/**
	 * Gets the shapes.
	 *
	 * @return shapes
	 */
	public HashMap<String, Shape> getShapes() {
		return shapes;
	}
	
	/**
	 * Sets the shapes.
	 *
	 * @param shapes shapes
	 */
	public void setShapes(HashMap<String, Shape> shapes) {
		this.shapes = shapes;
	}
	
	/**
	 * Gets the edges.
	 *
	 * @return edges
	 */
	public HashMap<String, Edge> getEdges() {
		return edges;
	}
	
	/**
	 * Sets the edges.
	 *
	 * @param edges edges
	 */
	public void setEdges(HashMap<String, Edge> edges) {
		this.edges = edges;
	}
	
	/**
	 * Gets the shape ref.
	 *
	 * @return shapeRef
	 */
	public HashMap<String, String> getShapeRef() {
		return shapeRef;
	}
	
	/**
	 * Sets the shape ref.
	 *
	 * @param shapeRef shapeRef
	 */
	public void setShapeRef(HashMap<String, String> shapeRef) {
		this.shapeRef = shapeRef;
	}

	/**
	 * Gets the edge ref.
	 *
	 * @return edgeRef
	 */
	public HashMap<String, String> getEdgeRef() {
		return edgeRef;
	}
	
	/**
	 * Sets the edge ref.
	 *
	 * @param edgeRef edgeRef
	 */
	public void setEdgeRef(HashMap<String, String> edgeRef) {
		this.edgeRef = edgeRef;
	}
	
	/**
	 * Gets the association ref.
	 *
	 * @return associationRef
	 */
	public HashMap<String, String> getAssociationRef() {
		return associationRef;
	}
	
	/**
	 * Sets the association ref.
	 *
	 * @param associationRef the association ref
	 */
	public void setAssociationRef(HashMap<String, String> associationRef) {
		this.associationRef = associationRef;
	}

}