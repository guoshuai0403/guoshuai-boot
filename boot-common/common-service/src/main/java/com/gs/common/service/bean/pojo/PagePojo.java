package com.gs.common.service.bean.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * Created by gs on 2017/10/19.
 */
public class PagePojo<T> implements Serializable {

    // 当前页码
    private Integer pageIndex;

    // 页面大小
    private Integer pageSize=10;

    // 总页数
    private Integer totalPageCount;

    // 总条数
    private Integer record;

    // 当前页的数据
    private List<T> data;

    // 获取当前页码
    public Integer getPageIndex() {

        if (pageIndex == null) {

            return 1;
        }

        if (pageIndex < 1) {

            return 1;
        }

        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {

        if (pageSize == null) {

            return;
        }

        if (pageSize < 1) {

            pageSize = 1;
        }

        this.pageSize = pageSize;
    }

    public Integer getTotalPageCount() {

        if (this.getRecord() == null) {

            return null;
        }

        totalPageCount = (this.getRecord() + pageSize - 1) / pageSize;

        if (totalPageCount < 1) {

            totalPageCount = 1;
        }

        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {

        if (record == null) {

            record = 0;
        }

        if (record < 1) {

            record = 0;
        }

        this.record = record;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
