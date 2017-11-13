package io.github.xyzc1988.common.bean;

import java.util.ArrayList;
import java.util.List;

public class PageModel<T> {
    /**
     * 当前页码
     */
    private long pageIndex = 0;
    /**
     * 每页行数
     */
    private long pageSize = 10;
    /**
     * 总行数
     */
    private long totalCount = 0;
    /**
     * 数据集
     */
    private List<T> data = new ArrayList<>();

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
