package g56514.stibride.handler;

import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.presenter.Presenter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Represents the "Edit" button handler.
 *
 * @author yohan
 */
public class EditButtonHandler implements EventHandler<ActionEvent> {

    private Presenter presenter;

    /**
     * Creates a new instance of <code>EditButtonHandler</code>.
     *
     * @param presenter the presenter.
     */
    public EditButtonHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Allows to handle the click on the "Edit" button.
     *
     * @param t the action event.
     */
    @Override
    public void handle(ActionEvent t) {
        try {
            if (presenter.getFavoris() == null) {
                presenter.showErrorSearchWithEmptyFavori();
            } else {
                presenter.updateDBCallingEditPage();
            }
        } catch (RepositoryException ex) {
            System.out.println("Exception from EditButtonHandler : "
                    + ex.getMessage());
            Logger.getLogger(AddToFavorisButtonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
