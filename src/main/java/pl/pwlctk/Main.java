package pl.pwlctk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private static Stage stage;
    private final String FXML = "/mainWindow.fxml";
    static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setResources(Bundle.getResourceBundle("pl"));
        stage = primaryStage;

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(Bundle.getResourceBundle().getString("title.application"));
        primaryStage.show();
    }

    void changeLanguage(String language) {
        try {
            stage.getScene().setRoot(FXMLLoader.load(getClass().getResource(FXML), ResourceBundle.getBundle("bundles/messages", new Locale(language))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
