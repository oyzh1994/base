package cn.oyzh.store.test;

import cn.oyzh.store.jdbc.migration.H2SqliteMigrator;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 使用本机dev1真实H2源库验证全库迁移结果。
 *
 * 测试只复制H2源文件，不读取或修改dev1现有SQLite数据库。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public class Dev1MigrationVerificationTest {

    private static final Path DEV1_H2 = Path.of(
            System.getProperty("user.home"), ".easyshell_dev1", "db.mv.db");

    private Path workDirectory;

    private Path sourceBase;

    private Path sourceDatabase;

    private Path sqliteDatabase;

    private String dev1Sha256;

    @Before
    public void setUp() throws Exception {
        Assume.assumeTrue("dev1 H2 database not found: " + DEV1_H2,
                Files.isRegularFile(DEV1_H2));
        this.dev1Sha256 = sha256(DEV1_H2);
        this.workDirectory = Files.createTempDirectory("easyshell-dev1-migration-");
        this.sourceBase = this.workDirectory.resolve("db");
        this.sourceDatabase = Path.of(this.sourceBase + ".mv.db");
        this.sqliteDatabase = this.workDirectory.resolve("migrated.db");
        Files.copy(DEV1_H2, this.sourceDatabase);
    }

    @After
    public void tearDown() throws Exception {
        if (this.dev1Sha256 != null) {
            assertEquals("dev1 H2 database was modified", this.dev1Sha256, sha256(DEV1_H2));
        }
        if (this.workDirectory != null) {
            try (Stream<Path> paths = Files.walk(this.workDirectory)) {
                paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
            }
        }
    }

    @Test
    public void migratesAllDev1TablesWithValueParity() throws Exception {
        String sourceSha256 = sha256(this.sourceDatabase);
        H2SqliteMigrator.MigrationStat stat =
                H2SqliteMigrator.migrate(this.sourceDatabase, this.sqliteDatabase);

        assertTrue(stat.getTableCount() > 0);
        assertTrue(stat.getRowCount() > 0);
        assertEquals("H2 source copy was modified", sourceSha256, sha256(this.sourceDatabase));
        this.assertFullDataParity(stat);
        this.assertMigrationRecord(stat, sourceSha256);
    }

    private void assertFullDataParity(H2SqliteMigrator.MigrationStat stat) throws Exception {
        Map<String, List<String>> sourceSchema = this.readH2Schema();
        Map<String, List<String>> sourceRows = this.readRows(this.h2Url(), sourceSchema, null);
        Map<String, List<String>> targetSchema = this.readSqliteSchema();
        Map<String, List<String>> targetRows = this.readRows(
                "jdbc:sqlite:" + this.sqliteDatabase, targetSchema, sourceSchema.keySet());

        assertEquals(sourceSchema.keySet(), targetSchema.keySet());
        assertEquals(sourceRows.keySet(), targetRows.keySet());
        assertEquals(sourceRows, targetRows);
        assertEquals(stat.getTableCount(), sourceRows.size());

        long rowCount = 0L;
        for (Map.Entry<String, List<String>> entry : sourceRows.entrySet()) {
            rowCount += entry.getValue().size();
        }
        assertEquals(stat.getRowCount(), rowCount);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("PRAGMA integrity_check")) {
            assertTrue(resultSet.next());
            assertEquals("ok", resultSet.getString(1));
        }
    }

    private String h2Url() {
        return "jdbc:h2:file:" + this.sourceBase
                + ";IFEXISTS=TRUE;ACCESS_MODE_DATA=r;DB_CLOSE_ON_EXIT=FALSE";
    }

    private Map<String, List<String>> readH2Schema() throws Exception {
        try (Connection connection = DriverManager.getConnection(this.h2Url(), "", "")) {
            connection.setReadOnly(true);
            DatabaseMetaData metaData = connection.getMetaData();
            Map<String, List<String>> schema = new LinkedHashMap<>();
            try (ResultSet tables = metaData.getTables(
                    connection.getCatalog(), "PUBLIC", "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    List<String> columns = new ArrayList<>();
                    try (ResultSet resultSet = metaData.getColumns(
                            connection.getCatalog(), "PUBLIC", tableName, null)) {
                        while (resultSet.next()) {
                            columns.add(resultSet.getString("COLUMN_NAME"));
                        }
                    }
                    Collections.sort(columns);
                    schema.put(tableName, columns);
                }
            }
            return schema;
        }
    }

    private Map<String, List<String>> readSqliteSchema() throws Exception {
        Map<String, List<String>> schema = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement();
             ResultSet tables = statement.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type='table'"
                             + " AND name NOT LIKE 'sqlite_%' ORDER BY name")) {
            while (tables.next()) {
                String tableName = tables.getString(1);
                List<String> columns = new ArrayList<>();
                try (Statement columnStatement = connection.createStatement();
                     ResultSet resultSet = columnStatement.executeQuery(
                             "PRAGMA table_info(" + quote(tableName) + ")")) {
                    while (resultSet.next()) {
                        columns.add(resultSet.getString("name"));
                    }
                }
                Collections.sort(columns);
                schema.put(tableName, columns);
            }
        }
        return schema;
    }

    private Map<String, List<String>> readRows(String url, Map<String, List<String>> schema,
                                                Iterable<String> expectedTables) throws Exception {
        Map<String, List<String>> rows = new LinkedHashMap<>();
        boolean sqlite = url.startsWith("jdbc:sqlite:");
        try (Connection connection = DriverManager.getConnection(url, sqlite ? null : "", sqlite ? null : "")) {
            if (!sqlite) {
                connection.setReadOnly(true);
            }
            for (Map.Entry<String, List<String>> table : schema.entrySet()) {
                if (expectedTables != null) {
                    boolean found = false;
                    for (String expectedTable : expectedTables) {
                        if (expectedTable.equals(table.getKey())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                }
                List<String> tableRows = new ArrayList<>();
                String sql = "SELECT " + quoteColumns(table.getValue())
                        + " FROM " + quote(table.getKey());
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        StringBuilder row = new StringBuilder();
                        for (int index = 1; index <= table.getValue().size(); index++) {
                            row.append(canonical(resultSet.getObject(index))).append('\u001f');
                        }
                        tableRows.add(row.toString());
                    }
                }
                Collections.sort(tableRows);
                rows.put(table.getKey(), tableRows);
            }
        }
        return rows;
    }

    private void assertMigrationRecord(H2SqliteMigrator.MigrationStat stat,
                                       String sourceSha256) throws Exception {
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        assertTrue(Files.isRegularFile(recordFile));
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile)) {
            record.load(input);
        }
        assertEquals("COMPLETED", record.getProperty("status"));
        assertEquals("MIGRATED", record.getProperty("record_origin"));
        assertEquals(String.valueOf(stat.getTableCount()), record.getProperty("table_count"));
        assertEquals(String.valueOf(stat.getRowCount()), record.getProperty("row_count"));
        assertEquals(sourceSha256, record.getProperty("source_sha256"));
        assertEquals(sha256(this.sqliteDatabase), record.getProperty("sqlite_sha256"));
        assertNotNull(record.getProperty("h2_version"));
    }

    private static String canonical(Object value) throws SQLException, IOException {
        if (value == null) {
            return "<NULL>";
        }
        if (value instanceof byte[] bytes) {
            return "B:" + java.util.HexFormat.of().formatHex(bytes);
        }
        if (value instanceof Blob blob) {
            try {
                return "B:" + java.util.HexFormat.of().formatHex(
                        blob.getBytes(1L, Math.toIntExact(blob.length())));
            } finally {
                blob.free();
            }
        }
        if (value instanceof Clob clob) {
            try {
                return "S:" + clob.getSubString(1L, Math.toIntExact(clob.length()));
            } finally {
                clob.free();
            }
        }
        if (value instanceof Boolean bool) {
            return "N:" + (bool ? "1" : "0");
        }
        if (value instanceof Number number) {
            return "N:" + new BigDecimal(number.toString()).stripTrailingZeros().toPlainString();
        }
        if (value instanceof java.sql.Timestamp timestamp) {
            return "T:" + timestamp.toInstant();
        }
        if (value instanceof java.sql.Date date) {
            return "T:" + date.toLocalDate();
        }
        if (value instanceof java.sql.Time time) {
            return "T:" + time.toLocalTime();
        }
        if (value instanceof java.util.Date date) {
            return "T:" + date.toInstant();
        }
        if (value instanceof LocalDate || value instanceof LocalTime
                || value instanceof LocalDateTime || value instanceof ZonedDateTime
                || value instanceof OffsetDateTime || value instanceof Instant) {
            return "T:" + value;
        }
        if (value instanceof CharSequence) {
            return "S:" + value;
        }
        return "O:" + value.getClass().getName() + ":" + value;
    }

    private static String quoteColumns(List<String> columns) {
        StringBuilder sql = new StringBuilder();
        for (String column : columns) {
            sql.append(quote(column)).append(',');
        }
        sql.deleteCharAt(sql.length() - 1);
        return sql.toString();
    }

    private static String quote(String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }

    private String sha256(Path file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        StringBuilder result = new StringBuilder();
        for (byte data : digest.digest(Files.readAllBytes(file))) {
            result.append(String.format("%02x", data));
        }
        return result.toString();
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }
}
