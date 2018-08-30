package com.gs.common.service.bean.response.code;


import com.gs.common.util.enums.IBaseEnum;

/**
 * 给客户端发送的响应信息
 * Created by gs on 2018/6/12 0012.
 */
public enum ResponseCode implements IBaseEnum {
    SUCCESS(200, "success"),

    /** 业务相关 6XX */


    /** 系统预留 9XX */
    VALID_TONKEN(901, "鉴权失败"),
    VALID_PHONE(902, "手机号验证失败"),
    VALID_PARAMS(903, "无效的参数"),
    VALID_RESOURCE(904, "资源不存在"),
    VALID_SMS_CODE(905, "短信验证码错误"),
    FAIL_OPERA(925, "操作失败"),
    ERROR_SERVER(998, "服务器异常，请稍后重试"),
    ERROR_UNKNOW(999, "未知错误");
    ;


    // ------每个字段属性的枚举类都有下边这一坨代码------------------------
    /** 代码 */
    private Integer code;
    /** 描述信息 */
    private String comment;

    /** 构造函数私有化 */
    ResponseCode(Integer code, String comment){
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
}
