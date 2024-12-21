package cn.oyzh.store.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oyzh
 * @since 2024-09-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKeyColumn {

    private String columnName;

    private Object columnData;

}
