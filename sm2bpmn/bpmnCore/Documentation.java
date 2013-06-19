package bpmnCore;

/**
 * The Class Documentation represents an description of an bpmn element.
 */
public class Documentation {
	
	/** The id. */
	private String id;
	
	/** The content. */
	private String content;
	
	/**
	 * Class constructor instantiates a new documentation.
	 */
	public Documentation(){
		
	}
	
	/**
	 * Class constructor instantiates a new documentation.
	 *
	 * @param id the id
	 */
	public Documentation(String id){
		this.id = id;
	}
	
	/**
	 * Class constructor instantiates a new documentation.
	 *
	 * @param id the id
	 * @param content the content
	 */
	public Documentation(String id, String content){
		this.id = id;
		this.content = content;
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
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
