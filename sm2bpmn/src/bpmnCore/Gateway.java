package bpmnCore;

import java.util.HashSet;

/**
 * The Class Gateway represents a gateway according to the bpmn model.
 */
public class Gateway extends Element {
	
	/** The incoming. */
	private HashSet<String> incoming;
	
	/** The outgoing. */
	private HashSet<String> outgoing;
	
	/**
	 * Class constructor instantiates a new gateway.
	 *
	 * @param id the id
	 */	
	public Gateway(String id) {
		super(id);
		incoming = new HashSet<String>();
		outgoing = new HashSet<String>();	
	}
	
	/**
	 * Class constructor instantiates a new gateway.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Gateway(String id, String name) {
		super(id, name);
		incoming = new HashSet<String>();
		outgoing = new HashSet<String>();	
	}
	
	/**
	 * Gets the incoming.
	 *
	 * @return incoming
	 */
	public HashSet<String> getIncoming() {
		return incoming;
	}
	
	/**
	 * Sets the incoming.
	 *
	 * @param incoming incoming
	 */
	public void setIncoming(HashSet<String> incoming) {
		this.incoming = incoming;
	}
	
	/**
	 * Gets the outgoing.
	 *
	 * @return outgoing
	 */
	public HashSet<String> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Sets the outgoing.
	 *
	 * @param outgoing outgoing
	 */
	public void setOutgoing(HashSet<String> outgoing) {
		this.outgoing = outgoing;
	}
}