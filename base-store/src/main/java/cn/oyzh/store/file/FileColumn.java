package cn.oyzh.store.file;


/**
 * 文件字段
 *
 * @author oyzh
 * @since 2024-11-27
 */
public class FileColumn {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 位置
     */
    private int position;

    /**
     * 构造文件字段
     *
     * @param name 名称
     */
    public FileColumn(String name) {
        this.name = name;
    }

    /**
     * 构造文件字段
     *
     * @param name 名称
     * @param desc 描述
     */
    public FileColumn(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * 构造文件字段
     *
     * @param name     名称
     * @param position 位置
     */
    public FileColumn(String name, int position) {
        this.name = name;
        this.position = position;
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
     * 获取描述
     *
     * @return 描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置描述
     *
     * @param desc 描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取位置
     *
     * @return 位置
     */
    public int getPosition() {
        return position;
    }

    /**
     * 设置位置
     *
     * @param position 位置
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
