package com.jnzy.mdm.util.cipher;

import org.apache.commons.codec.binary.Base64;

public final class EncryptUtil {

	public final static String CHARSET = "UTF-8";

	private EncryptUtil() {
		throw new Error("Utility classes should not instantiated!");
	}
	
	/**
	 * 解密
	 * @param msg 待解密字符串
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptMessage(String msg, String key)
			throws Exception {
		byte[] data = TripleDESUtil.decrypt(Base64.decodeBase64(msg),
				Base64.decodeBase64(key));
		return new String(data, CHARSET);
	}

	/**
	 * 解密
	 * 
	 * @param msg
	 *            待解密字符串
	 * @param charset
	 *            编码
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptMessage(String msg, String charset, String key)
			throws Exception {
		byte[] data = TripleDESUtil.decrypt(Base64.decodeBase64(msg),
				Base64.decodeBase64(key));
		return new String(data, charset);
	}

	
	/**
	 * 加密
	 * 
	 * @param msg
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 * @author hanweizhao
	 */
	public static String encryptMessage(String msg, String key)
			throws Exception {
		byte[] data = TripleDESUtil.encrypt(msg.getBytes(CHARSET),
				Base64.decodeBase64(key));
		return Base64.encodeBase64String(data);
	}
	
	/**
	 * 加密
	 * @param msg 待加密字符串
	 * @param charset 编码
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptMessage(String msg, String charset, String key)throws Exception{
		byte[] data = TripleDESUtil.encrypt(msg.getBytes(charset), Base64.decodeBase64(key));
		return Base64.encodeBase64String(data);
	}
	

}
