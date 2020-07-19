package com.product.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import com.product.http.SmsHttpClient;
import com.product.service.SmsService;

import net.sf.json.JSONObject;

/**
 * 创世华信
 * 
 * @author syl
 *
 */
public class CreationSmsServiceImpl extends SmsService {
	
	private Logger logger = LoggerFactory.getLogger(CreationSmsServiceImpl.class);
	
	@Bean(name = "CreationSmsServiceImpl", initMethod = "getInstance")
	public static SmsService getInstance(String... args) {
		if(args.length == 2) {
			return new CreationSmsServiceImpl(args[0], args[1]);
		}
		if(args.length == 3) {
			return new CreationSmsServiceImpl(args[0], args[1], args[2]);
		}
		return null;
	}

	/**
	 * 构造函数
	 * 
	 * @param name
	 * 			商户名称
	 * @param pwd
	 * 			商户密码
	 */
	public CreationSmsServiceImpl(String name, String pwd) {
		super("http://sh2.ipyy.com/smsJson.aspx", name, pwd);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param url
	 * 			访问地址
	 * @param name
	 * 			商户名称
	 * @param pwd
	 * 			商户密码
	 */
	public CreationSmsServiceImpl(String url, String name, String pwd) {
		super(url, name, pwd);
	}
	
	@Override
	public boolean sms(String mobile, String message, Map<String, String> params) {
		String result = null;
		{
			Map<String, String> param = new HashMap<>();
			if(params != null && !params.isEmpty()) {
				param.putAll(params);
			}
			param.put("action", "send");
			param.put("account", super.getName());
			param.put("password", super.getPwd());
			param.put("mobile", mobile);
			param.put("content", message);
			
			result = SmsHttpClient.getJson(super.getUrl(), Consts.UTF_8, null, param);
		}
		if(StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.fromObject(result);
			if(json != null && !json.isNullObject() && !json.isEmpty() && json.containsKey("returnstatus")) {
				String status = json.getString("returnstatus");
				if(StringUtils.isNotBlank(status) && status.equals("Success")) {
					return true;
				} else {
					logger.error("创世华信发送短讯失败：status ---> " + status + ", message --> " + json.getString("message"));
				}
			}
		}
		return false;
	}
}