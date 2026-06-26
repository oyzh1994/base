package cn.oyzh.store.test;

import cn.oyzh.common.util.ResourceUtil;
import cn.oyzh.store.file.CsvTypeFileReader;
import cn.oyzh.store.file.FileColumns;
import cn.oyzh.store.file.FileReadConfig;
import cn.oyzh.store.file.FileRecord;
import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-05-12
 */
public class CsvTypeFileReaderTest {

    @Test
    public void test1() throws Exception {
        String file = "/1.csv.csv";
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath(file));
        config.setDataRowStarts(2);
        config.setTxtIdentifier(null);
        config.setFieldSeparator(',');
        FileColumns columns = new FileColumns();
        columns.addColumn("Num");
        columns.addColumn("ID_num");
        CsvTypeFileReader reader = new CsvTypeFileReader(config, columns);
        while (true) {
            FileRecord fileRecord = reader.readRecord();
            if (fileRecord == null) {
                break;
            }
            System.out.println(fileRecord);
        }
        reader.close();
    }

    @Test
    public void test2() throws Exception {
        String file = "/2.csv.csv";
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath(file));
        config.setDataRowStarts(2);
        FileColumns columns = new FileColumns();
        columns.addColumn("name");
        columns.addColumn("age");
        CsvTypeFileReader reader = new CsvTypeFileReader(config, columns);
        while (true) {
            FileRecord fileRecord = reader.readRecord();
            if (fileRecord == null) {
                break;
            }
            System.out.println(fileRecord);
        }
        reader.close();
    }

    @Test
    public void test3() throws Exception {
        String file = "/3.csv.csv";
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath(file));
        config.setDataRowStarts(2);
        config.setFieldSeparator(',');
        FileColumns columns = new FileColumns();
        columns.addColumn("name");
        columns.addColumn("age");
        CsvTypeFileReader reader = new CsvTypeFileReader(config, columns);
        while (true) {
            FileRecord fileRecord = reader.readRecord();
            if (fileRecord == null) {
                break;
            }
            System.out.println(fileRecord);
        }
        reader.close();
    }
}
