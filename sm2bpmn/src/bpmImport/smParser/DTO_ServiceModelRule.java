/**
 * 
 */
package bpmImport.smParser;

import java.util.ArrayList;

/**
 * The Class DTO_ServiceModelRule is a temporal rule of the service model.
 *
 * @author Björn Buchwald
 */
public class DTO_ServiceModelRule {

	/** The name. */
	private String name;
	
	/** The pre rules (preconditions). */
	private ArrayList<Rule> preRules;
	
	/** The post rules (postconditions). */
	private ArrayList<Rule> postRules;
	
	/**
	 * Instantiates a new DTO_Service model rule.
	 */
	public DTO_ServiceModelRule(){
		
		preRules = new ArrayList<Rule>();
		postRules = new ArrayList<Rule>();
		
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the pre rules.
	 *
	 * @return the preRules
	 */
	public ArrayList<Rule> getPreRules() {
		return preRules;
	}

	/**
	 * Sets the pre rules.
	 *
	 * @param preRules the preRules to set
	 */
	public void setPreRules(ArrayList<Rule> preRules) {
		this.preRules = preRules;
	}
	
	/**
	 * Gets the post rules.
	 *
	 * @return the postRules
	 */
	public ArrayList<Rule> getPostRules() {
		return postRules;
	}

	/**
	 * Sets the post rules.
	 *
	 * @param postRules the postRules to set
	 */
	public void setPostRules(ArrayList<Rule> postRules) {
		this.postRules = postRules;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DTO_ServiceModelRule [name=" + name + ", preRules=" + preRules
				+ ", postRules=" + postRules + "]";
	}
	
}
