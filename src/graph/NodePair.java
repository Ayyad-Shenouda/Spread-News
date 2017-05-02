package graph;


/**
 * @author Ayyad Shenouda.
 * this class represent any pair of nodes used in potential friends method in CapGraph class.
 * takes 2 nodes.
 */

public class NodePair {
	private Node firstNode;
	private Node secondNode;
	
	//constructor takes 2 nodes.
	public NodePair(Node node1, Node node2) {
		this.firstNode = node1;
		this.secondNode = node2;
	}
	
	//return the first node.
	public Node getFirstNode() {
		return firstNode;
	}
	
	//return the second node.
	public Node getSecontNode() {
		return secondNode;
	}
	
	public String toString() {
		return "[" + firstNode.toString() + " " + secondNode.toString() + "]";
	}
	
	//hence that if node 1 paired with node 2 is also as node 2 paried with node 1.
	public boolean equals(NodePair o) {
		
		if(this.firstNode.equals(o.firstNode) && this.secondNode.equals(o.secondNode)) {
			return true;
		}
		return false;
	}
}
