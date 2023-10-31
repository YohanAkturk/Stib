package g56514.stibride.model;

import java.util.*;

/**
 * Represents the graph of the STIB network.
 * 
 * @author yohan
 */
public class Graph {

    private List<Node> nodes = new ArrayList<>();

    /**
     * Add a node into the graph.
     * 
     * @param node the node to be added.
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Getter of nodes.
     * 
     * @return the nodes.
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Setter of nodes.
     * 
     * @param nodes the nodes.
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
