/**   **/
package com.gs.common.util.encrypt.rsa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA非对称签名工具类
 *
 * @auth gs
 * @since 2018年3月29日上午10:51:55
 */
public class RsaSignUtil {
	
	/**
	 * 用私钥对信息生成数字签名
	 *
	 * @auth gs
	 * @since 2018年3月28日下午5:25:05
	 * @param data
	 *            - 已加密数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String create(String data, String privateKey) throws Exception {
		
		return RsaSignUtil.create(data.getBytes(RsaConstant.ENCODING_DEFAULT), privateKey);
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @auth gs
	 * @since 2018年3月29日上午10:03:10
	 * @param data
	 *            - 已加密数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String create(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(RsaConstant.SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return new String(Base64.encodeBase64(signature.sign()));
	}
	
	/**
	 * 校验数字签名
	 *
	 * @auth gs
	 * @since 2018年3月28日下午5:24:00
	 * @param data
	 *            - 已加密数据
	 * @param publicKey
	 *            - 公钥(BASE64编码)
	 * @param sign
	 *            - 数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String data, String publicKey, String sign) throws Exception {
		
		return RsaSignUtil.verify(data.getBytes(RsaConstant.ENCODING_DEFAULT), publicKey, sign);
	}

	/**
	 * 校验数字签名
	 *
	 * @auth gs
	 * @since 2018年3月29日上午10:02:32
	 * @param data
	 *            - 已加密数据
	 * @param publicKey
	 *            - 公钥(BASE64编码)
	 * @param sign
	 *            - 数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(RsaConstant.SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decodeBase64(sign));
	}
}
