package pl.pwlctk.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FxmlUtils {

    public static Pane fxmlLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        Locale.setDefault(new Locale("pl"));

        loader.setResources(getResourcebundle());
        try {
            return loader.load();
        } catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
        return null;
    }

    public static ResourceBundle getResourcebundle() {
        return ResourceBundle.getBundle("bundles/messages");
    }

    public static void setResourcesBundle(String locale) {
        Locale.setDefault(new Locale(locale));

    }
}
