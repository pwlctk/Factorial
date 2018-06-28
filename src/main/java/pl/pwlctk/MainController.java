package pl.pwlctk;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final int BIG_NUMBER = 100000;
    private Main main = new Main();
    @FXML
    public Button showResultButton;
    @FXML
    public Label mainLabel;
    @FXML
    public CheckBox singleThreadCheckBox;
    @FXML
    public CheckBox multiThreadCheckBox;
    @FXML
    public CheckBox autoThreadCheckBox;
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
    public Label computeLabel;
    @FXML
    public ToggleGroup languageGroup;
    @FXML
    public MenuItem saveToFileMenuItem;
    @FXML
    private TextField numberField;
    @FXML
    private Button computeButton;

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Optional<ButtonType> closeResponse = confirmationDialog();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
            Effects.setDefault();
        }
    };

    private static void aboutApplication() {
        Effects.setBlur();
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.initOwner(Main.getStage());
        informationAlert.setTitle(Bundle.bundle.getString("about.title"));
        informationAlert.setHeaderText(Bundle.bundle.getString("about.header"));
        informationAlert.setContentText(Bundle.bundle.getString("about.content"));
        informationAlert.showAndWait();
        Effects.setDefault();
    }

    private static Optional<ButtonType> confirmationDialog() {
        Effects.setBlur();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initOwner(Main.getStage());
        confirmationAlert.setTitle(Bundle.bundle.getString("exit.title"));
        confirmationAlert.setHeaderText(Bundle.bundle.getString("exit.header"));
        return confirmationAlert.showAndWait();
    }

    @FXML
    public void closeApplication() {
        Optional<ButtonType> exitButton = confirmationDialog();
        if (exitButton.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        } else {
            Effects.setDefault();
        }
    }

    @FXML
    public void computeFactorial() {
        //zamieniam na inta i pozniej z powrotem na Stringa, aby pozbyć się możliwych zer na początku
        ProgramData.factorialNumber = String.valueOf(Integer.parseInt(numberField.getText()));
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();


        if (ProgramData.autoThreadCheckBox) {
            if (Integer.parseInt(ProgramData.factorialNumber) >= BIG_NUMBER / 10) {
                ProgramData.threadsStatusMessage = "threads.statusMessageMulti";
                ProgramData.result = CalculateFactorial.calculateFactorialMultiThreading(ProgramData.factorialNumber);
            } else {
                ProgramData.threadsStatusMessage = "threads.statusMessageSingle";
                ProgramData.result = CalculateFactorial.calculateFactorialSingleThreading(ProgramData.factorialNumber);
            }
        } else if (ProgramData.singleThreadCheckBox) {
            ProgramData.threadsStatusMessage = "threads.statusMessageSingle";
            ProgramData.result = CalculateFactorial.calculateFactorialSingleThreading(ProgramData.factorialNumber);
        } else {
            ProgramData.threadsStatusMessage = "threads.statusMessageMulti";
            ProgramData.result = CalculateFactorial.calculateFactorialMultiThreading(ProgramData.factorialNumber);
        }

        endTime = System.currentTimeMillis();

        ProgramData.statusMessage = "factorial.statusMessageFinish";
        ProgramData.computeLabelVisibility = true;
        computeLabel.setVisible(ProgramData.computeLabelVisibility);
        computeLabel.setText(ProgramData.getMessage());

        statusMessageLabel.setText(ProgramData.getStatusMessage() + ProgramData.getThreadsStatusMessage());
        ProgramData.calculateTimeLabelIsDisabled = false;
        ProgramData.calculateTimeFieldIsDisabled = false;
        ProgramData.calculateTimeFieldText = endTime - startTime + " ms";
        ProgramData.numberOfDigitsLabelIsDisabled = false;
        ProgramData.numberOfDigitsIsDisabled = false;
        ProgramData.numberOfDigitsText = ProgramData.result.length() + "";

        if (Integer.parseInt(ProgramData.factorialNumber) >= BIG_NUMBER) {
            ProgramData.showResultButton = true;
            showResultButton.setVisible(ProgramData.showResultButton);
            resultTextArea.setText("");
            ProgramData.disableResultTextArea = true;

        } else {
            ProgramData.showResultButton = false;
            showResultButton.setVisible(ProgramData.showResultButton);
            resultTextArea.setText(ProgramData.result);
            ProgramData.disableResultTextArea = false;
        }
        resultTextArea.setDisable(ProgramData.disableResultTextArea);

        calculateTimeField.setText(endTime - startTime + " ms");
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);
        numberOfDigitsField.setDisable(false);
        numberOfDigitsLabel.setDisable(false);
        numberOfDigitsField.setText(ProgramData.numberOfDigitsText);
        saveToFileMenuItem.setDisable(false);
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
    public void keyReleasedProperty(KeyEvent keyEvent) {
        boolean isDisabled = true;
        if (numberField.getText().matches("[0-9]*")) {
            ProgramData.statusMessage = "factorial.statusMessageGo";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
            isDisabled = (numberField.getText().isEmpty());
            if (keyEvent.getCode() == KeyCode.ENTER && !numberField.getText().isEmpty()) {
                computeFactorial();
            }

        } else {
            ProgramData.statusMessage = "factorial.statusMessageBadInput";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
        }
        if (numberField.getText().isEmpty()) {
            ProgramData.statusMessage = "factorial.statusMessageReady";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
        }

        if (numberField.getText().length() > 5 && numberField.getText().matches("[1-9]{1}[0-9]*")) {
            ProgramData.computeLabelVisibility = true;
            computeLabel.setVisible(true);
            computeLabel.setText(ProgramData.getWarningMessage());

        } else if (!numberField.getText().matches("[0-9]*")) {
            ProgramData.computeLabelVisibility = true;
            computeLabel.setVisible(true);
            computeLabel.setText(ProgramData.getMessage());
        } else if (keyEvent.getCode() != KeyCode.ENTER) {
            computeLabel.setVisible(false);
            ProgramData.computeLabelVisibility = false;
        }
        computeButton.setDisable(isDisabled);
        ProgramData.computeButtonIsDisabled = isDisabled;
    }

    @FXML
    public void saveToFile() {
        Effects.setBlur();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ProgramData.getSaveExtension(), "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName(ProgramData.getFileName());

        File file = fileChooser.showSaveDialog(Main.getStage());

        if (file != null) {
            SaveToDisk.writeToDisk(ProgramData.result, file.getPath());
        }
        Effects.setDefault();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int threads = Runtime.getRuntime().availableProcessors();
        Platform.runLater(() -> numberField.requestFocus());
        Main.getStage().setOnCloseRequest(confirmCloseEventHandler);

        multiThreadCheckBox.setText(ProgramData.getThreadsLabel() + " (" + threads + ")");
        singleThreadCheckBox.setSelected(ProgramData.singleThreadCheckBox);
        multiThreadCheckBox.setSelected(ProgramData.multiThreadCheckBox);
        autoThreadCheckBox.setSelected(ProgramData.autoThreadCheckBox);
        statusMessageLabel.setText(ProgramData.getStatusMessage() + ProgramData.getThreadsStatusMessage());
        modernaRadioMenuItem.setSelected(ProgramData.modernaStyle);
        caspianRadioMenuItem.setSelected(!ProgramData.modernaStyle);
        polishRadioMenu.setSelected(ProgramData.polishLanguage);
        englishRadioMenu.setSelected(!ProgramData.polishLanguage);
        saveToFileMenuItem.setDisable(ProgramData.result.isEmpty());
        computeLabel.setText(ProgramData.getMessage());
        alwaysOnTopMenuItem.setSelected(ProgramData.alwaysOnTop);
        resultTextArea.setText(ProgramData.getResult());
        numberField.setText(ProgramData.factorialNumber);
        computeButton.setDisable(ProgramData.computeButtonIsDisabled);
        calculateTimeField.setDisable(ProgramData.calculateTimeFieldIsDisabled);
        calculateTimeField.setText(ProgramData.calculateTimeFieldText);
        calculateTimeLabel.setDisable(ProgramData.calculateTimeLabelIsDisabled);
        numberOfDigitsLabel.setDisable(ProgramData.numberOfDigitsLabelIsDisabled);
        numberOfDigitsField.setText(ProgramData.numberOfDigitsText);
        numberOfDigitsField.setDisable(ProgramData.numberOfDigitsIsDisabled);
        computeLabel.setVisible(ProgramData.computeLabelVisibility);
        showResultButton.setVisible(ProgramData.showResultButton);
        resultTextArea.setDisable(ProgramData.disableResultTextArea);
    }

    @FXML
    public void checkBoxAuto() {
        autoThreadCheckBox.setSelected(true);
        singleThreadCheckBox.setSelected(false);
        multiThreadCheckBox.setSelected(false);
        ProgramData.autoThreadCheckBox = true;
        ProgramData.singleThreadCheckBox = false;
        ProgramData.multiThreadCheckBox = false;
    }

    @FXML
    public void checkBoxSingle() {
        autoThreadCheckBox.setSelected(false);
        singleThreadCheckBox.setSelected(true);
        multiThreadCheckBox.setSelected(false);
        ProgramData.autoThreadCheckBox = false;
        ProgramData.singleThreadCheckBox = true;
        ProgramData.multiThreadCheckBox = false;
    }

    @FXML
    public void checkBoxMulti() {
        autoThreadCheckBox.setSelected(false);
        singleThreadCheckBox.setSelected(false);
        multiThreadCheckBox.setSelected(true);
        ProgramData.autoThreadCheckBox = false;
        ProgramData.singleThreadCheckBox = false;
        ProgramData.multiThreadCheckBox = true;
    }

    @FXML
    public void showResult() {
        ProgramData.disableResultTextArea = false;
        resultTextArea.setText(ProgramData.getResult());
        resultTextArea.setDisable(ProgramData.disableResultTextArea);
        ProgramData.showResultButton = false;
        showResultButton.setVisible(ProgramData.showResultButton);

    }
}
