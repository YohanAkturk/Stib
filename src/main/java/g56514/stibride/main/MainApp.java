package g56514.stibride.main;

import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.model.Model;
import g56514.stibride.presenter.Presenter;
import g56514.stibride.view.View;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class that allows to lunch app.
 * 
 * @author yohan
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws RepositoryException, SQLException, IOException {
        Model model = new Model();
        View view = new View(stage);
        Presenter presenter = new Presenter(model, view);
        model.addObserver(presenter);
        view.initHandlerButtons(presenter);
        presenter.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
