package bpmImport;

import bpmnCore.MemoryModel;

/**
 * The Class Import produce the memory model.
 */
public abstract class Import {

	/**
	 * Gets the memory model.
	 *
	 * @return MemoryModel
	 */
	public abstract MemoryModel getMemoryModel();	
}