package g56514.stibride.handler;

import g56514.stibride.gestionDB.dto.Pair;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.model.Node;
import g56514.stibride.presenter.Presenter;
import g56514.stibride.thread.SearchService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Represents the "Search with favori" button handler.
 *
 * @author yohan
 */
public class SearchWithFavoriButtonHandler
        implements EventHandler<ActionEvent> {

    private Presenter presenter;

    /**
     * Creates a new instance of <code>SearchWithFavoriButtonHandler</code>.
     *
     * @param presenter the presenter.
     */
    public SearchWithFavoriButtonHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Allows to handle the click on the "Search" button.
     *
     * @param t the action event.
     */
    @Override
    public void handle(ActionEvent t) {
        try {
            if (presenter.getFavoris() == null) {
                presenter.showErrorSearchWithEmptyFavori();
            } else {
                //Without thread
                //presenter.launchRouteSearchWithFavori();
                presenter.getProgressBar().setVisible(true);
                searchService();
            }
        } catch (RepositoryException ex) {
            System.out.println("Exception from SearchWithFavoriButtonHandler : "
                    + ex.getMessage());
            Logger.getLogger(SearchButtonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchService() throws RepositoryException {
        Pair<Node, Node> pair = presenter.getSrcAndDestFromFavoriForThread();
        SearchService service = new SearchService(presenter.getModel(), pair.getFirst(), pair.getSecond());
        service.setOnFailed((t) -> {
            presenter.showErrorWithThread();
        });
        presenter.getProgressBar().progressProperty().bind(service.progressProperty());
        service.start();
    }

}
