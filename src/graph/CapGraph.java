/**
 * 
 */
package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import util.GraphLoader;

/**
 * @author Ayyad Shenouda.
 * 
 * this class represent the main data structure that i wil use for built the project.
 * it represent graph using  adjacency list.
 *
 */
public class CapGraph implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */

	private HashMap<Integer, Node> vertices;   
	private HashSet<Edge> edges;
	
	public CapGraph() {
		vertices = new HashMap<>();
		edges = new HashSet<>();
	}
	
	/**
	 * add new vertix or new node to the graph.
	 * it make a node with value num and put it in vertices map.
	 **/
	@Override
	public void addVertex(int num) {
		Node node = new Node(num);
		vertices.put(num, node);
	}

	/* 
	 * add edge between 2 nodes the parameters is the value of the 2 nodes (every node have unique value).
	 * get the nodes corresponding to each value.
	 * make a new edge with this 2 nodes and added to the edges set and added for the from nodes edges set also.
	 */
	@Override
	public void addEdge(int from, int to) {
		Node n1 = vertices.get(from);
		Node n2 = vertices.get(to);

		// check nodes are valid
		if (n1 == null)
			throw new NullPointerException("addEdge: pt1:"+from+"is not in graph");
		if (n2 == null)
			throw new NullPointerException("addEdge: pt2:"+to+"is not in graph");
		Edge edge = new Edge(n1, n2);
		edges.add(edge);
		n1.addEdge(edge);
	}
	
	
	// return the number of the vertices in the graph.
	public int numberOfVertices() {
		return vertices.size();
	}
	
	// return the number of the vertices in the graph.
	public int numberOfEdges() {
		return edges.size();
	}

	/**
	 * returns the set of all neighbors nodes to specific node. 
	 **/
	private Set<Node> getNeighbors(Node node) {
		return node.getNeighbors();
	}
	
	/**
	 * return Node with its value.
	 **/
	public Node getNode(int num) {
		return vertices.get(num);
	}
	
	/* Return the graph's connections in a readable format. 
     * The keys in this HashMap are the vertices in the graph.
     * The values are the nodes that are reachable via a directed
     * edge from the corresponding key. 
	 * The returned representation ignores edge weights and 
	 * multi-edges.
	 **/
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		HashMap<Integer, HashSet<Integer>> values = new HashMap<>();
		Set<Integer> nodes = vertices.keySet();
		for(int nodeInteger : nodes) {
			Node node = vertices.get(nodeInteger);
			Set<Node> neighbors = getNeighbors(node);
			HashSet<Integer> nodesValues = new HashSet<>();
			for(Node neigbhor : neighbors) {
				nodesValues.add(neigbhor.getValue());
			}
			values.put(nodeInteger, nodesValues);
		}
		return values;
	}
	
	/**
	 * 
	 * @param userValue
	 * @return set of NodePair for all potential friends to specific user.
	 */
	public List<NodePair> potentialFriends(int userValue) {
		Node user = vertices.get(userValue);
		List<NodePair> potentialFriends = new LinkedList<>();
		Set<Node> userFriends = user.getNeighbors();
		for(Node friend : userFriends) {
			for(Node anotherFriend : userFriends) {
				if(friend == anotherFriend) {
					continue;
				}
				Set<Node> friendFriends = friend.getNeighbors();
				if(!friendFriends.contains(anotherFriend)) {
					potentialFriends.add(new NodePair(friend, anotherFriend));
				}
			}
		}
		return potentialFriends;
	}
	
	/**
	 * this method 
	 * returns 1 if user 1 has more friends than user2.
	 * returns 2 if user 2 has more friends than user1.
	 * returns 0 if their friends equals.
	 **/
	public int whoHasMoreFriends(int user1, int user2) {
		Node node1 = vertices.get(user1);
		Node node2 = vertices.get(user2);
		int numberOfUser1Friends = node1.getNeighbors().size();
		int numberOfUser2Friends = node2.getNeighbors().size();
		if(numberOfUser1Friends > numberOfUser2Friends) {
			return 1;
		} else if(numberOfUser1Friends < numberOfUser2Friends) {
			return 2;
		}
		return 0;
	}
	
	/**
		returns list of the users have the highest number of friends.
	 **/
	
	public List<Integer> whoAreTheMostFamousUsers() {
		Set<Integer> users = vertices.keySet();
		int largerstFriends = 0;
		List<Integer> famousUsers = new LinkedList<>();
		for(int user: users) {
			Node node = vertices.get(user);
			if(node.getNeighbors().size() == largerstFriends) {
				famousUsers.add(user);
			} else if(node.getNeighbors().size() > largerstFriends) {
				famousUsers.clear();
				famousUsers.add(user);
				largerstFriends = node.getNeighbors().size();
			}
		}
		
		return famousUsers;
	}
	
	
	/**
	 * returns list of the users that if all of them post some news all the network will see that post.
	 * 1 - it takes the user have the highest number of neighbors
	 * 2 - added to the list.
	 * 3 - remove the user and his neighbors from the graph.
	 * 4 - repeat until we remove all users.
	 **/
	public List<Node> broadcast() {
		HashMap<Node, Set<Node>> nodes = constructSetOfNodes();
		List<Node> dominateNodes = new LinkedList<>();
		Set<Node> keys = nodes.keySet();
		Node dominateNode = null;
		while(nodes.size() > 0) {
			int maximumNeighbors = -1;
			for (Node node : keys) {
				int numberOfNodeNeighbors = nodes.get(node).size();
				if(numberOfNodeNeighbors > maximumNeighbors) {
					dominateNode = node;
					maximumNeighbors = numberOfNodeNeighbors;
				}
			}
			dominateNodes.add(dominateNode);
			removeMarkedVertices(nodes, dominateNode);
		}
		return dominateNodes;
	}
	
	/**
	 * copy the graph to map because we don't want to change our graph
	 **/
	private HashMap<Node, Set<Node>> constructSetOfNodes() {
		HashMap<Node, Set<Node>> nodes = new HashMap<>();
		Set<Integer> keys = this.vertices.keySet();
		for (int nodeValue : keys) {
			Node node = this.vertices.get(nodeValue);
			Set<Node> neighbors = new HashSet<>(node.getNeighbors());
			nodes.put(node, neighbors);
		}
		return nodes;
	}
	
	/**
	 * remove a node and its neighbors from map.
	 **/
	private void removeMarkedVertices(HashMap<Node, Set<Node>> nodes, Node node) {
		
		Set<Node> nodesToDelete = node.getNeighbors();
		nodesToDelete.add(node);
		for(Node nodeToDelete : nodesToDelete) {
			removeNodeFromMap(nodes, nodeToDelete);
		}
		
	}
	
	/**
	 * remove one node from a map
	 **/
	private void removeNodeFromMap(HashMap<Node, Set<Node>> nodes, Node node) {
		if(nodes.containsKey(node)) {
			nodes.remove(node);
		}
		Set<Node> Keys = nodes.keySet();
		for (Node key : Keys) {
			Set<Node> neighobrs = nodes.get(key);
			if(neighobrs.contains(node)) {
				neighobrs.remove(node);
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		CapGraph graph = new CapGraph();
		GraphLoader.loadGraph(graph, "data/potentialFriendsTest/small_test_graphAnswer.txt");
			
		List<Node> l = graph.broadcast();
		
		System.out.println(l);
	}
}