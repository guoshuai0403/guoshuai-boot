/**   **/
package com.gs.common.util.encrypt.rsa;

/**
 * RSA 加密相关常量池
 *
 * @auth gs
 * @since 2018年3月29日上午11:12:14
 */
public class RsaConstant {

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * RSA最大加密明文大小
	 */
	public static final Integer MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	public static final Integer MAX_DECRYPT_BLOCK = 128;
	
	/**
	 * 默认编码格式
	 */
	public static final String ENCODING_DEFAULT = "UTF-8";
}
