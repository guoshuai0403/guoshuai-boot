package com.gs.owner.generator.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.gs.common.service.bean.response.bean.ResponseErrorMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理 配置
 * Created by guoshuai on 2018/8/28 0028.
 */
@ControllerAdvice
public class GlobalDefultExceptionHandler {

    /**
     * 参数异常全局异常捕获
     * @param request
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public void IllegalArgumentExceptionHandler(HttpServletRequest request,
                                                                IllegalArgumentException e, HttpServletResponse response) throws IOException {
        // 系统调试模式
        Boolean isSysDebug = false;
        if (isSysDebug) {
            ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage();
            // 调试代码时这里可以显示给客户端
            responseErrorMessage.setMsg(e.getMessage());
            response.setStatus(responseErrorMessage.getCode());
            // 让客户端用utf8来解析返回的数据
            response.setHeader("Content-type", "text/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(responseErrorMessage));
        } else {
            // 系统关闭调试模式
            throw new IOException();
        }
    }

    /**
     * 系统异常集中处理器
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(HttpServletRequest request, Exception e){
        Log log = LogFactory.getLog(GlobalDefultExceptionHandler.class);
        log.error(e.getMessage());
        return "error";
    }
}
