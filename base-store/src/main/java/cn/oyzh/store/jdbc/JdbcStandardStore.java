package cn.oyzh.store.jdbc;

import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.store.jdbc.h2.H2StandardOperator;
import cn.oyzh.store.jdbc.param.PageParam;
import cn.oyzh.store.jdbc.param.QueryParam;
import cn.oyzh.store.jdbc.param.QueryParams;
import cn.oyzh.store.jdbc.param.SelectParam;
import cn.oyzh.store.jdbc.param.DeleteParam;
//import cn.oyzh.store.jdbc.sqlite.SqliteStandardOperator;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * jdbc标准存储
 *
 * @author oyzh
 * @since 2024-09-23
 */
public abstract class JdbcStandardStore<M extends Serializable> extends JdbcStore<M> {

    /**
     * 标准操作器
     */
    private final JdbcStandardOperator operator;

    /**
     * 构造jdbc标准存储
     */
    public JdbcStandardStore() {
        try {
            TableDefinition tableDefinition = this.tableDefinition();
//            if (JdbcManager.dialect == JdbcDialect.H2) {
                this.operator = new H2StandardOperator(tableDefinition);
//            } else {
//                this.operator = new SqliteStandardOperator(tableDefinition);
//            }
            this.operator.initTable();
            this.init();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected TableDefinition tableDefinition() {
        if (this.operator != null) {
            return this.operator.getTableDefinition();
        }
        return TableDefinition.ofClass(this.modelClass());
    }

    /**
     * 新增模型数据
     *
     * @param model 模型
     * @return 结果
     */
    public boolean insert(M model) {
        if (model != null) {
            try {
                // 处理主键值
                this.tableDefinition().handlePrimaryKeyValue(model);
                return this.operator.insert(this.toRecord(model)) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 更新模型数据
     *
     * @param model 模型
     * @return 结果
     */
    public boolean update(M model) {
        if (model != null) {
            try {
                TableDefinition tableDefinition = this.tableDefinition();
                PrimaryKeyColumn primaryKey = tableDefinition.primaryKeyColumn(model);
                if (primaryKey == null) {
                    return false;
                }
                Map<String, Object> record = this.toRecord(model);
                return this.operator.update(record, primaryKey) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 是否存在指定主键的数据
     *
     * @param primaryKey 主键值
     * @return 结果
     */
    public boolean exist(Object primaryKey) {
        if (primaryKey != null) {
            try {
                return this.operator.exist(primaryKey);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 是否存在符合条件的数据
     *
     * @param params 查询条件
     * @return 结果
     */
    public boolean exist(Map<String, Object> params) {
        try {
            return this.operator.exist(params);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 根据主键查询单个模型
     *
     * @param primaryKey 主键值
     * @return 模型
     */
    public M selectOne(Object primaryKey) {
        try {
            return this.toModel(this.operator.selectOne(primaryKey));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据查询参数查询单个模型
     *
     * @param selectParam 查询参数
     * @return 模型
     */
    public M selectOne(SelectParam selectParam) {
        try {
            return this.toModel(this.operator.selectOne(selectParam));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据查询条件查询单个模型
     *
     * @param queryParam 查询条件
     * @return 模型
     */
    public M selectOne(QueryParam queryParam) {
        try {
            return this.toModel(this.operator.selectOne(queryParam));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 查询全部模型
     *
     * @return 模型列表
     */
    public List<M> selectList() {
        return this.selectList((SelectParam) null);
    }

    /**
     * 根据查询条件查询模型列表
     *
     * @param queryParam 查询条件
     * @return 模型列表
     */
    public List<M> selectList(QueryParam queryParam) {
        SelectParam param = new SelectParam();
        param.addQueryParam(queryParam);
        return this.selectList(param);
    }

    /**
     * 根据查询参数查询模型列表
     *
     * @param param 查询参数
     * @return 模型列表
     */
    public List<M> selectList(SelectParam param) {
        try {
            List<Map<String, Object>> list = this.operator.selectList(param);
            if (CollectionUtil.isNotEmpty(list)) {
                List<M> models = new ArrayList<>();
                for (Map<String, Object> map : list) {
                    models.add(this.toModel(map));
                }
                return models;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据查询条件统计数据条数
     *
     * @param queryParam 查询条件
     * @return 数据条数
     */
    public long selectCount(QueryParam queryParam) {
        try {
            return this.operator.selectCount(List.of(queryParam));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    /**
     * 根据查询条件列表统计数据条数
     *
     * @param params 查询条件列表
     * @return 数据条数
     */
    public long selectCount(List<QueryParam> params) {
        try {
            return this.operator.selectCount(params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    /**
     * 根据关键字统计数据条数
     *
     * @param kw      关键字
     * @param columns 关键字匹配的列
     * @return 数据条数
     */
    public long selectCount(String kw, List<String> columns) {
        return this.selectCount(kw, columns, null);
    }

    /**
     * 根据关键字和查询条件统计数据条数
     *
     * @param kw          关键字
     * @param columns     关键字匹配的列
     * @param queryParams 查询条件
     * @return 数据条数
     */
    public long selectCount(String kw, List<String> columns, QueryParams queryParams) {
        try {
            return this.operator.selectCount(kw, columns, queryParams);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    /**
     * 分页查询模型列表
     *
     * @param kw        关键字
     * @param columns   关键字匹配的列
     * @param pageParam 分页参数
     * @return 模型列表
     */
    public List<M> selectPage(String kw, List<String> columns, PageParam pageParam) {
        try {
            List<Map<String, Object>> list = this.operator.selectPage(kw, columns, pageParam);
            if (CollectionUtil.isNotEmpty(list)) {
                List<M> models = new ArrayList<>();
                for (Map<String, Object> map : list) {
                    models.add(this.toModel(map));
                }
                return models;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 根据模型删除数据
     *
     * @param model 模型
     * @return 结果
     */
    public boolean delete(M model) {
        if (model != null) {
            try {
                Object primaryKeyValue = this.tableDefinition().getPrimaryKeyValue(model);
                return this.operator.delete(primaryKeyValue) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据主键删除数据
     *
     * @param primaryKey 主键值
     * @return 结果
     */
    public boolean delete(Object primaryKey) {
        if (primaryKey != null) {
            try {
                return this.operator.delete(primaryKey) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据删除参数删除数据
     *
     * @param deleteParam 删除参数
     * @return 结果
     */
    public boolean delete(DeleteParam deleteParam) {
        try {
            return this.operator.delete(deleteParam) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 清空全部数据
     *
     * @return 结果
     */
    public boolean clear() {
        return this.delete((DeleteParam) null);
    }

}
