package pl.pwlctk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    private static final String FXML_MAIN_WINDOW_FXML = "/fxml/mainWindow.fxml";
    private static Stage stage; //Musiałem tak zrobic, aby dzialalo setAlwaysOnTop w MainController
    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        stage = primaryStage;
        Pane borderPane = FxmlUtils.fxmlLoader(FXML_MAIN_WINDOW_FXML);
        scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourcebundle().getString("title.application"));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
