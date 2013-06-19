package bpmnCore;

/**
 * The Class Process represents an process according to the bpmn model.
 */
public class Process {
	
	/** The id. */
	private String	id;
	
	/**
	 * Class constructor instantiates a new process.
	 */
	public Process(){
		
	}
	
	/**
	 * Class constructor instantiates a new process.
	 *
	 * @param id the id
	 */
	public Process(String id){
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
