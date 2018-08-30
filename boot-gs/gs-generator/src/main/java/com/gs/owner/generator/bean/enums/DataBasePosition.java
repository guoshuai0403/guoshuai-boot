package com.gs.owner.generator.bean.enums;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.util.enums.IBaseEnum;

/**
 * 数据库位置枚举类
 * Created by guoshuai on 2018/8/24 0024.
 */
public enum DataBasePosition implements IBaseEnum {

    LCOAL(1, "本地"),
    REMOTE(2, "远程");

    private Integer code;

    private String comment;

    DataBasePosition(int code, String comment){
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
