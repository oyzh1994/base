package cn.oyzh.common.dto;

import java.util.Collections;
import java.util.List;

/**
 * 分页信息
 *
 * @param <T> 分页数据类型
 * @author oyzh
 * @since 2020/9/15
 */
public class Paging<T> {

    /**
     * 每页显示数量
     */
    private long limit = 10;

    /**
     * 数据总数
     */
//    @Getter
//    @Accessors(fluent = true, chain = true)
    private long count;

    /**
     * 总页数
     */
//    @Getter
//    @Accessors(fluent = true, chain = true)
    private long countPage;

    /**
     * 当前页
     */
//    @Getter
//    @Accessors(fluent = true, chain = true)
    private long currentPage;

    /**
     * 数据列表
     */
//    @Getter
//    @Accessors(fluent = true, chain = true)
    private List<T> dataList;

    /**
     * 空数据实例
     */
    public static final Paging<Object> EMPTY = new Paging<>(Collections.emptyList());

    /**
     * 构造分页实例
     *
     * @param limit 每页显示数量
     */
    public Paging(long limit) {
        this(Collections.emptyList(), limit);
    }

    /**
     * 构造分页实例
     *
     * @param dataList 数据列表
     */
    public Paging(List<T> dataList) {
        this.dataList(dataList);
    }

    /**
     * 构造分页实例
     *
     * @param dataList 数据列表
     * @param limit    每页显示数量
     */
    public Paging(List<T> dataList, long limit) {
        if (limit <= 0) {
            limit = 10;
        }
        this.limit = limit;
        this.dataList(dataList);
    }

    /**
     * 构造分页实例
     *
     * @param dataList 数据列表
     * @param limit    每页显示数量
     * @param count    数据总数
     */
    public Paging(List<T> dataList, long limit, long count) {
        if (limit <= 0) {
            limit = 10;
        }
        this.limit = limit;
        this.count = count;
        this.dataList = dataList;
        if (this.count <= this.limit) {
            this.countPage = 1;
        } else if (this.count % this.limit == 0) {
            this.countPage = this.count / this.limit;
        } else {
            this.countPage = this.count / this.limit + 1;
        }
        this.currentPage = 0;
    }

    /**
     * 设置数据列表
     *
     * @param dataList 数据列表
     * @return 分页对象
     */
    public Paging<T> dataList(List<T> dataList) {
        if (dataList == null) {
            dataList = Collections.emptyList();
        }
        this.dataList = dataList;
        this.count = this.dataList.size();
        if (this.count <= this.limit) {
            this.countPage = 1;
        } else if (this.count % this.limit == 0) {
            this.countPage = this.count / this.limit;
        } else {
            this.countPage = this.count / this.limit + 1;
        }
        this.currentPage = 0;
        return this;
    }

    /**
     * 首页
     *
     * @return 分页内容
     */
    public List<T> first() {
        this.currentPage = 0;
        return this.pageData();
    }

    /**
     * 尾页
     *
     * @return 分页内容
     */
    public List<T> last() {
        this.currentPage = this.countPage - 1;
        return this.pageData();
    }

    /**
     * 上一页
     *
     * @return 分页内容
     */
    public List<T> prev() {
        if (this.currentPage != 0) {
            --this.currentPage;
        }
        return this.pageData();
    }

    /**
     * 下一页
     *
     * @return 分页内容
     */
    public List<T> next() {
        if (this.currentPage < this.countPage - 1) {
            ++this.currentPage;
        }
        return this.pageData();
    }

    /**
     * 上一页页码
     *
     * @return 页码
     */
    public long prevPage() {
        if (this.currentPage != 0) {
            return this.currentPage - 1;
        }
        return this.currentPage;
    }

    /**
     * 下一页页码
     *
     * @return 页码
     */
    public long nextPage() {
        if (this.currentPage < this.countPage - 1) {
            return this.currentPage + 1;
        }
        return this.currentPage;
    }

    /**
     * 尾页页码
     *
     * @return 页码
     */
    public long lastPage() {
        long lastPage = this.countPage - 1;
        return Math.max(0, lastPage);
    }

    /**
     * 更新当前分页
     *
     * @param pageNo 页码
     */
    public void currentPage(long pageNo) {
        if (pageNo <= 0) {
            this.currentPage = 0;
        } else if (pageNo >= this.countPage) {
            this.currentPage = this.countPage - 1;
        } else {
            this.currentPage = pageNo;
        }
    }

    /**
     * 获取开始下标
     *
     * @return 开始下标
     */
    public long startIndex() {
        return this.currentPage * this.limit;
    }

    /**
     * 获取分页数据
     *
     * @param pageNo 页码
     * @return 分页内容
     */
    public List<T> page(long pageNo) {
        this.currentPage(pageNo);
        return this.pageData();
    }

    /**
     * 是否为空
     *
     * @return 结果
     */
    public boolean isEmpty() {
        return this.dataList == null || this.dataList.isEmpty();
    }

    /**
     * 格式化分页
     *
     * @param tpl 模板内容
     * @return 格式化后的内容
     */
    public String formatTpl(String tpl) {
        if (tpl == null) {
            return "";
        }
        tpl = tpl.replaceFirst("#count", String.valueOf(this.count));
        tpl = tpl.replaceFirst("#limit", String.valueOf(this.limit));
        if (!this.isEmpty()) {
            tpl = tpl.replaceFirst("#currentPage", String.valueOf(this.currentPage + 1));
            tpl = tpl.replaceFirst("#countPage", String.valueOf(this.countPage));
        } else {
            tpl = tpl.replaceFirst("#currentPage", "0");
            tpl = tpl.replaceFirst("#countPage", "0");
        }
        return tpl;
    }

    /**
     * 分页数据
     *
     * @return 分页数据
     */
    private List<T> pageData() {
        long start = this.limit * this.currentPage;
        long end = Math.min(start + this.limit, this.count);
        return this.dataList.subList((int) start, (int) end);
    }

    /**
     * 修正页码，使其落在有效范围内
     *
     * @param pageNo 页码
     * @return 修正后的页码
     */
    public long fixPageNo(long pageNo) {
        if (pageNo >= this.countPage) {
            pageNo = this.countPage - 1;
        } else if (pageNo < 0) {
            pageNo = 0;
        }
        return pageNo;
    }

    /**
     * 获取数据总数
     *
     * @return 数据总数
     */
    public long count() {
        return this.count;
    }

    /**
     * 获取每页显示数量
     *
     * @return 每页显示数量
     */
    public long limit() {
        return this.limit;
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public long countPage() {
        return this.countPage;
    }

    /**
     * 获取当前页
     *
     * @return 当前页
     */
    public long currentPage() {
        return this.currentPage;
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    public List<T> dataList() {
        return this.dataList;
    }
}
