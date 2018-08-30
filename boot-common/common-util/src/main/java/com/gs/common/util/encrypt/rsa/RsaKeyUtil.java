/**   **/
package com.gs.common.util.encrypt.rsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.gs.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


/**
 * RSA非对称加密秘钥生成工具类
 *
 * @auth gs
 * @since 2018年3月29日上午10:52:46
 */
public class RsaKeyUtil {
	
	/**
	 * 生成秘钥对 - 公钥 - 私钥
	 *
	 * @auth gs
	 * @since 2018年3月28日下午4:07:58
	 * @return
	 */
	public static Map<String, String> genKeyPair() {

		return RsaKeyUtil.genKeyPair(null);
	}

	/**
	 * 生成秘钥对, 并且在本地生成秘钥文件 - 公钥 - 私钥
	 * 
	 * @auth gs
	 * @since 2018年3月28日下午3:10:27
	 * @param filePath
	 *            - 秘钥对在本地生成文件的路径，为Null时不在本地生成文件
	 */
	public static Map<String, String> genKeyPair(String filePath) {

		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(RsaConstant.KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
		String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));

		Map<String, String> result = new HashMap<>();
		result.put("privateKeyStr", privateKeyStr);
		result.put("publicKeyStr", publicKeyStr);

		if (!StringUtils.isBlank(filePath)) {

			FileUtil.createFile(filePath, "privateKey.keystore", privateKeyStr);
			FileUtil.createFile(filePath, "publicKey.keystore", publicKeyStr);
		}

		return result;
	}

	/**
	 * 从文件中输入流中加载公钥
	 *
	 * @auth gs
	 * @since 2018年3月28日下午4:49:29
	 * @param publicKeyFileName
	 *            - 存放公钥的文件名
	 * @return
	 * @throws Exception
	 */
	public static String loadPublicKeyByFile(String publicKeyFileName) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(publicKeyFileName));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 *
	 * @auth gs
	 * @since 2018年3月28日下午4:48:06
	 * @param privateKeyFileName
	 *            - 私钥文件名
	 * @return
	 * @throws Exception
	 */
	public static String loadPrivateKeyByFile(String privateKeyFileName) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(privateKeyFileName));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}
	
	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥
	 *
	 * @auth gs
	 * @since 2018年3月28日下午4:47:38
	 * @param privateKeyStr
	 *            - 私钥数据字符串
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
}
