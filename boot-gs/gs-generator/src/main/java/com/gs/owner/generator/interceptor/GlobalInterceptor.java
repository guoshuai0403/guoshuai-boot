package com.gs.owner.generator.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * description: 全局拦截器，实现参数打印
 *
 * @auth guoshuai
 * @since 2018/8/27
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(GlobalInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // request-headers
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer headers = new StringBuffer("the request-header is: ");
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
            headers.append(s + " == " + header + ",");
        }

        // request-parameter
        StringBuffer params = new StringBuffer("the request-params is: ");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            params.append(s + " == " + parameter + ",");
        }

        // 日志打印
        logger.info("----------------------------------");
        logger.info("the request URI is :" + request.getRequestURI());
        logger.info(headers.toString());
        logger.info(params.toString());

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


        super.afterCompletion(request, response, handler, ex);
    }

}
