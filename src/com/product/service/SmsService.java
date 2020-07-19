package com.product.service;

import java.util.Map;

/**
 * 短讯服务-服务层
 * 
 * @author syl
 *
 */
public abstract class SmsService {

	/**
	 * 访问地址
	 */
	private String url;
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * 商户名称
	 */
	private String name;
	public String getName() {
		return this.name;
	}
	
	/**
	 * 商户密码
	 */
	private String pwd;
	public String getPwd() {
		return this.pwd;
	}
	
	public SmsService() {}
	
	/**
	 * 构造函数
	 * 
	 * @param name
	 * 			商户名称
	 * @param pwd
	 * 			商户密码
	 */
	public SmsService(String name, String pwd) {
		this.name = name;
		this.pwd = name;
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
	public SmsService(String url, String name, String pwd) {
		this.url = url;
		this.name = name;
		this.pwd = pwd;
	}
	
	/**
	 * 发送短讯
	 * 
	 * @param mobile
	 * 			手机号码(用逗号分割)
	 * @param message
	 * 			短讯消息
	 * @param params
	 * 			设置参数
	 * @return
	 */
	public abstract boolean sms(String mobile, String message, Map<String, String> params);
}