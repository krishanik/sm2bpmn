package bpmnCore;

/**
 * The Class Point represents a 2D coordinate.
 */
public class Point {
	
	/** The x. */
	private double x;
	
	/** The y. */
	private double y;
	
	/**
	 * Instantiates a new point.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y y
	 */
	public void setY(double y) {
		this.y = y;
	}
}