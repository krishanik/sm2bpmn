package bpmLayout;

import java.util.HashSet;

import bpmnCore.Edge;
import bpmnCore.Bounds;
import bpmnCore.MemoryModel;
import bpmnCore.Point;
import bpmnCore.Shape;


public class SimpleLayouter extends Layouter{
	private MemoryModel mm;

	private double shapeHeigh;
	private double shapeWidth;
	private double deltaWidth;
	private double deltaHeight;
	
	/**
	 *
	 */
	public SimpleLayouter()
	{
		shapeHeigh = 64;
		shapeWidth = 128;
		deltaWidth = 128;
		deltaHeight = 64;	
	}
	
	public double getShapeHeigh() {
		return this.shapeHeigh;
	}
	public void setShapeHeigh(double shapeHeigh) {
		this.shapeHeigh = shapeHeigh;
	}	
	public double getShapeWidth() {
		return this.shapeWidth;
	}
	public void setShapeWidth(double shapeWidth) {
		this.shapeWidth = shapeWidth;
	}
	public double getDeltaWidth() {
		return this.deltaWidth;
	}
	public void setDeltaWidth(double deltaWidth) {
		this.deltaWidth = deltaWidth;
	}
	public double getDeltaHeight() {
		return this.deltaHeight;
	}
	public void setDeltaHeight(double deltaHeight) {
		this.deltaHeight = deltaHeight;
	}
	
	/**
	 * @param mm MemoryModel
	 */
	public void layoutMemoryModel(MemoryModel mm) {
		this.mm = mm;
		double currentWidth = getShapeWidth();
		HashSet<String> firstLayer = new HashSet<String>(mm.getStartEvents().keySet());
		
		// set Bounds
		double sp = (firstLayer.size() * getShapeHeigh() +  getDeltaHeight() * ( firstLayer.size() - 1.0 ))/2.0;
		int i = 0;
		for( String id: firstLayer)
		{
			String shapeID = mm.getShapeRef().get(id);
			mm.getShapes().get(shapeID).getBounds().add(new Bounds(currentWidth, sp - (getShapeHeigh() + getDeltaHeight()) * i,getShapeWidth(),getShapeHeigh()));
			i++;
		}		
		
		drawNextLayer(currentWidth, firstLayer);
		
		alignDiagram();
	}
	
	/**
	 *
	 */
	private void drawNextLayer(double currentWidth, HashSet<String> currentLayer) {
		
		HashSet<String> nextLayer = new HashSet<String>();
		int associationCount = 0;
		for(String id: currentLayer) {
			for(String edgeID: mm.getSuccessors(id)) {
				HashSet<String> successors = mm.getSuccessors(edgeID);
				nextLayer.addAll(successors);
				// annotations
				for(String sID: successors) {
					if(mm.getAssociationRef().containsKey(sID)) ++associationCount;
				}
			}				
		}
		
		if( nextLayer.isEmpty() ) return;
		
		
		// set Bounds
		double sp = (nextLayer.size() * getShapeHeigh() +  getDeltaHeight() * ( nextLayer.size() + associationCount - 1.0 ))/2.0;
		currentWidth += getDeltaWidth()+getShapeWidth();
		int i = 0;
		for( String id: nextLayer)
		{
			String shapeID = mm.getShapeRef().get(id);
			mm.getShapes().get(shapeID).getBounds().add(new Bounds(currentWidth, sp - (getShapeHeigh() + getDeltaHeight()) * i,getShapeWidth(),getShapeHeigh()));
			i++;
			
			// draw annotation if any
			if( mm.getAssociationRef().containsKey(id) ) {
				String assID = mm.getAssociationRef().get(id);
				String annID = mm.getAssociations().get(assID).getSourceId();
				String annShapeID = mm.getShapeRef().get(annID);
				mm.getShapes().get(annShapeID).getBounds().add(new Bounds(currentWidth, sp - (getShapeHeigh() + getDeltaHeight()) * i,getShapeWidth(),getShapeHeigh()));
				i++;
				
				// draw edge: annotation - task 
				String edgeID  = mm.getEdgeRef().get(assID);
				Point pos1 = mm.getShapes().get(annShapeID).getBounds().get(0).getPosition();
				mm.getShapes().get(annShapeID).getBounds().get(0).getPosition().setX(pos1.getX() + getShapeWidth()/2.0);
				Point pos2 = mm.getShapes().get(shapeID).getBounds().get(0).getPosition();
				Point newPos1 = new Point(pos1.getX(), pos1.getY() + getShapeHeigh()/2.0);
				Point newPos2 = new Point(pos2.getX() + getShapeWidth()/2.0, pos2.getY());
				mm.getEdges().get(edgeID).getPoints().add(newPos1);
				mm.getEdges().get(edgeID).getPoints().add(newPos2);				
			}
		}
		
		// set Edges
		for(String id: currentLayer) {
			String shape1ID = mm.getShapeRef().get(id);
			for(String id2: mm.getSuccessors(id)) {
				String edgeID = mm.getEdgeRef().get(id2);
				for(String id3: mm.getSuccessors(id2)) {
					String shape2ID = mm.getShapeRef().get(id3);
					Point pos1 = mm.getShapes().get(shape1ID).getBounds().get(0).getPosition();
					mm.getEdges().get(edgeID).getPoints().add(new Point(pos1.getX() + getShapeWidth(), pos1.getY() + getShapeHeigh()/2.0));
					Point pos2 = mm.getShapes().get(shape2ID).getBounds().get(0).getPosition();
					mm.getEdges().get(edgeID).getPoints().add(new Point(pos2.getX(), pos2.getY() + getShapeHeigh()/2.0));
				}
			}
		}
		
		drawNextLayer(currentWidth, nextLayer);
	}
	
	private void alignDiagram() {
		double minY = 0;
		for(Shape shape: mm.getShapes().values()) {
			for(Bounds bounds: shape.getBounds()) {
				if (minY > bounds.getPosition().getY()) minY = bounds.getPosition().getY();
			}
		}
		
		if(0.0 > minY) minY = (-1.0)*minY;
		minY += getShapeHeigh()/2.0;
		
		for(Shape shape: mm.getShapes().values()) {
			for(Bounds bounds: shape.getBounds()) {
				double oldY = bounds.getPosition().getY();;
				bounds.getPosition().setY(oldY + minY);
			}
		}
		
		for(Edge edge: mm.getEdges().values()) {
			for(Point point: edge.getPoints()) {
				double oldY = point.getY();
				point.setY(oldY + minY);
			}
		}
	}
}















