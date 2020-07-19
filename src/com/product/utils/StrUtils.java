package com.product.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.product.constants.Constants;

import net.sf.json.JSONObject;

/**
 * 字符-工具类
 * 
 * @author syl
 *
 */
public class StrUtils {
	
	public static String getOpenid(String code){
		String openid = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WEB_WX_APPID
				+ "&secret=" + Constants.WEB_WX_SECRET + "&code=" + code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if (null != jsonObject) {
			openid = jsonObject.get("openid") + "";
			
		}
		return openid;
	}
	
	/**
	  * 函数功能说明： 获取用户code。
	  * @param redirectUrl  code重定向地址
	  * @param appId        
	  * @return 
	  * @throws
	  */
	public static void getCode(String redirectUrl,HttpServletResponse response){
		redirectUrl=URLEncoder.encode(redirectUrl);
		String url = "https://open.weixin.qq.com/connect/oauth2/" +
				"authorize?appid="+Constants.WEB_WX_APPID+"&redirect_uri="+redirectUrl+"" +
				"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		System.out.println("授权地址" + url);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * byte转16进制
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
	       Formatter formatter = new Formatter();
	       for (byte b : hash) {
	           formatter.format("%02x", b);
	       }
	       String result = formatter.toString();
	       formatter.close();
	       return result;
	 }
	
	/**
	 * 判断字符串是否为空
	 * @author lss
	 * @date 2017-7-6
	 * @param str
	 * @return true:字符串为空，false:字符串非空
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0)
				|| "null".equals(str) || "".equals(str));
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @author lss
	 * @date 2017-7-6
	 * @param str
	 * @return true:字符串为空，false:字符串非空
	 */
	public static boolean isNotEmpty(String str) {
		return StrUtils.isEmpty(str)?false:true;
	}

	/**
	 * 首字母大写
	 * 
	 * @param target
	 * 			目标字符
	 * @return
	 */
	public static String toUpperCase(String target) {
		if(!Character.isUpperCase(target.charAt(0))) {
			StringBuilder builder = new StringBuilder(target);
			builder.setCharAt(0, Character.toUpperCase(target.charAt(0)));
			target = builder.toString();
		}
		return target;
	}
	
	/**
	 * 首字母大写(前缀条件)
	 * 
	 * @param prefix
	 * 			前缀条件
	 * @param target
	 * 			目标字符
	 * @return
	 */
	public static String toUpperCase(String prefix, String target) {
		String[] targets = target.split(prefix);
		StringBuffer sb = new StringBuffer();
		for(String key : targets) {
			if(StringUtils.isNotBlank(key)) {
				sb.append(StrUtils.toUpperCase(key));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 首字母小写
	 * 
	 * @param target
	 * 			目标字符
	 * @return
	 */
	public static String toLowerCase(String target) {
		if(!Character.isLowerCase(target.charAt(0))) {
			StringBuilder builder = new StringBuilder(target);
			builder.setCharAt(0, Character.toLowerCase(target.charAt(0)));
			target = builder.toString();
		}
		return target;
	}
	
	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 * @author lss
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * @author lss
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
}