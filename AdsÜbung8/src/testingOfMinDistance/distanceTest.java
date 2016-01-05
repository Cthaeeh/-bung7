package testingOfMinDistance;

import static org.junit.Assert.*;

import org.junit.Test;

import diGraph.DiGraph;

public class distanceTest {

	@Test
	public void test() {
		Integer one = 1, two = 2, three = 3, four= 4, five = 5, six = 6;
		
		DiGraph test = new DiGraph();
		test.addNode(one);
		test.addNode(two);
		test.addNode(three);
		test.addNode(four);
		test.addNode(five);
		test.addNode(six);
		
		test.addEdge(test.find(one), test.find(two));
		
		test.addEdge(test.find(two), test.find(three));
		
		test.addEdge(test.find(three), test.find(six));
		
		test.addEdge(test.find(four), test.find(three));		
		test.addEdge(test.find(four), test.find(two));
		
		test.addEdge(test.find(five), test.find(four));
		
		test.addEdge(test.find(six), test.find(five));
		
		int output = test.minDistance(test.find(one), test.find(six), test.find(five));

		assertEquals(5,output);
	}
	
	

}