package cn.oyzh.i18n;


import java.util.Locale;

/**
 * i18n区域信息，用于描述一种语言区域及其名称、显示名称和说明
 *
 * @author oyzh
 * @since 2025-01-23
 */
public class I18nLocale {

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域对象
     */
    private Locale locale;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 描述
     */
    private String description;

    /**
     * 构造区域信息
     *
     * @param name        区域名称
     * @param locale      区域对象
     * @param displayName 显示名称
     * @param description 描述
     */
    public I18nLocale(String name, Locale locale, String displayName, String description) {
        this.name = name;
        this.locale = locale;
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * 获取区域名称
     *
     * @return 区域名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置区域名称
     *
     * @param name 区域名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取区域对象
     *
     * @return 区域对象
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 设置区域对象
     *
     * @param locale 区域对象
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * 获取显示名称
     *
     * @return 显示名称
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 设置显示名称
     *
     * @param displayName 显示名称
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
