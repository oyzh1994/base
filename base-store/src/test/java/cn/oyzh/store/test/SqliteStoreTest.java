package cn.oyzh.store.test;

import cn.oyzh.store.jdbc.Column;
import cn.oyzh.store.jdbc.ColumnDefinition;
import cn.oyzh.store.jdbc.JdbcConn;
import cn.oyzh.store.jdbc.JdbcConst;
import cn.oyzh.store.jdbc.JdbcDialect;
import cn.oyzh.store.jdbc.JdbcHelper;
import cn.oyzh.store.jdbc.JdbcKeyValueStore;
import cn.oyzh.store.jdbc.JdbcManager;
import cn.oyzh.store.jdbc.JdbcResultSet;
import cn.oyzh.store.jdbc.JdbcStandardStore;
import cn.oyzh.store.jdbc.PrimaryKey;
import cn.oyzh.store.jdbc.Table;
import cn.oyzh.store.jdbc.TableDefinition;
import cn.oyzh.store.jdbc.param.DeleteParam;
import cn.oyzh.store.jdbc.param.OrderByParam;
import cn.oyzh.store.jdbc.param.PageParam;
import cn.oyzh.store.jdbc.param.QueryParam;
import cn.oyzh.store.jdbc.param.QueryParams;
import cn.oyzh.store.jdbc.param.SelectParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 测试基于SQLite的JdbcStore建表、迁移、增删改查与键值存储能力。
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SqliteStoreTest {

    private Path databaseDir;
    private Path databaseFile;

    @Before
    public void setUp() throws IOException {
        JdbcManager.destroy();
        this.databaseDir = Files.createTempDirectory("base-store-sqlite-");
        this.databaseFile = this.databaseDir.resolve("test.db");
        JdbcConst.dbFile(this.databaseFile.toString());
        JdbcConst.dbDialect(JdbcDialect.SQLITE);
        JdbcManager.dialect = JdbcDialect.SQLITE;
    }

    @After
    public void tearDown() throws IOException {
        JdbcManager.destroy();
        try (Stream<Path> paths = Files.walk(this.databaseDir)) {
            paths.sorted(Comparator.reverseOrder()).forEach(this::deleteQuietly);
        }
    }

    @Test
    public void standardStoreSupportsCrudQueriesAndReservedColumns() {
        UserStore store = new UserStore();
        User alice = new User(1L, "Alice", "desc's", true, new byte[]{1, 2, 3});
        User bob = new User(2L, "Bob", "remove", true, new byte[]{4});
        assertTrue(store.insert(alice));
        assertTrue(store.insert(bob));

        User selected = store.selectOne(1L);
        assertNotNull(selected);
        assertEquals("Alice", selected.getName());
        assertEquals("desc's", selected.getDescription());
        assertTrue(selected.isActive());
        assertArrayEquals(new byte[]{1, 2, 3}, selected.getPayload());

        alice.setName("Alicia");
        alice.setDescription("updated");
        assertTrue(store.update(alice));
        assertEquals("Alicia", store.selectOne(QueryParam.of("desc", "updated")).getName());
        assertTrue(store.exist(Map.of("desc", "updated")));

        QueryParams activeParams = new QueryParams();
        activeParams.add("active", true);
        assertEquals(1L, store.selectCount("Ali", List.of("name", "desc"), activeParams));

        PageParam pageParam = new PageParam(1, 0);
        pageParam.addQueryParam(QueryParam.of("active", true));
        assertEquals("Alicia", store.selectPage("Ali", List.of("name", "desc"), pageParam).get(0).getName());

        SelectParam selectParam = new SelectParam();
        selectParam.addOrderByParam(new OrderByParam("id", "desc"));
        selectParam.setLimit(1L);
        assertEquals(2L, store.selectList(selectParam).get(0).getId());

        DeleteParam deleteParam = new DeleteParam();
        deleteParam.addQueryParam(QueryParam.of("desc", "remove"));
        deleteParam.setLimit(1L);
        assertTrue(store.delete(deleteParam));
        assertEquals(1L, store.selectCount(List.of(QueryParam.of("active", true))));
        assertTrue(store.clear());
        assertEquals(0L, store.selectCount(List.of()));
    }

    @Test
    public void standardStoreRebuildsChangedSchemaAndKeepsCommonData() throws Exception {
        LegacyMigrationStore legacyStore = new LegacyMigrationStore();
        MigrationRecord legacyRecord = new MigrationRecord();
        legacyRecord.id = 7L;
        legacyRecord.name = "kept";
        legacyRecord.obsolete = "drop-me";
        assertTrue(legacyStore.insert(legacyRecord));

        ExtendedMigrationStore extendedStore = new ExtendedMigrationStore();
        MigrationRecord migrated = extendedStore.selectOne(7L);
        assertNotNull(migrated);
        assertEquals("kept", migrated.name);
        assertEquals(0L, migrated.count);

        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection,
                    "SELECT COUNT(*) FROM sqlite_master WHERE name LIKE 'app_migration_backup_%'");
            assertTrue(resultSet.next());
            assertEquals(0L, resultSet.getLong(1));
            resultSet.close();
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    @Test
    public void keyValueStoreReplacesAndClearsData() {
        TestKeyValueStore store = new TestKeyValueStore();
        HashMap<String, Object> values = new HashMap<>();
        values.put("theme", "dark");
        values.put("fontSize", 14);
        assertTrue(store.update(values));
        assertEquals("dark", store.select().get("theme"));
        assertEquals("14", String.valueOf(store.select().get("fontSize")));

        HashMap<String, Object> replacement = new HashMap<>();
        replacement.put("theme", "light");
        assertTrue(store.update(replacement));
        assertEquals(1, store.select().size());
        assertTrue(store.clear());
        assertTrue(store.select().isEmpty());
    }

    @Test
    public void standardStoreRoundTripsDateAndTimeValues() {
        TemporalStore store = new TemporalStore();
        LocalDateTime createdAt = LocalDateTime.of(2026, 10, 21, 16, 30, 15, 123_000_000);
        LocalDate birthday = LocalDate.of(1994, 5, 20);
        LocalTime alarm = LocalTime.of(7, 30, 10);
        ZonedDateTime zonedAt = ZonedDateTime.of(2026, 10, 21, 8, 0, 0, 0, ZoneId.of("Asia/Shanghai"));
        TemporalRecord record = new TemporalRecord(1L, createdAt, birthday, alarm, zonedAt);
        assertTrue(store.insert(record));

        TemporalRecord selected = store.selectOne(1L);
        assertNotNull(selected);
        assertEquals(createdAt, selected.createdAt);
        assertEquals(birthday, selected.birthday);
        assertEquals(alarm, selected.alarm);
        assertEquals(zonedAt, selected.zonedAt);
        assertEquals(1L, store.selectOne(QueryParam.of("created_at", createdAt)).id);
    }

    private void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }

    @Table("app_users")
    public static class User implements Serializable {

        @PrimaryKey(autoGeneration = false)
        @Column("id")
        private long id;

        @Column("name")
        private String name;

        @Column("desc")
        private String description;

        @Column("active")
        private boolean active;

        @Column("payload")
        private byte[] payload;

        public User() {
        }

        public User(long id, String name, String description, boolean active, byte[] payload) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.active = active;
            this.payload = payload;
        }

        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isActive() {
            return this.active;
        }

        public byte[] getPayload() {
            return this.payload;
        }
    }

    public static class UserStore extends JdbcStandardStore<User> {

        @Override
        protected Class<User> modelClass() {
            return User.class;
        }
    }

    @Table("app_temporal_values")
    public static class TemporalRecord implements Serializable {

        @PrimaryKey(autoGeneration = false)
        @Column("id")
        private long id;

        @Column("created_at")
        private LocalDateTime createdAt;

        @Column("birthday")
        private LocalDate birthday;

        @Column("alarm")
        private LocalTime alarm;

        @Column("zoned_at")
        private ZonedDateTime zonedAt;

        public TemporalRecord() {
        }

        public TemporalRecord(long id, LocalDateTime createdAt, LocalDate birthday,
                              LocalTime alarm, ZonedDateTime zonedAt) {
            this.id = id;
            this.createdAt = createdAt;
            this.birthday = birthday;
            this.alarm = alarm;
            this.zonedAt = zonedAt;
        }
    }

    public static class TemporalStore extends JdbcStandardStore<TemporalRecord> {

        @Override
        protected Class<TemporalRecord> modelClass() {
            return TemporalRecord.class;
        }
    }

    @Table("app_migration")
    public static class MigrationRecord implements Serializable {

        @PrimaryKey(autoGeneration = false)
        @Column("id")
        private long id;

        @Column("name")
        private String name;

        @Column("obsolete")
        private String obsolete;

        @Column("count")
        private long count;
    }

    public static class LegacyMigrationStore extends JdbcStandardStore<MigrationRecord> {

        @Override
        protected Class<MigrationRecord> modelClass() {
            return MigrationRecord.class;
        }

        @Override
        protected TableDefinition tableDefinition() {
            TableDefinition definition = new TableDefinition();
            definition.setTableName("app_migration");
            definition.setColumns(List.of(
                    column("id", "INTEGER", true),
                    column("name", "TEXT", false),
                    column("obsolete", "TEXT", false)
            ));
            return definition;
        }
    }

    public static class ExtendedMigrationStore extends JdbcStandardStore<MigrationRecord> {

        @Override
        protected Class<MigrationRecord> modelClass() {
            return MigrationRecord.class;
        }

        @Override
        protected TableDefinition tableDefinition() {
            TableDefinition definition = new TableDefinition();
            definition.setTableName("app_migration");
            definition.setColumns(List.of(
                    column("id", "INTEGER", true),
                    column("name", "TEXT", false),
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

    public static class TestKeyValueStore extends JdbcKeyValueStore<HashMap<String, Object>> {

        @Override
        protected Class<HashMap<String, Object>> modelClass() {
            return (Class<HashMap<String, Object>>) (Class<?>) HashMap.class;
        }

        @Override
        protected TableDefinition tableDefinition() {
            TableDefinition definition = new TableDefinition();
            definition.setTableName("app_settings");
            return definition;
        }

        @Override
        protected HashMap<String, Object> toRecord(HashMap<String, Object> model) {
            return model;
        }

        @Override
        protected HashMap<String, Object> toModel(Map<String, Object> record) {
            return new HashMap<>(record);
        }
    }
}
