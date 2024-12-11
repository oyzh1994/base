package cn.oyzh.store.file;

import lombok.Data;

/**
 * @author oyzh
 * @since 2024-11-27
 */
@Data
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
}
