package pl.pwlctk.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.pwlctk.Main;
import pl.pwlctk.utils.DialogUtils;
import pl.pwlctk.utils.FxmlUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.ResourceBundle;

import static pl.pwlctk.utils.FxmlUtils.getResourcebundle;

public class MainController {

    @FXML
    private static ResourceBundle bundle = getResourcebundle();

    @FXML
    private static String result = "";

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
    private TextField numberField;

    @FXML
    private Button computeButton;

    @FXML
    public MenuItem saveToFileMenuItem;

    @FXML
    public void computeFactorial(ActionEvent actionEvent) {
        String message = bundle.getString("factorial.statusMessageFinish");
        statusMessage.setText(message);
        BigInteger numberToCalculate = new BigInteger(numberField.getText());
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        result = calculateFactorial(numberToCalculate).toString();
        endTime = System.currentTimeMillis();

        calculateTimeField.setText(endTime - startTime + " ms");
        resultTextArea.setText(result);
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);

        factorialNumber = numberField.getText();
        numberOfDigitsField.setDisable(false);
        numberOfDigitsLabel.setDisable(false);
        numberOfDigitsField.setText(result.length() + "");
    }

    @FXML
    public void keyReleasedProperty(KeyEvent keyEvent) {
        String number = numberField.getText();
        String message;
        boolean isDisabled = true;
        if (number.matches("[0-9]*")) {
            message = bundle.getString("factorial.statusMessageGo");
            statusMessage.setText(message);
            isDisabled = (number.isEmpty());
        } else {
            message = bundle.getString("factorial.statusMessageBadInput");
            statusMessage.setText(message);
        }
        if (number.isEmpty()) {
            message = bundle.getString("factorial.statusMessageReady");
            statusMessage.setText(message);
        }
        computeButton.setDisable(isDisabled);
        calculateTimeField.setDisable(true);
        calculateTimeField.setText("");
        calculateTimeLabel.setDisable(true);
        numberOfDigitsField.setDisable(true);
        numberOfDigitsField.setText("");
        numberOfDigitsLabel.setDisable(true);
    }

    private BigInteger calculateFactorial(BigInteger n) {
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ZERO)) {
            result = result.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return result;
    }

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

    @FXML
    public void saveToFile(ActionEvent actionEvent) {
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
            writeToDisk(result, file.getPath());
        }
    }

    @FXML
    public void checkResultText() {
        if (!result.isEmpty()) {
            saveToFileMenuItem.setDisable(false);
        }
    }


    private static void writeToDisk(String invocation, String path) {

        try {
            Files.write(Paths.get(path), invocation.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
