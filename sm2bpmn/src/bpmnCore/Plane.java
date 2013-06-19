package bpmnCore;

/**
 * The Class Plane represents a plane according to the bpmn model.
 */
public class Plane {
	
	/** The ref id. */
	private String refId;
	
	/**
	 * Class constructor instantiates a new plane.
	 */
	public Plane(){
		
	}
	
	/**
	 * Class constructor instantiates a new plane.
	 *
	 * @param refId the ref id
	 */
	public Plane(String refId){
		this.refId = refId;
	}
	
	/**
	 * Gets the ref id.
	 *
	 * @return refId
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * Sets the ref id.
	 *
	 * @param refId refId
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}
}