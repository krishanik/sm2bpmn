package bpmnCore;

/**
 * The Class StartEvent represents a start event according to the bpmn model.
 */
public class StartEvent extends Element{
	
	/** The outgoing. */
	private String outgoing;

	/**
	 * Class constructor instantiates a new start event.
	 *
	 * @param id the id
	 */
	public StartEvent(String id) {
		super(id);
	}
	
	/**
	 * Class constructor instantiates a new start event.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public StartEvent(String id, String name) {
		super(id, name);
	}
	
	/**
	 * Gets the outgoing.
	 *
	 * @return outgoing
	 */
	public String getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Sets the outgoing.
	 *
	 * @param outgoing outgoing
	 */
	public void setOutgoing(String outgoing) {
		this.outgoing = outgoing;
	}
}
