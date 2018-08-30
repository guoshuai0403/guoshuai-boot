package com.gs.owner.generator.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.util.enums.IBaseEnum;

/**
 * 数据库类型字段枚举
 * Created by guoshuai on 2018/8/24 0024.
 */
public enum DataBaseType implements IBaseEnum {

    MYSQL(1, "mysql数据库"),
    ORACLE(2, "oracle数据库");

    private int code;

    private String comment;

    DataBaseType(int code, String comment){
        this.code = code;
        this.comment = comment;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
