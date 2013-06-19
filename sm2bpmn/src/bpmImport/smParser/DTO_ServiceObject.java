package bpmImport.smParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class DTO_ServiceObject represents a service component.
 *
 * @author Björn Buchwald
 */
public class DTO_ServiceObject {
	
	/** The type. */
	private DTO_ServiceObjectType type;
	
	/** The description. */
	private String description;
	
	/** The guid. */
	private String guid;
	
	/** The name. */
	private String name;
	
	/** The successors. */
	private ArrayList<DTO_ServiceObject> successors;
	
	/** The attributes. */
	private HashMap<String, ArrayList<String>> attributes;
	
	//private ArrayList<Characteristics> characteristics;
	
	/**
	 * Instantiates a new DTO_Service object.
	 */
	public DTO_ServiceObject(){
		
		this.successors = new ArrayList<DTO_ServiceObject>();
		this.attributes = new HashMap<String, ArrayList<String>>();
		this.attributes.put("SMtoBPMN_componentHierarchy", new ArrayList<String>());
		this.description = ""; 

	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public DTO_ServiceObjectType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(DTO_ServiceObjectType type) {
		this.type = type;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the guid.
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the guid.
	 *
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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
	 * Gets the successors.
	 *
	 * @return the successors
	 */
	public ArrayList<DTO_ServiceObject> getSuccessors() {
		return successors;
	}

	/**
	 * Sets the successors.
	 *
	 * @param successors the successors to set
	 */
	public void setSuccessors(ArrayList<DTO_ServiceObject> successors) {
		this.successors = successors;
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public HashMap<String, ArrayList<String>> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes the attributes to set
	 */
	public void setAttributes(HashMap<String, ArrayList<String>> attributes) {
		this.attributes = attributes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		/*return "DTO_ServiceObject [type=" + type + ", description="
				+ description + ", guid=" + guid + ", name=" + name
				+ ", attributes=" + attributes
				+ "]";*/
		return name;
	}
	
}
