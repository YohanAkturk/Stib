package g56514.stibride.thread;

import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.repository.StationRepository;
import g56514.stibride.model.Model;
import g56514.stibride.model.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Thread that allows to search the shortest path for a source and a
 * destination.
 *
 * @author yohan
 */
public class SearchService extends Service<Void> {

    private Model model;
    private Node source;
    private Node dest;
    private StationRepository repo;

    /**
     * Creates a new instance of <code>SearchService</code>.
     *
     * @param model the model.
     * @param source the source.
     * @param dest the destination.
     */
    public SearchService(Model model, Node source, Node dest) throws RepositoryException {
        repo = new StationRepository();
        this.model = model;
        this.source = source;
        this.dest = dest;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<Node> settledNodes = new ArrayList<>();
                List<Node> unsettledNodes = new ArrayList<>();
                unsettledNodes.add(source);
                int cpt = 0;
                updateProgress(0, 100);
                while (!unsettledNodes.isEmpty()) {
                    Node currentNode = model.getLowestDistanceNode(unsettledNodes);
                    unsettledNodes.remove(currentNode);
                    currentNode.setVisited(true);
                    for (Map.Entry<Node, Integer> adjacencyPair
                            : currentNode.getAdjacentNodes().entrySet()) {
                        cpt++;
                        Node adjacentNode = model.getNodeById(adjacencyPair.getKey().getId(), model.getStations());
                        Integer edgeWeight = adjacencyPair.getValue();
                        if (!settledNodes.contains(adjacentNode) && !adjacentNode.isVisited()) {
                            model.calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                            unsettledNodes.add(adjacentNode);

                        }
                        updateProgress(cpt, currentNode.getAdjacentNodes().keySet().size());
                    }
                }
                dest.getShortestPath().add(dest);

                //data to send to presenter to update view.
                List<Pair<String, List<Integer>>> data = new ArrayList<>();
                //get the name and the lines for every statios in the shortestPath of the destination.
                for (int i = 0; i < dest.getShortestPath().size(); i++) {
                    for (int j = 0; j < model.getStations().size(); j++) {
                        if (model.getStations().get(j).getId() == dest.getShortestPath().get(i).getId()) {
                            List<Integer> lines = repo.selectLinesForStation(model.getStations().get(j).getId());
                            data.add(new Pair(model.getStations().get(j).getName(), lines));
                        }
                    }
                }
                model.notifyObservers(data, "POPULATE_VIEW_TABLE");
                return null;
            }
        };
    }
}
