package com.gs.owner.generator.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.util.enums.IBaseEnum;

/**
 * 表字段键索引的类型
 * Created by guoshuai on 2018/8/24 0024.
 */
public enum ColumnIndexType implements IBaseEnum {

    PRI(1, "主键索引"),
    UNI(2, "唯一索引"),
    MUL(3, "普通索引");

    private int code;

    private String comment;

    ColumnIndexType(int code, String comment){
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
