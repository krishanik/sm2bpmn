package bpmnCore;

/**
 * The Class Association represents an association according to bpmn model.
 */
public class Association{
	
	/** The id. */
	private String id;
	
	/** The source id. */
	private String sourceId;
	
	/** The target id. */
	private String targetId;
	
	/**
	 * Class constructor instantiates a new association.
	 *
	 * @param id the id
	 * @param sourceId the source id
	 * @param targetId the target id
	 * @return id
	 */
	public Association(String id, String sourceId, String targetId) {
		this.id = id;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the source id.
	 *
	 * @return sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * Sets the source id.
	 *
	 * @param sourceId sourceId
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * Gets the target id.
	 *
	 * @return targetId
	 */
	public String getTargetId() {
		return targetId;
	}
	
	/**
	 * Sets the target id.
	 *
	 * @param targetId targetId
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}