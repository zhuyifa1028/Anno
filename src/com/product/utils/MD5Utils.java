package com.product.utils;

import java.security.MessageDigest;

/**
 * 鍔犲瘑-宸ュ叿绫�
 * 
 * @author syl
 *
 */
public class MD5Utils {

	/**
	 * 瀛楃涓插姞瀵�(32浣�)
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String md5(String value) throws Exception {
		StringBuffer buffer = new StringBuffer();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		char[] values = value.toCharArray();
		byte[] bytes = new byte[values.length];
		
		for(int i = 0; i < values.length; i++) {
			bytes[i] = (byte) values[i];
		}
		byte[] md5 = digest.digest(bytes);
		for(int i = 0; i < md5.length; i++) {
			int val = ((int) md5[i]) & 0xff;
			if(val < 16) {
				buffer.append("0");
			}
			buffer.append(Integer.toHexString(val));
		}
		return buffer.toString();
	}
}