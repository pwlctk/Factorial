package pl.pwlctk;

class ProgramData {
    static boolean polishLanguage = true;
    static boolean alwaysOnTop;
    static boolean modernaStyle = true;
    static String result = "";
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
    static String factorialNumber = "";
    static String threadsStatusMessage = "";
    static boolean singleThreadCheckBox;
    static boolean multiThreadCheckBox;
    static boolean autoThreadCheckBox = true;

    static String getThreadsStatusMessage() {
        if (threadsStatusMessage.isEmpty()) {
            return "";
        } else {
            return Bundle.bundle.getString(threadsStatusMessage);
        }
    }

    static String getThreadsLabel() {
        return Bundle.bundle.getString("threads.multi");
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
        return Bundle.bundle.getString(longComputeTimeWarningLabel);
    }

}
