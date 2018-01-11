package project4;
import java.awt.geom.*;
import java.util.*;

public class CliqueAlgorithm {

		public static void findKlique (Graph G, int klique) {
			//Preparation (generate new graph only containing nodes with degree >= to k
			Graph Gprime = new Graph();
			Gprime = Preparation(G, klique);
			    boolean done = false;
				while (done == false){
					System.out.println(smallDelta(Gprime) + " vs. " + klique);
					System.out.println("Prepare again");
					Gprime = Preparation(Gprime, klique);
					if(smallDelta(Gprime) >= klique-1) {
					   done=true;
					   break;
					}
					
				}			
			System.out.println("Printing subgraph containing " + Gprime.numVertices() + "vertices");
			Gprime.printGraph();
			
			//initialization
			Initialization(Gprime, klique);
			
			//Movement
			Movement(Gprime);
			
			//begin search
			boolean found = Search(Gprime, klique);
			
			if (found == true){
				System.out.print(Gprime.numVertices());
				System.out.println("Finished");
			}
			if (found == false){
				System.out.println("No clique of size " + klique + " has been found in this Graph.");
			}
		}
		
		
		/*
		 * Finally got this to work.
		 * Returns a subgraph Gprime that with a deltaMin > k-1.
		 */
		public static Graph Preparation(Graph G, int k) {
			int degreeTotal = 0;
			HashMap<Vertex, Integer> potentialClique = new HashMap <Vertex,Integer>();
			Graph Gprime = new Graph();
			//Calculate which nodes will have a high enough degree.
			for (Vertex v : G.getVertices()) {
				if (v.degree >= k-1){
				degreeTotal = v.degree;
				for (Vertex w : G.adjacentTo(v)){
					if(w.degree <= k-1){
						degreeTotal--;
					}
				 }
			     }
				potentialClique.put(v, degreeTotal);
		    }	
			//Pick out the nodes and edges that have the correct degree.
			for (Vertex v : G.getVertices()){
				for(Vertex w : G.adjacentTo(v)){
					//Make damn sure they're not below k-1
					if (potentialClique.get(w) >= k-1 && potentialClique.get(v) >= k-1){
						if(v.degree >= k-1 && w.degree >= k-1){
						Gprime.addEdge(v.name, w.name);
						}
					}
				}
			   
			}
			return Gprime;
		
		}
		public static void Initialization(Graph G, int k) {
			//Initialize
			for (Vertex v : G.getVertices()){
				for (int i=0; i < G.numVertices(); i++){
					v.location.x = i; v.location.y = i;
				}
			}
		}
		public static void Movement (Graph G){
			//Movement 
			//Random? Unsure if order the edges was supposed to be random. Currently is just moving all the vertices based on their relation to each other.
				/*	Vertex[][] edges = new Vertex[G.numEdges()][2];
					for (int i=0; i < G.numEdges(); i++){
					   for (Vertex v : G.getVertices()) {
							   for (Vertex u : G.adjacentTo(v)){
									 edges[i][0] = v;
									 edges[i][1] = u;
								   }
						       }
						  }*/

			Point2D.Double midpoint;
			for (Vertex v : G.getVertices()){
				for (Vertex u : G.adjacentTo(v.name)){	
			    	midpoint = Mid(v, u);
				    v.location = midpoint; u.location = midpoint;
					}
		    }
	   }

		public static boolean Search(Graph G, int k){
			//TODO: check if the vertices in circle form a clique
			boolean cliqueFound = false;
			for (Vertex v : G.getVertices()) {
					//check if points are within a circle with around that vertex
					//If number of points inside the radius is enough to form a k-click, check to see if that group contains a clique
				   if (v.degree >=k-1){
					if (drawCircle(v, G).size() >= k){
						//System.out.println("Checking nodes around " + v.name);
					    if (checkClique(G, drawCircle(v,G), v, k) == true){
							cliqueFound = true;
						}		
				    }
				   }
			}		
			return cliqueFound;
		}
		
		public static ArrayList<Vertex> drawCircle(Vertex v, Graph G){
			ArrayList<Vertex> innerCircle = new ArrayList<Vertex>();
			for (Vertex w : G.getVertices()) {
				if (calcDistance(v,w) < 1){
					innerCircle.add(w);
				}
			}
			return innerCircle;		
		}
		
		
		//Test possibles for a k-clique
		public static boolean checkClique(Graph G, ArrayList<Vertex> potentialClique, Vertex start, int k){
			boolean cliqueFound = false;
			ArrayList<Vertex> candidates = new ArrayList<Vertex>();
			Vertex previous = start;
			Vertex last = null;
			candidates.add(start); //Add root node to potential clique
			//System.out.println("Testing. k = " + k + "\n Vertices to be checked: " + potentialClique.size());

				for (Vertex v : potentialClique){
					for (Vertex w : G.adjacentTo(v)){ //Check the adjacent nodes in each potentialClique vertex
				       if (w == previous && !candidates.contains(v)){  
						   previous = v;
						   candidates.add(v);
					   }
					}
				}
			last = candidates.get(candidates.size() - 1);	   
			      if (candidates.size() >= k){
			    	  for (Vertex u : G.adjacentTo(last)) {
			    	   if (u == start){
			    		   System.out.println(potentialClique.size() + " nodes of degree > k-1.");
			    		   System.out.println("k = " + k + "\n");
			    		   System.out.println("Potential Clique found at Vertex #" + u.name);
			    		   System.out.println("Potential Clique: ");
			    		   
			    		   //print entire clique
			    		   for(Vertex v : candidates){
			    			   System.out.println("Node " + v.name + " Adj: " + G.adjacentTo(v));
			    		   }
			    		   cliqueFound = true;
			    		   return cliqueFound;
			    	   }
			    	   else {
			    		   candidates.remove(last);
			    		   cliqueFound = false;
			    		   return cliqueFound;
			   }
			}
			}
			return cliqueFound; 
		}	
	 	
		
		public static int smallDelta (Graph G) {
			Vertex smallest = new Vertex("test");
			smallest.degree = Integer.MAX_VALUE;
			for (Vertex v : G.getVertices()){
					if (smallest.degree > v.degree){
						smallest = v;
					}
				}
			
			return smallest.degree;
		   }
		
		//Returns midpoint for the movement phase.
		public static Point2D.Double Mid(Vertex v, Vertex u){
			Point2D.Double p1 = v.location;
			Point2D.Double p2 = u.location;
	        double x = (p1.getX() + p2.getX()) / 2;
	        double y = (p1.getY() + p2.getY()) / 2;
	        Point2D.Double midpoint = new Point2D.Double(x, y);
			return midpoint;
		}
		
		//calculates distance between two points for the search phase
		public static double calcDistance (Vertex v, Vertex u){
			Point2D.Double p1 = v.location;
			Point2D.Double p2 = u.location;
			double distance = p1.distance(p2);
			return distance;
		}
	   }





