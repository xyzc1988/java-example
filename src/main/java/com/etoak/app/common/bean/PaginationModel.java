package com.etoak.app.common.bean;

import java.util.ArrayList;
import java.util.List;

public class PaginationModel<T> {
    /**
     * 当前页码
     */
    private long currentPage = 0;
    /**
     * 每页行数
     */
    private long pageSize = 10;
    /**
     * 总行数
     */
    private long total = 0;
    /**
     * 数据集
     */
    private List<T> data = new ArrayList();

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
