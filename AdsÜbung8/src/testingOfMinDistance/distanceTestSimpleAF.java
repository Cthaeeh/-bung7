package testingOfMinDistance;

import static org.junit.Assert.*;

import org.junit.Test;

import diGraph.DiGraph;
import diGraph.DiGraphNode;

public class distanceTestSimpleAF {
	
	
	@Test
	public void test() {
		//Create some Objects (doesnt matter what)
		Integer testNodeOne = 1, testNodeTwo = 2, testNodeThree = 3, testNodeFour= 4;
		
		//Create a Graph with 4 Nodes
		
		DiGraph test = new DiGraph();
		
		System.out.println("create Nodes");
		test.addNode(testNodeOne);
		test.addNode(testNodeTwo);
		test.addNode(testNodeThree);
		test.addNode(testNodeFour);
		
		DiGraphNode one = test.find(testNodeOne);
		DiGraphNode two = test.find(testNodeTwo);
		DiGraphNode three = test.find(testNodeThree);
		DiGraphNode four = test.find(testNodeFour);
		
		//Add some edges (four is center of the graph)
		System.out.println("add edge 1");
		test.addEdge(one, four);
		System.out.println("add edge 2");
		test.addEdge(two, four);
		System.out.println("add edge 3");
		test.addEdge(three, four);
		
		//compute distance of 1 to 4 , 2 to 4 and 3 to 4 (should be 3)
		int output = test.minDistance(test.find(testNodeOne), test.find(testNodeTwo), test.find(testNodeThree));
	
		assertEquals(3,output);
	}	
}
