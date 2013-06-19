package bpmnCore;

import java.util.ArrayList;

/**
 * The Class Edge represents an graphical path element.
 */
public class Edge{
	
	/** The id. */
	private String id;
	
	/** The ref id. */
	private String refId;	
	
	/** The points. */
	private ArrayList<Point> points;
	
	/**
	 * Class constructor instantiates a new edge.
	 */	
	public Edge() {
		points = new ArrayList<Point>();
	}
	
	/**
	 * Class constructor instantiates a new edge.
	 *
	 * @param id id
	 */	 
	public Edge(String id) {
		points = new ArrayList<Point>();
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
	 * @return refID
	 */
	public String getRefId() {
		return refId;
	}
	
	/**
	 * Sets the ref id.
	 *
	 * @param refId the new ref id
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	/**
	 * Gets the points.
	 *
	 * @return points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * Sets the points.
	 *
	 * @param points points
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
}