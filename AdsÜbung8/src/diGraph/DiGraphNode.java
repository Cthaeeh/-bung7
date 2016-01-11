package diGraph;

public class DiGraphNode {
	
	Object key;							
	List adjacencyList = new List();						//list of neighbor nodes
	int sumOfDistances = 0; 								//The sum of distances to U V and W (the professors)	
	
	VISITORS visitorState = VISITORS.NONE;					//Who visited ? By default no professor ever visited the node.
	
	public enum VISITORS {												//possible visitorStates
		 NONE, PROF_U, PROF_V, PROF_W, PROF_UV, PROF_UW, PROF_VW,		// I guess I could do this with int-values and it would be some ns faster, but the code isn´t readable then
		 ALL;
	}
	
	//Constructor
	public DiGraphNode(Object key){
		this.key=key;
		//Save key and initialize adjacencyList(empty)
		adjacencyList = new List();
	}
	
	//Methods
	public List getNeighbors(){
		return adjacencyList;
	}
	
	public Object getKey(){
		return key;
	}
		
	/**		Returns the visitorState( e.g. what Prof. visited ) after a visit by someone
	 * 		
	 * 		If someone was already there nothing changes and we return null
	 * 		Otherwise safe the distance to the visitor (prof) and return the new visitor state
	 * 					
	 * @param visitor		Who is visiting ?
	 * @param walkDistOfVisitor	How far did he walk to get here ?
	 * @return					visitorState e.g by whom this node was visited
	 */
	public VISITORS visit(VISITORS visitor, int walkDistOfVisitor){
		//System.out.println("VisitorState:" + visitorState.toString() + "  Is visited by " + visitor.toString()+ " Distance Visitor walked "+ walkDistOfVisitor +" Name " + key.toString());
		
		if(visitorState==visitor) return null;		//Probably the most common case: The Professor visits one of his neighbor nodes again.
		
		if(visitorState==VISITORS.NONE){			//Someone comes here first
			sumOfDistances+=walkDistOfVisitor;		//Save the distance to this prof.
			return visitorState= visitor;			//return a not null visitorstate, so we can search the neighbornodes of this node in the next iteration 
		}
		
		//If we had one different Visitor before
		if((visitorState==VISITORS.PROF_U && visitor == VISITORS.PROF_V) || (visitorState==VISITORS.PROF_V && visitor == VISITORS.PROF_U)){
			DiGraph.intersectionStateUV=true;
			sumOfDistances+=walkDistOfVisitor;		//Save the distance to this prof.
			return visitorState = VISITORS.PROF_UV;
		}
		if((visitorState==VISITORS.PROF_V && visitor == VISITORS.PROF_W) || (visitorState==VISITORS.PROF_W && visitor == VISITORS.PROF_V)){
			sumOfDistances+=walkDistOfVisitor;		//Save the distance to this prof.
			DiGraph.intersectionStateVW=true;
			return visitorState = VISITORS.PROF_VW;
		}
		if((visitorState==VISITORS.PROF_U && visitor == VISITORS.PROF_W) || (visitorState==VISITORS.PROF_W && visitor == VISITORS.PROF_U)){
			sumOfDistances+=walkDistOfVisitor;		//Save the distance to this prof.
			DiGraph.intersectionStateUW=true;
			return visitorState = VISITORS.PROF_UW;
		}
		
		//If we had two visitors before but the new one was already there -> do nothing and return null
		if((visitorState==VISITORS.PROF_UV && visitor == VISITORS.PROF_U) || (visitorState==VISITORS.PROF_UW && visitor == VISITORS.PROF_U)) return null;
		if((visitorState==VISITORS.PROF_UV && visitor == VISITORS.PROF_V) || (visitorState==VISITORS.PROF_VW && visitor == VISITORS.PROF_V)) return null;
		if((visitorState==VISITORS.PROF_VW && visitor == VISITORS.PROF_W) || (visitorState==VISITORS.PROF_UW && visitor == VISITORS.PROF_W)) return null;
		
		//If we had two visitors before and the new one wasn´t already here (WE GOT AN OPTIMAL MEETING POINT CANDIDATE)
		if((visitorState==VISITORS.PROF_UV && visitor == VISITORS.PROF_W) || (visitorState==VISITORS.PROF_UW && visitor == VISITORS.PROF_V) 
				|| (visitorState==VISITORS.PROF_VW && visitor == VISITORS.PROF_U)){
			sumOfDistances+=walkDistOfVisitor;
			
			DiGraph.intersectionStateUV=true;
			DiGraph.intersectionStateVW=true;
			DiGraph.intersectionStateUW=true;
			if(DiGraph.finalDistance == 0 || DiGraph.finalDistance > sumOfDistances) DiGraph.finalDistance = sumOfDistances;
			return visitorState = VISITORS.ALL;
		}
		return visitorState;		
	}
	
}