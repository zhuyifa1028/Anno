package com.product.bean;

import java.util.Date;

/**
 * 
 * 管理员-实体类
 * 
 * @author YMC
 */
public class Admin extends BaseBean {

	private static final long serialVersionUID = 1L;

	/** 默认密码 */
	public static final String ADMIN_DEFAULT_PWD = "123456";

	/** 管理员状态(启用) */
	public static final String ADMIN_STATE_NOT = "0";
	/** 管理员状态(禁用) */
	public static final String ADMIN_STATE_YES = "1";
	/** 系统缓存(管理员状态) */
	public static final String ADMIN_STATE_KEY = "ADMIN_STATE";

	/** 是否隐藏(是) */
	public static final String ADMIN_IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String ADMIN_IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String ADMIN_IS_DISPLAY_KEY = "ADMIN_IS_DISPLAY";

	/** 语言环境(中文) */
	public static final String ADMIN_LANGUAGE_CN = "0";
	/** 语言环境(英文) */
	public static final String ADMIN_LANGUAGE_US = "1";
	/** 系统缓存(语言环境) */
	public static final String ADMIN_LANGUAGE_KEY = "ADMIN_LANGUAGE";
	
	/** 权限管理(开通) */
	public static final String ADMIN_ADMINISTRATE_NOT = "0";
	/** 权限管理(未开通) */
	public static final String ADMIN_ADMINISTRATE_YES = "1";
	/** 权限缓存(权限管理) */
	public static final String ADMIN_ADMINISTRATE_KEY = "ADMIN_ADMINISTRATE";

	/** 用户管理(开通) */
	public static final String ADMIN_ADMINUSER_NOT = "0";
	/** 用户管理(未开通) */
	public static final String ADMIN_ADMINUSER_YES = "1";
	/** 用户缓存(用户管理) */
	public static final String ADMIN_ADMINUSER_KEY = "ADMIN_ADMINUSER";

	/** 司机管理(开通) */
	public static final String ADMIN_ADMINDRIVER_NOT = "0";
	/** 司机管理(未开通) */
	public static final String ADMIN_ADMINDRIVER_YES = "1";
	/** 司机缓存(司机管理) */
	public static final String ADMIN_ADMINDRIVER_KEY = "ADMIN_ADMINDRIVER";
	
	/** 统计管理(开通) */
	public static final String ADMIN_ADMINTJ_NOT = "0";
	/** 统计管理(未开通) */
	public static final String ADMIN_ADMINTJ_YES = "1";
	/** 统计缓存(统计管理) */
	public static final String ADMIN_ADMINTJ_KEY = "ADMIN_ADMINTJ";
	
	/** 培训管理(开通) */
	public static final String ADMIN_ADMINPX_NOT = "0";
	/** 培训管理(未开通) */
	public static final String ADMIN_ADMINPX_YES = "1";
	/** 培训缓存(培训管理) */
	public static final String ADMIN_ADMINPX_KEY = "ADMIN_ADMINPX";
	
	/** 用户确定管理(开通) */
	public static final String ADMIN_ADMINUC_NOT = "0";
	/** 用户确定管理(未开通) */
	public static final String ADMIN_ADMINUC_YES = "1";
	/** 用户确定缓存(用户确定管理) */
	public static final String ADMIN_ADMINUC_KEY = "ADMIN_ADMINUC";
	
	/** 投诉反馈(开通) */
	public static final String ADMIN_ADMINTS_NOT = "0";
	/** 投诉反馈(未开通) */
	public static final String ADMIN_ADMINTS_YES = "1";
	/** 投诉反馈缓存(投诉反馈) */
	public static final String ADMIN_ADMINTS_KEY = "ADMIN_ADMINTS";
	
	/** 帮助中心(开通) */
	public static final String ADMIN_ADMINBZ_NOT = "0";
	/** 帮助中心(未开通) */
	public static final String ADMIN_ADMINBZ_YES = "1";
	/** 帮助中心缓存(帮助中心) */
	public static final String ADMIN_ADMINBZ_KEY = "ADMIN_ADMINBZ";
	
	/** 飞机火车管理(开通) */
	public static final String ADMIN_ADMINAREA_NOT = "0";
	/** 飞机火车管理(未开通) */
	public static final String ADMIN_ADMINAREA_YES = "1";
	/** 飞机火车管理缓存(飞机火车管理) */
	public static final String ADMIN_ADMINAREA_KEY = "ADMIN_ADMINAREA";
	
	/**
	 * 管理员ID
	 */
	private Integer adminId;
	/**
	 * 管理员名称
	 */
	private String adminName;
	/**
	 * 管理员密码
	 */
	private String adminPwd;
	/**
	 * 管理员电话
	 */
	private String adminTel;
	/**
	 * 管理员头像
	 */
	private String adminPic;

	/**
	 * 是否隐藏(1:是、0:否)
	 */
	private String adminIsDisplay;
	/**
	 * 语言环境(中文、英文)
	 */
	private String adminLanguage;

	/**
	 * 权限管理
	 */
	private String adminAdministrate;
	/**
	 * 用户管理
	 */
	private String adminAdminuser;

	/**
	 * 司机管理
	 */
	private String adminAdmindriver;
	/**
	 * 统计管理
	 */
	private String adminAdmintj;
	/**
	 * 培训管理
	 */
	private String adminAdminpx;
	/**
	 * 用户确认
	 */
	private String adminAdminuc;
	/**
	 * 投诉反馈
	 */
	private String adminAdmints;
	/**
	 * 帮助中心
	 */
	private String adminAdminbz;
	/**
	 * 飞机火车管理
	 */
	private String adminAdminarea;
	/**
	 * 管理员状态
	 */
	private String adminState;

	/**
	 * 登录时间
	 */
	private Date loginDate;

	/**
	 * 登录地址
	 */
	private String loginAddre;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName == null ? null : adminName.trim();
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd == null ? null : adminPwd.trim();
	}

	public String getAdminTel() {
		return adminTel;
	}

	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel == null ? null : adminTel.trim();
	}

	public String getAdminState() {
		return adminState;
	}

	public void setAdminState(String adminState) {
		this.adminState = adminState == null ? null : adminState.trim();
	}

	public String getAdminPic() {
		return adminPic;
	}

	public void setAdminPic(String adminPic) {
		this.adminPic = adminPic;
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

	public String getAdminIsDisplay() {
		return adminIsDisplay;
	}

	public void setAdminIsDisplay(String adminIsDisplay) {
		this.adminIsDisplay = adminIsDisplay;
	}

	public String getAdminLanguage() {
		return adminLanguage;
	}

	public void setAdminLanguage(String adminLanguage) {
		this.adminLanguage = adminLanguage;
	}

	public String getAdminAdministrate() {
		return adminAdministrate;
	}

	public void setAdminAdministrate(String adminAdministrate) {
		this.adminAdministrate = adminAdministrate;
	}

	public String getAdminAdminuser() {
		return adminAdminuser;
	}

	public void setAdminAdminuser(String adminAdminuser) {
		this.adminAdminuser = adminAdminuser;
	}

	public String getAdminAdmindriver() {
		return adminAdmindriver;
	}

	public void setAdminAdmindriver(String adminAdmindriver) {
		this.adminAdmindriver = adminAdmindriver;
	}

	public String getAdminAdmintj() {
		return adminAdmintj;
	}

	public void setAdminAdmintj(String adminAdmintj) {
		this.adminAdmintj = adminAdmintj;
	}

	public String getAdminAdminpx() {
		return adminAdminpx;
	}

	public void setAdminAdminpx(String adminAdminpx) {
		this.adminAdminpx = adminAdminpx;
	}

	public String getAdminAdminuc() {
		return adminAdminuc;
	}

	public void setAdminAdminuc(String adminAdminuc) {
		this.adminAdminuc = adminAdminuc;
	}

	public String getAdminAdmints() {
		return adminAdmints;
	}

	public void setAdminAdmints(String adminAdmints) {
		this.adminAdmints = adminAdmints;
	}

	public String getAdminAdminbz() {
		return adminAdminbz;
	}

	public void setAdminAdminbz(String adminAdminbz) {
		this.adminAdminbz = adminAdminbz;
	}

	public String getAdminAdminarea() {
		return adminAdminarea;
	}

	public void setAdminAdminarea(String adminAdminarea) {
		this.adminAdminarea = adminAdminarea;
	}

}
