package bpmnCore;

/**
 * The Class Annotation represents an annotation according to the bpmn model.
 */
public class Annotation {
	
	/** The id. */
	private String id;
	
	/** The text. */
	private String text;
	
	/**
	 * Class constructor instantiates a new annotation.
	 *
	 * @param id id
	 * @param text text
	 */
	public Annotation(String id, String text) {
		this.id = id;
		this.text = text;
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
	 * Gets the text.
	 *
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}
}