package com.gs.common.service.bean.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.gs.common.util.constant.Constant;

import java.util.Date;

/**
 * 实体类的基本类 - 完成公共属性及方法的封装
 * Created by SIMON
 * on 2017/9/28.
 */
public class BasePojo<T> extends PagePojo<T> {

    // 实体类唯一索引
    private Object id;

    // 数据创建时间
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date sysCreatedAt;

    // 数据更新时间
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date sysUpdatedAt;

    // 数据有效状态
    private Boolean sysStatus;

    // 开始时间 - 仅作为查询时使用
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date startSysCreatedAt;

    // 结束时间 - 仅作为查询时使用
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date endSysCreatedAt;

    // 开始时间 - 仅作为查询时使用
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date startSysUpdatedAt;

    // 结束时间 - 仅作为查询时使用
    @JSONField(format = Constant.DatePattern.YYYY_MM_DD_HH_MM_SS)
    private Date endSysUpdatedAt;

    /** 数据库查询、页面要展示的字段 */
    private transient String[] fields;

    // mybatis排序
    private String orderBy;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Date getSysCreatedAt() {
        return sysCreatedAt;
    }

    public void setSysCreatedAt(Date sysCreatedAt) {
        this.sysCreatedAt = sysCreatedAt;
    }

    public Date getSysUpdatedAt() {
        return sysUpdatedAt;
    }

    public void setSysUpdatedAt(Date sysUpdatedAt) {
        this.sysUpdatedAt = sysUpdatedAt;
    }

    // 默认值是真
    public Boolean getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Boolean sysStatus) {
        this.sysStatus = sysStatus;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Date getStartSysCreatedAt() {
        return startSysCreatedAt;
    }

    public void setStartSysCreatedAt(Date startSysCreatedAt) {
        this.startSysCreatedAt = startSysCreatedAt;
    }

    public Date getEndSysCreatedAt() {
        return endSysCreatedAt;
    }

    public void setEndSysCreatedAt(Date endSysCreatedAt) {
        this.endSysCreatedAt = endSysCreatedAt;
    }

    public Date getStartSysUpdatedAt() {
        return startSysUpdatedAt;
    }

    public void setStartSysUpdatedAt(Date startSysUpdatedAt) {
        this.startSysUpdatedAt = startSysUpdatedAt;
    }

    public Date getEndSysUpdatedAt() {
        return endSysUpdatedAt;
    }

    public void setEndSysUpdatedAt(Date endSysUpdatedAt) {
        this.endSysUpdatedAt = endSysUpdatedAt;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
