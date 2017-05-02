package graph;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.GraphLoader;
import util.NodePairsLoader;

/**
 * @author Ayyad Shenouda.
 *
 */

public class SocialNetworksTester {
	CapGraph graph;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		graph = new CapGraph();
		GraphLoader.loadGraph(graph, "data/small_test_graph.txt");
	}
	
	@Test
	public void testPotentialFriends() {
		List<NodePair> answer = new LinkedList<>();
		List<NodePair> pairs = graph.potentialFriends(0);
		NodePairsLoader.loadList(graph, answer, "data/potentialFriendsTest/small_test_graphAnswer.txt");
		assertTrue(pairs.retainAll(answer));
		assertTrue(answer.retainAll(pairs));
	}
	
	@Test
	public void testwhoHasMoreFriends() {
		assertEquals(1, graph.whoHasMoreFriends(0, 1));
		assertEquals(2, graph.whoHasMoreFriends(3, 0));
		assertEquals(0, graph.whoHasMoreFriends(3, 4));
		graph.addEdge(1, 2);
		graph.addEdge(2, 1);
		graph.addEdge(3, 4);
		graph.addEdge(4, 3);
		assertEquals(0, graph.whoHasMoreFriends(2, 3));
		assertEquals(1, graph.whoHasMoreFriends(3, 5));
		assertEquals(2, graph.whoHasMoreFriends(6, 4));
	}
	
	@Test
	public void testwhoAreTheMostFamousUsers() {
		LinkedList<Integer> answer = new LinkedList<>();
		List<Integer> famousList = graph.whoAreTheMostFamousUsers();
		answer.add(0);
		assertEquals(answer, famousList);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(1, 4);
		graph.addEdge(1, 5);
		graph.addEdge(1, 6);
		graph.addEdge(6, 1);
		graph.addEdge(5, 1);
		graph.addEdge(4, 1);
		graph.addEdge(3, 1);
		graph.addEdge(2, 1);
		famousList = graph.whoAreTheMostFamousUsers();
		answer.add(1);
		assertEquals(answer, famousList);
		CapGraph g = new CapGraph();
		g.addVertex(0);
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		famousList = g.whoAreTheMostFamousUsers();
		answer.add(2);
		answer.add(3);
		answer.add(4);
		assertEquals(answer, famousList);	
		g.addEdge(2, 4);
		g.addEdge(4, 2);
		famousList = g.whoAreTheMostFamousUsers();
		answer.clear();
		answer.add(2);
		answer.add(4);
		assertEquals(answer, famousList);
		
	}
	
	@Test
	public void testBroadcast() {
		List<Node> answer = new LinkedList<>();
		List<Node> broadcasting = graph.broadcast(); 
		answer.add(graph.getNode(0));
		assertEquals(answer, broadcasting);
	}
}
