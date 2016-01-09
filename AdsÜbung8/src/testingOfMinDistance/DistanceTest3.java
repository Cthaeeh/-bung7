package testingOfMinDistance;

import static org.junit.Assert.*;

import org.junit.Test;

import diGraph.DiGraph;
import diGraph.DiGraphNode;

public class DistanceTest3 {

	@Test
	public void test() {
		Integer one = 1, two = 2, three = 3, four= 4, five = 5, six = 6, seven = 7, eight = 8, nine =9, ten = 10, eleven = 11;
		
		DiGraph test = new DiGraph();
		test.addNode(one);
		test.addNode(two);
		test.addNode(three);
		test.addNode(four);
		test.addNode(five);
		test.addNode(six);
		test.addNode(seven);
		test.addNode(eight);
		test.addNode(nine);
		test.addNode(ten);
		test.addNode(eleven);
		
		DiGraphNode nodeOne = test.find(one);
		DiGraphNode nodeTwo = test.find(two);
		DiGraphNode nodeThree = test.find(three);
		DiGraphNode nodeFour = test.find(four);
		DiGraphNode nodeFive = test.find(five);
		DiGraphNode nodeSix = test.find(six);
		DiGraphNode nodeSeven = test.find(seven);
		DiGraphNode nodeEight = test.find(eight);
		DiGraphNode nodeNine = test.find(nine);
		DiGraphNode nodeTen = test.find(ten);		
		
		test.addEdge(nodeOne, nodeTwo);
		test.addEdge(nodeOne, nodeFour);
		
		test.addEdge(nodeTwo, nodeOne);
		
		test.addEdge(nodeThree, nodeTwo);
		test.addEdge(nodeThree, nodeFive);
		test.addEdge(nodeThree, nodeFour);
		
		test.addEdge(nodeFour, nodeThree);
		test.addEdge(nodeFour, nodeSix);
		test.addEdge(nodeFour, nodeOne);
		test.addEdge(nodeFour, nodeEight);
				
		test.addEdge(nodeFive, nodeThree);
		test.addEdge(nodeFive, nodeSix);
		
		test.addEdge(nodeSix, nodeFour);
		test.addEdge(nodeSix, nodeFive);
		test.addEdge(nodeSix, nodeSeven);
		
		test.addEdge(nodeSeven, nodeFive);
		test.addEdge(nodeSeven, nodeSix);
		test.addEdge(nodeSeven, nodeEight);
		
		test.addEdge(nodeEight, nodeSix);
		test.addEdge(nodeEight, nodeSeven);
		test.addEdge(nodeEight, nodeFour);
		test.addEdge(nodeEight, nodeNine);
		
		test.addEdge(nodeNine, nodeEight);
		test.addEdge(nodeNine, nodeTen);
		
		test.addEdge(nodeTen, nodeFour);
		
		int outputA = test.minDistance(test.find(ten), test.find(two), test.find(seven));
		int outputB = test.minDistance(test.find(two), test.find(ten), test.find(seven));
		int outputC = test.minDistance(test.find(seven), test.find(two), test.find(ten));

		assertEquals(5,outputA-outputB+outputC);
	}

}
