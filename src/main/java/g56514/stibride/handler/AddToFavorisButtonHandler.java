package g56514.stibride.handler;

import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.presenter.Presenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Represents the "Add to favori" button handler.
 *
 * @author yohan
 */
public class AddToFavorisButtonHandler implements EventHandler<ActionEvent> {

    private Presenter presenter;

    /**
     * Creates a new instance of <code>AddToFavorisButtonHandler</code>.
     *
     * @param presenter the presenter.
     */
    public AddToFavorisButtonHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Allows to handle the click on the "Add to favori" button.
     *
     * @param t the action event.
     */
    @Override
    public void handle(ActionEvent t) {
        if (presenter.getOrigin() == null || presenter.getDestination() == null) {
            presenter.showErrorEmptySrcAndDest();
        } else {
            try {
                presenter.insertNewFavoris();
            } catch (RepositoryException e) {
                System.out.println("Exception from AddToFavorisButtonHandler : "
                        + e.getMessage());
                presenter.showErrorExistingFavori();
            }
        }
    }
}
