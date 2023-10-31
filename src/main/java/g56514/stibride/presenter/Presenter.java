package g56514.stibride.presenter;

import g56514.stibride.gestionDB.dto.FavoriDto;
import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.model.Model;
import g56514.stibride.model.Node;
import g56514.stibride.observer.Observer;
import g56514.stibride.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;

/**
 * Represents a presenter of the application.
 *
 * @author yohan
 */
public class Presenter implements Observer {

    private Model model;
    private View view;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Creates a new instance of <code>Presenter</code>.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void initialize() throws RepositoryException {
        List<String> stationsName = model.getStationNames();
        ObservableList<String> options = FXCollections.observableArrayList(stationsName);
        view.initializeSrcAndDest(options);

        List<String> favoriNames = model.getFavoriNames();
        ObservableList<String> favoris = FXCollections.observableArrayList(favoriNames);
        view.initializeFavori(favoris);
    }

    /**
     * Lunch the search of the shortest path.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void launchRouteSearch() throws RepositoryException {
        Node origin = model.getNodeByName(view.getOrigin());
        Node destination = model.getNodeByName(view.getDestination());
        System.out.println("origine = " + origin);
        System.out.println("destination = " + destination);
        model.resetGraph();
        model.calculateShortestPathFromSource(origin, destination);
    }

    public Pair<Node, Node> getSrcAndDestForThread() {
        model.resetGraph();
        return new Pair(model.getNodeByName(view.getOrigin()), model.getNodeByName(view.getDestination()));
    }

    public Pair<Node, Node> getSrcAndDestFromFavoriForThread() throws RepositoryException {
        String favori = view.getFavoris();
        String nameSrc = model.getNameFromIdStation(model.getFavoryByName(favori).getDeparture());
        String nameDest = model.getNameFromIdStation(model.getFavoryByName(favori).getArrival());
        model.resetGraph();
        return new Pair(model.getNodeByName(nameSrc), model.getNodeByName(nameDest));
    }

    /**
     * Lunch the search of the shortest path with the favori.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void launchRouteSearchWithFavori() throws RepositoryException {
        String favori = view.getFavoris();
        String nameSrc = model.getNameFromIdStation(model.getFavoryByName(favori).getDeparture());
        String nameDest = model.getNameFromIdStation(model.getFavoryByName(favori).getArrival());
        System.out.println("origine = " + nameSrc);
        System.out.println("destination = " + nameDest);

        Node origin = model.getNodeByName(nameSrc);
        Node destination = model.getNodeByName(nameDest);
        model.resetGraph();
        model.calculateShortestPathFromSource(origin, destination);
    }

    /**
     * Allows to insert a new favori.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void insertNewFavoris() throws RepositoryException {
        String origin = view.getOrigin();
        String destination = view.getDestination();
        System.out.println("origine = " + origin);
        System.out.println("destination = " + destination);
        String nameFavori = view.showAlertToAddToFavoris();
        if (nameFavori != null) {
            model.createFavoris(nameFavori, origin, destination);
        } else {
            System.out.println("le dialog pour ajouter aux favoris a été annuler"
                    + " j'ai donc renvoyé null dans la méthode et j'ai rien ajouté"
                    + " dans la DB");
        }
    }

    /**
     * Allows to delete a favori.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void deleteFavori() throws RepositoryException {
        model.deleteFavori(view.getFavoris());
    }

    /**
     * Set data of a selected favori in the edit dialog.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public int setActualDataOfUserFavori() throws RepositoryException {
        Pair departureArrival = model.getDepartureWithName(view.getFavoris());
        view.getOriginEditPage().getSelectionModel().select(departureArrival.getFirst());
        view.getDestinationEditPage().getSelectionModel().select(departureArrival.getSecond());
        return model.getFavoryByName(view.getFavoris()).getKey();
    }

    /**
     * Display a dialog for user in order to update a selected favori.
     *
     * @throws RepositoryException if there is a repository error.
     */
    public void updateDBCallingEditPage() throws RepositoryException {
        List<String> stationsName = model.getStationNames();
        ObservableList<String> options = FXCollections.observableArrayList(stationsName);
        view.initializeBoxesOfEditPage(options);
        int id = setActualDataOfUserFavori();
        String[] tab = view.editPage(id);
        System.out.println("data reçus");
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i]);
        }
        if (!tab[0].equals("cancelChoiceDoNothing")) {
            model.edit(tab);
        }
    }

    /**
     * Update the view.
     *
     * @param obj the give data.
     */
    @Override
    public void update(Object obj, String consigne) {
        if (consigne.equals("POPULATE_VIEW_TABLE")) {
            List<Pair<String, List<Integer>>> data = (List<Pair<String, List<Integer>>>) obj;
            view.fillTable(data);
            view.fillTable(data);
        } else if (consigne.equals("DELETE")) {
            view.removeFavoriFromList((String) obj);
        } else if (consigne.equals("EDIT")) {
            FavoriDto favoriDto = (FavoriDto) obj;
            String favori = favoriDto.toString();
            String[] tabForFavoriName = favori.split(",");
            view.editAfterRegisterInDB(tabForFavoriName[1]);
        } else if (consigne.equals("CREATE")) {
            List<String> list = new ArrayList<>();
            list.add((String) obj);
            ObservableList<String> favoriNameObs = FXCollections.observableArrayList(list);
            view.fillFavoriBox(favoriNameObs);
        }
    }

    /**
     * Getter of origin.
     *
     * @return the origin.
     */
    public String getOrigin() {
        return view.getOrigin();
    }

    /**
     * Getter of destination.
     *
     * @return the destination.
     */
    public String getDestination() {
        return view.getDestination();
    }

    /**
     * Getter of favoris.
     *
     * @return the favoris.
     */
    public String getFavoris() {
        return view.getFavoris();
    }

    /**
     * Displays error when there are not source or destination selected.
     */
    public void showErrorEmptySrcAndDest() {
        view.showErrorEmptySrcAndDest();
    }

    /**
     * Displays error when there is error with thread.
     */
    public void showErrorWithThread() {
        view.showErrorWithThread();
    }

    /**
     * Displays error when a favori already exists.
     */
    public void showErrorExistingFavori() {
        view.showErrorExistingFavori();
    }

    /**
     * Displays error when there are not favori selected.
     */
    public void showErrorSearchWithEmptyFavori() {
        view.showErrorSearchWithEmptyFavori();
    }

    /**
     * Displays a dialog that allows user to confirm deletion.
     *
     * @return true if the user confirms the deletion, false else.
     */
    public boolean showConfirmDelete() {
        return view.showConfirmDelete();
    }

    public Model getModel() {
        return model;
    }

    public ProgressBar getProgressBar() {
        return view.getProgressBar();
    }
}
