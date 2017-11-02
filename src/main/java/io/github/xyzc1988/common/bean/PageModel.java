package io.github.xyzc1988.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * DataTables分页封装类
 * Created by zhangcheng on 2017/8/30.
 */
public class PageModel<T> {
    /**
     * 过滤前总记录数
     */
    private long iTotalRecords = 0;
    /**
     * 过滤后总记录数
     */
    private long iTotalDisplayRecords = 0;
    /**
     * 页面发来的参数，原样返回
     */
    private String sEcho;
    /**
     * 数据集
     */
    private List<T> data = new ArrayList();

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "iTotalRecords=" + iTotalRecords +
                ", iTotalDisplayRecords=" + iTotalDisplayRecords +
                ", sEcho='" + sEcho + '\'' +
                ", data=" + data +
                '}';
    }
}
