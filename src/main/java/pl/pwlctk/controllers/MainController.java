package pl.pwlctk.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;
import pl.pwlctk.Main;
import pl.pwlctk.utils.DialogUtils;

import javax.security.auth.callback.LanguageCallback;
import java.util.Optional;

public class MainController {

    @FXML
    public void closeApplication() {
        Optional<ButtonType> result = DialogUtils.confirmationDialog();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void setModerna() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    @FXML
    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);

    }

    @FXML
    public void about() {
        DialogUtils.aboutApplication();
    }

    @FXML
    public void setAlwaysOnTop(ActionEvent actionEvent) {
        Stage stage = Main.getStage();
        boolean isSelected = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        stage.setAlwaysOnTop(isSelected);
    }

    @FXML
    public void switchToPolish(ActionEvent actionEvent) {
        System.out.println("Polska!");
    }

    @FXML
    public void switchToEnglish(ActionEvent actionEvent) {
        System.out.println("Nie Polska!");
    }

    public void saveToFile(ActionEvent actionEvent) {
        System.out.println("Zapis do pliku!");
    }
}
