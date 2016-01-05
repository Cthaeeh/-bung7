package diGraph;

public class DiGraphNode {
	
	Object key;							
	List adjacencyList = new List();	//My Neighbor List
	int sumOfDistances = 0; 			//Its very important that this is initialized with zero 	
	
	VISITORS recentVisitors = VISITORS.NONE;
	
	
	public enum VISITORS {									//by whom where they visited
		 NONE, PROF_U, PROF_V, PROF_W, PROF_UV, PROF_UW, PROF_VW,
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
	
	public VISITORS visit(VISITORS isVisitedBy, int walkingDistance){
		System.out.println("I was visited by:" + recentVisitors.toString() + "  I am visited by " + isVisitedBy.toString()+ " he walked "+ walkingDistance +" My Name is " + key.toString());
		
		if(recentVisitors==isVisitedBy) return null;		//Probably the most common case: The Professor visits one of his neighbor nodes again.
		
		if(recentVisitors==VISITORS.NONE){
			sumOfDistances+=walkingDistance;
			return recentVisitors= isVisitedBy;
		}
		
		//If we had one different Visitor before
		if((recentVisitors==VISITORS.PROF_U && isVisitedBy == VISITORS.PROF_V) || (recentVisitors==VISITORS.PROF_V && isVisitedBy == VISITORS.PROF_U)){
			DiGraph.intersectionStateUV=true;
			sumOfDistances+=walkingDistance;
			return recentVisitors = VISITORS.PROF_UV;
		}
		if((recentVisitors==VISITORS.PROF_V && isVisitedBy == VISITORS.PROF_W) || (recentVisitors==VISITORS.PROF_W && isVisitedBy == VISITORS.PROF_V)){
			sumOfDistances+=walkingDistance;
			DiGraph.intersectionStateVW=true;
			return recentVisitors = VISITORS.PROF_VW;
		}
		if((recentVisitors==VISITORS.PROF_U && isVisitedBy == VISITORS.PROF_W) || (recentVisitors==VISITORS.PROF_W && isVisitedBy == VISITORS.PROF_U)){
			sumOfDistances+=walkingDistance;
			DiGraph.intersectionStateUW=true;
			return recentVisitors = VISITORS.PROF_UW;
		}
		
		//If we had two visitors before but the new one was already there.
		if((recentVisitors==VISITORS.PROF_UV && isVisitedBy == VISITORS.PROF_U) || (recentVisitors==VISITORS.PROF_UW && isVisitedBy == VISITORS.PROF_U)) return null;
		if((recentVisitors==VISITORS.PROF_UV && isVisitedBy == VISITORS.PROF_V) || (recentVisitors==VISITORS.PROF_VW && isVisitedBy == VISITORS.PROF_V)) return null;
		if((recentVisitors==VISITORS.PROF_VW && isVisitedBy == VISITORS.PROF_W) || (recentVisitors==VISITORS.PROF_UW && isVisitedBy == VISITORS.PROF_W)) return null;
		
		//If we had two visitors before and the new one wasn�t already here (Thats what we want -> Profs should meet here)
		if((recentVisitors==VISITORS.PROF_UV && isVisitedBy == VISITORS.PROF_W) || (recentVisitors==VISITORS.PROF_UW && isVisitedBy == VISITORS.PROF_V) 
				|| (recentVisitors==VISITORS.PROF_VW && isVisitedBy == VISITORS.PROF_U)){
			sumOfDistances+=walkingDistance;
			DiGraph.intersectionStateUV=true;
			DiGraph.intersectionStateVW=true;
			DiGraph.intersectionStateUW=true;
			DiGraph.finalDistance= sumOfDistances;
			System.out.println("I was visited by all" + key.toString());
			return recentVisitors = VISITORS.ALL;
		}

		
		return recentVisitors;		
	}
	
}
