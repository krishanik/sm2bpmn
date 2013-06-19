/**
 * 
 */
package application;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import bpmImport.SMImport;
import bpmImport.smParser.Parser;
import bpmLayout.Layouter;
import bpmLayout.SimpleLayouter;
import bpmnCore.IdGenerator;
import bpmnCore.MemoryModel;
import bpmnCore.ServiceGraph;
import bpmnExport.BPMNSerializer;
import bpmnExport.Serializer;


/**
 * The Class SM2BPMNStarter starts a new service model to business process model process.
 *
 * @author Björn Buchwald
 */
public class SM2BPMNStarter {
	
	/** The program attributes. */
	private HashMap<String, Object> attributes;
	
	/**
	 * Class constructor instantiates a new service model to business process model process.
	 *
	 * @param args the args
	 */
	public SM2BPMNStarter(String[] args){
		
		attributes = new HashMap<String, Object>();
		generateAttributes(args);
		
	}
	
	/**
	 * Generate the program attributes like input and output file name.
	 *
	 * @param args the args
	 */
	private void generateAttributes(String[] args){
		
		//read input params
		attributes.put("inputFileName", "");
		attributes.put("outputFileName", "");
		
		if(args.length == 0){
			
			System.out.println("No arguments found!");
			System.out.println("\nUse:");
			System.out.println("\nParser");
			System.out.println("\t-i inputFileName (required)");
			System.out.println("\t-o outputFileName");
			System.exit(1);

		}
		
		for(int i = 0; i < args.length; i++){
			
			if(args[i].contains("-i") && args.length >= i+1){
				
				attributes.put("inputFileName", args[i+1]);
				
			}
			if(args[i].contains("-o") && args.length >= i+1){
				
				attributes.put("outputFileName", args[i+1]);
				
			}
			i++;
		}
		
		if(attributes.get("inputFileName").equals("")){
			
			System.out.println("Wrong arguments!");
			System.out.println("\nUse:");
			System.out.println("\nParser");
			System.out.println("\t-i inputFileName (required)");
			System.out.println("\t-o outputFileName");
			
			System.exit(1);
			
		}
		
		if(attributes.get("outputFileName").equals("")){
			
			attributes.put("outputFileName", "out.xml");
			
		}
		
	}

	/**
	 * Starts the service model to business process model process.
	 */
	public void start(){
		
		//Parsing--------------------------------------------------------------------------------
		
		Parser parser = new Parser((String) attributes.get("inputFileName"));
		parser.parseSM();
		parser.postprocessing();
		
		//ServiceGraph---------------------------------------------------------------------------
		
		ServiceGraph serviceGraph = new ServiceGraph(parser.getServiceObjectList());
		serviceGraph.createServiceGraph(parser.getModelRules());
		
		//BPM-Memory-Model-----------------------------------------------------------------------------
		
		IdGenerator idGen = new IdGenerator();
		MemoryModel bpmnData =  new SMImport(serviceGraph, idGen).getMemoryModel();
		bpmnData.createEdges(idGen);
		bpmnData.createAssociationRef();
		bpmnData.createShapes(idGen);
		bpmnData.createTypeMap();
		bpmnData.setDefaultValues(idGen);
		
		//Layout---------------------------------------------------------------------------------
		
		Layouter simpleLayout = new SimpleLayouter();
		simpleLayout.layoutMemoryModel(bpmnData);
		
		//Serialize------------------------------------------------------------------------------
		
		Serializer serializer = new BPMNSerializer();
		String bpmnOutput = serializer.toString(bpmnData);
		
		FileWriter fileWriter1 = null;
        try {

            File newTextFile = new File((String) attributes.get("outputFileName"));
            fileWriter1 = new FileWriter(newTextFile);
            fileWriter1.write( bpmnOutput );
            fileWriter1.close();
        } catch (IOException ex) {
            System.out.println("Error while creating output file!");
        }
		
	}
	
	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes the attributes to set
	 */
	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * The main method generate a new SM2BPMNStarter object and run start() methode,
	 * program arguments are:
	 * -i inputFileName (required)
	 * -o outputFileName
	 *
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
		
		SM2BPMNStarter starter = new SM2BPMNStarter(args);
		
		starter.start();
		
	}

}
