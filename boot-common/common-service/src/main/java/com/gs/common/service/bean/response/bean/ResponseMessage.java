package com.gs.common.service.bean.response.bean;


import com.gs.common.service.bean.response.code.ResponseCode;

import java.util.HashMap;

/**
 * 给客户端返回数据的接口
 * Created by SIMON
 * on 2017/10/1.
 */
public class ResponseMessage extends ResponseErrorMessage {

    // 返回的对象
    private Object obj;

    // 无参构造器
    public ResponseMessage(){
        this(ResponseCode.SUCCESS);
    }

    // 有参构造器
    public ResponseMessage(ResponseCode responseCode){
        super.setCode(responseCode.getCode());
        super.setMsg(responseCode.getComment());
    }

    public Object getObj() {

        // 为null时反馈给客户端{}
        if (obj == null) {

            return new HashMap<>();
        }

        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}

