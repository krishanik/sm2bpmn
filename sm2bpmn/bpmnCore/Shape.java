package bpmnCore;

import java.util.ArrayList;

/**
 * The Class Shape represents an graphical element.
 */
public class Shape {
	
	/** The id. */
	private String id;
	
	/** The ref id. */
	private String refId;
	
	/** The bounds of this shape. */
	private ArrayList<Bounds> bounds;

	/**
	 * Class constructor instantiates a new shape.
	 */	
	public Shape() {
		bounds =  new ArrayList<Bounds>();
	}
	
	/**
	 * Class constructor instantiates a new shape.
	 *
	 * @param id id
	 */	 
	public Shape(String id) {
		bounds =  new ArrayList<Bounds>();
		setId(id);
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
	 * Gets the ref id.
	 *
	 * @return refId
	 */
	public String getRefId() {
		return refId;
	}
	
	/**
	 * Sets the ref id.
	 *
	 * @param refId refId
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	/**
	 * Gets the bounds.
	 *
	 * @return bounds
	 */
	public ArrayList<Bounds> getBounds() {
		return bounds;
	}

	/**
	 * Sets the bounds.
	 *
	 * @param bounds bounds
	 */
	public void setBounds(ArrayList<Bounds> bounds) {
		this.bounds = bounds;
	}
	
}