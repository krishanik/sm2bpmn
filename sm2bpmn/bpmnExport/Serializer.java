package bpmnExport;

import bpmnCore.MemoryModel;

/**
 * The Class Serializer serializes a memory model to a specific output format.
 */
public abstract class Serializer {
	
	/**
	 * Creates a specific output format.
	 *
	 * @param mm the memory model
	 * @return String specific output format.
	 */
	public abstract String toString(MemoryModel mm);
}