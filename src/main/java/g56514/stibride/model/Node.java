package g56514.stibride.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a node of the STIB network's graph.
 * 
 * @author yohan
 */
public class Node {

    private int id;
    private String name;
    private List<Node> shortestPath = new ArrayList<>();
    private Integer distance = Integer.MAX_VALUE;
    private boolean visited = false;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    /**
     * Creates a new instance of <code>Node</code>.
     *
     * @param id the id of the node.
     * @param name the name of the node.
     */
    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter of id.
     * 
     * @return the node id.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of name.
     * 
     * @return the station's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name.
     * 
     * @param name the favori's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of shortest path.
     * 
     * @return the shortest name.
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * Setter of shortest path.
     * 
     * @param shortestPath the shortest name.
     */
    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    /**
     * Getter of distance.
     * 
     * @return the distance.
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Setter of distance.
     * 
     * @param distance the distance.
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    
    /**
     * Getter of visited.
     * 
     * @return true or false if the node is visited or not.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Specify if the node is visited or not.
     * 
     * @param visited the value which determines if the node is visited.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Getter the adjecent nodes.
     * 
     * @return the adjacent nodes.
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Setter the adjecent nodes.
     * 
     * @param adjacentNodes the adjacent nodes.
     */
    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    /**
     * Add a destination into adjacent nodes.
     * 
     * @param destination the node to be added into the adjacent map.
     * @param distance the distance until the destination.
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    /**
     * Returns the string representation of the data.
     * 
     * @return the string representation of the data.
     */
    @Override
    public String toString() {
        return "Node(" + "id=" + id + ", name=" + name + ", distance=" + distance + ", adjacentNodes=" + adjacentNodes + ')';
    }

    
}
