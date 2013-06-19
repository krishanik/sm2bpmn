package bpmnCore;

/**
 * The Class Task represents an task according to the bpmn model.
 */
public class Task extends Element {
	
	/** The documentation. */
	private Documentation documentation;
	
	/** The incoming. */
	private String incoming;
	
	/** The outgoing. */
	private String outgoing;
	
	/**
	 * Class constructor instantiates a new task.
	 *
	 * @param id task id
	 */
	public Task(String id) {
		super(id);			
		documentation = new Documentation();	
	}
	
	/**
	 * Class constructor instantiates a new task.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Task(String id, String name){
		super(id, name);
		documentation = new Documentation();
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

	/**
	 * Gets the documentation.
	 *
	 * @return the documentation
	 */
	public Documentation getDocumentation() {
		return documentation;
	}

	/**
	 * Sets the documentation.
	 *
	 * @param documentation the documentation to set
	 */
	public void setDocumentation(Documentation documentation) {
		this.documentation = documentation;
	}
}