package bpmnCore;

/**
 * The Class SequenceFlow represents a sequence flow according to the bpmn model
 */
public class SequenceFlow extends Element {
	
	/** The source ref. */
	private String sourceRef;
	
	/** The target ref. */
	private String targetRef;
	
	/**
	 * Class constructor instantiates a new sequence flow.
	 *
	 * @param id SequenceFlow id
	 * @param sourceRef source reference id
	 * @param targetRef target reference id
	 */
	public SequenceFlow(String id, String sourceRef, String targetRef) {
		super(id);
		this.sourceRef = sourceRef;
		this.targetRef = targetRef;
	}
	
	/**
	 * Class constructor instantiates a new sequence flow.
	 *
	 * @param id the id
	 * @param name the name
	 * @param sourceRef the source ref
	 * @param targetRef the target ref
	 */
	public SequenceFlow(String id, String name, String sourceRef, String targetRef) {
		super(id, name);
		this.sourceRef = sourceRef;
		this.targetRef = targetRef;
	}
	
	/**
	 * Gets the source ref.
	 *
	 * @return sourceRef
	 */
	public String getSourceRef() {
		return sourceRef;
	}
	
	/**
	 * Sets the source ref.
	 *
	 * @param sourceRef sourceRef
	 */
	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}
	
	/**
	 * Gets the target ref.
	 *
	 * @return targetRef
	 */
	public String getTargetRef() {
		return targetRef;
	}
	
	/**
	 * Sets the target ref.
	 *
	 * @param targetRef targetRef
	 */
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
}