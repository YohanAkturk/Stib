package g56514.stibride.model;

import g56514.stibride.gestionDB.config.ConfigManager;
import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.dto.StationDto;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.repository.FavoriRepository;
import g56514.stibride.gestionDB.repository.StationRepository;
import g56514.stibride.gestionDB.repository.StopRepository;
import g56514.stibride.observer.Observable;
import g56514.stibride.observer.Observer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yohan
 */
public class Model extends Observable {

    private Graph graph;
    private List<Node> stations = new ArrayList<>();
    private static StationRepository repo;
    StopRepository stopRepo;
    private static FavoriRepository repoFavori;

    /**
     * Creates a new instance of <code>Model</code>.
     *
     * @throws RepositoryException if there is a repository error.
     * @throws java.sql.SQLException if there is a SQL statement error.
     * @throws java.io.IOException if there is a I/O error.
     */
    public Model() throws RepositoryException, SQLException, IOException {
        ConfigManager.getInstance().load();
        repo = new StationRepository();
        stopRepo = new StopRepository();
        repoFavori = new FavoriRepository();
        this.graph = new Graph();
        initializeGraph();
    }

    /**
     * Returns all station names.
     * 
     * @return a list with station names.
     * @throws RepositoryException if there is a repository error.
     */
    public List<String> getStationNames() throws RepositoryException {
        List<StationDto> stations = repo.getAll();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < stations.size(); i++) {
            names.add(stations.get(i).getName());
        }
        return names;
    }

    /**
     * Returns all favori names.
     * 
     * @return a list with favori station names.
     * @throws RepositoryException if there is a repository error.
     */
    public List<String> getFavoriNames() throws RepositoryException {
        List<FavoriDto> favoris = repoFavori.getAll();
        List<String> nameFavoris = new ArrayList<>();
        for (int i = 0; i < favoris.size(); i++) {
            nameFavoris.add(favoris.get(i).getName());
        }
        return nameFavoris;
    }

    /**
     * Returns the name of a specific station.
     * 
     * @param id the id of the station.
     * @return the name of the station.
     * @throws RepositoryException if there is a repository error.
     */
    public String getNameFromIdStation(Integer id) throws RepositoryException {
        return repo.selectName(id);
    }

    /**
     * Returns a favory item from the database by given name.
     * 
     * @param nameFavori the name of the favori.
     * @return the favori.
     * @throws RepositoryException if there is a repository error.
     */
    public FavoriDto getFavoryByName(String nameFavori) throws RepositoryException {
        return repoFavori.getWithNameOfFavori(nameFavori);
    }
    
    /**
     * Initialize the graph (addDestination with 1 because distance is 
     * considered only 1 between 2 stations).
     * 
     * @throws RepositoryException if there is a repository error.
     */
    private void initializeGraph() throws RepositoryException {
        List<StationDto> dtos = repo.getAll();
        List<Node> stationsRef = new ArrayList<>();
        dtos.forEach(dto -> {
            stationsRef.add(new Node(dto.getKey(), dto.getName()));
        });
        dtos.forEach(dto -> {
            stations.add(new Node(dto.getKey(), dto.getName()));
        });
        StopRepository stopRepo = new StopRepository();
        for (int a = 0; a < stations.size(); a++) {
            Node station = stations.get(a);
            station.setDistance(Integer.MAX_VALUE);
            List<StopDto> stops = repo.selectStationsForEveryLines(station.getId());
            for (int j = 0; j < stops.size(); j++) {
                StationDto afterStation = repo.selectNeighbour((int) stops.get(j).getKey().getFirst(), (int) stops.get(j).getIdOrder(), true);
                StationDto beforeStation = repo.selectNeighbour((int) stops.get(j).getKey().getFirst(), (int) stops.get(j).getIdOrder(), false);
                Node neighbour;
                if (afterStation != null && (neighbour = getNodeById(afterStation.getKey(), stationsRef)) != null) {
                    station.addDestination(neighbour, Integer.MAX_VALUE);
                }
                if (beforeStation != null && (neighbour = getNodeById(beforeStation.getKey(), stationsRef)) != null) {
                    station.addDestination(neighbour, Integer.MAX_VALUE);
                }
                graph.addNode(station);

            }
        }
    }

    /**
     * Resets the nodes of the graph.
     */
    public void resetGraph() {
        graph.getNodes().forEach(node -> {
            node.setShortestPath(new ArrayList<>());
            node.setDistance(Integer.MAX_VALUE);
            node.setVisited(false);
        });
    }

    /**
     * Returns a node by the given id.
     * 
     * @param id the id of the searched node.
     * @param nodes the list of nodes.
     * @return the searched node.
     */
    public Node getNodeById(int id, List<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            if (id == nodes.get(i).getId()) {
                return nodes.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a node by the given name.
     * 
     * @param name the name of the searched node.
     * @return the searched node.
     */
    public Node getNodeByName(String name) {
        for (int i = 0; i < stations.size(); i++) {
            if (name.equals(stations.get(i).getName())) {
                return stations.get(i);
            }
        }
        return null;
    }

    /**
     * Determines the shortest path between a source and a destination nodes. 
     * This algorithm and the its two private methods 
     * (getLowestDistanceNode and calculateMinimumDistance) was inspired by the 
     * following site: https://www.baeldung.com/java-dijkstra
     * 
     * @param source the source node.
     * @param dest the dest node.
     * @throws RepositoryException if there is a repository error.
     */
    public void calculateShortestPathFromSource(Node source, Node dest) throws RepositoryException {
        List<Node> settledNodes = new ArrayList<>();
        List<Node> unsettledNodes = new ArrayList<>();
        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            currentNode.setVisited(true);
            for (Map.Entry<Node, Integer> adjacencyPair
                    : currentNode.getAdjacentNodes().entrySet()) {
                
                Node adjacentNode = getNodeById(adjacencyPair.getKey().getId(), stations);
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode) && !adjacentNode.isVisited()) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
        }
        dest.getShortestPath().add(dest);

        //data to send to presenter to update view.
        List<Pair<String, List<Integer>>> data = new ArrayList<>();
        //get the name and the lines for every statios in the shortestPath of the destination.
        for (int i = 0; i < dest.getShortestPath().size(); i++) {
            for (int j = 0; j < stations.size(); j++) {
                if (stations.get(j).getId() == dest.getShortestPath().get(i).getId()) {
                    List<Integer> lines = repo.selectLinesForStation(stations.get(j).getId());
                    data.add(new Pair(stations.get(j).getName(), lines));
                }
            }
        }
        notifyObservers(data, "POPULATE_VIEW_TABLE");
    }

    /**
     * Returns the node with the lowest distance.
     * 
     * @param unsettledNodes the list of unsettled nodes.
     * @return the node with the lowest distance.
     */
    public Node getLowestDistanceNode(List<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (lowestDistanceNode == null) {
                lowestDistanceNode = node;
            } else if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Determines the minimum distance of a node.
     * 
     * @param evaluationNode the current node.
     * @param edgeWeigh the edge weigh.
     * @param sourceNode the source node.
     */
    public void calculateMinimumDistance(Node evaluationNode,
            Integer edgeWeigh, Node sourceNode) {

        Integer sourceDistance = sourceNode.getDistance();

        if (sourceDistance + edgeWeigh <= evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);

        }
    }

    /**
     * Allows to create a new favori.
     * 
     * @param name the name of the favori.
     * @param origin its origin.
     * @param destination its destination.
     * @throws RepositoryException if there is a repository error.
     */
    public void createFavoris(String name, String origin, String destination) throws RepositoryException {
        int departure = 0, arrival = 0;
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(origin)) {
                departure = stations.get(i).getId();
            }
            if (stations.get(i).getName().equals(destination)) {
                arrival = stations.get(i).getId();
            }
        }
            repoFavori.add(new FavoriDto(0, name, departure, arrival));
        notifyObservers(name, "CREATE");
    }

    /**
     * Allows to delete a favori.
     * 
     * @param favoriName the name of the favori.
     * @throws RepositoryException if there is a repository error.
     */
    public void deleteFavori(String favoriName) throws RepositoryException {
        repoFavori.deleteWithName(favoriName);
        notifyObservers(favoriName, "DELETE");
    }

    /**
     * Returns the departure and the arrival of a favori in a pair.
     * 
     * @param favoriName the favori name.
     * @return a pair with the start and the destination of the given favori 
     * name.
     * @throws RepositoryException if there is a repository error.
     */
    public Pair<String, String> getDepartureWithName(String favoriName) throws RepositoryException {
        FavoriDto dto = repoFavori.getWithNameOfFavori(favoriName);
        String departure = repo.selectName(dto.getDeparture());
        String arrival = repo.selectName(dto.getArrival());
        return new Pair(departure, arrival);
    }

    /**
     * Edit a favori.
     * 
     * @param tab the array with data to be updated.
     * @throws RepositoryException if there is a repository error.
     */
    public void edit(String[] tab) throws RepositoryException {
        FavoriDto dto = new FavoriDto(Integer.parseInt(tab[0]), tab[1], repo.selectId(tab[2]), repo.selectId(tab[3]));
        repoFavori.update(dto);
        notifyObservers(dto, "EDIT");
    }

    public List<Node> getStations() {
        return stations;
    }
    
    @Override
    public void notifyObservers(Object obj, String consigne) {
        super.notifyObservers(obj, consigne);
    }

    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

}
