package bpmImport.smParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * The Class Parser parses service configuration file.
 *
 * @author Björn Buchwald
 */
public class Parser {
	
	/** view comments */
	private static final boolean COMMENTS = false;
	/** The input file name. */
	private String inputFileName;
	
	/** The xml reader. */
	private XMLReader xmlReader; 
	
	/** The service object content handler extract data */
	private ServiceObjectContentHandler serviceObjectContentHandler;
	
	/** The service object list holds service components */
	private ArrayList<DTO_ServiceObject> serviceObjectList;
	 
	/** The temporal model rules. */
	private ArrayList<DTO_ServiceModelRule> modelRules;
	
	/**
	 * Class constructor instantiates a new parser.
	 *
	 * @param inputFileName the input file name
	 */
	public Parser(String inputFileName){
		
		this.inputFileName = inputFileName;
		
		this.serviceObjectContentHandler = new ServiceObjectContentHandler();
		
		this.serviceObjectList = new ArrayList<DTO_ServiceObject>();
		
		this.modelRules = new ArrayList<DTO_ServiceModelRule>();
		
	}
	
	/**
	 * Parses the service configuration.
	 */
	public void parseSM(){
		
		try {
	    	
	    	// XMLReader erzeugen
	    	xmlReader = XMLReaderFactory.createXMLReader();
	      
	    	// Pfad zur XML Datei
	    	FileReader reader = new FileReader(inputFileName);
		    InputSource inputSource = new InputSource(reader);
		
		    // PersonenContentHandler wird übergeben
		    xmlReader.setContentHandler(serviceObjectContentHandler);
		    
		    // Parsen wird gestartet
		    xmlReader.parse(inputSource);
		    
	    } catch (FileNotFoundException e) {
	    	
		      System.out.println("Input file not found!");
		      System.exit(1);
		      
		} catch (IOException e) {
			
		      e.printStackTrace();
		      
		} catch (SAXException e) {
			
		      e.printStackTrace();
		      
		}
		
	}
	
	/**
	 * Checks if is valid rule.
	 *
	 * @param rule the rule
	 * @return true, if is valid rule
	 */
	private boolean isValidRule(Rule rule){
		
		if(!rule.isSelected() || rule.getRuleType() == null || rule.getDTO_RuleElementBase() != DTO_RuleElementType.DTO_RuleElementComponent) return false;
		
		return true;
		
	}
	
	/**
	 * Process valid service model rules.
	 */
	public void processValidServiceModelRules(){
		
		boolean isValidRule;
		
		for(DTO_ServiceModelRule curServiceModelRule : serviceObjectContentHandler.getModelRules()){
			
			isValidRule = true;
			
			for(Rule postRule : curServiceModelRule.getPostRules()){
				
				if(!isValidRule(postRule)){
						
						isValidRule = false;
						break;
				}
					
			}
			
			if(!isValidRule) continue;
			
			for(Rule preRule : curServiceModelRule.getPreRules()){
				

				if(!isValidRule(preRule)){
						
						isValidRule = false;
						break;
				}
				
			}
			
			if(!isValidRule) continue;
			
			this.modelRules.add(curServiceModelRule);
		
		}
		/*
		for(DTO_ServiceModelRule rule : this.modelRules){
			 
			 if(COMMENTS) System.out.println(rule.getName());
			 
		}
		*/
	}
	
	//set hierachy of service modeller graph in attribute "SMtoBPMN_componentHierarchy" to each service object
	/**
	 * Traverse service graph.
	 *
	 * @param actNode the act node
	 */
	private void traverseSMGraph(DTO_ServiceObject actNode){
		
		int size = actNode.getSuccessors().size();
		
		while(size > 0){
			
			DTO_ServiceObject sucServiceNode = actNode.getSuccessors().get(size-1);
			
			//erstelle Namenshierarchie
			if(sucServiceNode.getType() == DTO_ServiceObjectType.DTO_ServiceObjectComponent){
				
				sucServiceNode.getAttributes().get("SMtoBPMN_componentHierarchy").addAll(0, actNode.getAttributes().get("SMtoBPMN_componentHierarchy"));
				
			}else{ //ueberspringe Connectoren bei der Namenshierarchie
				
				sucServiceNode.getAttributes().get("SMtoBPMN_componentHierarchy").clear();
				sucServiceNode.getAttributes().get("SMtoBPMN_componentHierarchy").addAll(actNode.getAttributes().get("SMtoBPMN_componentHierarchy"));
			}
			
			size--;
			
			traverseSMGraph(sucServiceNode);
			
		}
		
	}
	
	/**
	 * Process service component hierarchy.
	 */
	public void processServiceComponentHierarchy(){
		 
		DTO_ServiceObject rootService = null;
		boolean isRoot = true;
		
		//successors of nodes
		for(Edge edge : serviceObjectContentHandler.getEdgeList()){
			 
			for(DTO_ServiceObject parentNode : serviceObjectContentHandler.getServiceObjectList()){
				
				if( parentNode.getGuid().equals(edge.getParent()) ){
					
					for(DTO_ServiceObject curNode : serviceObjectContentHandler.getServiceObjectList()){
						
						if( curNode.getGuid().equals(edge.getChild() )){
							 
							parentNode.getSuccessors().add(curNode);
							break;
							
						}
						 	
					}
					break;
				}
			 
			}
			 
		}
		
		for(DTO_ServiceObject node : serviceObjectContentHandler.getServiceObjectList()){
			
			if(COMMENTS){
				
				System.out.println("Node: "+node.getName());
			
				for(DTO_ServiceObject service : node.getSuccessors()){
					
					System.out.println("Successor: "+service.getName());
					
				}
				
			}
			
		}
		
		//get root
		for(DTO_ServiceObject node : serviceObjectContentHandler.getServiceObjectList()){
			
			for(Edge edge : serviceObjectContentHandler.getEdgeList()){
				
				if(node.getGuid() == edge.getChild()){
					
					isRoot = false;
					break;
					
				}
				
			}
			
			if(isRoot){
				
				rootService = node;
				break;
				
			}
		
		}
		if(COMMENTS) System.out.println("Root: "+rootService.toString());
		
		//getLeafs
		for(DTO_ServiceObject node : serviceObjectContentHandler.getServiceObjectList()){
			
			if(node.getSuccessors().size() == 0){
				
				this.serviceObjectList.add(node);
				
			}
			
		}
		
		if(COMMENTS){
			
			System.out.println("Leafs: "); 
			
			for(DTO_ServiceObject serviceObject : this.serviceObjectList){
				
				System.out.println(serviceObject.toString());
				
			}
			
		}
		
		//deep-first search
		traverseSMGraph(rootService);
		
		if(COMMENTS){
			System.out.println("After traverse: ");
		
			for(DTO_ServiceObject serviceObject : this.serviceObjectList){
				
				System.out.println("Check attibutes: ");
				System.out.print(serviceObject.getName()+": ");
				System.out.println(serviceObject.getAttributes().toString());
				
			}
			
		}
		
	}
	
	/**
	 * Postprocessing choose valid rules and gets the component hierarchy.
	 */
	public void postprocessing(){
		
		processValidServiceModelRules();
			
		processServiceComponentHierarchy();
		
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
	 * Sets the service object list.
	 *
	 * @param serviceObjectList the serviceObjectList to set
	 */
	public void setServiceObjectList(ArrayList<DTO_ServiceObject> serviceObjectList) {
		this.serviceObjectList = serviceObjectList;
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
	 * Sets the model rules.
	 *
	 * @param modelRules the modelRules to set
	 */
	public void setModelRules(ArrayList<DTO_ServiceModelRule> modelRules) {
		this.modelRules = modelRules;
	}
	
	/**
	 * Gets the input file name.
	 *
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * Sets the input file name.
	 *
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	
}


