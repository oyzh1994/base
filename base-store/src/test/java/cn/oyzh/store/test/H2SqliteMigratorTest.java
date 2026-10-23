package cn.oyzh.store.test;

import cn.oyzh.store.jdbc.migration.H2SqliteMigrator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 测试H2迁移至SQLite的数据完整性、原子性与幂等性。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public class H2SqliteMigratorTest {

    private Path databaseDir;
    private Path h2Base;
    private Path h2Database;
    private Path sqliteDatabase;

    @Before
    public void setUp() throws IOException {
        this.databaseDir = Files.createTempDirectory("base-store-migration-");
        this.h2Base = this.databaseDir.resolve("legacy-db");
        this.h2Database = Path.of(this.h2Base + ".mv.db");
        this.sqliteDatabase = this.databaseDir.resolve("app.db");
    }

    @After
    public void tearDown() throws IOException {
        try (Stream<Path> paths = Files.walk(this.databaseDir)) {
            paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
        }
    }

    @Test
    public void migratesOnceAndPreservesH2Source() throws Exception {
        createH2Database(this.h2Base);
        String sourceSha256 = sha256(this.h2Database);
        long sourceModified = Files.getLastModifiedTime(this.h2Database).toMillis();

        H2SqliteMigrator.Result result = H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);
        assertEquals(H2SqliteMigrator.Status.MIGRATED, result.getStatus());
        assertNotNull(result.getStat());
        assertEquals(1, result.getStat().getTableCount());
        assertEquals(2L, result.getStat().getRowCount());
        assertEquals(sourceSha256, sha256(this.h2Database));
        assertEquals(sourceModified, Files.getLastModifiedTime(this.h2Database).toMillis());
        verifyMigratedData();

        String sqliteSha256 = sha256(this.sqliteDatabase);
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        Files.move(recordFile, H2SqliteMigrator.pendingMigrationRecordFile(this.sqliteDatabase));
        H2SqliteMigrator.Result recovered =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);
        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, recovered.getStatus());
        assertEquals(sqliteSha256, sha256(this.sqliteDatabase));
        assertTrue(Files.isRegularFile(recordFile));

        H2SqliteMigrator.Result secondResult =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);
        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, secondResult.getStatus());
        assertEquals(sqliteSha256, sha256(this.sqliteDatabase));
    }

    @Test
    public void newInstallWithoutH2SkipsMigration() throws Exception {
        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertTrue(Files.notExists(this.sqliteDatabase));
        assertTrue(Files.notExists(H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase)));
        assertTrue(Files.notExists(H2SqliteMigrator.pendingMigrationRecordFile(this.sqliteDatabase)));

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE first_run (id INTEGER PRIMARY KEY)");
        }
        assertTrue(Files.isRegularFile(this.sqliteDatabase));
    }

    @Test
    public void existingRecordWinsEvenIfSqliteIsEmpty() throws Exception {
        createH2Database(this.h2Base);
        Files.createFile(this.sqliteDatabase);
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        Files.writeString(recordFile, "existing-record");

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertEquals(0L, Files.size(this.sqliteDatabase));
        assertEquals("existing-record", Files.readString(recordFile));
    }

    @Test
    public void existingRecordWinsEvenIfSqliteIsMissing() throws Exception {
        createH2Database(this.h2Base);
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        Files.writeString(recordFile, "existing-record");

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertTrue(Files.notExists(this.sqliteDatabase));
        assertEquals("existing-record", Files.readString(recordFile));
    }

    @Test
    public void missingRecordForNonEmptySqliteIsRegenerated() throws Exception {
        createH2Database(this.h2Base);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE existing_data (id INTEGER PRIMARY KEY, name TEXT)");
            statement.execute("INSERT INTO existing_data VALUES(1,'kept')");
        }
        String sqliteSha256 = sha256(this.sqliteDatabase);
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertEquals(sqliteSha256, sha256(this.sqliteDatabase));
        assertTrue(Files.isRegularFile(recordFile));
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile)) {
            record.load(input);
        }
        assertEquals("COMPLETED", record.getProperty("status"));
        assertEquals("REGENERATED", record.getProperty("record_origin"));
        assertEquals("NOT_RECORDED", record.getProperty("table_count"));

        H2SqliteMigrator.Result secondResult =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);
        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, secondResult.getStatus());
        assertEquals(sqliteSha256, sha256(this.sqliteDatabase));
    }

    @Test
    public void corruptH2DoesNotBlockRecordRegeneration() throws Exception {
        Files.write(this.h2Database, "not-an-h2-database".getBytes(StandardCharsets.UTF_8));
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE existing_data (id INTEGER PRIMARY KEY, name TEXT)");
            statement.execute("INSERT INTO existing_data VALUES(1,'kept')");
        }
        String sqliteSha256 = sha256(this.sqliteDatabase);

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertEquals(sqliteSha256, sha256(this.sqliteDatabase));
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        assertTrue(Files.isRegularFile(recordFile));
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile)) {
            record.load(input);
        }
        assertEquals("REGENERATED", record.getProperty("record_origin"));
    }

    @Test
    public void stalePendingRecordIsDiscarded() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE existing_data (id INTEGER PRIMARY KEY, name TEXT)");
            statement.execute("INSERT INTO existing_data VALUES(1,'kept')");
        }
        Path pendingFile = H2SqliteMigrator.pendingMigrationRecordFile(this.sqliteDatabase);
        Files.writeString(pendingFile,
                "record_format=H2_TO_SQLITE_V1\n"
                        + "migration_id=H2_TO_SQLITE_V1\n"
                        + "sqlite_size=1\n"
                        + "sqlite_sha256=stale\n");

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.NOT_NEEDED, result.getStatus());
        assertTrue(Files.notExists(pendingFile));
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        assertTrue(Files.isRegularFile(recordFile));
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile)) {
            record.load(input);
        }
        assertEquals("REGENERATED", record.getProperty("record_origin"));
    }

    @Test
    public void stalePendingRecordDoesNotBlockMigrationIntoEmptyTarget() throws Exception {
        createH2Database(this.h2Base);
        Files.createFile(this.sqliteDatabase);
        Path pendingFile = H2SqliteMigrator.pendingMigrationRecordFile(this.sqliteDatabase);
        Files.writeString(pendingFile,
                "record_format=H2_TO_SQLITE_V1\n"
                        + "migration_id=H2_TO_SQLITE_V1\n"
                        + "sqlite_size=1\n"
                        + "sqlite_sha256=stale\n");

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.MIGRATED, result.getStatus());
        assertTrue(Files.notExists(pendingFile));
        assertTrue(Files.isRegularFile(H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase)));
        verifyMigratedData();
    }

    @Test
    public void emptySqliteWithoutRecordContinuesMigration() throws Exception {
        createH2Database(this.h2Base);
        Files.createFile(this.sqliteDatabase);

        H2SqliteMigrator.Result result =
                H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);

        assertEquals(H2SqliteMigrator.Status.MIGRATED, result.getStatus());
        verifyMigratedData();
    }

    @Test
    public void failedMigrationLeavesTargetUntouched() throws Exception {
        createH2Database(this.h2Base);
        Files.write(this.h2Database, "not-an-h2-database".getBytes(StandardCharsets.UTF_8));
        Files.createFile(this.sqliteDatabase);

        H2SqliteMigrator.Result result = H2SqliteMigrator.migrateIfNeeded(this.h2Base, this.sqliteDatabase);
        assertEquals(H2SqliteMigrator.Status.FAILED, result.getStatus());
        assertNotNull(result.getError());
        assertEquals(0L, Files.size(this.sqliteDatabase));
        assertTrue(Files.notExists(H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase)));
        assertTrue(Files.notExists(H2SqliteMigrator.pendingMigrationRecordFile(this.sqliteDatabase)));
        try (Stream<Path> paths = Files.list(this.databaseDir)) {
            assertTrue(paths.noneMatch(path -> path.getFileName().toString().contains(".migration-")));
        }
    }

    private void createH2Database(Path databaseBase) throws Exception {
        String url = "jdbc:h2:file:" + databaseBase + ";DB_CLOSE_DELAY=0;DB_CLOSE_ON_EXIT=FALSE";
        try (Connection connection = DriverManager.getConnection(url, "", "");
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE legacy_values"
                    + " (id BIGINT PRIMARY KEY, name VARCHAR(255), enabled TINYINT,"
                    + " payload VARBINARY(32), created_at TIMESTAMP)");
            statement.execute("CREATE UNIQUE INDEX legacy_name_idx ON legacy_values(name)");
            String insertSql = "INSERT INTO legacy_values"
                    + " (id,name,enabled,payload,created_at) VALUES(?,?,?,?,?)";
            try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                insert.setLong(1, 1L);
                insert.setString(2, "kept");
                insert.setInt(3, 1);
                insert.setBytes(4, new byte[]{1, 2, 3});
                insert.setTimestamp(5, Timestamp.valueOf("2026-10-23 10:20:30"));
                insert.executeUpdate();

                insert.setLong(1, 2L);
                insert.setString(2, "also-kept");
                insert.setInt(3, 0);
                insert.setBytes(4, new byte[]{4, 5});
                insert.setTimestamp(5, Timestamp.valueOf("2026-10-23 11:22:33"));
                insert.executeUpdate();
            }
        }
    }

    private void verifyMigratedData() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.sqliteDatabase);
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT id,name,enabled,payload,created_at FROM legacy_values ORDER BY id")) {
                assertTrue(resultSet.next());
                assertEquals(1L, resultSet.getLong("id"));
                assertEquals("kept", resultSet.getString("name"));
                assertEquals(1, resultSet.getInt("enabled"));
                assertArrayEquals(new byte[]{1, 2, 3}, resultSet.getBytes("payload"));
                assertNotNull(resultSet.getString("created_at"));
                assertTrue(resultSet.next());
                assertEquals("also-kept", resultSet.getString("name"));
            }
            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT COUNT(*) FROM sqlite_master WHERE name='_jdbc_migration_record'")) {
                assertTrue(resultSet.next());
                assertEquals(0L, resultSet.getLong(1));
            }
            try (ResultSet resultSet = statement.executeQuery("PRAGMA integrity_check")) {
                assertTrue(resultSet.next());
                assertEquals("ok", resultSet.getString(1));
            }
            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT COUNT(*) FROM sqlite_master WHERE type='index'"
                            + " AND lower(name)='legacy_name_idx' AND upper(sql) LIKE 'CREATE UNIQUE INDEX%'")) {
                assertTrue(resultSet.next());
                assertEquals(1L, resultSet.getLong(1));
            }
        }
        Path recordFile = H2SqliteMigrator.migrationRecordFile(this.sqliteDatabase);
        assertTrue(Files.isRegularFile(recordFile));
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile)) {
            record.load(input);
        }
        assertEquals("H2_TO_SQLITE_V1", record.getProperty("record_format"));
        assertEquals("H2_TO_SQLITE_V1", record.getProperty("migration_id"));
        assertEquals("COMPLETED", record.getProperty("status"));
        assertEquals("1", record.getProperty("table_count"));
        assertEquals("2", record.getProperty("row_count"));
        assertEquals(sha256(this.h2Database), record.getProperty("source_sha256"));
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
