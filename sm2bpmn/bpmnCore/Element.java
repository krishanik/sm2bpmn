package bpmnCore;

/**
 * The Class Element represents a generalized Element of the bpmn model.
 */
public class Element {
	
	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new element.
	 *
	 * @param id the id
	 */
	public Element(String id){
		
		this.id = id;
		
	}
	
	/**
	 * Instantiates a new element.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Element(String id, String name){
		
		this.id = id;
		this.name = name;
		
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
	
	/**
	 * Gets the name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}
}