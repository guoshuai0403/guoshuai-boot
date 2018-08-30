package com.gs.common.util.constant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * description: 常量池
 *
 * @auth gs
 * @since 2018/8/27
 */
public interface Constant extends Serializable{

    Log log = LogFactory.getLog(Thread.currentThread().getClass());

    /** 字符串utf-8编码 */
    String ENCODING_UTF8 = "UTF-8";

    // 日期格式
    interface DatePattern{
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String HH_MM = "HH:mm";
    }
}
