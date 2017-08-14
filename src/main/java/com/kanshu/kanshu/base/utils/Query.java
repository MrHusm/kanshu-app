package com.kanshu.kanshu.base.utils;

/**
 * @author hushengmeng
 * @date 2017/7/4.
 */
import java.io.Serializable;

public class Query implements Serializable {
    private static final long serialVersionUID = 817880730448759944L;
    private String order;
    private boolean isAsc;
    private int page = 1;
    private int offset = 0;
    private int pageSize = 15;

    public Query() {
    }

    public Query(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean getIsAsc() {
        return this.isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        int page = this.page == 0?1:this.page;
        this.offset = (page - 1) * this.pageSize;
        return this.offset;
    }
}
