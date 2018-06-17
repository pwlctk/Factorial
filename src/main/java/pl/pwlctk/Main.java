package pl.pwlctk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.pwlctk.utils.FxmlUtils;


public class Main extends Application {

    private static final String FXML_MAIN_WINDOW_FXML = "/fxml/mainWindow.fxml";
    public static final String LANGUAGE ="pl";
    private static Stage stage; //Musiałem tak zrobic, aby dzialalo setAlwaysOnTop w MainController

    @Override
    public void start(Stage primaryStage){
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        stage = primaryStage;
        Pane borderPane = FxmlUtils.fxmlLoader(FXML_MAIN_WINDOW_FXML);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourcebundle().getString("title.application"));
        primaryStage.show();

    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
