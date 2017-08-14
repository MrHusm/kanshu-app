package com.kanshu.kanshu.base.utils;

/**
 * @author hushengmeng
 * @date 2017/7/4.
 */
import java.io.Serializable;
import java.util.List;

public class PageFinder<T> implements Serializable {
    private static int DEFAULT_PAGE_SIZE = 20;
    private int pageSize;
    private List<T> data;
    private int rowCount;
    private int pageCount;
    private int pageNo;
    private boolean hasPrevious;
    private boolean hasNext;
    private int currentNumber;

    public PageFinder(int pageNo, int rowCount) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.hasPrevious = false;
        this.hasNext = false;
        this.pageNo = pageNo;
        this.rowCount = rowCount;
        this.pageCount = this.getTotalPageCount();
        this.refresh();
    }

    public PageFinder(int pageNo, int pageSize, int rowCount) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.hasPrevious = false;
        this.hasNext = false;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.pageCount = this.getTotalPageCount();
        this.refresh();
    }

    public PageFinder(int pageNo, int pageSize, int rowCount, List<T> data) {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.hasPrevious = false;
        this.hasNext = false;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.pageCount = this.getTotalPageCount();
        this.data = data;
        this.refresh();
    }

    private final int getTotalPageCount() {
        return this.rowCount % this.pageSize == 0?this.rowCount / this.pageSize:this.rowCount / this.pageSize + 1;
    }

    private void refresh() {
        if(this.pageCount <= 1) {
            this.hasPrevious = false;
            this.hasNext = false;
        } else if(this.pageNo == 1) {
            this.hasPrevious = false;
            this.hasNext = true;
        } else if(this.pageNo == this.pageCount) {
            this.hasPrevious = true;
            this.hasNext = false;
        } else {
            this.hasPrevious = true;
            this.hasNext = true;
        }

    }

    public int getPageSize() {
        return this.pageSize;
    }

    public Object getResult() {
        return this.data;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean isHasPrevious() {
        return this.hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getCurrentNumber() {
        if(this.data != null) {
            this.currentNumber = this.data.size();
        }

        return this.currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartOfPage() {
        return (this.pageNo - 1) * this.pageSize;
    }
}
