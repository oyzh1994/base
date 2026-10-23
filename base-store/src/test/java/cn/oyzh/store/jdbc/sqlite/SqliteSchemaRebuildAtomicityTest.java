package cn.oyzh.store.jdbc.sqlite;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 验证SQLite动态结构重建失败时不会留下半完成表。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public class SqliteSchemaRebuildAtomicityTest {

    private Path databaseDir;

    private Path database;

    @Before
    public void setUp() throws Exception {
        this.databaseDir = Files.createTempDirectory("base-store-rebuild-atomicity-");
        this.database = this.databaseDir.resolve("db");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.database);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE atomic_table"
                    + " (id INTEGER PRIMARY KEY, name TEXT)");
            statement.execute("INSERT INTO atomic_table VALUES(1,'first'),(2,'second')");
        }
        JdbcManager.destroy();
        JdbcConst.dbFile(this.database.toString());
        JdbcConst.dbDialect(JdbcDialect.SQLITE);
        JdbcManager.dialect = JdbcDialect.SQLITE;
    }

    @After
    public void tearDown() throws IOException {
        JdbcManager.destroy();
        if (this.databaseDir != null) {
            try (Stream<Path> paths = Files.walk(this.databaseDir)) {
                paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
            }
        }
    }

    @Test
    public void failedSchemaRebuildRollsBackCompletely() throws Exception {
        try {
            new BrokenSchemaStore();
            fail("schema rebuild should fail");
        } catch (RuntimeException expected) {
            assertTrue(expected.getCause() instanceof Exception);
        }

        assertEquals(List.of("id:INTEGER:pk=1", "name:TEXT:pk=0"), this.columnDefinitions());
        assertEquals(List.of("1|first", "2|second"), this.rows());
        assertEquals(0L, this.backupTableCount());
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.database);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("PRAGMA integrity_check")) {
            assertTrue(resultSet.next());
            assertEquals("ok", resultSet.getString(1));
        }
    }

    private List<String> columnDefinitions() throws Exception {
        List<String> columns = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.database);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("PRAGMA table_info(atomic_table)")) {
            while (resultSet.next()) {
                columns.add(resultSet.getString("name") + ":" + resultSet.getString("type")
                        + ":pk=" + resultSet.getInt("pk"));
            }
        }
        return columns;
    }

    private List<String> rows() throws Exception {
        List<String> rows = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.database);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id,name FROM atomic_table ORDER BY id")) {
            while (resultSet.next()) {
                rows.add(resultSet.getLong(1) + "|" + resultSet.getString(2));
            }
        }
        return rows;
    }

    private long backupTableCount() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.database);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT COUNT(*) FROM sqlite_master WHERE type='table'"
                             + " AND name LIKE 'atomic_table_backup_%'")) {
            return resultSet.next() ? resultSet.getLong(1) : -1L;
        }
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    /**
     * 结构原子性测试模型
     */
    public static class AtomicityRecord implements Serializable {
    }

    /**
     * 使用非法列类型触发重建失败
     */
    public static class BrokenSchemaStore extends JdbcStandardStore<AtomicityRecord> {

        @Override
        protected Class<AtomicityRecord> modelClass() {
            return AtomicityRecord.class;
        }

        @Override
        protected TableDefinition tableDefinition() {
            TableDefinition definition = new TableDefinition();
            definition.setTableName("atomic_table");
            definition.setColumns(List.of(
                    column("id", "INTEGER", true),
                    column("name", "TEXT", false),
                    column("broken", "BROKEN(", false)
            ));
            return definition;
        }
    }

    private static ColumnDefinition column(String name, String type, boolean primaryKey) {
        ColumnDefinition definition = new ColumnDefinition();
        definition.setColumnName(name);
        definition.setColumnType(type);
        definition.setFieldName(name);
        definition.setPrimaryKey(primaryKey);
        return definition;
    }
}
