package com.gs.owner.generator.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.gs.owner.generator.bean.enums.ColumnIndexType;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库列对象
 * Created by guoshuai on 2018/8/29 0029.
 */
public class ColumnBean implements Serializable{

    /** 列名称 */
    private String name;

    /** 列数据类型 */
    private String dataType;

    /** 列注释 */
    private String comment;

    /** 索引类型 */
    private ColumnIndexType indexType;

    /** 列允许的最大长度 */
    private Integer maxLength;

    /** 关联一个数据库表对象 */
    private TableBean tableBean;

    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ColumnIndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(ColumnIndexType indexType) {
        this.indexType = indexType;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public TableBean getTableBean() {
        return tableBean;
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ColumnBean{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", comment='" + comment + '\'' +
                ", indexType=" + indexType +
                ", tableBean=" + tableBean +
                '}';
    }
}
