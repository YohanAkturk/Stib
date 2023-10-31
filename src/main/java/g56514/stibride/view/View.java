package g56514.stibride.view;

import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.handler.AddToFavorisButtonHandler;
import g56514.stibride.handler.DeleteButtonHandler;
import g56514.stibride.handler.EditButtonHandler;
import g56514.stibride.handler.SearchButtonHandler;
import g56514.stibride.handler.SearchWithFavoriButtonHandler;
import g56514.stibride.model.DataToDisplay;
import g56514.stibride.presenter.Presenter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.control.SearchableComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the FX view of the application.
 *
 * @author yohan
 */
public class View {

    private Button btn;

    @FXML
    private ScrollPane plan;

    @FXML
    private Button search;
    @FXML
    private Button addToFavoris;
    @FXML
    private Button favoriSearch;
    @FXML
    private Button edit;
    @FXML
    private Button delete;

    @FXML
    private ScrollPane trajet;

    @FXML
    private SearchableComboBox<String> origin;
    @FXML
    private SearchableComboBox<String> destination;
    @FXML
    private SearchableComboBox<String> favoris;

    @FXML
    private TableView<DataToDisplay> table;
    @FXML
    private TableColumn<DataToDisplay, String> stations;
    @FXML
    private TableColumn<DataToDisplay, List<Integer>> lines;

    @FXML
    private Label state;
    @FXML
    private Label result;
    @FXML
    private Label logo;
    @FXML
    private ProgressBar progressBar;

    private SearchableComboBox originEditPage = new SearchableComboBox();
    private SearchableComboBox destinationEditPage = new SearchableComboBox();

    /**
     * Creates a new instance of <code>View</code>.
     *
     * @param stage the stage.
     * @throws java.io.IOException if there is a I/O error.
     */
    public View(Stage stage) throws IOException {
        FXMLLoader loader
                = new FXMLLoader(getClass().getResource("/fxml/sceneBuilder.fxml"));
        loader.setController(this);
        VBox root = loader.load();
        progressBar.setVisible(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes all station names for source and destination boxes.
     */
    public void initializeSrcAndDest(ObservableList<String> stationNames) {
        origin.setItems(stationNames);
        destination.setItems(stationNames);
    }

    /**
     * Initializes all station names for source and destination boxes in the
     * edit page.
     *
     * @param stationNames all the stations.
     */
    public void initializeBoxesOfEditPage(ObservableList<String> stationNames) {
        originEditPage.setItems(stationNames);
        destinationEditPage.setItems(stationNames);
    }

    /**
     * Initializes all favoris names for favori box.
     *
     * @param stationNames the favori station names.
     */
    public void initializeFavori(ObservableList<String> stationNames) {
        favoris.setItems(stationNames);
    }

    /**
     * Initializes all handlers waiting for the user to act on the buttons in
     * the view.
     *
     * @param presenter the presenter.
     */
    public void initHandlerButtons(Presenter presenter) {
        System.out.println("enter add event ");
        SearchButtonHandler searchHandler = new SearchButtonHandler(presenter);
        search.setOnAction(searchHandler);
        AddToFavorisButtonHandler addToFavorisHandler = new AddToFavorisButtonHandler(presenter);
        addToFavoris.setOnAction(addToFavorisHandler);
        //We can't simply read de value of the selected item from SearchableComboBox.
        //Indeed, the clicked involves the triggering of more than one event.
        //So, we need a button to do the action of searching.
        SearchWithFavoriButtonHandler favoriSearchHandler = new SearchWithFavoriButtonHandler(presenter);
        favoriSearch.setOnAction(favoriSearchHandler);
        EditButtonHandler editHandler = new EditButtonHandler(presenter);
        edit.setOnAction(editHandler);
        DeleteButtonHandler deleteHandler = new DeleteButtonHandler(presenter);
        delete.setOnAction(deleteHandler);
    }

    /**
     * Allows to add a new favori for the user.
     *
     * @return the favori's name.
     */
    public String showAlertToAddToFavoris() {
        TextInputDialog td = new TextInputDialog("enter you favori name");
        td.setHeaderText("your favori");
        Optional<String> result = td.showAndWait();
        if (result.isPresent()) {
            return td.getEditor().getText();
        } else {
            return null;
        }
    }

    /**
     * Allows to edit the favori selected by the user.
     *
     * @param id the id of the favori.
     * @return the data entered by the user.
     * @throws RepositoryException if there is a repository error.
     */
    public String[] editPage(int id) throws RepositoryException {
        Dialog dialog = new Dialog();
        dialog.setTitle("edit page");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField(getFavoris());
        dialogPane.setContent(new VBox(8, textField, originEditPage, destinationEditPage));

        //show the dialog and user can edit
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) { //sa fs tjr OK PRESSED... => je check si c'est OK ou bien si c'est Annuler qui a été sélectionné ;)
            System.out.println("PRESSED TO CLOSE WINDOW");
            if (result.get().getText().equals("OK")) {
                System.out.println(result.get().getText());
                //user has validated its choices
                //now, I get the user's entries to update the DB

                String[] tab = new String[4];
                tab[0] = String.valueOf(id);
                tab[1] = textField.getText();
                tab[2] = (String) originEditPage.getValue();
                tab[3] = (String) destinationEditPage.getValue();
                return tab;
            }
        }
        System.out.println(result.get().getText());
        String tab[] = {"cancelChoiceDoNothing"};
        return tab;
    }

    /**
     * Refreshes the favoris.
     *
     * @param favoriName the favori name that will replace the old favori name.
     */
    public void editAfterRegisterInDB(String favoriName) {
        for (int i = 0; i < favoris.getItems().size(); i++) {
            if (favoris.getItems().get(i).equals(getFavoris())) {
                favoris.getItems().set(i, favoriName);
            }
        }
    }

    /**
     * Getter of origin.
     *
     * @return selected source.
     */
    public String getOrigin() {
        return origin.getValue();
    }

    /**
     * Getter of destination.
     *
     * @return selected destination.
     */
    public String getDestination() {
        return destination.getValue();
    }

    /**
     * Getter of favoris.
     *
     * @return selected favori.
     */
    public String getFavoris() {
        return favoris.getValue();
    }

    /**
     * Getter of origin in the edit page.
     *
     * @return selected source in the edit page.
     */
    public SearchableComboBox getOriginEditPage() {
        return originEditPage;
    }

    /**
     * Getter of destination in the edit page.
     *
     * @return selected destination in the edit page.
     */
    public SearchableComboBox getDestinationEditPage() {
        return destinationEditPage;
    }

    /**
     * Removes a given favori.
     *
     * @param favoriName the favori name to delete the favori.
     */
    public void removeFavoriFromList(String favoriName) {
        favoris.getItems().remove(favoriName);
    }

    /**
     * Allows to fill the table view.
     *
     * @param data the data to be displayed that are the station name and its
     * lines.
     */
    public void fillTable(List<Pair<String, List<Integer>>> data) {
        table.getItems().clear();
        stations.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        lines.setCellValueFactory(new PropertyValueFactory<>("lines"));

        ObservableList<DataToDisplay> list = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            DataToDisplay dataTmp = new DataToDisplay(data.get(i).getFirst(), data.get(i).getSecond());
            list.add(dataTmp);
        }
        list.forEach(e -> {
            System.out.println(e.getStationName());
        });
        table.setItems(list);

    }

    /**
     * Allows to fill the favoris box.
     *
     * @param favoriName the favori names.
     */
    public void fillFavoriBox(ObservableList<String> favoriName) {
        favoris.getItems().addAll(FXCollections.observableArrayList(favoriName));
    }

    /**
     * Displays error when there are not source or destination selected.
     */
    public void showErrorEmptySrcAndDest() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Empty fields for origin and destination");
        alert.setContentText("you must enter a source and a destination!");
        alert.showAndWait();
    }
    
    /**
     * Displays error when there is error with thread.
     */
    public void showErrorWithThread() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error happend with thread");
        alert.showAndWait();
    }

    /**
     * Displays error when a favori already exists.
     */
    public void showErrorExistingFavori() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("this favourite already exists");
        alert.setContentText("you must enter a non-existent favourite name!");
        alert.showAndWait();
    }

    /**
     * Displays error when there are not favori selected.
     */
    public void showErrorSearchWithEmptyFavori() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Empty field for favori");
        alert.setContentText("you must enter a favori!");
        alert.showAndWait();
    }

    /**
     * Displays a dialog that allows user to confirm deletion.
     *
     * @return true if the user confirms the deletion, false else.
     */
    public boolean showConfirmDelete() {
        boolean isDeleted = false;
        Dialog dialog = new Dialog();
        dialog.setTitle("delete page");
        dialog.setHeaderText("Would you delete " + getFavoris() + " ?");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        dialogPane.setContent(new VBox(8));

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) { //je check si c'est OK ou bien si c'est Annuler qui a été sélectionné
            if (result.get().getText().equals("Oui")) {
                //user has validated his choices
                //now, I get the user's entries to update the DB
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
    
}
