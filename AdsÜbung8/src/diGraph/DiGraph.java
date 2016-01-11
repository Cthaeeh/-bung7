package diGraph;

import diGraph.DiGraphNode.VISITORS;

	/* 09.01.2015
	 * Gruppe 04
	 * Aufgabe 4
	 * Tim Appelt
	 * u.
	 * Kai Hofmann
	 * Luft und Raumfahrtinformatik
	 * 
	 */

public class DiGraph {
	
	final List nodes;							//A list of all nodes in the graph
	int edgeCount = 0;
	
	
	static int finalDistance = 0;				//The sum of distances from U,V,W to the optimal meeting point 
	boolean resetState = true;				
	static boolean intersectionStateUV = false;	//If the neighborhood of U and V intersected
	static boolean intersectionStateUW = false;	//If the neighborhood of U and W intersected
	static boolean intersectionStateVW = false;	//If the neighborhood of V and W intersected
	
	//Constructors
	public DiGraph(){
		nodes = new List();
	}
	
	public DiGraph(Object[] keys, boolean[][] adjacencyMatrix){		
		nodes = new List();														//initialize global nodes List
		DiGraphNode [] nodesArray = new DiGraphNode[keys.length]; 				//just local for handling the adjacencyMatrix (you can access a certain Node by Number)

		for(int k = 0;k<keys.length;k++){										//iterate over all keys and make nodes of them
			DiGraphNode newNode = new DiGraphNode(keys[k]);
			nodesArray[k] = newNode;
			nodes.insert(newNode);
		}
		
		for(int i = 0;i < nodesArray.length;i++){								//Iterate over all nodes		
			for( int j = 0;j < nodesArray.length;j++ ){		
				if(adjacencyMatrix[i][j]){										//Then look in the Matrix if there is an edge to another node	
					addEdge(nodesArray[i],nodesArray[j]);						//If so create that edge
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
			edgeCount++;					
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
	
	/**	Computes the best meeting point for the three Nodes.
	 * 
	 * ----------------------------------general Idea-------------------------------------------------------------------------------------	
	 * This works by creating a neighborhood for each professor. The neighborhoods are represented by visitor states of the nodes.
	 * All three neighborhoods are enlarged step by step. (While doing that the distance to the prof is saved). 
	 * If exactly two of them intersect the remaining one grows 3x faster.
	 * If all three neighborhoods intersect the first time at a node this is a optimal meeting point candidate (and the distance to all 3 profs is saved
	 * in the finalDistance variable). The while loop is finished anyways to check if better meeting points occur in this iteration. 
	 * If so they overwrite the final distance. Finally the distance is returned   
	 * If the neighborhoods never intersect -1 is returned
	 * 
	 * @param u	Node where prof U. lives
	 * @param v Node where prof V. lives 
	 * @param w Node where prof W. lives
	 * @return the minimum distance ( u - meetingpoint ) + ( v - meetingpoint ) + ( w - meetingpoint )  
	 */
	public int minDistance(DiGraphNode u,DiGraphNode v,DiGraphNode w){
		int walkingDistanceU = 0,walkingDistanceV = 0,walkingDistanceW = 0;				//How far each Prof. walked 
		
		List uNeighborList = new List();			//A list of the neighbors in the neighborhood of prof. U that i am currently working on	
		List wNeighborList = new List();			//A list of the neighbors in the neighborhood of prof. W that i am currently working on		
		List vNeighborList = new List();			//A list of the neighbors in the neighborhood of prof. V that i am currently working on	
		
		if(resetState){
			resetGraph();							//Set all nodes to there initial state. (No visitors, all distances 0)
		}		
		uNeighborList.insert(u);					//First of all all profs are neighbors of themselves and have a distance of 0 to them self 
		u.visit(VISITORS.PROF_U, 0);
		vNeighborList.insert(v);
		v.visit(VISITORS.PROF_V, 0);
		wNeighborList.insert(w);
		w.visit(VISITORS.PROF_W, 0);
		
		while(walkingDistanceU + walkingDistanceV + walkingDistanceW < edgeCount){			// I know this loop is kind of ugly, but I hope it is fast.
			
			if(intersectionStateUV&&(!intersectionStateUW)&&(!intersectionStateVW)){		//If just U and V intersected W must walk faster 
				walkingDistanceW++;
				wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);	//W moves two edge forward (in every possible direction)
				walkingDistanceW++;
				wNeighborList=getNeighbors(wNeighborList,VISITORS.PROF_W,walkingDistanceW);
			}
			if(intersectionStateUW&&(!intersectionStateUV)&&(!intersectionStateVW)){		//If just U and W intersected V must walk faster 
				walkingDistanceV++;
				vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);	//V moves two edge forward (in every possible direction)
				walkingDistanceV++;
				vNeighborList=getNeighbors(vNeighborList,VISITORS.PROF_V,walkingDistanceV);
			}
			if(intersectionStateVW&&(!intersectionStateUV)&&(!intersectionStateUW)){		//If just V and W intersected U must walk faster 
				walkingDistanceU++;
				uNeighborList=getNeighbors(uNeighborList,VISITORS.PROF_U,walkingDistanceU);	//U moves two edges forward (in every possible direction)
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
			if(uNeighborList.isEmpty() && vNeighborList.isEmpty() && wNeighborList.isEmpty()) break;	//If we searched everything to the end, but couldn't find an intersection till now.
		}
		return -1;
	}
	
	public void resetGraph() {
		finalDistance = 0;
		intersectionStateUV = intersectionStateUW = intersectionStateVW = false;		
		ListItem temp = nodes.getHead();
		while(temp != null){
			DiGraphNode node = temp.key;
			node.visitorState = VISITORS.NONE;		//Set all nodes their initial state 
			node.sumOfDistances = 0;
			temp = temp.next;
		}
	}
	
	/**	searches all neighbor nodes of the handed nodes, visits (and marks them as visited) them and returns them if they were not visited by this visitor before.
	 * @param nodes	the nodes ( already visited ) whose neighbors we visit
	 * @param visitor 	the identity of the prof who is visiting.	
	 * @param walkingDistance how far this visitor walked ( e.g. how far they are away from this prof. )
	 * @return a List of Neighbors of nodes ( if they were not already visited by Visitor )
	 */
	private List getNeighbors(List nodes, VISITORS visitor, int walkingDistance) {		
		List newNeighborList = new List();														
		
		ListItem node = nodes.getHead();																	
		while(node != null){										 							//Iterate over nodes
			DiGraphNode temp = node.key;											
			
			ListItem neighborNode = temp.adjacencyList.getHead();								//Iterate over the neighbors of each of them				
			while(neighborNode != null){							
				if(neighborNode.key.visit(visitor, walkingDistance)==null);						//If the neighbor was already visited by this visitor (e.g. the visit returns null) then do nothing 
				else{
					newNeighborList.insert(neighborNode.key);									//Otherwise insert in the new neighbors list																								
				}
				neighborNode = neighborNode.next;												//Check the next node
			}			
			node = node.next;																	//Check the next node
		}
		return newNeighborList;
	}
	
}
