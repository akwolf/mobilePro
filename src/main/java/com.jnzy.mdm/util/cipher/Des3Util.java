package com.jnzy.mdm.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class Des3Util {

	// 密钥
	// private final static String secretKey = "58421235liuhongjun123456";

	// 向量
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/**
	 * 生成秘钥
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public final static String getSecretKey(String IEMI) {
		String secretKey = IEMI.substring(0, 8) + "sdjnzyxxkmdm2016";
		return secretKey;
	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText, String secretKey)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64Util.encode(encryptData);
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText, String secretKey)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));

		return new String(decryptData, encoding);
	}

	public static void main(String[] args) throws Exception {
//		添加车辆
//		String str = encode(
//				"{appusername:\"jnzy\",apppassword:\"sdjnzykj\",biaoshi:\"android\",versionCode:\"1\",carId:\"2\",carType:\"1\",carCity:\"济南\",plateNumber:\"1\",vin:\"1\",motorNumber:\"1\",userId:\"1\"}",
//				"58421235sdjnzyxxkjxx2015");
//		String str = encode(
//				"{content:\"SN\",type:\"1\"}",
//				"12345678sdjnzyxxkmdm2016");
//		String str = encode(
//				"{appusername:\"jnzy\",apppassword:\"sdjnzykj\",biaoshi:\"android\",question:\"问题\",mobile:\"13256738589\",classify:\"1\"}",
//				"58421235sdjnzyxxkjxx2015");
		//String str = encode("{bId:\"1\",cId:\"1\",iemi:\"58421235sdfasdfasfadfas\",type:\"1\",classify:\"1\",question:\"1\",appealId:\"1\",name:\"张三\",password:\"321456\",mobile:\"13256738589\",lawsId:\"1\",appusername:\"jnzy\",apppassword:\"sdjnzykj\",biaoshi:\"android\",versionCode:\"1\"}","58421235sdjnzyxxkjxx2015");
//		更新用户信息
//		String str = encode(
//				"{appusername:\"jnzy\",apppassword:\"sdjnzykj\",biaoshi:\"android\",versionCode:\"1\",userId:\"2\",nickname:\"哈哈\",sex:\"女\",age:\"10\",qq:\"1456\",email:\"112\"}",
//				"58421235sdjnzyxxkjxx2015");
//		获取省市
//		String str = encode(
//				"{appusername:\"jnzy\",apppassword:\"sdjnzykj\",biaoshi:\"android\",versionCode:\"1\",carType:\"1\"}",
//				"58421235sdjnzyxxkjxx2015");

		//String str1 = decode("vRcCxh3C6jQl0WWJT4QLbdeM+jYSsmFeWbrujilkytyoL3ZLhnS38a1LIeiV b3t+w1cm3xycatF/YDuP/wR7EtMY10QRBetTvef9600wXH2cwAqnaf3P1yte tSE393dNeYGVbEp58eT/QivgQDCFvw==","58421235sdjnzyxxkjxx2015");
		


//		String str1 = decode("9b5vRKn6zDi9Aw4flNrLDw==","12345678sdjnzyxxkjxx2015");

		String str1 = decode("WdEGjuAjraHbgkIcRXpeHg==","12345678sdjnzyxxkmdm2016");

//		String password = MD5Util.string2MD5("8W9P"+ MD5Util.string2MD5("111111"));

//		System.out.println("加密后的字符：" + str);
		System.out.println("解密后的字符：" + str1);
//		System.out.println("password" + password);
		
	}
}
