package util;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import graph.CapGraph;
import graph.Node;
import graph.NodePair;

/**
 * @author Ayyad Shenouda
 * 
 * Utility class to add NodePairs to list of NodePair.
 *
 */

public class NodePairsLoader {
	   /**
     * Loads node pairs with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to 2 vertices.
     */ 
    public static void loadList(CapGraph g, List<NodePair> l, String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            Node node1 = g.getNode(n1);
            Node node2 = g.getNode(n2);
            NodePair newPair = new NodePair(node1, node2);
            l.add(newPair);
        }
        sc.close();
    }
}
