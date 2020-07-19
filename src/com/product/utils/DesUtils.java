package com.product.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.product.constants.Constants;

/**
 * 加密解密-工具类
 * 
 * @author syl
 *
 */
public class DesUtils {
	
	/**加密钥键*/
	private SecretKey key;
	/**替换集合*/
	private static final Map<String, String> replace;
	
	static {
		replace = new HashMap<>();
		replace.put("\\+", "\\[s]");
		replace.put("/", "\\[y]");
		replace.put("=", "\\[l]");
	}
	
	public DesUtils(String value) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		this.key = factory.generateSecret(new DESKeySpec(value.getBytes(Constants.utf8)));
	}
	
	/**
	 * 加密
	 * 
	 * @param value
	 * 			字符串
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String value) throws Exception {
		Encoder encoder = Base64.getEncoder();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
		String result = encoder.encodeToString(cipher.doFinal(value.getBytes(Constants.utf8)));
		for (Map.Entry<String, String> entry : replace.entrySet()) {
			result = result.replaceAll(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 解密
	 * 
	 * @param value
	 * 			字符串
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String value) throws Exception {
		for (Map.Entry<String, String> entry : replace.entrySet()) {
			value = value.replaceAll(entry.getValue(), entry.getKey());
		}
		Decoder decoder = Base64.getDecoder();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
		return new String(cipher.doFinal(decoder.decode(value)), Constants.utf8);
	}
}