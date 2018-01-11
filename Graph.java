package project4;

/**
 * Undirected, unweighted simple graph data type

 * <p>
 *  Notes:
 *  <ul>
 *   <li> Parallel edges are not allowed
 *   <li> Self loops are allowed
 *  </ul>
 *  <p>
 *  This Graph class was adapted from:
 *  <a href="http://www.cs.princeton.edu/introcs/45graph/Graph.java">Graph.java</a> by 
 *  by Robert Sedgewick and Kevin Wayne
 *  
 *  @BrendanCassidy added the following:
 *  -The update the degree of each node whenever a new edge is added to the graph
 *  - Added a console print method to replace file io method
 *  - Removed some unused functionality to make it a little more simple.
 *  - Added the "removeVertex/Edges" methods but they don't work.
 *  
 */
import java.util.*;

public class Graph {
	private HashMap<Vertex, TreeSet<Vertex>> myAdjList;
	private HashMap<String, Vertex> myVertices;
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
	private int myNumVertices;
	private int myNumEdges;

	/**
	 * Construct empty Graph
	 */
	public Graph() {
		myAdjList = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;

	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 */
	public Vertex addVertex(String name) {
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
			myAdjList.put(v, new TreeSet<Vertex>());
			myNumVertices += 1;
		}
		return v;
	}
	
	public void removeVertex(Vertex delete) {
		myVertices.remove(delete.name);
		myAdjList.remove(delete.name);
		for(Vertex v : adjacentTo(delete)){
			removeEdges(delete,v);
		}
		myNumVertices -= 1;
		
	}

	/**
	 * Returns the Vertex matching v
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return the Vertex with a name that matches v or null
	 * if no such Vertex exists in this Graph
	 */
	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}

	/**
	 * Returns true iff v is in this Graph, false otherwise
	 */
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}

	/**
	 * Is from-to, an edge in this Graph. The graph is 
	 */
	public boolean hasEdge(String from, String to) {
		if (!hasVertex(from) || !hasVertex(to))
			return false;
		return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
	}
	
	/**
	 * Add to to from's set of neighbors, and add from to to's
	 * set of neighbors. Does not add an edge if another edge
	 * already exists
	 */
	public void addEdge(String from, String to) {
		Vertex v, w;
		if (hasEdge(from, to))
			return;
		myNumEdges += 1;
		if ((v = getVertex(from)) == null)
			v = addVertex(from);
		if ((w = getVertex(to)) == null)
			w = addVertex(to);
		myAdjList.get(v).add(w);
		myAdjList.get(w).add(v);
		w.degree++;
		v.degree++;
	}
	
	public void removeEdges(Vertex from, Vertex to){
		if(hasEdge(from.name, to.name)){
			myNumEdges -=1;
			myAdjList.get(from).remove(to);
			myAdjList.get(to).remove(from);
			from.degree--;
			to.degree--;
		}
	}

	/**
	 * Return an iterator over the neighbors of Vertex named v
	 * to the Vertex named v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(String name) {
		if (!hasVertex(name))
			return EMPTY_SET;
		return myAdjList.get(getVertex(name));
	}
	
	public HashMap<Vertex, TreeSet<Vertex>> getAdjList(Vertex v) {
		return myAdjList;
	}
	
	/**
	 * Return an iterator over the neighbors of Vertex v
	 * to the Vertex v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(Vertex v) {
		if (!myAdjList.containsKey(v))
			return EMPTY_SET;
		return myAdjList.get(v);
	}

	/**
	 * Returns an Iterator over all Vertices in this Graph
	 */
	public Iterable<Vertex> getVertices() {
		return myVertices.values();
	}

	public int numVertices()
	{
		return myNumVertices;
	}
	
	public int numEdges()
	{
		return myNumEdges;
	}
	

	/*
	 * Returns adjacency-list representation of graph
	 */
	public String toString() {
		String s = "";
		for (Vertex v : myVertices.values()) {
			s += v + ": ";
			for (Vertex w : myAdjList.get(v)) {
				s += w + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	public void printGraph() {
		for (Vertex v : getVertices()) {
			System.out.print("Node " + v + ": ");
		for (Vertex w : adjacentTo(v.name)) {
			System.out.print(w + " ");
		}
		System.out.print(", Degree: " + v.degree);
		System.out.println();
		//System.out.println(myAdjList);
  	}
	}
	

	}

	