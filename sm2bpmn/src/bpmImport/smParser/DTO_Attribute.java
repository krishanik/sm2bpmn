package bpmImport.smParser;

import java.util.ArrayList;

/**
 * The Class DTO_Attribute is an attribute of a service object.
 *
 * @author Björn Buchwald
 */
public class DTO_Attribute {

	/** The name. */
	private String name;
	
	/** The values. */
	private ArrayList<String> values;
	
	/**
	 * Class constructor instantiates a new DTO_Attribute.
	 *
	 * @param name the name
	 */
	public DTO_Attribute(String name) {
		
		this.name = name;
		this.values = new ArrayList<String>();
		
	}
	
	/**
	 * Instantiates a new DTO_Attribute.
	 */
	public DTO_Attribute() {
		
		this.values = new ArrayList<String>();
		
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
	 * Gets the values.
	 *
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the values to set
	 */
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DTO_Attribute [name=" + name + ", values=" + values + "]";
	}
	
	
	
}
