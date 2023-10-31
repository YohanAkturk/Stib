package g56514.stibride.handler;

import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.presenter.Presenter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Represents the "Delete" button handler.
 *
 * @author yohan
 */
public class DeleteButtonHandler implements EventHandler<ActionEvent> {

    private Presenter presenter;

    /**
     * Creates a new instance of <code>DeleteButtonHandler</code>.
     *
     * @param presenter the presenter.
     */
    public DeleteButtonHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Allows to handle the click on the "Delete" button.
     *
     * @param t the action event.
     */
    @Override
    public void handle(ActionEvent t) {
        try {
            if (presenter.getFavoris() == null) {
                presenter.showErrorSearchWithEmptyFavori();
            } else {
                boolean delete = presenter.showConfirmDelete();
                if (delete) {
                    presenter.deleteFavori();
                }
            }
        } catch (RepositoryException ex) {
            System.out.println("Exception from DeleteButtonHandler : "
                    + ex.getMessage());
            Logger.getLogger(SearchButtonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
