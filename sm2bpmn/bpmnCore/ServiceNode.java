package bpmnCore;

import java.util.HashSet;
// TODO: Auto-generated Javadoc

/**
 * The Class ServiceNode is a node of the temporal service graph.
 *
 * @author Björn Buchwald
 */
public class ServiceNode {

	/** The label. */
	private String label;
	
	/** The id. */
	private String id;
	
	/** The predecessors. */
	private HashSet<String> predecessors;
	
	/** The direct successors. */
	private HashSet<String> successors;
	
	/** The all successors. */
	private HashSet<String> allSuccessors;
	
	/**
	 * Class constructor instantiates a new service node.
	 *
	 * @param id the id
	 * @param label the label
	 */
	public ServiceNode(String id, String label){
		
		this.id = id;
		this.label = label;
		this.predecessors = new HashSet<String>();
		this.successors = new HashSet<String>();
		this.allSuccessors = new HashSet<String>();
		
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Adds the successor list.
	 *
	 * @param newSuccessors the new successors
	 */
	public void addSuccessorList(HashSet<String> newSuccessors){
		
		this.successors.addAll(newSuccessors);
		
	}
	
	/**
	 * Adds the predecessor list.
	 *
	 * @param newPredecessors the new predecessors
	 */
	public void addPredecessorList(HashSet<String> newPredecessors){
		
		this.predecessors.addAll(newPredecessors);
		
	}
	
	/**
	 * Adds the successor.
	 *
	 * @param newSuccessor the new successor
	 */
	public void addSuccessor(String newSuccessor){
		
		this.successors.add(newSuccessor);
		
	}
	
	/**
	 * Adds the predecessor.
	 *
	 * @param newPredecessor the new predecessor
	 */
	public void addPredecessor(String newPredecessor){
		
		this.predecessors.add(newPredecessor);
		
	}
	
	/**
	 * Remove predecessor.
	 *
	 * @param predecessor the predecessor
	 * @return true, if successful
	 */
	public boolean remPredecessor(String predecessor){
		
		if(!this.predecessors.contains(predecessor)) return false;
		
		this.predecessors.remove(predecessor);
		
		return true;
		
	}
	
	/**
	 * Remove successor.
	 *
	 * @param successor the successor
	 * @return true, if successful
	 */
	public boolean remSuccessor(String successor){
		
		if(!this.successors.contains(successor)) return false;
		
		this.successors.remove(successor);
		
		return true;
		
	}

	/**
	 * Gets the predecessors.
	 *
	 * @return the predecessors
	 */
	public HashSet<String> getPredecessors() {
		return predecessors;
	}

	/**
	 * Gets the successors.
	 *
	 * @return the predecessors
	 */
	public HashSet<String> getSuccessors() {
		return successors;
	}
	
	/**
	 * Gets the all successors.
	 *
	 * @return the allSuccessors
	 */
	public HashSet<String> getAllSuccessors() {
		return allSuccessors;
	}

	/**
	 * Sets the all successors.
	 *
	 * @param allSuccessors the allPredecessors to set
	 */
	public void setAllSuccessors(HashSet<String> allSuccessors) {
		this.allSuccessors = allSuccessors;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceNode [label=" + label + ", id=" + id + ", predecessors="
				+ predecessors + ", successors=" + successors
				+ ", allSuccessors=" + allSuccessors + "]";
		//return label;
	}
	
}
