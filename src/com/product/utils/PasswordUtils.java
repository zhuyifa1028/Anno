package com.product.utils;

/**
 * 密码工具类
 *
 * @author zhu
 */
public class PasswordUtils {

    /**
     * 密码强度校验
     *
     * @param text 密码文本
     * @return 是否通过
     */
    public static boolean strengthValid(String text) {
        // 密码长度
        int length = text.length();

        // 是否大于8位
        if (length < 8) {
            return false;
        }

        // 是否包含大写
        for (int i = 0; i < length; i++) {
            // 循环判断密码文本的字符
            char charAt = text.charAt(i);
            if (Character.isUpperCase(charAt)) {
                return true;
            }
        }
        return false;
    }

}