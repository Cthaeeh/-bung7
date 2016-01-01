package diGraph;

public class DiGraphNode {
	
	Object key;
	List adjacencyList = new List();
	
	//Constructor
	public DiGraphNode(Object key){
		this.key=key;					//Save key and initialize adjacencyList(empty)
		adjacencyList = new List();
	}
	
	//Methods
	public List getNeighbors(){
		return adjacencyList;
	}
	
	public Object getKey(){
		return key;
	}
	
	
}
