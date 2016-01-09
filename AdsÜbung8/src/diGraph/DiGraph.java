package diGraph;

import diGraph.DiGraphNode.VISITORS;

	/* 16.12.2015
	 * Gruppe 04
	 * Aufgabe 4
	 * Tim Appelt
	 * 
	 * 
	 * u.
	 * Kai Hofmann
	 * Luft und Raumfahrtinformatik
	 * 
	 */

public class DiGraph {
	
	final List nodes;	//A List of all my Nodes
	List uNeighborList = new List();
	List wNeighborList = new List();
	List vNeighborList = new List();
	static int finalDistance = 0;
	
	static boolean intersectionStateUV = false;
	static boolean intersectionStateUW = false;
	static boolean intersectionStateVW = false;
	
	//Constructors
	public DiGraph(){
		nodes = new List();
	}
	
	public DiGraph(Object[] keys, boolean[][] adjacencyMatrix){
		
		nodes = new List();														//initialize global nodes List
		DiGraphNode [] nodesAsArray = new DiGraphNode[keys.length]; 			//just local for handling the adjacencyMatrix (you can access a certain Node by Number)
		
		for(int k = 0;k<keys.length;k++){										//iterate over all keys and make nodes of them
			DiGraphNode newNode = new DiGraphNode(keys[k]);
			nodesAsArray[k] = newNode;
			nodes.insert(newNode);
		}
		
		for(int i = 0;i < nodesAsArray.length;i++){								//Iterate over all nodes		
			for( int j = 0;j < nodesAsArray.length;j++ ){		
				if(adjacencyMatrix[i][j]){								//Then look in the Matrix if there is an arrow to another node	
					addEdge(nodesAsArray[i],nodesAsArray[j]);	//If so our Node saves that in its adjacency List
				}
			}
		}
 	}


	//Methods
	public DiGraphNode addNode(Object key){
		DiGraphNode newNode = new DiGraphNode(key);		//Create a new Node and add it to the List of all Nodes in the Graph 	
		nodes.insert(newNode);
		return newNode;
	}
	
	public void addEdge(DiGraphNode v1, DiGraphNode v2){
			v1.adjacencyList.insert(v2);	//If we have the Nodes v1,v2 in our graph save v2 in the adjacecncyList of v1		
	}
	
	public DiGraphNode find(Object key){	
		ListItem temp = nodes.getHead();							//Start with the head of the List		
		while(temp != null){										 
			DiGraphNode test = (DiGraphNode) temp.key;				//Get the key recent the node
			if(test.key.equals(key))return (DiGraphNode) temp.key;	//If it is the correct one return the node
			temp = temp.next;										//Check the next node
		}
		System.out.println("Failed to find Node:" + key.toString());
		return null;												
	}
	
	/**	Computes the optimal meeting point for the three Nodes.
	 * 
	 * @param u	Node where prof U. lives
	 * @param v Node where prof V. lives 
	 * @param w Node where prof W. lives
	 * @return the minimum distance ( u - meetingpoint ) + ( v - meetingpoint ) + ( w - meetingpoint )  
	 */
	public int minDistance(DiGraphNode u,DiGraphNode v,DiGraphNode w){
		int walkingDistanceU = 0,walkingDistanceV = 0,walkingDistanceW = 0;				//How far each Prof. walked 
		int iterations = 0;
		
		resetGraph();
		
		uNeighborList.insert(u);
		u.visitorState = VISITORS.PROF_U;
		vNeighborList.insert(v);
		v.visitorState = VISITORS.PROF_V;
		wNeighborList.insert(w);
		w.visitorState = VISITORS.PROF_W;
		
		while(iterations <2*nodes.size){													//this loop is uglier than the night FIXME
			
			if(intersectionStateUV&&(!intersectionStateUW)&&(!intersectionStateVW)){
				walkingDistanceW++;
				wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);
			}
			if(intersectionStateUW&&(!intersectionStateUV)&&(!intersectionStateVW)){
				walkingDistanceV++;
				vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);
			}
			if(intersectionStateVW&&(!intersectionStateUV)&&(!intersectionStateUW)){
				walkingDistanceU++;
				uNeighborList=getNeighbors(uNeighborList,VISITORS.PROF_U,walkingDistanceU);
			}
			
			
			walkingDistanceU++;																//each prof moves one edge forward (in every possible direction)
			uNeighborList=getNeighbors(uNeighborList,VISITORS.PROF_U,walkingDistanceU);
			walkingDistanceV++;
			vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);
			walkingDistanceW++;
			wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);
			
			
			
			
			
			if(finalDistance > 0)return finalDistance;				
			iterations++;
		}
		return -1;
	}
	
	public void resetGraph() {
		uNeighborList.empty();
		vNeighborList.empty();
		wNeighborList.empty();	
		
		finalDistance = 0;
		
		intersectionStateUV = intersectionStateUW = intersectionStateVW = false;
		
		ListItem temp = nodes.getHead();
			while(temp != null){
				DiGraphNode node = temp.key;
				node.visitorState = VISITORS.NONE;
				node.sumOfDistances = 0;
				temp = temp.next;
			}
		
	}
	
	
	/**	searches all neighbor nodes of the handed nodes visits (and marks them as visited) them and returns them if they were not visited by this visitor before
	 * @param nodes	the nodes ( already visited ) whose neighbors we visit
	 * @param visitor 	the identity of the prof who is visiting.	
	 * @param walkingDistance how far this visitor walked ( e.g. how far they are away from this prof. )
	 * @return a List of Neighbors of nodes ( if they were not already visited by Visitor )
	 */
	private List getNeighbors(List nodes, VISITORS visitor, int walkingDistance) {		
		List newNeighborList = new List();														//This Method is uglier than the nigh FIXME (Idea: implement an iterator in list )
		
		ListItem node = nodes.getHead();																	
		while(node != null){										 							//Iterate over nodes
			DiGraphNode temp = node.key;											
			
			ListItem neighborNode = temp.adjacencyList.getHead();								//Iterate over the neighbors of each of them				
			while(neighborNode != null){							
				if(neighborNode.key.visit(visitor, walkingDistance)==null);				//If the neighbor was already visited by this visitor (e.g. the visit returns null) then do nothing (break is for code optimization, not sure if this is also a Bug )
				else{
					newNeighborList.insert(neighborNode.key);									//Insert in the new neighbors list																								
				}
				neighborNode = neighborNode.next;												//Check the next node
			}			
			node = node.next;																	//Check the next node
		}
		return newNeighborList;
	}
	
	
	
	
	
	
	
	
	
	
	
}
