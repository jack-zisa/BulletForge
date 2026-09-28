package dev.creoii.bulletforge.util.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private ResourceBundle bundle;

    public I18n(Locale locale) {
        setLocale(locale);
    }

    public void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("i18n.texts", locale);
    }

    public String get(String key) {
        return bundle.getString(key);
    }
}
