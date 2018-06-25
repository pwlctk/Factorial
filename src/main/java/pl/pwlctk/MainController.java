package pl.pwlctk;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static pl.pwlctk.FxmlUtils.getResourcebundle;

public class MainController implements Initializable {

    @FXML
    private static ResourceBundle bundle = getResourcebundle();

    @FXML
    private static String factorialNumber;

    @FXML
    public TextField numberOfDigitsField;

    @FXML
    public Label numberOfDigitsLabel;

    @FXML
    public Label statusMessage;

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
    private TextField numberField;

    @FXML
    private Button computeButton;

    @FXML
    public MenuItem saveToFileMenuItem;

    private String message;

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
        return confirmationAlert.showAndWait();

    }

    //Na potem
    static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initOwner(Main.getStage());
        errorAlert.setTitle(bundle.getString("error.title"));
        errorAlert.setHeaderText(bundle.getString("error.header"));
        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();
    }

    @FXML
    public void computeFactorial() {
        ProgramData.statusMessageId = 3;
        statusMessage.setText(bundle.getString("factorial.statusMessageFinish"));
        BigInteger numberToCalculate = new BigInteger(numberField.getText());
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        ProgramData.result = Factorial.calculateFactorial(numberToCalculate).toString();
        endTime = System.currentTimeMillis();

        calculateTimeField.setText(endTime - startTime + " ms");
        resultTextArea.setText(ProgramData.result);
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);

        factorialNumber = numberField.getText();
        numberOfDigitsField.setDisable(false);
        numberOfDigitsLabel.setDisable(false);
        numberOfDigitsField.setText(ProgramData.result.length() + "");
        saveToFileMenuItem.setDisable(false);
        ProgramData.calculateTimeLabelIsDisabled = false;
        ProgramData.calculateTimeFieldIsDisabled = false;
        ProgramData.calculateTimeFieldText = endTime - startTime + " ms";
        ProgramData.numberOfDigitsLabelIsDisabled = false;
        ProgramData.numberOfDigitsIsDisabled = false;
        ProgramData.numberOfDigitsText = ProgramData.result.length() + "";
    }

    @FXML
    public void keyReleasedProperty(KeyEvent keyEvent) {
        ProgramData.numberFieldText = numberField.getText();
        boolean isDisabled = true;
        if (ProgramData.numberFieldText.matches("[0-9]*")) {
            ProgramData.statusMessageId = 1;
            statusMessage.setText(bundle.getString("factorial.statusMessageGo"));
            isDisabled = (ProgramData.numberFieldText.isEmpty());
            if (keyEvent.getCode() == KeyCode.ENTER && !ProgramData.numberFieldText.isEmpty()) {
                computeFactorial();
            }

        } else {
            ProgramData.statusMessageId = 2;
            statusMessage.setText(bundle.getString("factorial.statusMessageBadInput"));

        }
        if (ProgramData.numberFieldText.isEmpty()) {
            ProgramData.statusMessageId = 3;
            statusMessage.setText(bundle.getString("factorial.statusMessageReady"));
        }


        if (numberField.getText().length() > 5 && ProgramData.numberFieldText.matches("[0-9]*")) {
            longComputeTimeWarningLabel.setVisible(true);
            ProgramData.longComputeTimeWarningLabelVisibility = true;
            longComputeTimeWarningLabel.setText(bundle.getString("longComputeTimeWarningLabel"));
            ProgramData.longComputeTimeWarningLabelId = 0;
        } else if (!ProgramData.numberFieldText.matches("[0-9]*")) {
            longComputeTimeWarningLabel.setVisible(true);
            ProgramData.longComputeTimeWarningLabelVisibility = true;
            longComputeTimeWarningLabel.setText(bundle.getString("factorial.statusMessageBadInput"));
            ProgramData.longComputeTimeWarningLabelId = 1;

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
        Stage stage = Main.getStage();
        boolean isSelected = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        stage.setAlwaysOnTop(isSelected);
        ProgramData.alwaysOnTop = isSelected;
    }

    @FXML
    private void changeLocale(String language) {
        Scene scene = Main.getScene();
        try {
            scene.setRoot(FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"), ResourceBundle.getBundle("bundles/messages", new Locale(language))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void switchToPolish() {
        bundle = getResourcebundle("pl");
        changeLocale("pl");
    }

    @FXML
    public void switchToEnglish() {
        bundle = getResourcebundle("en");
        changeLocale("en");
    }

    @FXML
    public void saveToFile() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter save.extension
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(FxmlUtils.getResourcebundle().getString("save.extension") + " (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Set default directory and filename
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        String fileName = (FxmlUtils.getResourcebundle().getString("factorial") + "(" + factorialNumber + ")");
        fileChooser.setInitialFileName(fileName);

        //set to file
        File file = fileChooser.showSaveDialog(Main.getStage());

        if (file != null) {
            SaveToDisk.writeToDisk(ProgramData.result, file.getPath());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxmlUtils.setResourcesBundle(resources.getLocale().getLanguage());
        switch (resources.getLocale().getLanguage()) {
            case "en":
                englishRadioMenu.setSelected(true);
                break;
            case "pl":
                polishRadioMenu.setSelected(true);
                break;
            default:
                break;
        }

        switch (ProgramData.statusMessageId) {
            case 0:
                statusMessage.setText(bundle.getString("factorial.statusMessageReady"));
                break;
            case 1:
                statusMessage.setText(bundle.getString("factorial.statusMessageGo"));
                break;
            case 2:
                statusMessage.setText(bundle.getString("factorial.statusMessageBadInput"));
                break;
            case 3:
                statusMessage.setText(bundle.getString("factorial.statusMessageFinish"));
                break;
        }

        if (ProgramData.modernaStyle) {
            styleGroup.selectToggle(modernaRadioMenuItem);
        } else {
            styleGroup.selectToggle(caspianRadioMenuItem);
        }


        if (ProgramData.result.equals("")) {
            saveToFileMenuItem.setDisable(true);
        } else {
            saveToFileMenuItem.setDisable(false);
        }

        if (ProgramData.longComputeTimeWarningLabelId == 0) {
            longComputeTimeWarningLabel.setText(bundle.getString("longComputeTimeWarningLabel"));
        } else {
            longComputeTimeWarningLabel.setText(bundle.getString("factorial.statusMessageBadInput"));
        }

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
