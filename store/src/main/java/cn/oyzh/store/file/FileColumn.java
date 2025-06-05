package cn.oyzh.store.file;


/**
 * 文件字段
 *
 * @author oyzh
 * @since 2024-11-27
 */
public class FileColumn {

    private String name;

    private String desc;

    private int position;

    public FileColumn(String name) {
        this.name = name;
    }

    public FileColumn(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public FileColumn(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
