package pl.pwlctk;

import java.util.Locale;
import java.util.ResourceBundle;

class Bundle {
    static ResourceBundle bundle = getResourceBundle();

    static ResourceBundle getResourceBundle(String locale) {
        return ResourceBundle.getBundle("bundles/messages", new Locale(locale));
    }

    static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles/messages");
    }
}
