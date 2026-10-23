package cn.oyzh.store.jdbc.migration;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.store.jdbc.sqlite.SqliteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * H2数据库一次性迁移至SQLite。
 *
 * @author oyzh
 * @since 2026-10-23
 */
public final class H2SqliteMigrator {

    /**
     * 迁移标识
     */
    private static final String MIGRATION_ID = "H2_TO_SQLITE_V1";

    /**
     * 迁移记录格式
     */
    private static final String RECORD_FORMAT = "H2_TO_SQLITE_V1";

    /**
     * H2数据库后缀
     */
    private static final List<String> H2_SUFFIXES = List.of(".mv.db", ".h2.db");

    /**
     * 构造函数
     */
    private H2SqliteMigrator() {
    }

    /**
     * 检测旧H2数据并在需要时迁移至SQLite。
     *
     * @param h2DatabaseBase H2数据库基础路径
     * @param sqliteDatabase SQLite数据库文件
     * @return 迁移结果
     */
    public static Result migrateIfNeeded(Path h2DatabaseBase, Path sqliteDatabase) {
        Path recordFile = migrationRecordFile(sqliteDatabase);
        if (migrationRecordExists(recordFile)) {
            return Result.notNeeded(null, sqliteDatabase);
        }
        if (recoverPendingRecord(sqliteDatabase, recordFile)) {
            return Result.notNeeded(null, sqliteDatabase);
        }
        if (isNonEmptyFile(sqliteDatabase)) {
            Path h2Database = findH2Database(h2DatabaseBase);
            try {
                regenerateRecord(recordFile, h2Database, sqliteDatabase);
                return Result.notNeeded(h2Database, sqliteDatabase);
            } catch (Exception ex) {
                JulLog.error("H2 to SQLite migration record recovery failed, source:" + h2Database
                        + ", target:" + sqliteDatabase + ", error:" + ex.getMessage(), ex);
                return Result.failed(h2Database, sqliteDatabase, ex);
            }
        }
        Path h2Database = findH2Database(h2DatabaseBase);
        if (h2Database == null) {
            return Result.notNeeded(null, sqliteDatabase);
        }
        try {
            return Result.migrated(h2Database, sqliteDatabase, migrate(h2Database, sqliteDatabase));
        } catch (Exception ex) {
            JulLog.error("H2 to SQLite migration failed, source:" + h2Database
                    + ", target:" + sqliteDatabase + ", error:" + ex.getMessage(), ex);
            return Result.failed(h2Database, sqliteDatabase, ex);
        }
    }

    /**
     * 将H2数据库迁移至SQLite。
     *
     * @param h2Database    H2数据库文件
     * @param sqliteDatabase SQLite数据库文件
     * @return 迁移统计
     * @throws Exception 迁移异常
     */
    public static MigrationStat migrate(Path h2Database, Path sqliteDatabase) throws Exception {
        Path source = h2Database.toAbsolutePath().normalize();
        Path target = sqliteDatabase.toAbsolutePath().normalize();
        if (!Files.isRegularFile(source)) {
            throw new IOException("H2 database does not exist: " + source);
        }
        if (source.equals(target)) {
            throw new IllegalArgumentException("H2 and SQLite database files must be different");
        }
        Path recordFile = migrationRecordFile(target);
        if (migrationRecordExists(recordFile)) {
            throw new IllegalStateException("SQLite migration record already exists: " + recordFile);
        }
        Path parent = target.getParent();
        if (parent == null) {
            throw new IOException("SQLite database must have a parent directory: " + target);
        }
        Files.createDirectories(parent);

        FileFingerprint before = FileFingerprint.read(source);
        Path temp = Files.createTempFile(parent, target.getFileName() + ".migration-", ".tmp");
        Path pendingRecord = pendingMigrationRecordFile(target);
        boolean moved = false;
        boolean recordCompleted = false;
        JulLog.info("H2 to SQLite migration started, source:{}, target:{}", source, target);
        try {
            MigrationStat stat = migrateToTemp(source, temp);
            FileFingerprint after = FileFingerprint.read(source);
            if (!before.equals(after)) {
                throw new IOException("H2 source changed during migration: " + source);
            }
            force(temp);
            writeRecord(pendingRecord, source, target, temp, stat);
            moveAtomically(temp, target);
            moved = true;
            moveAtomically(pendingRecord, recordFile);
            recordCompleted = true;
            forceDirectory(parent);
            JulLog.info("H2 to SQLite migration completed, source:{}, target:{}, tables:{}, rows:{}",
                    source, target, stat.getTableCount(), stat.getRowCount());
            return stat;
        } finally {
            if (!moved || !recordCompleted) {
                deleteQuietly(temp);
                deleteQuietly(Path.of(temp + "-journal"));
                deleteQuietly(Path.of(temp + "-wal"));
                deleteQuietly(Path.of(temp + "-shm"));
                if (!moved) {
                    deleteQuietly(pendingRecord);
                }
            }
        }
    }

    /**
     * 迁移数据至临时SQLite文件。
     *
     * @param source H2数据库文件
     * @param temp   SQLite临时文件
     * @return 迁移统计
     * @throws Exception 迁移异常
     */
    private static MigrationStat migrateToTemp(Path source, Path temp) throws Exception {
        Path sourceBase = h2DatabaseBase(source);
        String sourceUrl = "jdbc:h2:file:" + sourceBase + ";IFEXISTS=TRUE;ACCESS_MODE_DATA=r;DB_CLOSE_ON_EXIT=FALSE";
        String targetUrl = "jdbc:sqlite:" + temp;
        try (Connection sourceConnection = DriverManager.getConnection(sourceUrl, "", "");
             Connection targetConnection = DriverManager.getConnection(targetUrl)) {
            sourceConnection.setReadOnly(true);
            targetConnection.setAutoCommit(false);
            execute(targetConnection, "PRAGMA journal_mode=DELETE");
            execute(targetConnection, "PRAGMA foreign_keys=ON");

            String h2Version = readH2Version(sourceConnection);
            List<TableSchema> tables = readTables(sourceConnection);
            long rowCount = 0L;
            for (TableSchema table : tables) {
                createTable(targetConnection, table);
                rowCount += copyRows(sourceConnection, targetConnection, table);
                copyIndexes(targetConnection, table);
            }
            targetConnection.commit();

            verifyTarget(targetConnection, tables, rowCount);
            return new MigrationStat(tables.size(), rowCount, h2Version);
        }
    }

    /**
     * 获取H2版本。
     *
     * @param connection H2连接
     * @return H2版本
     * @throws SQLException sql异常
     */
    private static String readH2Version(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT H2VERSION()")) {
            return resultSet.next() ? resultSet.getString(1) : "";
        }
    }

    /**
     * 读取待迁移的表结构。
     *
     * @param connection H2连接
     * @return 表结构列表
     * @throws SQLException sql异常
     */
    private static List<TableSchema> readTables(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        List<TableSchema> tables = new ArrayList<>();
        try (ResultSet resultSet = metaData.getTables(connection.getCatalog(), "PUBLIC", "%", new String[]{"TABLE"})) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tables.add(readTable(metaData, connection.getCatalog(), tableName));
            }
        }
        tables.sort(Comparator.comparing(table -> table.name.toLowerCase(Locale.ROOT)));
        return tables;
    }

    /**
     * 读取单表结构。
     *
     * @param metaData 数据库元数据
     * @param catalog  数据库目录
     * @param tableName 表名称
     * @return 表结构
     * @throws SQLException sql异常
     */
    private static TableSchema readTable(DatabaseMetaData metaData, String catalog,
                                         String tableName) throws SQLException {
        Map<String, ColumnSchema> columns = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        try (ResultSet resultSet = metaData.getColumns(catalog, "PUBLIC", tableName, null)) {
            while (resultSet.next()) {
                ColumnSchema column = new ColumnSchema(
                        resultSet.getString("COLUMN_NAME"),
                        toSqliteType(resultSet.getInt("DATA_TYPE"), resultSet.getString("TYPE_NAME")),
                        resultSet.getInt("NULLABLE") != DatabaseMetaData.columnNullable
                );
                columns.put(column.name, column);
            }
        }
        if (columns.isEmpty()) {
            throw new SQLException("H2 table has no columns: " + tableName);
        }

        Map<Short, String> primaryKeys = new TreeMap<>();
        try (ResultSet resultSet = metaData.getPrimaryKeys(catalog, "PUBLIC", tableName)) {
            while (resultSet.next()) {
                primaryKeys.put(resultSet.getShort("KEY_SEQ"), resultSet.getString("COLUMN_NAME"));
            }
        }
        return new TableSchema(tableName, new ArrayList<>(columns.values()),
                new ArrayList<>(primaryKeys.values()),
                readIndexes(metaData, catalog, tableName, new LinkedHashSet<>(primaryKeys.values())));
    }

    /**
     * 读取二级索引。
     *
     * @param metaData   数据库元数据
     * @param catalog    数据库目录
     * @param tableName  表名称
     * @param primaryKeys 主键字段
     * @return 索引列表
     * @throws SQLException sql异常
     */
    private static List<IndexSchema> readIndexes(DatabaseMetaData metaData, String catalog,
                                                  String tableName, Set<String> primaryKeys) throws SQLException {
        Map<String, IndexSchema> indexes = new TreeMap<>();
        Set<String> primaryKeyNames = lowerCaseSet(primaryKeys);
        try (ResultSet resultSet = metaData.getIndexInfo(catalog, "PUBLIC", tableName, false, false)) {
            while (resultSet.next()) {
                short type = resultSet.getShort("TYPE");
                if (type == DatabaseMetaData.tableIndexStatistic) {
                    continue;
                }
                String indexName = resultSet.getString("INDEX_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                if (indexName == null || columnName == null) {
                    continue;
                }
                boolean nonUnique = resultSet.getBoolean("NON_UNIQUE");
                IndexSchema index = indexes.computeIfAbsent(indexName.toLowerCase(Locale.ROOT),
                        key -> new IndexSchema(indexName, nonUnique));
                index.addColumn(resultSet.getShort("ORDINAL_POSITION"), columnName);
            }
        }
        indexes.values().removeIf(index -> lowerCaseSet(index.columns).equals(primaryKeyNames));
        return new ArrayList<>(indexes.values());
    }

    /**
     * 创建SQLite表。
     *
     * @param connection SQLite连接
     * @param table      表结构
     * @throws SQLException sql异常
     */
    private static void createTable(Connection connection, TableSchema table) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(SqliteUtil.wrap(table.name)).append(" (");
        for (ColumnSchema column : table.columns) {
            sql.append(SqliteUtil.wrap(column.name)).append(" ").append(column.type);
            if (column.notNull) {
                sql.append(" NOT NULL");
            }
            sql.append(",");
        }
        if (!table.primaryKeys.isEmpty()) {
            sql.append("PRIMARY KEY (");
            for (String column : table.primaryKeys) {
                sql.append(SqliteUtil.wrap(column)).append(",");
            }
            sql.deleteCharAt(sql.length() - 1).append("),");
        }
        sql.deleteCharAt(sql.length() - 1).append(")");
        execute(connection, sql.toString());
    }

    /**
     * 复制表数据。
     *
     * @param sourceConnection H2连接
     * @param targetConnection SQLite连接
     * @param table            表结构
     * @return 复制行数
     * @throws Exception 迁移异常
     */
    private static long copyRows(Connection sourceConnection, Connection targetConnection,
                                 TableSchema table) throws Exception {
        List<String> columnNames = new ArrayList<>();
        for (ColumnSchema column : table.columns) {
            columnNames.add(column.name);
        }
        String columns = quoteColumns(columnNames);
        String selectSql = "SELECT " + columns + " FROM " + SqliteUtil.wrap(table.name);
        String insertSql = "INSERT INTO " + SqliteUtil.wrap(table.name)
                + " (" + columns + ") VALUES (" + "?,".repeat(columnNames.size());
        insertSql = insertSql.substring(0, insertSql.length() - 1) + ")";

        long copied = 0L;
        try (PreparedStatement sourceStatement = sourceConnection.prepareStatement(selectSql);
             ResultSet resultSet = sourceStatement.executeQuery();
             PreparedStatement targetStatement = targetConnection.prepareStatement(insertSql)) {
            int batchCount = 0;
            while (resultSet.next()) {
                for (int index = 0; index < columnNames.size(); index++) {
                    targetStatement.setObject(index + 1, toSqliteValue(resultSet.getObject(index + 1)));
                }
                targetStatement.addBatch();
                copied++;
                if (++batchCount == 500) {
                    targetStatement.executeBatch();
                    batchCount = 0;
                }
            }
            if (batchCount > 0) {
                targetStatement.executeBatch();
            }
        }
        return copied;
    }

    /**
     * 创建SQLite二级索引。
     *
     * @param targetConnection SQLite连接
     * @param table            表结构
     * @throws SQLException sql异常
     */
    private static void copyIndexes(Connection targetConnection, TableSchema table) throws SQLException {
        Set<String> usedNames = new LinkedHashSet<>();
        for (IndexSchema index : table.indexes) {
            String indexName = index.name;
            if (!usedNames.add(indexName.toLowerCase(Locale.ROOT))) {
                indexName = table.name + "_" + indexName;
            }
            String sql = "CREATE " + (index.unique ? "UNIQUE " : "") + "INDEX "
                    + SqliteUtil.wrap(indexName) + " ON " + SqliteUtil.wrap(table.name)
                    + " (" + quoteColumns(new ArrayList<>(index.columns)) + ")";
            execute(targetConnection, sql);
        }
    }

    /**
     * 校验迁移后的SQLite数据库。
     *
     * @param connection SQLite连接
     * @param tables     源表结构
     * @param rowCount   源数据行数
     * @throws SQLException sql异常
     */
    private static void verifyTarget(Connection connection, List<TableSchema> tables,
                                     long rowCount) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("PRAGMA integrity_check")) {
            if (!resultSet.next() || !"ok".equalsIgnoreCase(resultSet.getString(1))) {
                throw new SQLException("SQLite integrity check failed");
            }
        }
        long actualRows = 0L;
        for (TableSchema table : tables) {
            actualRows += countRows(connection, table.name);
        }
        if (actualRows != rowCount) {
            throw new SQLException("SQLite row count mismatch, expected:" + rowCount + ", actual:" + actualRows);
        }
    }

    /**
     * 获取SQLite迁移记录文件。
     *
     * @param sqliteDatabase SQLite数据库文件
     * @return 迁移记录文件
     */
    public static Path migrationRecordFile(Path sqliteDatabase) {
        return Path.of(sqliteDatabase.toAbsolutePath().normalize() + ".migration.properties");
    }

    /**
     * 获取SQLite待完成迁移记录文件。
     *
     * @param sqliteDatabase SQLite数据库文件
     * @return 待完成迁移记录文件
     */
    public static Path pendingMigrationRecordFile(Path sqliteDatabase) {
        return Path.of(sqliteDatabase.toAbsolutePath().normalize() + ".migration.pending");
    }

    /**
     * 判断迁移记录是否已经完成。
     *
     * @param recordFile 迁移记录文件
     * @return 结果
     */
    private static boolean migrationRecordExists(Path recordFile) {
        return Files.isRegularFile(recordFile);
    }

    /**
     * 恢复数据库已替换但记录尚未发布的迁移。
     *
     * @param sqliteDatabase SQLite数据库文件
     * @param recordFile     迁移记录文件
     * @return 是否恢复成功
     */
    private static boolean recoverPendingRecord(Path sqliteDatabase, Path recordFile) {
        Path pendingFile = pendingMigrationRecordFile(sqliteDatabase);
        if (!Files.isRegularFile(pendingFile)) {
            return false;
        }
        try {
            Properties record = readRecord(pendingFile);
            FileFingerprint target = FileFingerprint.read(sqliteDatabase);
            boolean completed = RECORD_FORMAT.equals(record.getProperty("record_format"))
                    && MIGRATION_ID.equals(record.getProperty("migration_id"))
                    && String.valueOf(target.size).equals(record.getProperty("sqlite_size"))
                    && target.sha256.equals(record.getProperty("sqlite_sha256"));
            if (!completed) {
                JulLog.warn("Stale H2 to SQLite pending migration record discarded, file:{}", pendingFile);
                deleteQuietly(pendingFile);
                return false;
            }
            moveAtomically(pendingFile, recordFile);
            forceDirectory(recordFile.getParent());
            JulLog.info("H2 to SQLite pending migration record recovered, file:{}", recordFile);
            return true;
        } catch (Exception ex) {
            JulLog.warn("H2 to SQLite pending migration record recovery failed, file:{}, error:{}",
                    pendingFile, ex.getMessage());
            deleteQuietly(pendingFile);
            return false;
        }
    }

    /**
     * 写入待发布的迁移记录。
     *
     * @param pendingFile 待发布迁移记录文件
     * @param source      H2数据库文件
     * @param target      SQLite数据库文件
     * @param temp        SQLite临时文件
     * @param stat        迁移统计
     * @throws Exception 迁移异常
     */
    private static void writeRecord(Path pendingFile, Path source, Path target,
                                    Path temp, MigrationStat stat) throws Exception {
        FileFingerprint sourceFingerprint = FileFingerprint.read(source);
        FileFingerprint targetFingerprint = FileFingerprint.read(temp);
        Properties record = completedRecord();
        record.setProperty("record_origin", "MIGRATED");
        record.setProperty("source_file", source.toString());
        record.setProperty("source_size", String.valueOf(sourceFingerprint.size));
        record.setProperty("source_modified_millis", String.valueOf(sourceFingerprint.modifiedMillis));
        record.setProperty("source_sha256", sourceFingerprint.sha256);
        record.setProperty("h2_version", stat.h2Version);
        record.setProperty("sqlite_file", target.getFileName().toString());
        record.setProperty("sqlite_size", String.valueOf(targetFingerprint.size));
        record.setProperty("sqlite_sha256", targetFingerprint.sha256);
        record.setProperty("table_count", String.valueOf(stat.tableCount));
        record.setProperty("row_count", String.valueOf(stat.rowCount));
        record.setProperty("migrated_at", Instant.now().toString());
        writeRecordFile(pendingFile, record);
    }

    /**
     * 重新生成已完成迁移记录。
     *
     * @param recordFile 迁移记录文件
     * @param source     H2数据库文件
     * @param target     SQLite数据库文件
     * @throws Exception 迁移异常
     */
    private static void regenerateRecord(Path recordFile, Path source, Path target) throws Exception {
        Properties record = completedRecord();
        record.setProperty("record_origin", "REGENERATED");
        record.setProperty("source_file", source == null ? "" : source.toString());
        record.setProperty("source_size", source == null ? "NOT_FOUND" : String.valueOf(Files.size(source)));
        record.setProperty("source_modified_millis", source == null ? "NOT_FOUND"
                : String.valueOf(Files.getLastModifiedTime(source).toMillis()));
        record.setProperty("source_sha256", "NOT_RECORDED");
        record.setProperty("h2_version", "NOT_RECORDED");
        record.setProperty("sqlite_file", target.getFileName().toString());
        record.setProperty("sqlite_size", String.valueOf(Files.size(target)));
        record.setProperty("sqlite_sha256", "NOT_RECORDED");
        record.setProperty("table_count", "NOT_RECORDED");
        record.setProperty("row_count", "NOT_RECORDED");
        record.setProperty("migrated_at", Instant.now().toString());
        record.setProperty("recovered_at", Instant.now().toString());
        writeRecordFile(recordFile, record);
        JulLog.info("H2 to SQLite migration record regenerated, file:{}, sqlite:{}", recordFile, target);
    }

    /**
     * 构建已完成迁移记录。
     *
     * @return 迁移记录
     */
    private static Properties completedRecord() {
        Properties record = new Properties();
        record.setProperty("record_format", RECORD_FORMAT);
        record.setProperty("migration_id", MIGRATION_ID);
        record.setProperty("status", "COMPLETED");
        return record;
    }

    /**
     * 原子写入迁移记录文件。
     *
     * @param recordFile 迁移记录文件
     * @param record     迁移记录
     * @throws Exception 迁移异常
     */
    private static void writeRecordFile(Path recordFile, Properties record) throws Exception {
        Path parent = recordFile.getParent();
        Path tempRecord = Files.createTempFile(parent, recordFile.getFileName() + ".", ".tmp");
        boolean moved = false;
        try {
            try (OutputStream output = Files.newOutputStream(tempRecord, StandardOpenOption.WRITE)) {
                record.store(output, "H2 to SQLite migration record");
            }
            force(tempRecord);
            moveAtomically(tempRecord, recordFile);
            moved = true;
        } finally {
            if (!moved) {
                deleteQuietly(tempRecord);
            }
        }
    }

    /**
     * 判断文件是否存在且非空。
     *
     * @param file 文件
     * @return 结果
     */
    private static boolean isNonEmptyFile(Path file) {
        try {
            return Files.isRegularFile(file) && Files.size(file) > 0L;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 读取迁移记录。
     *
     * @param recordFile 迁移记录文件
     * @return 迁移记录
     * @throws IOException io异常
     */
    private static Properties readRecord(Path recordFile) throws IOException {
        if (!Files.isRegularFile(recordFile)) {
            throw new IOException("Migration record does not exist: " + recordFile);
        }
        Properties record = new Properties();
        try (InputStream input = Files.newInputStream(recordFile, StandardOpenOption.READ)) {
            record.load(input);
        }
        return record;
    }

    /**
     * 查找H2数据库文件。
     *
     * @param h2DatabaseBase H2数据库基础路径
     * @return H2数据库文件
     */
    private static Path findH2Database(Path h2DatabaseBase) {
        Path base = h2DatabaseBase.toAbsolutePath().normalize();
        for (String suffix : H2_SUFFIXES) {
            Path database = Path.of(base + suffix);
            if (Files.isRegularFile(database)) {
                return database;
            }
        }
        return null;
    }

    /**
     * 获取H2数据库基础路径。
     *
     * @param h2Database H2数据库文件
     * @return H2数据库基础路径
     */
    private static Path h2DatabaseBase(Path h2Database) {
        String fileName = h2Database.toString();
        for (String suffix : H2_SUFFIXES) {
            if (fileName.endsWith(suffix)) {
                return Path.of(fileName.substring(0, fileName.length() - suffix.length()));
            }
        }
        return h2Database;
    }

    /**
     * 将H2数据类型转换为SQLite类型亲和性。
     *
     * @param jdbcType JDBC类型
     * @param typeName H2类型名称
     * @return SQLite类型
     */
    private static String toSqliteType(int jdbcType, String typeName) {
        return switch (jdbcType) {
            case java.sql.Types.BOOLEAN, java.sql.Types.BIT, java.sql.Types.TINYINT,
                 java.sql.Types.SMALLINT, java.sql.Types.INTEGER, java.sql.Types.BIGINT -> "INTEGER";
            case java.sql.Types.REAL, java.sql.Types.FLOAT, java.sql.Types.DOUBLE,
                 java.sql.Types.DECIMAL, java.sql.Types.NUMERIC -> "REAL";
            case java.sql.Types.BINARY, java.sql.Types.VARBINARY,
                 java.sql.Types.LONGVARBINARY, java.sql.Types.BLOB -> "BLOB";
            default -> normalizeTypeAffinity(typeName);
        };
    }

    /**
     * 根据类型名称获取SQLite类型亲和性。
     *
     * @param typeName 类型名称
     * @return SQLite类型
     */
    private static String normalizeTypeAffinity(String typeName) {
        String type = typeName == null ? "" : typeName.toUpperCase(Locale.ROOT);
        if (type.contains("BLOB") || type.contains("BINARY")) {
            return "BLOB";
        }
        if (type.contains("INT") || type.contains("BOOL")) {
            return "INTEGER";
        }
        if (type.contains("REAL") || type.contains("FLOA") || type.contains("DOUB") || type.contains("DEC")) {
            return "REAL";
        }
        return "TEXT";
    }

    /**
     * 转换为SQLite驱动可写入的值。
     *
     * @param value H2数据值
     * @return SQLite数据值
     * @throws SQLException sql异常
     */
    private static Object toSqliteValue(Object value) throws SQLException {
        if (value == null || value instanceof Number || value instanceof byte[]
                || value instanceof CharSequence) {
            return value;
        }
        if (value instanceof Blob blob) {
            try {
                return blob.getBytes(1L, Math.toIntExact(blob.length()));
            } finally {
                blob.free();
            }
        }
        if (value instanceof Clob clob) {
            try {
                return clob.getSubString(1L, Math.toIntExact(clob.length()));
            } finally {
                clob.free();
            }
        }
        if (value instanceof Byte[] bytes) {
            byte[] result = new byte[bytes.length];
            for (int index = 0; index < bytes.length; index++) {
                result[index] = bytes[index];
            }
            return result;
        }
        return SqliteUtil.toSqlValue(value);
    }

    /**
     * 统计表行数。
     *
     * @param connection 数据库连接
     * @param tableName  表名称
     * @return 行数
     * @throws SQLException sql异常
     */
    private static long countRows(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + SqliteUtil.wrap(tableName))) {
            return resultSet.next() ? resultSet.getLong(1) : 0L;
        }
    }

    /**
     * 执行sql。
     *
     * @param connection 数据库连接
     * @param sql        sql
     * @throws SQLException sql异常
     */
    private static void execute(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * 引用字段列表。
     *
     * @param columns 字段列表
     * @return 字段列表sql
     */
    private static String quoteColumns(List<String> columns) {
        StringBuilder sql = new StringBuilder();
        for (String column : columns) {
            sql.append(SqliteUtil.wrap(column)).append(",");
        }
        return sql.deleteCharAt(sql.length() - 1).toString();
    }

    /**
     * 字段名称转为小写集合。
     *
     * @param names 字段名称
     * @return 小写集合
     */
    private static Set<String> lowerCaseSet(Set<String> names) {
        Set<String> result = new LinkedHashSet<>();
        for (String name : names) {
            result.add(name.toLowerCase(Locale.ROOT));
        }
        return result;
    }

    /**
     * 原子替换目标文件。
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException io异常
     */
    private static void moveAtomically(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException ex) {
            throw new IOException("Atomic SQLite migration is not supported on this filesystem: " + target, ex);
        }
    }

    /**
     * 强制将文件写入磁盘。
     *
     * @param file 文件
     * @throws IOException io异常
     */
    private static void force(Path file) throws IOException {
        try (FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE)) {
            channel.force(true);
        }
    }

    /**
     * 强制将目录写入磁盘。
     *
     * @param directory 目录
     * @throws IOException io异常
     */
    private static void forceDirectory(Path directory) {
        try (FileChannel channel = FileChannel.open(directory, StandardOpenOption.READ)) {
            channel.force(true);
        } catch (Exception ex) {
            // Directory fsync is not supported by all platforms (notably Windows).
            // The data and record files have already been moved atomically at this point.
            JulLog.warn("Directory fsync skipped, directory:{}, reason:{}", directory, ex.getMessage());
        }
    }

    /**
     * 删除文件。
     *
     * @param file 文件
     */
    private static void deleteQuietly(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
        }
    }

    /**
     * H2表结构
     */
    private static final class TableSchema {

        private final String name;

        private final List<ColumnSchema> columns;

        private final List<String> primaryKeys;

        private final List<IndexSchema> indexes;

        private TableSchema(String name, List<ColumnSchema> columns,
                            List<String> primaryKeys, List<IndexSchema> indexes) {
            this.name = name;
            this.columns = columns;
            this.primaryKeys = primaryKeys;
            this.indexes = indexes;
        }
    }

    /**
     * SQLite字段结构
     */
    private static final class ColumnSchema {

        private final String name;

        private final String type;

        private final boolean notNull;

        private ColumnSchema(String name, String type, boolean notNull) {
            this.name = name;
            this.type = type;
            this.notNull = notNull;
        }
    }

    /**
     * SQLite索引结构
     */
    private static final class IndexSchema {

        private final String name;

        private final boolean unique;

        private final Map<Short, String> orderedColumns = new TreeMap<>();

        private Set<String> columns = new LinkedHashSet<>();

        private IndexSchema(String name, boolean nonUnique) {
            this.name = name;
            this.unique = !nonUnique;
        }

        private void addColumn(short ordinal, String column) {
            this.orderedColumns.put(ordinal, column);
            this.columns = new LinkedHashSet<>(this.orderedColumns.values());
        }
    }

    /**
     * H2源文件指纹
     */
    private record FileFingerprint(long size, long modifiedMillis, String sha256) {

        private static FileFingerprint read(Path file) throws IOException {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (Exception ex) {
                throw new IOException("SHA-256 is unavailable", ex);
            }
            try (FileChannel channel = FileChannel.open(file, StandardOpenOption.READ)) {
                ByteBuffer buffer = ByteBuffer.allocate(8192);
                while (channel.read(buffer) > 0) {
                    buffer.flip();
                    digest.update(buffer);
                    buffer.clear();
                }
            }
            StringBuilder sha256 = new StringBuilder();
            for (byte data : digest.digest()) {
                sha256.append(String.format("%02x", data));
            }
            return new FileFingerprint(Files.size(file), Files.getLastModifiedTime(file).toMillis(), sha256.toString());
        }
    }

    /**
     * 迁移统计
     */
    public static final class MigrationStat {

        private final int tableCount;

        private final long rowCount;

        private final String h2Version;

        private MigrationStat(int tableCount, long rowCount, String h2Version) {
            this.tableCount = tableCount;
            this.rowCount = rowCount;
            this.h2Version = h2Version;
        }

        public int getTableCount() {
            return this.tableCount;
        }

        public long getRowCount() {
            return this.rowCount;
        }

        public String getH2Version() {
            return this.h2Version;
        }
    }

    /**
     * 迁移结果
     */
    public static final class Result {

        private final Status status;

        private final Path source;

        private final Path target;

        private final MigrationStat stat;

        private final Exception error;

        private Result(Status status, Path source, Path target, MigrationStat stat, Exception error) {
            this.status = status;
            this.source = source;
            this.target = target;
            this.stat = stat;
            this.error = error;
        }

        private static Result notNeeded(Path source, Path target) {
            return new Result(Status.NOT_NEEDED, source, target, null, null);
        }

        private static Result migrated(Path source, Path target, MigrationStat stat) {
            return new Result(Status.MIGRATED, source, target, stat, null);
        }

        private static Result failed(Path source, Path target, Exception error) {
            return new Result(Status.FAILED, source, target, null, error);
        }

        public Status getStatus() {
            return this.status;
        }

        public Path getSource() {
            return this.source;
        }

        public Path getTarget() {
            return this.target;
        }

        public MigrationStat getStat() {
            return this.stat;
        }

        public Exception getError() {
            return this.error;
        }
    }

    /**
     * 迁移状态
     */
    public enum Status {
        /**
         * 已迁移
         */
        MIGRATED,
        /**
         * 无需迁移
         */
        NOT_NEEDED,
        /**
         * 迁移失败
         */
        FAILED
    }
}
