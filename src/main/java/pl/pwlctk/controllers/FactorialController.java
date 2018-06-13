package pl.pwlctk.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.math.BigInteger;
import java.util.ResourceBundle;

import static pl.pwlctk.utils.FxmlUtils.getResourcebundle;

public class FactorialController {

    static ResourceBundle bundle = getResourcebundle();

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

    String message;

    @FXML
    public void computeFactorial(ActionEvent actionEvent) {
        message = bundle.getString("factorial.statusMessageFinish");
        statusMessage.setText(message);
        BigInteger numberToCalculate = new BigInteger(numberField.getText());
        String result;
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        result = calculateFactorial(numberToCalculate).toString();
        endTime = System.currentTimeMillis();

        calculateTimeField.setText(endTime - startTime + " ms");
        resultTextArea.setText(result);
        calculateTimeField.setDisable(false);
        calculateTimeLabel.setDisable(false);
    }

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
    }

    public BigInteger calculateFactorial(BigInteger n) {
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ZERO)) {
            result = result.multiply(n);
            n = n.subtract(BigInteger.ONE);
        }
        return result;
    }
}
