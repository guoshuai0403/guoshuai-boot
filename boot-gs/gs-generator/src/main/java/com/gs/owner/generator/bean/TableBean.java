package com.gs.owner.generator.bean;

import com.gs.owner.generator.bean.enums.TableType;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表对象
 * Created by guoshuai on 2018/8/29 0029.
 */
public class TableBean implements Serializable {

    /** 表名 */
    private String name;

    /** 表注释 */
    private String comment;

    /** 表类型 */
    private TableType type;

    /** 关联一个数据库连接的对象 */
    private ConnectBean connectBean;

    /** 关联多个列对象 */
    private List<ColumnBean> columnBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TableType getType() {
        return type;
    }

    public void setType(TableType type) {
        this.type = type;
    }

    public ConnectBean getConnectBean() {
        return connectBean;
    }

    public void setConnectBean(ConnectBean connectBean) {
        this.connectBean = connectBean;
    }

    public List<ColumnBean> getColumnBeans() {
        return columnBeans;
    }

    public void setColumnBeans(List<ColumnBean> columnBeans) {
        this.columnBeans = columnBeans;
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", connectBean=" + connectBean +
                ", columnBeans=" + columnBeans +
                '}';
    }
}
