package cn.oyzh.store.sqlite;

import cn.oyzh.store.jdbc.JdbcKeyValueOperator;
import cn.oyzh.store.jdbc.TableDefinition;

public class SqliteKeyValueOperator extends JdbcKeyValueOperator {
    public SqliteKeyValueOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }
}
