package bpmnCore;

/**
 * The Class Diagram represents the area of graphical layout of the bpmn document to create.
 */
public class Diagram{
	
	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/**
	 * Class constructor instantiates a new diagram.
	 */
	public Diagram(){
		this.name = "";
		this.id = "";
	}
	
	/**
	 * Class constructor instantiates a new diagram.
	 *
	 * @param id the id
	 */
	public Diagram(String id) {
		this.id = id;
		this.name = "";
	}
	
	/**
	 * Class constructor instantiates a new diagram.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Diagram(String id, String name) {
		this.id = id;
		this.name = name;
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
	
}
