package com.gs.owner.generator.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.util.enums.IBaseEnum;

/**
 * 数据库表类型字段枚举
 * Created by guoshuai on 2018/8/24 0024.
 */
public enum TableType implements IBaseEnum {

    TABLE(1, "基础表"),
    VIEW(2, "视图");

    private int code;

    private String comment;

    TableType(int code, String comment){
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
