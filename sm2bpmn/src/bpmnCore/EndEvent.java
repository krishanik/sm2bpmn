package bpmnCore;

/**
 * The Class EndEvent represents an end event according to the bpmn model.
 */
public class EndEvent extends Element {
	
	/** The incoming. */
	private String incoming;
	
	/**
	 * Class constructor instantiates a new end event.
	 *
	 * @param id EndEvent id
	 */	
	public EndEvent(String id) {
		super(id);
	}
	
	/**
	 * Class constructor instantiates a new end event.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public EndEvent(String id, String name) {
		super(id, name);
	}
	
	/**
	 * Gets the incoming.
	 *
	 * @return incoming
	 */
	public String getIncoming() {
		return incoming;
	}
	
	/**
	 * Sets the incoming.
	 *
	 * @param incoming incoming
	 */
	public void setIncoming(String incoming) {
		this.incoming = incoming;
	}
}	