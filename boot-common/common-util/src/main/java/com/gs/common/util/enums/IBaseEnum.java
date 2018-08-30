package com.gs.common.util.enums;

/**
 * 字段枚举基础接口， 所有的字段枚举类都要实现这个接口
 * Created by gs on 2018/6/1 0001.
 */
public interface IBaseEnum {

    /**
     * 获取字段枚举的代码值
     * @return
     */
    Integer getCode();

    /**
     * 获取字段枚举的说明
     * @return
     */
    String getComment();
}
