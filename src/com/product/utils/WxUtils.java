package com.product.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.product.constants.Constants;
import com.weixin.message.SendMessageService.WebClientDevWrapper;

import net.sf.json.JSONObject;

public class WxUtils {
	
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
	 * 获取token
	 * @return
	 */
	public static  String getToken(){
		String code="";
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constants.WEB_WX_APPID+"&secret="+Constants.WEB_WX_SECRET+"";
		HttpClient client = new DefaultHttpClient();
		client = WebClientDevWrapper.wrapClient(client);
		HttpPost post = new HttpPost(url);
		HttpEntity entity = null;
		try {
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				if (charset == null) {
					charset = "utf-8";
				}
			}
			code = EntityUtils.toString(entity);
			code = code.substring(code.indexOf(":") + 2, code.indexOf(",") - 1);
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}

		return code;
	}
	/***
     * 获取jsapiTicket
     *
     * @return
     */
   public static String getJSApiTicket(String token){ 
	  
       String code="";
       String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";  
       HttpClient client = new DefaultHttpClient();
		client = WebClientDevWrapper.wrapClient(client);
		HttpPost post = new HttpPost(urlStr);
		HttpEntity entity = null;
		try {
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				if (charset == null) {
					charset = "utf-8";
				}
			}
			
			code = EntityUtils.toString(entity);
			code = (String) JSONObject.fromObject(code).get("ticket");  
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
       return  code;  
          
   }  

	public static Map<String, Object> getTokenOrTicket() {
		long time=new Date().getTime()/1000;
		Map<String, Object> map = new HashMap<>();
		String token = WxUtils.getToken();
		map.put("Time", time);
		map.put("Token", token);
		String ticket = WxUtils.getJSApiTicket(token);
		map.put("ticket", ticket);
		
		
		return map;
	}
	public static Map<String, Object> getTokenOrTicket(ServletContext servlet){
		long time=new Date().getTime()/1000;
		
		Map<String, Object> map = (Map<String, Object>) servlet.getAttribute(Constants.WEB_WX_ACCESS_TOKEN);
		if(map == null){
			map = WxUtils.getTokenOrTicket();
		}else{
			int etime = Integer.parseInt(map.get("Time").toString());
			if((time-etime)>6000){
				map = WxUtils.getTokenOrTicket();
			}
		}
		servlet.setAttribute(Constants.WEB_WX_ACCESS_TOKEN, map);
		return map;
	}
	
	
	
	
	
   
   /**
    * 获取用户经纬度sign
    * @param jsapi_ticket
    * @param url
    * @param nonce_str
    * @param timestamp
    * @return
    */
   public static String sign(String jsapi_ticket, String url,String nonce_str,String timestamp) {
       String str ;
       String signature = "";
       //注意这里参数名必须全部小写，且必须有序
       str  = "jsapi_ticket=" + jsapi_ticket +
                 "&noncestr=" + nonce_str +
                 "&timestamp=" + timestamp +
                 "&url=" + url;
       try {
           MessageDigest crypt = MessageDigest.getInstance("SHA-1");
           crypt.reset();
           crypt.update(str .getBytes("UTF-8"));
           signature = StrUtils.byteToHex(crypt.digest());
       }  catch (Exception e) {
           e.printStackTrace();
       }
       return signature;
   }
	
	
	public static void main(String[] args) {
		//getOpenid("wx2a4d5190d7080890", "d6c473b8d77c12e27669ff33684d8b7b", "token");
		System.out.println(DigestUtils.md5Hex("A1111111"));
	}

}
