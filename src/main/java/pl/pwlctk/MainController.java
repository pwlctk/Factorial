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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        message = bundle.getString("factorial.statusMessageFinish");
        GuiState.statusMessageId = 3;
        statusMessage.setText(message);
        BigInteger numberToCalculate = new BigInteger(numberField.getText());
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        GuiState.result = calculateFactorial(numberToCalculate).toString();
        endTime = System.currentTimeMillis();

        calculateTimeField.setText(endTime - startTime + " ms");
        resultTextArea.setText(GuiState.result);
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);

        factorialNumber = numberField.getText();
        numberOfDigitsField.setDisable(false);
        numberOfDigitsLabel.setDisable(false);
        numberOfDigitsField.setText(GuiState.result.length() + "");
        saveToFileMenuItem.setDisable(false);
        GuiState.calculateTimeLabelIsDisabled = false;
        GuiState.calculateTimeFieldIsDisabled = false;
        GuiState.calculateTimeFieldText = endTime - startTime + " ms";
        GuiState.numberOfDigitsLabelIsDisabled = false;
        GuiState.numberOfDigitsIsDisabled = false;
        GuiState.numberOfDigitsText = GuiState.result.length() + "";
    }

    @FXML
    public void keyReleasedProperty(KeyEvent keyEvent) {
        GuiState.numberFieldText = numberField.getText();
        boolean isDisabled = true;
        if (GuiState.numberFieldText.matches("[0-9]*")) {
            message = bundle.getString("factorial.statusMessageGo");
            GuiState.statusMessageId = 1;
            statusMessage.setText(message);
            isDisabled = (GuiState.numberFieldText.isEmpty());
            if (keyEvent.getCode() == KeyCode.ENTER && !GuiState.numberFieldText.isEmpty()) {
                computeFactorial();
            }

        } else {
            message = bundle.getString("factorial.statusMessageBadInput");
            GuiState.statusMessageId = 2;
            statusMessage.setText(message);

        }
        if (GuiState.numberFieldText.isEmpty()) {
            message = bundle.getString("factorial.statusMessageReady");
            GuiState.statusMessageId = 3;
            statusMessage.setText(message);
        }


        if (numberField.getText().length() > 5 && GuiState.numberFieldText.matches("[0-9]*")) {
            longComputeTimeWarningLabel.setVisible(true);
            GuiState.longComputeTimeWarningLabelVisibility = true;
            longComputeTimeWarningLabel.setText(bundle.getString("longComputeTimeWarningLabel"));
            GuiState.longComputeTimeWarningLabelId = 0;
        } else if (!GuiState.numberFieldText.matches("[0-9]*")) {
            longComputeTimeWarningLabel.setVisible(true);
            GuiState.longComputeTimeWarningLabelVisibility = true;
            longComputeTimeWarningLabel.setText(bundle.getString("factorial.statusMessageBadInput"));
            GuiState.longComputeTimeWarningLabelId = 1;

        } else {
            longComputeTimeWarningLabel.setVisible(false);
            GuiState.longComputeTimeWarningLabelVisibility = false;
        }
        computeButton.setDisable(isDisabled);
        GuiState.computeButtonIsDisabled = isDisabled;
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
        GuiState.modernaStyle = true;

    }

    @FXML
    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        GuiState.modernaStyle = false;

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
        GuiState.alwaysOnTop = isSelected;
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


    private BigInteger calculateFactorial(BigInteger n) {
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ZERO)) {
            result = result.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return result;
    }

    private static void writeToDisk(String invocation, String path) {
        try {
            Files.write(Paths.get(path), invocation.getBytes(), StandardOpenOption.CREATE);
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
            writeToDisk(GuiState.result, file.getPath());
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

        switch (GuiState.statusMessageId) {
            case 0:
                message = bundle.getString("factorial.statusMessageReady");
                statusMessage.setText(message);
                break;
            case 1:
                message = bundle.getString("factorial.statusMessageGo");
                statusMessage.setText(message);
                break;
            case 2:
                message = bundle.getString("factorial.statusMessageBadInput");
                statusMessage.setText(message);
                break;
            case 3:
                message = bundle.getString("factorial.statusMessageFinish");
                statusMessage.setText(message);
                break;
        }

        if (GuiState.modernaStyle) {
            styleGroup.selectToggle(modernaRadioMenuItem);
        } else {
            styleGroup.selectToggle(caspianRadioMenuItem);
        }


        if (GuiState.result.equals("")) {
            saveToFileMenuItem.setDisable(true);
        } else {
            saveToFileMenuItem.setDisable(false);
        }

        if (GuiState.longComputeTimeWarningLabelId == 0) {
            longComputeTimeWarningLabel.setText(bundle.getString("longComputeTimeWarningLabel"));
        } else {
            longComputeTimeWarningLabel.setText(bundle.getString("factorial.statusMessageBadInput"));
        }

        alwaysOnTopMenuItem.setSelected(GuiState.alwaysOnTop);
        resultTextArea.setText(GuiState.result);
        numberField.setText(GuiState.numberFieldText);
        computeButton.setDisable(GuiState.computeButtonIsDisabled);
        calculateTimeField.setDisable(GuiState.calculateTimeFieldIsDisabled);
        calculateTimeField.setText(GuiState.calculateTimeFieldText);
        calculateTimeLabel.setDisable(GuiState.calculateTimeLabelIsDisabled);
        numberOfDigitsLabel.setDisable(GuiState.numberOfDigitsLabelIsDisabled);
        numberOfDigitsField.setText(GuiState.numberOfDigitsText);
        numberOfDigitsField.setDisable(GuiState.numberOfDigitsIsDisabled);
        longComputeTimeWarningLabel.setVisible(GuiState.longComputeTimeWarningLabelVisibility);
    }
}
