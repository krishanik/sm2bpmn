package bpmnCore;

/**
 * The Class Bounds holds graphical information of a shape.
 */
public class Bounds{
	
	/** The position. */
	private Point position;
	
	/** The height. */
	private double height;
	
	/** The width. */
	private double width;
	
	/**
	 * Class constructor instantiates a new bounds.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 */	
	public Bounds(double x, double y, double width, double height) {
		position = new Point(x, y);
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Gets the position.
	 *
	 * @return position
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Sets the position.
	 *
	 * @param position position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * Gets the height.
	 *
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Gets the width.
	 *
	 * @return width
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
}
