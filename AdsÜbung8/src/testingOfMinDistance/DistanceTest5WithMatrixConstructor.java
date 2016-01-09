package testingOfMinDistance;

import static org.junit.Assert.*;

import org.junit.Test;

import diGraph.DiGraph;
import diGraph.DiGraphNode;

public class DistanceTest5WithMatrixConstructor {

	@Test
	public void test() {
		Integer zero = 0, one = 1, two = 2, three= 3, four = 4, five = 5, six = 6, seven = 7, eight =8, nine = 9;
		//Just the keys
		
		Integer[] keys = {zero,one,two,three,four,five,six,seven,eight,nine}; //the keys as an array
		int edges[][] = 		{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //copied from PABS, jsut inserted the ,,,,
								{0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, 
								{0, 0, 0, 0, 0, 1, 0, 0, 0, 1}, 
								{1, 0, 1, 0, 0, 0, 1, 0, 0, 0}, 
								{0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
								{0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, 
								{0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, 
								{0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
								{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}; 
		
		boolean[][] adjacencyMatrix = new boolean[10][10];
		for(int i = 0; i < 10;i++){
			for(int j = 0; j < 10; j++){
				if(edges[i][j]==1)adjacencyMatrix[i][j]=true;			//transfrom the int[][] array to boolean[][] array
				else{
					adjacencyMatrix[i][j] = false;
				}		
			}
		}
		DiGraph test = new DiGraph(keys,adjacencyMatrix);	//create new graph from the boolean array and key array
		
		
		int outputA = test.minDistance(test.find(zero), test.find(two), test.find(nine));		//calc mindist from 0 , 2 , 9
		System.out.println("RESULT IS:" + outputA);
		assertEquals(-1,outputA);		//check if correct
		
	}

}
