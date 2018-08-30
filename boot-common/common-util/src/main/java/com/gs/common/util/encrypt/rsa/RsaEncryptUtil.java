/**   **/
package com.gs.common.util.encrypt.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * RSA非对称加密、解密工具类
 * 
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 *
 * @auth gs
 * @since 2018年3月27日下午5:33:21
 */
public class RsaEncryptUtil {

	/**
	 * 工具类构造方法私有化
	 */
	private RsaEncryptUtil() {
	};

	/**
	 * 公钥加密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:32:25
	 * @param data
	 *            - 源数据
	 * @param publicKey
	 *            - 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > RsaConstant.MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, RsaConstant.MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * RsaConstant.MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 公钥加密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:32:13
	 * @param data
	 *            - 源数据
	 * @param publicKey
	 *            - 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String publicKey) throws Exception {

		return new String(Base64.encodeBase64URLSafe(
				RsaEncryptUtil.encryptByPublicKey(data.getBytes(RsaConstant.ENCODING_DEFAULT), publicKey)));
	}

	/**
	 * 私钥加密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:43:33
	 * @param data
	 *            - 源数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > RsaConstant.MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, RsaConstant.MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * RsaConstant.MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 私钥加密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:43:33
	 * @param data
	 *            - 源数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String data, String privateKey) throws Exception {

		return Base64.encodeBase64URLSafeString(RsaEncryptUtil.encryptByPrivateKey(data.getBytes(RsaConstant.ENCODING_DEFAULT), privateKey));
	}

	/**
	 * 私钥解密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:33:55
	 * @param data
	 *            - 已加密数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > RsaConstant.MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, RsaConstant.MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * RsaConstant.MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	/**
	 * 私钥解密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:33:55
	 * @param data
	 *            - 已加密数据
	 * @param privateKey
	 *            - 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, String privateKey) throws Exception {

		return new String(RsaEncryptUtil.decryptByPrivateKey(Base64.decodeBase64(data), privateKey));
	}

	/**
	 * 公钥解密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:45:55
	 * @param data - 已加密数据
	 * @param publicKey - 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > RsaConstant.MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, RsaConstant.MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * RsaConstant.MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	/**
	 * 公钥解密
	 *
	 * @auth gs
	 * @since 2018年3月29日上午11:45:55
	 * @param data - 已加密数据
	 * @param publicKey - 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String data, String publicKey) throws Exception {
		
		return new String(RsaEncryptUtil.decryptByPublicKey(Base64.decodeBase64(data), publicKey));
	}
	
	
}
