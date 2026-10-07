package cn.oyzh.common.dto;

import cn.oyzh.common.file.PropertiesFile;
import cn.oyzh.common.util.StringUtil;

import java.io.IOException;

/**
 * 项目信息
 *
 * @author oyzh
 * @since 2020/9/14
 */
public class Project {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 版本号
     */
    private String version;

    /**
     * 更新日期
     */
    private String updateDate;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 当前实例
     */
    private static Project instance;

    /**
     * 加载
     *
     * @return 项目对象
     */
    public static Project load() {
        if (instance == null) {
            synchronized (Project.class) {
                try {
                    PropertiesFile propFile = new PropertiesFile("/project.properties");
                    Project project = new Project();
                    String name = propFile.getProperty("project.name");
                    if (StringUtil.isNotBlank(name)) {
                        project.setName(name);
                    }
                    String type = propFile.getProperty("project.type");
                    if (StringUtil.isNotBlank(type)) {
                        project.setType(type);
                    }
                    String version = propFile.getProperty("project.version");
                    if (StringUtil.isNotBlank(version)) {
                        project.setVersion(version);
                    }
                    String updateDate = propFile.getProperty("project.updateDate");
                    if (StringUtil.isNotBlank(updateDate)) {
                        project.setUpdateDate(updateDate);
                    }
                    String copyright = propFile.getProperty("project.copyright");
                    if (StringUtil.isNotBlank(copyright)) {
                        project.setCopyright(copyright);
                    }
                    propFile.clear();
                    instance = project;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return instance;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取更新日期
     *
     * @return 更新日期
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新日期
     *
     * @param updateDate 更新日期
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取版权信息
     *
     * @return 版权信息
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 设置版权信息
     *
     * @param copyright 版权信息
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
