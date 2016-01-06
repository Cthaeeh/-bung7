package diGraph;

import diGraph.DiGraphNode.VISITORS;

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
		nodes = new List();		
		for(int k = 0;k<keys.length;k++){
			keys[k] = new DiGraphNode(keys[k]);
		}
		
		for(int k = 0;k < keys.length;k++){					//Iterate over all keys
			DiGraphNode temp = (DiGraphNode) keys[k];	//Create a Node for each of them			
			for( int row = 0;row < keys.length;row++ ){		
				if(adjacencyMatrix[k][row]==true){			//Then look in the Matrix if there is an arrow to another node	
					temp.adjacencyList.insert((DiGraphNode) keys[row]);	//If so our Node saves that in its adjacency List
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
	
	public int minDistance(DiGraphNode u,DiGraphNode v,DiGraphNode w){
		int walkingDistanceU = 0,walkingDistanceV = 0,walkingDistanceW = 0;				//How far each Prof. walked 
		int iterations = 0;
		
		uNeighborList.insert(u);
		u.visitorState = VISITORS.PROF_U;
		vNeighborList.insert(v);
		v.visitorState = VISITORS.PROF_V;
		wNeighborList.insert(w);
		w.visitorState = VISITORS.PROF_W;
		
		while(iterations <2*nodes.size){													//this loop is uglier than the night FIXME
			
			walkingDistanceU++;																//each prof moves one edge forward (in every possible direction)
			uNeighborList=getNeighbors(uNeighborList,VISITORS.PROF_U,walkingDistanceU);
			if(finalDistance > 0)return finalDistance;
			walkingDistanceV++;
			vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);
			if(finalDistance > 0)return finalDistance;
			walkingDistanceW++;
			wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);
			if(finalDistance > 0)return finalDistance;
			if(intersectionStateUV&&(!intersectionStateUW)&&(!intersectionStateVW)){
				walkingDistanceW++;
				wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);
			}
			if(finalDistance > 0)return finalDistance;
			if(intersectionStateUW&&(!intersectionStateUV)&&(!intersectionStateVW)){
				walkingDistanceV++;
				vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);
			}
			if(finalDistance > 0)return finalDistance;
			if(intersectionStateVW&&(!intersectionStateUV)&&(!intersectionStateUW)){
				walkingDistanceU++;
				uNeighborList=getNeighbors(uNeighborList,VISITORS.PROF_U,walkingDistanceU);
			}
			if(finalDistance > 0)return finalDistance;		
			iterations++;
		}
		return -1;
	}
	
	private List getNeighbors(List nodes, VISITORS visitor, int walkingDistance) {		
		List newNeighborList = new List();														//This Method is uglier than the nigh FIXME (Idea: implement an iterator in list )
		
		ListItem node = nodes.getHead();																	
		while(node != null){										 							//Iterate over nodes
			DiGraphNode temp = node.key;											
			
			ListItem neighborNode = temp.adjacencyList.getHead();								//Iterate over the neighbors of each of them				
			while(neighborNode != null){							
				if(neighborNode.key.visit(visitor, walkingDistance)==null)break;				//If the neighbor was already visited by this visitor (e.g. the visit returns null) then do nothing (break is for code optimization, not sure if this is also a Bug )
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
