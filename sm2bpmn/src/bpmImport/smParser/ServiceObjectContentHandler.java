/**
 *
 */
package bpmImport.smParser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * The Class ServiceObjectContentHandler parses the service modell elements and attributes.
 *
 * @author Björn Buchwald
 */
public class ServiceObjectContentHandler implements ContentHandler {
	
	/** The tag stack. */
	private ArrayList<String> tagStack = new ArrayList<String>();
	
	/** The service object list. */
	private ArrayList<DTO_ServiceObject> serviceObjectList = new ArrayList<DTO_ServiceObject>();
	 
	/** The edge list. */
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	 
	/** The model rules. */
	private ArrayList<DTO_ServiceModelRule> modelRules = new ArrayList<DTO_ServiceModelRule>();
	
	/** The current value. */
	private String currentValue;
	 
	/** The current service object. */
	private DTO_ServiceObject serviceObject;
	 
	/** The current edge. */
	private Edge edge;
	 
	/** The model info. */
	private ModelInfo modelInfo;
	
	/** The current service model rule. */
	private DTO_ServiceModelRule serviceModelRule;
	
	/** The current rule. */
	private Rule rule;
	
	/** The current attribute. */
	private DTO_Attribute attribute;
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		// Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
		// gespeichert
		currentValue = new String(ch, start, length);

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		//start tag to stack
		tagStack.add(qName);
		
		//parse tag DTO_ServiceObject
		if(qName.equals("d2p1:DTO_ServiceObject") && tagStack.contains("d2p1:ServiceNodes")) {
		      
		      serviceObject = new DTO_ServiceObject();
		      
		      //parse attribute type
		      if(atts.getValue("i:type").equals("d2p1:DTO_ServiceObjectComponent") ){
		    	  
		    	  serviceObject.setType( DTO_ServiceObjectType.DTO_ServiceObjectComponent );
		      
		      }
		      
		      if(atts.getValue("i:type").equals("d2p1:DTO_ServiceObjectConnector") ){
		    	  
		    	  serviceObject.setType( DTO_ServiceObjectType.DTO_ServiceObjectConnector );
		    	  
		      }
		      
		}
		
		if(qName.equals("d2p1:DTO_Attribute") && tagStack.contains("d2p1:Attributes") && tagStack.contains("d2p1:DTO_ServiceObject")){
	    	  
	    	  attribute = new DTO_Attribute();
	    	  
	      }
		
		if(qName.equals("d2p1:Edges") && tagStack.contains("d2p1:EdgesServiceNodes")) {
			
			edge = new Edge();
			
		}

		if(qName.equals("ModelInfo")) {
			
			modelInfo = new ModelInfo();
			
		}
		
		
		if(tagStack.contains("ModelRules")){
		
			if (qName.equals("d2p1:DTO_ServiceModelRule")) {
		    		
		    		serviceModelRule = new DTO_ServiceModelRule();
		    	
		    }
			
			if(tagStack.contains("d2p1:DTO_ServiceModelRule") && (tagStack.contains("d2p1:PostRules") || tagStack.contains("d2p1:PreRules")) ){
		
				if(qName.equals("d2p1:DTO_RuleElementBase")){
					
					rule = new Rule();
						
					//parse attribute type
					if(atts.getValue("i:type").equals("d2p1:DTO_RuleElementComponent")){
						  
						rule.setDTO_RuleElementBase( DTO_RuleElementType.DTO_RuleElementComponent );
					  
					}
					  
					if(atts.getValue("i:type").equals("d2p1:DTO_RuleElementConnector") ){
						  
						rule.setDTO_RuleElementBase( DTO_RuleElementType.DTO_RuleElementConnector );
						  
					}
						
				}
					
			}
		
		}	
			
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		//--------------------------------------------DTO_ServiceObject
		if(tagStack.contains("d2p1:ServiceNodes")){
		
			if(qName.equals("d2p1:Description")){
				
				serviceObject.setDescription(currentValue);
				
			}
			
			if(qName.equals("d2p1:Guid")){
				
				serviceObject.setGuid(currentValue);
				
			}
			
			if(qName.equals("d2p1:Name") && tagStack.size() == 5){
			
				serviceObject.setName(currentValue);
				serviceObject.getAttributes().get("SMtoBPMN_componentHierarchy").add(currentValue);
		
			}
			
			if(tagStack.contains("d2p1:Attributes") && tagStack.contains("d2p1:DTO_Attribute")){
				
				if(qName.equals("d2p1:Name")){
					
					attribute.setName(currentValue);
			
				}
				
				if(tagStack.contains("d2p1:Values") && !qName.equals("d2p1:Values")){
					
					attribute.getValues().add(currentValue);
					
				}
				
			}
			
			if(qName.equals("d2p1:DTO_Attribute")){
				
				serviceObject.getAttributes().put(attribute.getName(), attribute.getValues());
				//serviceObject.getAttributes().add(attribute);
				
			}
			
			// serviceObject in Liste abspeichern falls DTO_ServiceObject End-Tag erreicht
		    // wurde.
		    if (qName.equals("d2p1:DTO_ServiceObject")) {
		    	
		    	serviceObjectList.add(serviceObject);
		    	//System.out.println(serviceObject);
		      
		    }
		    
		}
	    
	    //---------------------------------------------Edges
	    if(tagStack.contains("d2p1:EdgesServiceNodes")){
	    
		    if(qName.equals("d2p1:Child")){
				
				edge.setChild(currentValue);
				
			}
			
			if(qName.equals("d2p1:Parent")){
				
				edge.setParent(currentValue);
				
			}
			
			if (qName.equals("d2p1:Edges")) {
		    	
		    	edgeList.add(edge);
		    	//System.out.println(edge);
		      
		    }
		
	    }	
		
		//------------------------------------------------ModelInfo
	    if(tagStack.contains("ModelInfo")){
	    
			if (qName.equals("d2p1:CreatedAt")) {
		    	
				modelInfo.setCreatedAt(currentValue);
		      
		    }
			
			if (qName.equals("Description")) {
				
				modelInfo.setDescription(currentValue);
	  
			}
			
			if (qName.equals("d2p1:IsPublic")) {
		    	
				if(currentValue.equals("true")){
					
					modelInfo.setPublic(true);
				
				}
				
				if(currentValue.equals("false")){
					
					modelInfo.setPublic(false);
				
				}
		      
		    }
			
			if (qName.equals("d2p1:LastChangeAt")) {
				
				modelInfo.setLastChangeAt(currentValue);
	  
			}
			
			if (qName.equals("d2p1:ModelType")) {
				
				modelInfo.setModelType(currentValue);
	  
			}
			
			if (qName.equals("d2p1:Name")) {
		
				modelInfo.setName(currentValue);
	
			}
			
			if (qName.equals("d2p1:State")) {
		
				modelInfo.setState(currentValue);
	
			}
			
			if (qName.equals("ModelInfo")) {
		    	
				//modelInfoList.add(modelInfo);
				//System.out.println(modelInfo);
		      
		    }
			
	    }
	    
	    //----------------------------------------------ModelRules
	    if (tagStack.contains("ModelRules") && tagStack.contains("d2p1:DTO_ServiceModelRule")) {
	    	
	    		
    		if(qName.equals("d2p1:Name")  && !(tagStack.contains("d2p1:PostRules") || tagStack.contains("d2p1:PreRules")) ){
    			
    			serviceModelRule.setName(currentValue);
    			
    		}
    		
    		if( (tagStack.contains("d2p1:PostRules") || tagStack.contains("d2p1:PreRules")) && tagStack.contains("d2p1:DTO_RuleElementBase")){
    			
    			if(qName.equals("d2p1:RuleType")){
    				
    				if(currentValue.equals("before")){ //before
    					
    					rule.setRuleType(RuleType.before);
    				
    				}
    				
    				if(currentValue.equals("iBefore")){	//iBefore
    					
    					rule.setRuleType(RuleType.iBefore);
    				
    				}
    				
    			}
    			
    			if(qName.equals("d2p1:ComponentName")){
    				
    				rule.setComponentName(currentValue);
    				
    			}
    			
    			if(qName.equals("d2p1:Selected")){
    				
    				if(currentValue.equals("true")){
    					
    					rule.setSelected(true);
    				
    				}
    				
    				if(currentValue.equals("false")){
    					
    					rule.setSelected(false);
    				
    				}

    			}
    			
    		}
    		
			if(qName.equals("d2p1:DTO_RuleElementBase") && tagStack.contains("d2p1:PostRules")){
				
				serviceModelRule.getPostRules().add(rule);
				
			}
			
			if(qName.equals("d2p1:DTO_RuleElementBase") && tagStack.contains("d2p1:PreRules")){
				
				serviceModelRule.getPreRules().add(rule);
				
			}
    		
	    }
    		
		if(qName.equals("d2p1:DTO_ServiceModelRule")){
			
			// füge Regel hinzu, wenn sie einer temporalen Beziehung entspricht
			// => Regeln sind before oder iBefore und alle Komponenten sind selected
			
			modelRules.add(serviceModelRule);
			
		}
	    
	    if(qName.equals("ModelRules")){
	    	
	    	//System.out.println(modelRules);
	    	
	    }
		
		//remove end tag from stack
		tagStack.remove(tagStack.size()-1);
		
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the service object list.
	 *
	 * @return the serviceObjectList
	 */
	public ArrayList<DTO_ServiceObject> getServiceObjectList() {
		return serviceObjectList;
	}

	/**
	 * Gets the edge list.
	 *
	 * @return the edgeList
	 */
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}

	/**
	 * Gets the model rules.
	 *
	 * @return the modelRules
	 */
	public ArrayList<DTO_ServiceModelRule> getModelRules() {
		return modelRules;
	}

	/**
	 * Gets the model info.
	 *
	 * @return the modelInfo
	 */
	public ModelInfo getModelInfo() {
		return modelInfo;
	}

}
