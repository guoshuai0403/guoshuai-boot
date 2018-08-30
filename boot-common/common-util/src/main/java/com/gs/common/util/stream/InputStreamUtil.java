package com.gs.common.util.stream;


import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * 输入流工具类
 * Created by gs on 2018/5/21 0021.
 */
public class InputStreamUtil {

    /**
     * 字符串转成输入流
     * @param content
     * @param charSet
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream stringToInputStream(String content, String charSet) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(content.getBytes(charSet));
    }

    /**
     * 输入流转化成字符串
     * @param inputStream
     * @return
     */
    public static String inputStreamToString(InputStream inputStream){
        StringBuffer result = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String temp = null;
            while ((temp = reader.readLine())!=null){
                result.append(temp + "\n");
            }
            if (StringUtils.isNotBlank(result.toString())){
                result.deleteCharAt(result.lastIndexOf("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 私有构造函数
     */
    private InputStreamUtil(){}
}
