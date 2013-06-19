/**
 * 
 */
package bpmnCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import bpmImport.smParser.DTO_ServiceModelRule;
import bpmImport.smParser.DTO_ServiceObject;
import bpmImport.smParser.Rule;
import bpmImport.smParser.RuleType;

/**
 * Class takes services in correct temporal order by creating an graph structure.
 * @author Björn Buchwald
 *
 */
public class ServiceGraph {
	
	/** view comments */
	private static final boolean COMMENTS = false;
	/** holds services of imported service configuration. */
	private ArrayList<DTO_ServiceObject> serviceObjectList;

	/** holds all services as nodes for graph structure. */
	private HashMap<String, ServiceNode> serviceNodeList;

	/** holds all post nodes for a given pre rule defined by before rules. */
	private HashMap<String, HashSet<String>> beforeRulePostNodes;
	
	/** holds all post nodes for a given pre rule defined by iBefore rules. */
	private HashMap<String, HashSet<String>> iBeforeRulePostNodes;
	
	/**
	 * Class constructor initialize node- and rule-lists.
	 *
	 * @param serviceObjectList the service object list
	 */
	public ServiceGraph(ArrayList<DTO_ServiceObject> serviceObjectList){
		
		this.serviceNodeList = new HashMap<String, ServiceNode>();
		this.beforeRulePostNodes = new HashMap<String, HashSet<String>>(); //for all before-rules
		this.iBeforeRulePostNodes = new HashMap<String, HashSet<String>>(); //for all iBefore-rules
		this.serviceObjectList = serviceObjectList;
	}
	
	/**
	 * transform all components in rules, which aren't leafs in service graph, to their associated leaf components.
	 *
	 * @param ruleListParam temporal rule list
	 */
	public void rulesToLeafNodes(ArrayList<Rule> ruleListParam){
		//copy rule list
		ArrayList<Rule> ruleList = new ArrayList<Rule>(ruleListParam);
		//proof left and right side of rule for all rules in this list
		for(Rule curRuleComponent: ruleList){
			
			boolean isLeaf = false;
			//proof if current ruleComponent is a leaf component
			for(DTO_ServiceObject service : serviceObjectList){
				
				if(service.getName().equals(curRuleComponent.getComponentName())){
					
					isLeaf = true;
					break;
					
				}
				
			}
			if(isLeaf){
				continue;
			}
			else{ //if this component is no leaf
				
				//list of leaf components which to replace with this rule component (inner node)
				HashSet<String> replaceRuleList = new HashSet<String>(); 
																		
				for(DTO_ServiceObject service : serviceObjectList){
					//get leaf, which contains this ruleComponent as predecessor in hierarchy 
					if(service.getAttributes().get("SMtoBPMN_componentHierarchy").contains(curRuleComponent.getComponentName())){
						//insert this leaf for afterwards replace with ruleComponent
						replaceRuleList.add(service.getName());
						
					}
					
				}
				//insert each leaf component as new rule component in this rule
				for(String replaceRule : replaceRuleList){

					Rule rule = new Rule();
					rule.setComponentName(replaceRule);
					rule.setDTO_RuleElementBase(curRuleComponent.getDTO_RuleElementBase());
					rule.setRuleType(curRuleComponent.getRuleType());
					rule.setSelected(curRuleComponent.isSelected());
					ruleListParam.add(rule);
					
				}
				//remove old rule component (inner node of service graph)
				ruleListParam.remove(curRuleComponent);
				
			}
			
		}
		
	}
	
	/**
	 * set all post rule components for a given pre rule component given by temporal conditions.
	 *
	 * @param modelRule DTO_ServiceModelRule current model rule
	 * @param ruleSuccessors HashMap<String, HashSet<String>> key= preRule , value= post rules for this preRules, given by temporal conditions (before, iBefore, ...)
	 */
	private void setRuleSuccessors(DTO_ServiceModelRule modelRule, HashMap<String, HashSet<String>> ruleSuccessors){
		
		for(bpmImport.smParser.Rule preRule: modelRule.getPreRules()){
			
			HashSet<String> postNodes = new HashSet<String>();
			
			for(bpmImport.smParser.Rule postRule: modelRule.getPostRules()){
				
				 postNodes.add(postRule.getComponentName());
				
			}
			
			//insert new postNodes for this preNode
			if(ruleSuccessors.containsKey(preRule.getComponentName())){
				ruleSuccessors.get(preRule.getComponentName()).addAll(postNodes);
			}else{	//if there is no rule with this preNode in the list
				ruleSuccessors.put(preRule.getComponentName(), postNodes);
			}
			
		}
		
	}
	
	/**
	 * creates the temporal service graph by applying temporal conditions.
	 *
	 * @param modelRules temporal rules
	 */
	public void createServiceGraph(ArrayList<DTO_ServiceModelRule> modelRules){
		
		//create serviceNodes out of serviceObjects
		for(DTO_ServiceObject service: serviceObjectList){
			
			serviceNodeList.put(service.getName(), new ServiceNode(service.getGuid(), service.getName()));
			
		}
		
		//transform all components of rules, which aren't leafs in service graph, to their associated leaf components.
		for(DTO_ServiceModelRule modelRule : modelRules){
			
			rulesToLeafNodes(modelRule.getPreRules());
			rulesToLeafNodes(modelRule.getPostRules());
			if(COMMENTS) System.out.println(modelRule.toString());
		}
		
		//set temporale rules;
		//create map with all temporal postNodes for each preNode 
		for(DTO_ServiceModelRule modelRule: modelRules){
			
			//if not a valid rule, cause no preRule or postRule, shouldn't be a case 
			if(modelRule.getPreRules().size() == 0 || modelRule.getPostRules().size() == 0) continue; 
			
			if(modelRule.getPreRules().get(0).getRuleType() == RuleType.iBefore){	//set list of iBefore-Rules
				setRuleSuccessors(modelRule, this.iBeforeRulePostNodes);
				if(COMMENTS) System.out.println("iBefore-rules: "+this.iBeforeRulePostNodes);
			}else if(modelRule.getPreRules().get(0).getRuleType() == RuleType.before){	//set list of before-Rules
				setRuleSuccessors(modelRule, this.beforeRulePostNodes);
				if(COMMENTS) System.out.println("before-rules: "+this.beforeRulePostNodes);
			}
			
		}
		
		//set direct predecessor and successors of serviceNodes by iBeforeRules
		//setPrePostNodes(this.beforeRulePostNodes);
		for(String sucNode: this.iBeforeRulePostNodes.keySet()){
			
			this.serviceNodeList.get(sucNode).addSuccessorList(this.iBeforeRulePostNodes.get(sucNode)); //add direct predecessors of current node
			
			for(String preNode: this.iBeforeRulePostNodes.get(sucNode)){
				this.serviceNodeList.get(preNode).addPredecessor(sucNode);	//add predecessor (preNode) for all successors (sucNodes)
			}
			
		}
		
		if(COMMENTS){
			System.out.println("Check iBefore rule creation: "+this.iBeforeRulePostNodes);
			System.out.println("Check service nodes: "+this.serviceNodeList);
		}
		//compute all successors for each node by traversing serviceGraph
		actualizeSuccessors();
		
		//vergleiche paarweise die AllSuccessors-Mengen der rechten Seite der Regeln
		//ist ein Nachfolger bereits in einer anderen Nachfolger-Menge enthalten
		//lösche ihn aus der rechten Seite der Regel
		for(String preNode: this.beforeRulePostNodes.keySet()){
			
			ArrayList<String> ruleSuccessorArray = new ArrayList<String>(this.beforeRulePostNodes.get(preNode)); //HashSet to ArrayList
			
			for(int i = 0; i < this.beforeRulePostNodes.get(preNode).size(); i++){
				
				for(int j = 0; j < this.beforeRulePostNodes.get(preNode).size(); j++){
					if(j == i) continue;
					
					if( this.serviceNodeList.get( ruleSuccessorArray.get(j) ).getAllSuccessors().contains(ruleSuccessorArray.get(i)) ){
						
						this.beforeRulePostNodes.get(preNode).remove( ruleSuccessorArray.get(i) );
						
					}
					
				}
				
			}
			if(COMMENTS) System.out.println("Check before rule creation after remove: "+this.beforeRulePostNodes);
			//insert direct successors of preNode by before-rules
			this.serviceNodeList.get(preNode).addSuccessorList(this.beforeRulePostNodes.get(preNode));
			//insert direct predecessor of succNodes
			for(String pre: this.beforeRulePostNodes.get(preNode)){
				
				this.serviceNodeList.get(pre).addPredecessor(preNode);
				
			}
			if(COMMENTS) System.out.println("Check direct pre and succ: "+this.serviceNodeList);
			
			//actualize list off all Successors for all service nodes
			actualizeSuccessors();
			if(COMMENTS) System.out.println("Check actualized allSuccessors list: "+this.serviceNodeList);
		}
		
		if(COMMENTS) System.out.println("Check service nodes end of iteration: "+this.serviceNodeList);
		
		//delete unnecessary edges
		//
		for(String node: this.serviceNodeList.keySet()){
			
			if(this.serviceNodeList.get(node).getSuccessors().size() <= 1) continue;
			
			ArrayList<String> successorArray = new ArrayList<String>(this.serviceNodeList.get(node).getSuccessors());
			
			for(int i = 0; i < successorArray.size(); i++){
				
				for(int j = 0; j < successorArray.size(); j++){
					
					if(i == j) continue;
					
					if( this.serviceNodeList.get(successorArray.get(j)).getAllSuccessors().contains(successorArray.get(i)) ){ 
						
						if(this.beforeRulePostNodes.get(node) != null && this.beforeRulePostNodes.get(node).contains(successorArray.get(i))){ //wenn before Regel
							
							//lösche Kante
							this.serviceNodeList.get(node).remSuccessor(successorArray.get(i));
							this.serviceNodeList.get(successorArray.get(i)).remPredecessor(node);
							
							if(COMMENTS) System.out.println("Check service nodes after remove unnecessary edges iteration: "+this.serviceNodeList);
						}
						
					}
					
					
				}
				
			}
			actualizeSuccessors();
			
		}
		
		if(COMMENTS){
			System.out.println("Check service nodes after remove unnecessary edges and actualized allPredecssors (end): ");
			System.out.println(this.serviceNodeList);
		}
		
	}
	
	/**
	 * compute successors of all nodes by traversing serviceGraph (see getNodeSuccessors()).
	 */
	private void actualizeSuccessors(){
		
		for(String node: this.serviceNodeList.keySet()){
			
			HashSet<String> actSuccessors = new HashSet<String>();
			
			//recursive
			getNodeSuccessors(node, actSuccessors);
			
			//add list off all Predecessors for this node
			this.serviceNodeList.get(node).setAllSuccessors(actSuccessors);
			
		}
		
	}
	
	/**
	 * recursive computation of all successors of this node.
	 *
	 * @param node the node
	 * @param successors the successors
	 * @return the node successors
	 */
	private void getNodeSuccessors(String node, HashSet<String> successors){
		
		for(String succe: this.serviceNodeList.get(node).getSuccessors()){
			
			successors.add(succe); //add this successors to successors list for actuell node
			getNodeSuccessors(succe, successors); //recursive function call
			
		}
			
	}
	
	/**
	 * get intersection of two HashSets (currently not used).
	 *
	 * @param a HashSet one
	 * @param b HashSet two
	 * @return intersection
	 */
	public HashSet<String> getIntersection(HashSet<String> a, HashSet<String> b){
		
		HashSet<String> intersection = new HashSet<String>();
		
		for(String first: a){
			
			for(String second: b){
				
				if(first.equals(second)) intersection.add(first);
				
			}
			
		}
		
		return intersection;
	}
	
	/**
	 * Gets the service node list.
	 *
	 * @return the serviceNodeList
	 */
	public HashMap<String, ServiceNode> getServiceNodeList() {
		return serviceNodeList;
	}
	
	
	/**
	 * Gets the service object list.
	 *
	 * @return the service object list
	 */
	public ArrayList<DTO_ServiceObject> getServiceObjectList() {
		return serviceObjectList;
	}

	/**
	 * Sets the service object list.
	 *
	 * @param serviceObjectList the new service object list
	 */
	public void setServiceObjectList(ArrayList<DTO_ServiceObject> serviceObjectList) {
		this.serviceObjectList = serviceObjectList;
	}
	
/*
	//test contains-function of HashSet
	public static void main(String[] args){
		
		HashSet<String> h1 = new HashSet<String>();
		h1.add("A");
		h1.add("B");
		h1.add("C");
		
		HashSet<String> h2 = new HashSet<String>();
		h2.add("A");
		h2.add("B");
		h2.add("C");
		
		if(h1.contains(h2)){
			
			if(COMMENTS) System.out.println("yes");
			
		}
		
	}
*/
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceGraph [serviceNodeList=" + serviceNodeList
				+ ", beforeRulePostNodes=" + beforeRulePostNodes
				+ ", iBeforeRulePostNodes=" + iBeforeRulePostNodes + "]";
	}
	
}
