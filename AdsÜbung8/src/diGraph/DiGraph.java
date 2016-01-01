package diGraph;

	/* 16.12.2015
	 * Gruppe 04
	 * Aufgabe 4
	 * Tim Appelt
	 * u.
	 * Kai Hofmann
	 * Luft und Raumfahrtinformatik
	 * 
	 * 
	 */

public class DiGraph {
	 
	final List nodes;	//A List of all my Nodes
	
	
	//Constructors
	public DiGraph(){
		nodes = new List();
	}
	
	public DiGraph(Object[] keys, boolean[][] adjacencyMatrix){		
		nodes = new List();		
		for(int k = 0;k < keys.length;k++){					//Iterate over all keys
			DiGraphNode temp = new DiGraphNode(keys[k]);	//Create a Node for each of them
			for( int row = 0;row < keys.length;row++ ){		
				if(adjacencyMatrix[k][row]==true){			//Then look in the Matrix if there is an arrow to another node	
					temp.adjacencyList.insert(keys[row]);	//If so our Node saves that in its adjacency List
				}
			}
			this.nodes.insert(temp);						//Add the Node to the List of all my Nodes in the Graph
		}
 	}

	//Methods
	public DiGraphNode addNode(Object key){
		DiGraphNode newNode = new DiGraphNode(key);		//Create a new Node and add it to the List of all Nodes in the Graph 	
		nodes.insert(newNode);
		return newNode;
	}
	
	public void addEdge(DiGraphNode v1, DiGraphNode v2){
		if(find(v1)!= null && find(v2)!= null)find(v1).adjacencyList.insert(find(v2));		//If we have the Nodes v1,v2 in our graph save v2 in the adjacecncyList of v1
	}
	
	public DiGraphNode find(Object key){	
		ListItem temp = nodes.getHead();							//Start with the head of the List		
		while(temp != null){										 
			DiGraphNode test = (DiGraphNode) temp.key;				//Get the key recent the node
			if(test.key.equals(key))return (DiGraphNode) temp.key;	//If it is the correct one return the node
			temp = temp.next;										//Check the next node
		}
		return null;												
	}
}
