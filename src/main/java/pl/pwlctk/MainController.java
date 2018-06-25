package pl.pwlctk;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Main main = new Main();

    @FXML
    public TextField numberOfDigitsField;

    @FXML
    public Label numberOfDigitsLabel;

    @FXML
    public Label statusMessageLabel;

    @FXML
    public TextArea resultTextArea;

    @FXML
    public TextField calculateTimeField;

    @FXML
    public Label calculateTimeLabel;

    @FXML
    public RadioMenuItem polishRadioMenu;

    @FXML
    public RadioMenuItem englishRadioMenu;

    @FXML
    public CheckMenuItem alwaysOnTopMenuItem;

    @FXML
    public RadioMenuItem modernaRadioMenuItem;

    @FXML
    public RadioMenuItem caspianRadioMenuItem;

    @FXML
    public ToggleGroup styleGroup;

    @FXML
    public Label longComputeTimeWarningLabel;

    @FXML
    public ToggleGroup languageGroup;

    @FXML
    private TextField numberField;

    @FXML
    private Button computeButton;

    @FXML
    public MenuItem saveToFileMenuItem;

    private static void aboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.initOwner(Main.getStage());
        informationAlert.setTitle(Bundle.bundle.getString("about.title"));
        informationAlert.setHeaderText(Bundle.bundle.getString("about.header"));
        informationAlert.setContentText(Bundle.bundle.getString("about.content"));
        informationAlert.showAndWait();
    }

    private static Optional<ButtonType> confirmationDialog() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initOwner(Main.getStage());
        confirmationAlert.setTitle(Bundle.bundle.getString("exit.title"));
        confirmationAlert.setHeaderText(Bundle.bundle.getString("exit.header"));
        return confirmationAlert.showAndWait();
    }

    @FXML
    public void computeFactorial() {
        ProgramData.statusMessage = "factorial.statusMessageFinish";
        statusMessageLabel.setText(ProgramData.getStatusMessage());
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        ProgramData.result = Factorial.calculateFactorial(numberField.getText());
        endTime = System.currentTimeMillis();

        ProgramData.calculateTimeLabelIsDisabled = false;
        ProgramData.calculateTimeFieldIsDisabled = false;
        ProgramData.calculateTimeFieldText = endTime - startTime + " ms";
        ProgramData.numberOfDigitsLabelIsDisabled = false;
        ProgramData.numberOfDigitsIsDisabled = false;
        ProgramData.numberOfDigitsText = ProgramData.result.length() + "";

        resultTextArea.setText(ProgramData.result);

        calculateTimeField.setText(endTime - startTime + " ms");
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);
        ProgramData.factorialNumber = numberField.getText();
        numberOfDigitsField.setDisable(false);
        numberOfDigitsLabel.setDisable(false);
        numberOfDigitsField.setText(ProgramData.numberOfDigitsText);
        saveToFileMenuItem.setDisable(false);
    }

    @FXML
    public void keyReleasedProperty(KeyEvent keyEvent) {
        ProgramData.numberFieldText = numberField.getText();
        boolean isDisabled = true;
        if (ProgramData.numberFieldText.matches("[0-9]*")) {
            ProgramData.statusMessage = "factorial.statusMessageGo";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
            isDisabled = (ProgramData.numberFieldText.isEmpty());
            if (keyEvent.getCode() == KeyCode.ENTER && !ProgramData.numberFieldText.isEmpty()) {
                computeFactorial();
            }

        } else {
            ProgramData.statusMessage = "factorial.statusMessageBadInput";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
        }
        if (ProgramData.numberFieldText.isEmpty()) {
            ProgramData.statusMessage = "factorial.statusMessageReady";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
        }

        if (numberField.getText().length() > 5 && ProgramData.numberFieldText.matches("[0-9]*")) {
            ProgramData.longComputeTimeWarningLabelVisibility = true;
            ProgramData.longComputeTimeWarningLabel = "longComputeTimeWarningLabel";
            longComputeTimeWarningLabel.setVisible(true);
            longComputeTimeWarningLabel.setText(ProgramData.getWarningMessage());

        } else if (!ProgramData.numberFieldText.matches("[0-9]*")) {
            ProgramData.longComputeTimeWarningLabel = "factorial.statusMessageBadInput";
            ProgramData.longComputeTimeWarningLabelVisibility = true;
            longComputeTimeWarningLabel.setVisible(true);
            longComputeTimeWarningLabel.setText(ProgramData.getWarningMessage());
        } else {
            longComputeTimeWarningLabel.setVisible(false);
            ProgramData.longComputeTimeWarningLabelVisibility = false;
        }
        computeButton.setDisable(isDisabled);
        ProgramData.computeButtonIsDisabled = isDisabled;
    }

    @FXML
    public void closeApplication() {
        Optional<ButtonType> exitButton = confirmationDialog();
        if (exitButton.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void setModerna() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        ProgramData.modernaStyle = true;
    }

    @FXML
    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        ProgramData.modernaStyle = false;
    }

    @FXML
    public void about() {
        aboutApplication();
    }

    @FXML
    public void setAlwaysOnTop(ActionEvent actionEvent) {
        boolean isSelected = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        Main.getStage().setAlwaysOnTop(isSelected);
        ProgramData.alwaysOnTop = isSelected;
    }

    @FXML
    public void switchToPolish() {
        ProgramData.polishLanguage = true;
        Bundle.bundle = Bundle.getResourceBundle("pl");
        main.changeLanguage("pl");
    }

    @FXML
    public void switchToEnglish() {
        ProgramData.polishLanguage = false;
        Bundle.bundle = Bundle.getResourceBundle("en");
        main.changeLanguage("en");
    }

    @FXML
    public void saveToFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ProgramData.getSaveExtension(), "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName(ProgramData.getFileName());

        File file = fileChooser.showSaveDialog(Main.getStage());

        if (file != null) {
            SaveToDisk.writeToDisk(ProgramData.result, file.getPath());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusMessageLabel.setText(ProgramData.getStatusMessage());
        modernaRadioMenuItem.setSelected(ProgramData.modernaStyle);
        caspianRadioMenuItem.setSelected(!ProgramData.modernaStyle);
        polishRadioMenu.setSelected(ProgramData.polishLanguage);
        englishRadioMenu.setSelected(!ProgramData.polishLanguage);
        saveToFileMenuItem.setDisable(ProgramData.result.isEmpty());
        longComputeTimeWarningLabel.setText(ProgramData.getWarningMessage());
        alwaysOnTopMenuItem.setSelected(ProgramData.alwaysOnTop);
        resultTextArea.setText(ProgramData.result);
        numberField.setText(ProgramData.numberFieldText);
        computeButton.setDisable(ProgramData.computeButtonIsDisabled);
        calculateTimeField.setDisable(ProgramData.calculateTimeFieldIsDisabled);
        calculateTimeField.setText(ProgramData.calculateTimeFieldText);
        calculateTimeLabel.setDisable(ProgramData.calculateTimeLabelIsDisabled);
        numberOfDigitsLabel.setDisable(ProgramData.numberOfDigitsLabelIsDisabled);
        numberOfDigitsField.setText(ProgramData.numberOfDigitsText);
        numberOfDigitsField.setDisable(ProgramData.numberOfDigitsIsDisabled);
        longComputeTimeWarningLabel.setVisible(ProgramData.longComputeTimeWarningLabelVisibility);
    }
}
