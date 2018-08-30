/**   **/
package com.gs.common.util.encrypt.aes;

import com.gs.common.util.encrypt.base64.BASE64Util;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;



/**
 * AES对称加密、解密工具类
 *
 * @auth gs
 * @since 2018年3月27日下午3:26:34
 */
public class AesEncryptUtil {
	
	/**
	 * 工具类构造方法私有化
	 */
	private AesEncryptUtil() {};
	
	/**
	 * AES加密
	 * @auth gs
	 * @since 2018年3月27日下午3:29:03
	 * @param key - aes密钥
	 * @param content - 需要加密的明文
	 * @param charset - 字符集
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String key, String charset) throws Exception {
		 
        String fullAlg = "AES/CBC/PKCS5Padding";
 
        Cipher cipher = Cipher.getInstance(fullAlg);
        IvParameterSpec iv = new IvParameterSpec(initIv(fullAlg));
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(BASE64Util.decodeBase64(key), "AES"),
                iv);
        byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
        return new String(BASE64Util.encodeBase64(encryptBytes));
    }
	
	/**
     * 解密
     * @param content 密文
     * @param key aes密钥
     * @param charset 字符集
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String content, String key, String charset) throws Exception {
         
        //反序列化AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(BASE64Util.decodeBase64(key), "AES");

         
        //128bit全零的IV向量
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = 0;
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
         
        //初始化加密器并加密
        Cipher deCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
        byte[] encryptedBytes = BASE64Util.decodeBase64(content);
        byte[] bytes = deCipher.doFinal(encryptedBytes);
        return new String(bytes);
    }
    
    /** 
     * 随机生成秘钥 
     */  
    public static String getGeneratorKey() throws Exception{
    	
    	return AesEncryptUtil.getKeyByPass(null);
    }    
      
    /**
     * 使用指定的字符串生成秘钥
     *
     * @auth gs
     * @since 2018年3月29日上午11:21:19
     * @param password
     * @return
     */
    public static String getKeyByPass(String password) throws Exception{
    	
        try {   
        	
        	//1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            
            //2.根据ecnodeRules规则初始化密钥生成器
            if (StringUtils.isEmpty(password)) {

            	//生成一个128位的随机源
                keygen.init(128);
            } else {
            	
            	//生成一个128位的随机源,根据传入的字节数组
                keygen.init(128, new SecureRandom(password.getBytes()));
            }
            
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            
            return new String(BASE64Util.encodeBase64(key.getEncoded()));
        } catch (NoSuchAlgorithmException e) {    
            e.printStackTrace();    
        }   
        return null;
    } 
    
    /**
     * 初始向量的方法, 全部为0. 这里的写法适合于其它算法,针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] initIv(String fullAlg) throws GeneralSecurityException {
    	
        Cipher cipher = Cipher.getInstance(fullAlg);
        
        int blockSize = cipher.getBlockSize();
        
        byte[] iv = new byte[blockSize];
        
        for (int i = 0; i < blockSize; ++i) {
        	
            iv[i] = 0;
        }
        
        return iv;
    }
}
