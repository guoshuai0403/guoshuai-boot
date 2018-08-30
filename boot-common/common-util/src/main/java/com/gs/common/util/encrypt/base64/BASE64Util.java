package com.gs.common.util.encrypt.base64;

import sun.misc.BASE64Decoder;

import java.lang.reflect.Method;

/**
 * 安全性很低的编码解码
 * Created by gs on 2017/10/19.
 */
public class BASE64Util {

    /**
     * Base64编码
     * @param input
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[]input) throws Exception{
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod= clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj=mainMethod.invoke(null, new Object[]{input});
        return (String)retObj;
    }

    /**
     * base64解码
     * @param input
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String input) throws Exception{
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod= clazz.getMethod("decode", String.class);
        mainMethod.setAccessible(true);
        Object retObj=mainMethod.invoke(null, input);
        return (byte[])retObj;
    }
    public static byte[] getStringImage(String base64String) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(base64String);
        return base64String != null ?  bytes: null;

    }
}
