package cn.oyzh.store.test;

import cn.oyzh.store.jdbc.ColumnDefinition;
import cn.oyzh.store.jdbc.JdbcConst;
import cn.oyzh.store.jdbc.JdbcDialect;
import cn.oyzh.store.jdbc.JdbcManager;
import cn.oyzh.store.jdbc.JdbcStandardStore;
import cn.oyzh.store.jdbc.TableDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 验证H2与SQLite动态字段变更及增删改查行为一致。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public class JdbcDialectParityTest {

    private Path databaseDir;

    @Before
    public void setUp() throws IOException {
        this.databaseDir = Files.createTempDirectory("base-store-parity-");
    }

    @After
    public void tearDown() throws IOException {
        JdbcManager.destroy();
        try (Stream<Path> paths = Files.walk(this.databaseDir)) {
            paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
        }
    }

    @Test
    public void dynamicSchemaAndCrudBehaveTheSame() throws Exception {
        List<String> h2Result = this.exerciseDialect(
                JdbcDialect.H2, this.databaseDir.resolve("h2-data"), true);
        List<String> sqliteResult = this.exerciseDialect(
                JdbcDialect.SQLITE, this.databaseDir.resolve("sqlite-data"), false);
        assertEquals(h2Result, sqliteResult);
    }

    private List<String> exerciseDialect(JdbcDialect dialect, Path database, boolean h2) throws Exception {
        JdbcManager.destroy();
        this.createLegacyTable(database, h2);
        JdbcConst.dbFile(database.toString());
        JdbcConst.dbDialect(dialect);
        JdbcManager.dialect = dialect;

        ParityStore store = new ParityStore();
        ParityRecord first = store.selectOne(1L);
        assertNotNull(first);
        assertEquals("kept", first.name);
        assertEquals("123", first.code);
        assertEquals(0L, first.count);
        assertFalse(store.exist(10L));

        ParityRecord added = new ParityRecord(3L, "inserted", "new", 30L);
        assertTrue(store.insert(added));
        assertTrue(store.exist(3L));

        first.name = "updated";
        first.code = "456";
        first.count = 10L;
        assertTrue(store.update(first));

        assertTrue(store.delete(2L));
        assertFalse(store.exist(2L));

        List<String> snapshot = new ArrayList<>();
        for (ParityRecord record : store.selectList()) {
            snapshot.add(record.id + "|" + record.name + "|" + record.code + "|" + record.count);
        }
        snapshot.sort(Comparator.naturalOrder());
        assertEquals(List.of("1|updated|456|10", "3|inserted|new|30"), snapshot);
        return snapshot;
    }

    private void createLegacyTable(Path database, boolean h2) throws Exception {
        String url = h2
                ? "jdbc:h2:file:" + database + ";DB_CLOSE_DELAY=0;DB_CLOSE_ON_EXIT=FALSE"
                : "jdbc:sqlite:" + database;
        String createSql = h2
                ? "CREATE TABLE PARITY_TABLE (ID BIGINT PRIMARY KEY, NAME VARCHAR(255),"
                + " CODE INTEGER, OBSOLETE VARCHAR(255))"
                : "CREATE TABLE parity_table (id INTEGER PRIMARY KEY, name TEXT, code INTEGER, obsolete TEXT)";
        String insertSql = h2
                ? "INSERT INTO PARITY_TABLE (ID,NAME,CODE,OBSOLETE) VALUES(1,'kept',123,'drop'),"
                + "(2,'remove',234,'drop')"
                : "INSERT INTO parity_table (id,name,code,obsolete) VALUES(1,'kept',123,'drop'),"
                + "(2,'remove',234,'drop')";
        try (Connection connection = DriverManager.getConnection(
                url, h2 ? "" : null, h2 ? "" : null);
             Statement statement = connection.createStatement()) {
            statement.execute(createSql);
            statement.execute(insertSql);
        }
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    /**
     * 对齐测试模型
     */
    public static class ParityRecord implements Serializable {

        private long id;

        private String name;

        private String code;

        private String obsolete;

        private long count;

        public ParityRecord() {
        }

        public ParityRecord(long id, String name, String code, long count) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.count = count;
        }
    }

    /**
     * 动态结构测试存储
     */
    public static class ParityStore extends JdbcStandardStore<ParityRecord> {

        @Override
        protected Class<ParityRecord> modelClass() {
            return ParityRecord.class;
        }

        @Override
        protected TableDefinition tableDefinition() {
            TableDefinition definition = new TableDefinition();
            definition.setTableName("parity_table");
            definition.setColumns(List.of(
                    column("id", "INTEGER", true),
                    column("name", "TEXT", false),
                    column("code", "TEXT", false),
                    column("count", "INTEGER", false)
            ));
            return definition;
        }
    }

    private static ColumnDefinition column(String name, String type, boolean primaryKey) {
        ColumnDefinition definition = new ColumnDefinition();
        definition.setColumnName(name);
        definition.setColumnType(type);
        definition.setPrimaryKey(primaryKey);
        definition.setFieldName(name);
        return definition;
    }
}
