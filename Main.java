package project4;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			int n = 1000;
			int k = 20;
			int density = 6000;
			Graph G = new Graph();
			G = GraphGenerator.testCaseGen(n, density);
			//G.printGraph();
			
			/*
			G.addVertex("1");
			G.addVertex("2");
			G.addVertex("3");
			G.addVertex("4");
			G.addVertex("5");
			G.addVertex("6");
			G.addVertex("7");
			
			G.addEdge("1", "2");
			G.addEdge("2", "3");
			G.addEdge("3", "1");
			G.addEdge("5", "4");
			G.addEdge("4", "1");
			G.addEdge("1", "7");
			G.addEdge("7", "6");
			G.addEdge("6", "1");
			G.printGraph();
			
			G.removeVertex(G.getVertex("1"));
			*/
			//G.printGraph();
			long startTime = System.currentTimeMillis();
			CliqueAlgorithm.findKlique(G, k);
			long endTime = System.currentTimeMillis();
			long deltaTime = endTime - startTime;
			System.out.println("Total Runtime: " + deltaTime);
			
			
		
	}

}
