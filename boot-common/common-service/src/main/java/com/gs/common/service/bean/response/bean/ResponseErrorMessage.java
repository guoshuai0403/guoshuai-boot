package com.gs.common.service.bean.response.bean;

import com.gs.common.service.bean.response.code.ResponseCode;
import com.gs.common.util.format.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

/**
 * 当请求出错时返回的对象
 * Created by gs on 2017/10/13.
 */
public class ResponseErrorMessage {

    // 返回码，0 - 正常，负数 - 错误
    private Integer code;

    // 返回给用户看的信息
    private String msg;

    // 本次请求的uri
    private String uri;

    // 响应时间
    private Timestamp responsetime;

    // 有参构造器
    public ResponseErrorMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 无参构造器
    public ResponseErrorMessage() {
        this(ResponseCode.VALID_PARAMS);
    }

    public ResponseErrorMessage(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.msg = responseCode.getComment();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUri() {
        if (StringUtils.isBlank(uri)) {

        }
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    // 默认返回当前时间
    public Timestamp getResponsetime() {
        if (responsetime == null) {

            responsetime = DateUtil.currentTimestamp();
        }
        return responsetime;
    }

    public void setResponsetime(Timestamp responsetime) {
        this.responsetime = responsetime;
    }
}
