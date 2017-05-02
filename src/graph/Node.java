package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ayyad Shenouda.
 * 
 * this class represent the nodes or vertices that we use in the graph.
 * using integer for the values and set for all it edges.
 *
 */

public class Node {
	
	private int value; //the value of this node
	private HashSet<Edge> edges; //the set of out neighbors from this node 
	
	// constructor takes the value of the node.
	public Node(int value) {
		this.value = value;
		this.edges = new HashSet<>();
	}
	
	//add neighbor to the node
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	//returns the value of this node.
	public int getValue() {
		return value;
	}
	
	//return set containing all node neighbors of this node.
	Set<Node> getNeighbors()
	{
		Set<Node> neighbors = new HashSet<Node>();
		for (Edge edge : edges) {
			neighbors.add(edge.getEndNode());
		}
		return neighbors;
	}
	
	// return all the set of the edges.
	public Set<Edge> getEdges()
	{
		return edges;
	}
	
	public String toString() {
		return ((Integer)value).toString();
	}

	//compare 2 nodes with each other by the value only because the every node have unique value.	
	public boolean equals(Node o) {
		if(this.value == o.value) {
			return true;
		}
		return false;
	}
}
