/**   **/
package com.gs.common.util.encrypt.hash;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * 散列算法加密工具类
 *
 * @auth gs
 * @since 2018年3月29日下午1:57:35
 */
public class HashEncryptUtil {
	
	/**
	 * 公盐
	 */
    private static final String PUBLIC_SALT = "yizhong";
    
    /**
	 * 加密算法RSA
	 */
    // 密文长度32位
	public static final String MD5_ALGORITHM = "MD5";
	
	// 密文长度40位
	public static final String SHA_ALGORITHM = "SHA";
	
	// 密文长度40位
	public static final String SHA1_ALGORITHM = "SHA1";
	
	// 密文长度64位
	public static final String SHA256_ALGORITHM = "SHA-256";
    
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",  
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /**
	 * 工具类构造函数私有化
	 */
	private HashEncryptUtil() {}
	
	/**
	 * 用户密码加密，盐值为 ：私盐+公盐
	 *
	 * @auth gs
	 * @since 2018年3月29日下午2:31:42
	 * @param password - 待加密密码
	 * @return
	 */
    public static String encryptPassword(String password){
    	
    	return encryptPassword(password, null);
    }
	
	/**
	 * 用户密码加密，盐值为 ：私盐+公盐
	 *
	 * @auth gs
	 * @since 2018年3月29日下午2:31:42
	 * @param password - 待加密密码
	 * @param salt - 私盐(用户自己的盐值)
	 * @return
	 */
    public static String encryptPassword(String password, String salt){
    	
        return encryptPassword(password, salt, null);
    }
    
    /**
	 * 用户密码加密，盐值为 ：私盐+公盐
	 *
	 * @auth gs
	 * @since 2018年3月29日下午2:31:42
	 * @param password - 待加密密码
	 * @param salt - 私盐(用户自己的盐值)
	 * @param algorithm - 加密算法，本工具类常量已经提供，可直接调用
	 * @return
	 */
    public static String encryptPassword(String password, String salt, String algorithm){
    	
    	// 默认是SHA-256加密算法
    	if (StringUtils.isBlank(algorithm)) {
    		
    		algorithm = HashEncryptUtil.SHA256_ALGORITHM;
    	}
    	
        return encode(HashEncryptUtil.PUBLIC_SALT + password + salt, algorithm);
    }
     
    /**
     * 加密算法
     *
     * @auth gs
     * @since 2018年3月29日下午2:33:09
     * @param originString - 待加密字符串
     * @param algorithm - 加密算法
     * @return
     */
    private static String encode(String originString, String algorithm){
        if (originString != null){  
            try{  
                //创建具有指定算法名称的信息摘要  
                MessageDigest md = MessageDigest.getInstance(algorithm);  
                
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算  
                byte[] results = md.digest(originString.getBytes());
                
                //将得到的字节数组变成字符串返回  
                String resultString = byteArrayToHexString(results);
                
                return resultString.toUpperCase();
                
            } catch(Exception ex){  
            	
                ex.printStackTrace();  
            }  
        }  
        return null;  
    } 
     
    /**  
     * 转换字节数组为十六进制字符串 
     * @param     字节数组 
     * @return    十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] b){  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++){  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
       
    /** 将一个字节转化成十六进制形式的字符串     */  
    private static String byteToHexString(byte b){  
        int n = b;  
        if (n < 0)  
            n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }
}
