package pl.pwlctk.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import pl.pwlctk.Main;
import java.util.Optional;
import java.util.ResourceBundle;

import static pl.pwlctk.utils.FxmlUtils.getResourcebundle;

public class DialogUtils {

    static ResourceBundle bundle = getResourcebundle();

    public static void aboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.initOwner(Main.getStage());
        informationAlert.setTitle(bundle.getString("about.title"));
        informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setContentText(bundle.getString("about.content"));
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initOwner(Main.getStage());
        confirmationAlert.setTitle(bundle.getString("exit.title"));
        confirmationAlert.setHeaderText(bundle.getString("exit.header"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;

    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initOwner(Main.getStage());
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }


}
