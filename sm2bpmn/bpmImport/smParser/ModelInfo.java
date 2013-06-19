/**
 * 
 */
package bpmImport.smParser;

/**
 * The Class ModelInfo holds important information about imported service configuration.
 *
 * @author Björn Buchwald
 */
public class ModelInfo {

	/** The created at. */
	private String createdAt;
	
	/** The description. */
	private String description;
	
	/** The is public. */
	private boolean isPublic;
	
	/** The last change at. */
	private String lastChangeAt;
	
	/** The model type. */
	private String modelType;
	
	/** The name. */
	private String name;
	
	/** The state. */
	private String state;
	
	/**
	 * Instantiates a new model info.
	 */
	public ModelInfo(){
		
	}

	/**
	 * Gets the created at.
	 *
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is public.
	 *
	 * @return the isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * Sets the public.
	 *
	 * @param isPublic the isPublic to set
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * Gets the last change at.
	 *
	 * @return the lastChangeAt
	 */
	public String getLastChangeAt() {
		return lastChangeAt;
	}

	/**
	 * Sets the last change at.
	 *
	 * @param lastChangeAt the lastChangeAt to set
	 */
	public void setLastChangeAt(String lastChangeAt) {
		this.lastChangeAt = lastChangeAt;
	}

	/**
	 * Gets the model type.
	 *
	 * @return the modelType
	 */
	public String getModelType() {
		return modelType;
	}

	/**
	 * Sets the model type.
	 *
	 * @param modelType the modelType to set
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
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

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelInfo [createdAt=" + createdAt + ", description="
				+ description + ", isPublic=" + isPublic + ", lastChangeAt="
				+ lastChangeAt + ", modelType=" + modelType + ", name=" + name
				+ ", state=" + state + "]";
	}
	
}
