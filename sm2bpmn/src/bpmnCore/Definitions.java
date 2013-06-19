package bpmnCore;

/**
 * The Class Definitions represents the definition area of a  bpmn document to create. 
 */
public class Definitions {
	
	/** The id. */
	String id;
	
	/**
	 * Class constructor instantiates a new definitions.
	 */
	public Definitions(){
		
	}
	
	/**
	 * Class constructor instantiates a new definitions.
	 *
	 * @param id the id
	 */
	public Definitions(String id){
		this.id = id;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
