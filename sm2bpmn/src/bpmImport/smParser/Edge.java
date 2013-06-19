/**
 * 
 */
package bpmImport.smParser;

/**
 * The Class Edge is an edge in the imported service configuration.
 *
 * @author Björn Buchwald
 */
public class Edge {
	
	/** The parent. */
	private String parent;
	
	/** The child. */
	private String child;
	
	/**
	 * Instantiates a new edge.
	 */
	public Edge(){
		
	}
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * Gets the child.
	 *
	 * @return the child
	 */
	public String getChild() {
		return child;
	}

	/**
	 * Sets the child.
	 *
	 * @param child the child to set
	 */
	public void setChild(String child) {
		this.child = child;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Edge [parent=" + parent + ", child=" + child + "]";
	}

}
