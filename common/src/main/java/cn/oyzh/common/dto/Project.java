package cn.oyzh.common.dto;

import cn.oyzh.common.file.PropertiesFile;
import cn.oyzh.common.util.StringUtil;
import lombok.Data;

import java.io.IOException;

/**
 * 项目信息
 *
 * @author oyzh
 * @since 2020/9/14
 */
//@Data
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
     * copyright
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
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
