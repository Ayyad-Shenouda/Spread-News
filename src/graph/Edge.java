package graph;

/**
 * @author Ayyad Shenouda.
 * this class represent the edge between 2 nodes it use 2 nodes represent the start and end nodes.
 */

public class Edge {
	private Node startNode;
	private Node endNode;

	//constructor takes 2 nodes.
	public Edge(Node startNode, Node endNode) {
		this.startNode = startNode;
		this.endNode = endNode;
	}

	// return start node.
	public Node getStartNode() {
		return startNode;
	}

	//return start node value.
	public int getStartValue() {
		return startNode.getValue();
	}

	//return end node.
	public Node getEndNode() {
		return endNode;
	}

	//return end node value.
	public int getEndValue() {
		return endNode.getValue();
	}


	public String toString() {
		 return startNode.toString() + "-->" + endNode.toString();
	}
}
