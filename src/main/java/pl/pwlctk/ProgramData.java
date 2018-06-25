package pl.pwlctk;


import javafx.scene.control.RadioMenuItem;

import java.util.ResourceBundle;

import static pl.pwlctk.FxmlUtils.getResourcebundle;

class ProgramData {
    static ResourceBundle bundle = getResourcebundle();

    //Menu Items
    static RadioMenuItem styleRadioMenuItem;
    static boolean polishLanguage = true;
    static boolean alwaysOnTop;
    static boolean modernaStyle = true;
    static String result = "";
    static String numberFieldText = "";
    static boolean computeButtonIsDisabled = true;
    static boolean calculateTimeFieldIsDisabled = true;
    static String calculateTimeFieldText;
    static boolean calculateTimeLabelIsDisabled = true;
    static boolean numberOfDigitsLabelIsDisabled = true;
    static String numberOfDigitsText = "";
    static boolean numberOfDigitsIsDisabled = true;
    static boolean longComputeTimeWarningLabelVisibility = false;
    static String longComputeTimeWarningLabel = "factorial.statusMessageBadInput";
    static String statusMessage = "factorial.statusMessageReady";

    static String getStatusMessage() {
        return bundle.getString(statusMessage);
    }

    static String getWarningMessage() {
        return bundle.getString(longComputeTimeWarningLabel);
    }

}
