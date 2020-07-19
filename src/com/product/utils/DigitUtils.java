package com.product.utils;

import java.util.Random;

/**
 * 序列-工具类
 * 
 * @author syl
 *
 */
public class DigitUtils {

	/**
	 * 获取数字序列
	 * 
	 * @param length
	 * @return
	 */
	public static Integer getDigitNumber(int length) {
		int min = (int) Math.pow(10, length - 1);
		int max = (int) Math.pow(10, length) - 1;
		
		Random random = new Random();
		int temp = Math.abs(random.nextInt());
		
		return temp % (max - min + 1) + min;
	}
	
	
}