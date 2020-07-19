package com.product.bean;

import java.util.Date;

/**
 * 用户-实体类
 * 
 * @author YMC
 *
 */
public class User extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	/** 账号状态(启用) */
	public static final String USER_STATE_NOT = "0";
	/** 账号状态(禁用) */
	public static final String USER_STATE_YES = "1";
	/** 系统缓存(账号状态) */
	public static final String USER_STATE_KEY = "USER_STATE";
	
	/** 系统缓存(缓存类别) */
	public static final String USER_CACHE_KEY = "USER_CACHE_%s";
	
	/** 是否隐藏(是) */
	public static final String USER_IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String USER_IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String USER_IS_DISPLAY_KEY = "USER_IS_DISPLAY";
	
	/**
	 * 登录地址
	 */
	private String userOpenid;
	
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 行政名称
	 */
	private String userAdmin;
	/**
	 * 用户密码
	 */
	private String userPwd;
	/**
	 * 用户电话
	 */
	private String userTel;
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	/**
	 * 公司名称
	 */
	private String userCompany;
	/**
	 * 具体地址
	 */
	private String userAddre;
	/**
	 * 用户头像
	 */
	private String userHeadpic;
	/**
	 * 司机电话
	 */
	private String userDriverid;
	/**
	 * 投诉类型1
	 */
	private String userComplsort;
	
	/**
	 * 投诉类型2
	 */
	private String userComplsorts;
	/**
	 * 投诉内容
	 */
	private String userCompldetails;
	/**
	 * 用户预约成功的详情
	 */
	private String userDetails;
	/**
	 * 账号状态
	 */
	private String userState;
	
	/**
	 * 是否隐藏 
	 */
	private String userIsdisplay;
	
	/**
	 * 登录时间
	 */
	private Date loginDate;
	
	/**
	 * 登录地址
	 */
	private String loginAddre;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
	public String getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(String userAdmin) {
		this.userAdmin = userAdmin == null ? null : userAdmin.trim();
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd == null ? null : userPwd.trim();
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel == null ? null : userTel.trim();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany == null ? null : userCompany.trim();
	}

	public String getUserAddre() {
		return userAddre;
	}

	public void setUserAddre(String userAddre) {
		this.userAddre = userAddre == null ? null : userAddre.trim();
	}

	public String getUserHeadpic() {
		return userHeadpic;
	}

	public void setUserHeadpic(String userHeadpic) {
		this.userHeadpic = userHeadpic;
	}

	public String getUserComplsort() {
		return userComplsort;
	}

	public void setUserComplsort(String userComplsort) {
		this.userComplsort = userComplsort == null ? null : userComplsort.trim();
	}

	public String getUserCompldetails() {
		return userCompldetails;
	}

	public void setUserCompldetails(String userCompldetails) {
		this.userCompldetails = userCompldetails == null ? null : userCompldetails.trim();
	}

	public String getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails == null ? null : userDetails.trim();
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginAddre() {
		return loginAddre;
	}

	public void setLoginAddre(String loginAddre) {
		this.loginAddre = loginAddre == null ? null : loginAddre.trim();
	}

	public String getUserIsdisplay() {
		return userIsdisplay;
	}

	public void setUserIsdisplay(String userIsdisplay) {
		this.userIsdisplay = userIsdisplay== null ? null : userIsdisplay.trim();
	}

	public String getUserComplsorts() {
		return userComplsorts;
	}

	public void setUserComplsorts(String userComplsorts) {
		this.userComplsorts = userComplsorts ==null ? null : userComplsorts.trim();
	}

	public String getUserDriverid() {
		return userDriverid;
	}

	public void setUserDriverid(String userDriverid) {
		this.userDriverid = userDriverid==null ? null:userDriverid.trim();
	}
	
	public String getUserOpenid() {
		return userOpenid;
	}

	public void setUserOpenid(String userOpenid) {
		this.userOpenid = userOpenid;
	}
}
