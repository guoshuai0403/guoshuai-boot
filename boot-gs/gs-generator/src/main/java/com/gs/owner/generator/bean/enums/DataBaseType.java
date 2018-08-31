package com.gs.owner.generator.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.util.enums.IBaseEnum;

/**
 * 数据库类型字段枚举
 * Created by guoshuai on 2018/8/24 0024.
 */
public enum DataBaseType implements IBaseEnum {

    MYSQL(1, "mysql数据库", "com.mysql.jdbc.Driver"),
    ORACLE(2, "oracle数据库", ""),
    SQL_SERVER(3, "sql-server数据库", "com.mysql.jdbc.Driver");

    private int code;

    private String comment;

    /** 连库驱动类 */
    private String driverClassName;

    DataBaseType(int code, String comment, String driverClassName){
        this.code = code;
        this.comment = comment;
        this.driverClassName = driverClassName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    public String getDriverClassName(){
        return this.driverClassName;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
