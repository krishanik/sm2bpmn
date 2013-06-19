package bpmnCore;

import bpmLayout.Layouter;
import bpmnExport.Serializer;

/**
 * The Class BPMNGraph.
 */
public class BPMNGraph {
	
	/** The mm. */
	private MemoryModel mm;
	
	/** The layouter. */
	private Layouter layouter;
	
	/** The serializer. */
	private Serializer serializer;
	
	/**
	 * Gets the mm.
	 *
	 * @return mm
	 */
	public MemoryModel getMm() {
		return mm;
	}
	
	/**
	 * Sets the mm.
	 *
	 * @param mm mm
	 */
	public void setMm(MemoryModel mm) {
		this.mm = mm;
	}
	
	/**
	 * Gets the layouter.
	 *
	 * @return layouter
	 */
	public Layouter getLayouter() {
		return layouter;
	}
	
	/**
	 * Sets the layouter.
	 *
	 * @param layouter layouter
	 */
	public void setLayouter(Layouter layouter) {
		this.layouter = layouter;
	}
	
	/**
	 * Gets the serializer.
	 *
	 * @return serializer
	 */
	public Serializer getSerializer() {
		return serializer;
	}
	
	/**
	 * Sets the serializer.
	 *
	 * @param serializer serializer
	 */
	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}
}