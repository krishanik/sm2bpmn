package bpmnCore;

/**
 * The Class IdGenerator.
 */
public class IdGenerator
{
	
	/** The state. */
	private int state;

	/**
	 * Instantiates a new id generator.
	 */
	public IdGenerator()
	{
		state = 0;
	}
	
	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public String getNextId()
	{
		String id = "id_" + new Integer(state).toString();
		state = state + 1;
		return id;
	}
}