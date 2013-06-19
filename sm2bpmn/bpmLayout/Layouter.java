package bpmLayout;

import bpmnCore.MemoryModel;

/**
 * The Class Layouter creates graphical information of bpmn elements and take them to memory model.
 */
public abstract class Layouter {
	
	/**
	 * Layout memory model.
	 *
	 * @param mm the mm
	 */
	public abstract void layoutMemoryModel(MemoryModel mm);
}