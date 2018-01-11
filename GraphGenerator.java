package project4;

import java.util.Random;

public class GraphGenerator {
	//Generate random test graph
	public static Graph testCaseGen(int n, int density){
		Random rand = new Random();
		Graph testGraph = new Graph();
		int edgecount = 0;
		
		//generate vertices
		for (int i=0; i<n; i++) {
			testGraph.addVertex(String.valueOf(i));
			}
		//generate edges
		while (edgecount < density){
			testGraph.addEdge(String.valueOf(rand.nextInt(n)), String.valueOf(rand.nextInt(n)));
			edgecount++;
		}
		return testGraph;
		}
}
