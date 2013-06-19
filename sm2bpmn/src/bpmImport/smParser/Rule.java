/**
 * 
 */
package bpmImport.smParser;

// TODO: Auto-generated Javadoc
/**
 * The Class Rule holds a component, which is involved in a temporal rule.
 *
 * @author Björn Buchwald
 */
public class Rule {

	/** The DTO_Rule element base. */
	private DTO_RuleElementType DTO_RuleElementBase;
	
	/** The rule type. */
	private RuleType ruleType;
	
	/** The component name. */
	private String componentName;
	
	//private String componentId; //use instead of name
	
	/** The selected. */
	private boolean selected;
	
	/**
	 * Instantiates a new rule.
	 */
	public Rule(){
		
	}

	/**
	 * Gets the dT o_ rule element base.
	 *
	 * @return the dTO_RuleElementBase
	 */
	public DTO_RuleElementType getDTO_RuleElementBase() {
		return DTO_RuleElementBase;
	}

	/**
	 * Sets the dT o_ rule element base.
	 *
	 * @param dTO_RuleElementBase the dTO_RuleElementBase to set
	 */
	public void setDTO_RuleElementBase(DTO_RuleElementType dTO_RuleElementBase) {
		DTO_RuleElementBase = dTO_RuleElementBase;
	}

	/**
	 * Gets the rule type.
	 *
	 * @return the ruleType
	 */
	public RuleType getRuleType() {
		return ruleType;
	}

	/**
	 * Sets the rule type.
	 *
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * Gets the component name.
	 *
	 * @return the componentName
	 */
	public String getComponentName() {
		return componentName;
	}

	/**
	 * Sets the component name.
	 *
	 * @param componentName the componentName to set
	 */
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	/**
	 * Checks if is selected.
	 *
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rule [DTO_RuleElementBase=" + DTO_RuleElementBase
				+ ", ruleType=" + ruleType + ", componentName=" + componentName
				+ ", selected=" + selected + "]";
	}
	
	
	
}
