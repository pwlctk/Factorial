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
    private final int BIG_NUMBER = 200000;
    private Main main = new Main();

    @FXML
    private Button showResultButton;
    @FXML
    private TextField numberOfDigitsField;
    @FXML
    private Label numberOfDigitsLabel;
    @FXML
    private Label statusMessageLabel;
    @FXML
    private TextArea resultTextArea;
    @FXML
    private TextField calculateTimeField;
    @FXML
    private Label calculateTimeLabel;
    @FXML
    private RadioMenuItem polishRadioMenu;
    @FXML
    private RadioMenuItem englishRadioMenu;
    @FXML
    private CheckMenuItem alwaysOnTopMenuItem;
    @FXML
    private RadioMenuItem modernaRadioMenuItem;
    @FXML
    private RadioMenuItem caspianRadioMenuItem;
    @FXML
    private Label computeLabel;
    @FXML
    private MenuItem saveToFileMenuItem;
    @FXML
    private TextField numberField;
    @FXML
    private Button computeButton;

    @FXML
    private void computeFactorial() {
        //zamieniam na inta i pozniej z powrotem na Stringa, aby pozbyć się możliwych zer na początku
        ProgramData.factorialNumber = String.valueOf(Integer.parseInt(numberField.getText()));

        Thread th = new Thread(this::runCalculateFactorial);
        th.start();
        loadingDialog(th);
    }

    @FXML
    private void closeApplication() {
        Optional<ButtonType> exitButton = confirmationDialog();
        if (exitButton.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        } else {
            Effects.setDefault();
        }
    }

    @FXML
    private void setModerna() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        ProgramData.modernaStyle = true;
    }

    @FXML
    private void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        ProgramData.modernaStyle = false;
    }

    @FXML
    private void about() {
        aboutApplication();
    }

    @FXML
    private void setAlwaysOnTop(ActionEvent actionEvent) {
        boolean isSelected = ((CheckMenuItem) actionEvent.getSource()).isSelected();
        Main.getStage().setAlwaysOnTop(isSelected);
        ProgramData.alwaysOnTop = isSelected;
    }

    @FXML
    private void switchToPolish() {
        ProgramData.polishLanguage = true;
        Bundle.bundle = Bundle.getResourceBundle("pl");
        main.changeLanguage("pl");
    }

    @FXML
    private void switchToEnglish() {
        ProgramData.polishLanguage = false;
        Bundle.bundle = Bundle.getResourceBundle("en");
        main.changeLanguage("en");
    }


    //metoda do refactoringu
    @FXML
    private void keyReleasedProperty(KeyEvent keyEvent) {
        boolean isDisabled = true;
        if (numberField.getText().matches("[0-9]*")) {
            ProgramData.statusMessage = "factorial.statusMessageGo";
            statusMessageLabel.setText(ProgramData.getStatusMessage());
            isDisabled = (numberField.getText().isEmpty());
            if (keyEvent.getCode() == KeyCode.ENTER && !numberField.getText().isEmpty()) {
                computeButton.fire();
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
    private void saveToFile() {
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

    @FXML
    private void showResult() {
        ProgramData.disableResultTextArea = false;
        resultTextArea.setText(ProgramData.getResult());
        resultTextArea.setDisable(ProgramData.disableResultTextArea);
        ProgramData.showResultButton = false;
        showResultButton.setVisible(ProgramData.showResultButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusMessageLabel.setText(ProgramData.getStatusMessage());
        Platform.runLater(() -> numberField.requestFocus());
        Main.getStage().setOnCloseRequest(confirmCloseEventHandler);

        initializeProgramData();
    }

    private void initializeProgramData() {
        modernaRadioMenuItem.setSelected(ProgramData.modernaStyle);
        caspianRadioMenuItem.setSelected(!ProgramData.modernaStyle);
        polishRadioMenu.setSelected(ProgramData.polishLanguage);
        englishRadioMenu.setSelected(!ProgramData.polishLanguage);
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
        saveToFileMenuItem.setDisable(ProgramData.saveToFileMenuItemIsDisabled);
        statusMessageLabel.setText(ProgramData.getStatusMessage());
    }

    private void calculationFactorialCancelled() {
        ProgramData.statusMessage = "factorial.statusMessageCanceled";
        ProgramData.calculateTimeFieldIsDisabled = true;
        ProgramData.calculateTimeLabelIsDisabled = true;
        ProgramData.numberOfDigitsLabelIsDisabled = true;
        ProgramData.numberOfDigitsIsDisabled = true;
        ProgramData.computeLabelVisibility = true;
        ProgramData.showResultButton = false;
        ProgramData.disableResultTextArea = true;
        ProgramData.result = "";
        ProgramData.numberOfDigitsText = "";
        ProgramData.saveToFileMenuItemIsDisabled = true;
        ProgramData.calculateTimeFieldText = "";
        initializeProgramData();
    }

    private void calculationFactorialCompleted() {
        ProgramData.statusMessage = "factorial.statusMessageFinish";
        ProgramData.computeLabelVisibility = true;
        ProgramData.calculateTimeLabelIsDisabled = false;
        ProgramData.calculateTimeFieldIsDisabled = false;
        ProgramData.numberOfDigitsLabelIsDisabled = false;
        ProgramData.numberOfDigitsIsDisabled = false;
        ProgramData.numberOfDigitsText = ProgramData.result.length() + "";

        if (Integer.parseInt(ProgramData.factorialNumber) >= BIG_NUMBER) {
            ProgramData.showResultButton = true;
            ProgramData.disableResultTextArea = true;

        } else {
            ProgramData.showResultButton = false;
            ProgramData.disableResultTextArea = false;
        }
        ProgramData.saveToFileMenuItemIsDisabled = false;
        ProgramData.calculateTimeFieldText = (CalculateFactorialMultiThreading.getComputeTime()) + " ms";

        initializeProgramData();
    }

    private void runCalculateFactorial() {
        ProgramData.result = CalculateFactorialMultiThreading.calculateFactorial(ProgramData.factorialNumber);
    }

    private static void aboutApplication() {
        Effects.setBlur();
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.initOwner(Main.getStage());
        informationAlert.setTitle(Bundle.bundle.getString("about.title"));
        informationAlert.setHeaderText(Bundle.bundle.getString("about.header"));
        int threads = Runtime.getRuntime().availableProcessors();
        informationAlert.setContentText(Bundle.bundle.getString("about.content") + " " + threads);
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

    //metoda do refactoringu
    private void loadingDialog(Thread th) {
        if (Integer.parseInt(ProgramData.factorialNumber) < BIG_NUMBER) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            calculationFactorialCompleted();

        } else {
            Effects.setBlur();
            Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
            loadingAlert.initOwner(Main.getStage());
            loadingAlert.setTitle(Bundle.bundle.getString("loading.wait"));
            loadingAlert.setHeaderText(Bundle.bundle.getString("loading.computeFactorial"));
            loadingAlert.setContentText(Bundle.bundle.getString("loading.message"));
            Button okButton = (Button) loadingAlert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText(Bundle.bundle.getString("loading.stop"));
            Thread loadingThread = new Thread(() -> {
                try {
                    th.join();
                    Platform.runLater(loadingAlert::close);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            });
            loadingThread.start();

            Optional<ButtonType> result = loadingAlert.showAndWait();
            if (!result.isPresent()) {
                if (th.isAlive()) {
                    th.interrupt();
                    calculationFactorialCancelled();
                } else {
                    calculationFactorialCompleted();
                }

            } else if (result.get() == ButtonType.OK) {
                th.interrupt();
                calculationFactorialCancelled();
            }

            Effects.setDefault();
        }
    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Optional<ButtonType> closeResponse = confirmationDialog();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
            Effects.setDefault();
        }
    };
}
