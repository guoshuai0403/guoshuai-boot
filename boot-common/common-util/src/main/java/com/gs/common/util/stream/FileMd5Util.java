package com.gs.common.util.stream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 二进制流文件生成MD5摘要
 *
 * @auth gs
 * @since 2018年5月10日下午2:27:43
 */
public class FileMd5Util {

	/**
	 * 根据文件生成该文件的二进制流的MD5摘要
	 *
	 * @auth gs
	 * @since 2018年5月10日下午2:36:24
	 * @param inputStream
	 * @return
	 */
	public static String create(InputStream inputStream) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, length);
			}
			BigInteger bigInt = new BigInteger(1, md.digest());
			return bigInt.toString(16);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 私有构造函数
	 */
	private FileMd5Util(){}
}
