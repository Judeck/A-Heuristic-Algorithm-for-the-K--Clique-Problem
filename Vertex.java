package project4;
import java.awt.geom.Point2D;


public class Vertex implements Comparable<Vertex> {
	/**
	 * label for Vertex
	 */
	public String name;  
	/**
	 * length of shortest path from source
	 */
	public int distance; 
	
	public Point2D.Double location = new Point2D.Double(0, 0);
	
	/**
	 * previous vertex on path from source
	 */
	public Vertex predecessor; // previous vertex
	
	/**
	 * Number of edges
	 */
	public int degree;
	

	public Vertex(String v)
	{
		name = v;
		predecessor = null;
		degree = 0;
		location.x = 0; location.y = 0;
	}

	/**
	 * The name of the Vertex is assumed to be unique, so it
	 * is used as a HashCode
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public String toString()
	{ 
		return name;
	}
	/**
	 * Compare on the basis of distance from source first and 
	 * then lexicographically
	 */
	public int compareTo(Vertex other)
	{
		int diff = distance - other.distance;
		if (diff != 0)
			return diff;
		else
			return name.compareTo(other.name);
	} 
}