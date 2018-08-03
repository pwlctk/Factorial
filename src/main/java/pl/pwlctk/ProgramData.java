package pl.pwlctk;

class ProgramData {
    static boolean polishLanguage = true;
    static boolean alwaysOnTop;
    static boolean modernaStyle = true;
    static String result = "";
    static boolean computeButtonIsDisabled = true;
    static boolean calculateTimeFieldIsDisabled = true;
    static String calculateTimeFieldText = "";
    static boolean calculateTimeLabelIsDisabled = true;
    static boolean numberOfDigitsLabelIsDisabled = true;
    static String numberOfDigitsText = "";
    static boolean numberOfDigitsIsDisabled = true;
    static boolean computeLabelVisibility = false;
    static String statusMessage = "factorial.statusMessageReady";
    static String factorialNumber = "";
    static boolean showResultButton = false;
    static boolean disableResultTextArea = true;
    static boolean saveToFileMenuItemIsDisabled = true;

    static String getResult() {
        if (disableResultTextArea) {
            return "";
        } else {
            return result;
        }
    }

    static String getStatusMessage() {
        return Bundle.bundle.getString(statusMessage);
    }

    static String getFileName() {
        return Bundle.bundle.getString("factorial") + "(" + factorialNumber + ")";
    }

    static String getSaveExtension() {
        return Bundle.bundle.getString("save.extension") + " (*.txt)";
    }

    static String getWarningMessage() {
        return Bundle.bundle.getString("longComputeTimeWarningLabel");
    }

//    static String getMessage() {
//        return Bundle.bundle.getString(statusMessage);
//    }

}
