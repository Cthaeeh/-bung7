package testingOfMinDistance;

import static org.junit.Assert.*;

import org.junit.Test;

import diGraph.DiGraph;
import diGraph.DiGraphNode;

public class DistanceTest4_PABS_Copy {

	@Test
	public void test() {
		Integer zero = 0, one = 1, two = 2, three= 3, four = 4, five = 5, six = 6, seven = 7, eight =8, nine = 9;
		
		DiGraph test = new DiGraph();
		test.addNode(zero);
		test.addNode(one);
		test.addNode(two);
		test.addNode(three);
		test.addNode(four);
		test.addNode(five);
		test.addNode(six);
		test.addNode(seven);
		test.addNode(eight);
		test.addNode(nine);
		
		DiGraphNode nodeZero = test.find(zero);
		DiGraphNode nodeOne = test.find(one);
		DiGraphNode nodeTwo = test.find(two);
		DiGraphNode nodeThree = test.find(three);
		DiGraphNode nodeFour = test.find(four);
		DiGraphNode nodeFive = test.find(five);
		DiGraphNode nodeSix = test.find(six);
		DiGraphNode nodeSeven = test.find(seven);
		DiGraphNode nodeEight = test.find(eight);
		DiGraphNode nodeTen = test.find(nine);		
		
		
		test.addEdge(nodeOne, nodeFive);
		
		test.addEdge(nodeTwo, nodeFive);
		test.addEdge(nodeTwo, nodeTen);
		
		test.addEdge(nodeThree, nodeZero);
		test.addEdge(nodeThree, nodeTwo);
		test.addEdge(nodeThree, nodeSix);
						
		test.addEdge(nodeFour, nodeThree);
		
		test.addEdge(nodeFive, nodeSix);
		
		test.addEdge(nodeSix, nodeFour);
		
		test.addEdge(nodeSix, nodeThree);
		
		test.addEdge(nodeSeven, nodeSix);
		
		test.addEdge(nodeEight, nodeSix);

		
		int outputA = test.minDistance(test.find(nine), test.find(one), test.find(six));

		assertEquals(-1,outputA);
	}

}
