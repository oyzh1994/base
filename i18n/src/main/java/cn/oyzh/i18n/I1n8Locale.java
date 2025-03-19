package cn.oyzh.i18n;


import java.util.Locale;

//@Data
//@AllArgsConstructor
public class I1n8Locale {

    private String name;

    private Locale locale;

    private String displayName;

    private String description;

    public I1n8Locale(String name, Locale locale, String displayName, String description) {
        this.name = name;
        this.locale = locale;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
