package com.product.bean;

import java.util.List;

/**
 * 平台栏目-实体类
 * 
 * @author ymc
 */
public class Programa extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	/** 栏目类型(栏目) */
	public static final String PROGRAMA_TYPE_COLUMN = "0";
	/** 栏目类型(信息) */
	public static final String PROGRAMA_TYPE_INFO = "1";
	/** 系统缓存(栏目类型) */
	public static final String PROGRAMA_TYPE_KEY = "PROGRAMA_TYPE";
	
	/** 是否隐藏(是) */
	public static final String IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String PROGRAMA_DISPLAY_TYPE_KEY = "PROGRAMA_DISPLAY_TYPE";
	
	/** 语言环境(中文) */
	public static final String PROGRAMA_LANGUAGE_CN = "0";
	/** 语言环境(英文) */
	public static final String PROGRAMA_LANGUAGE_US = "1";
	/** 系统缓存(语言环境) */
	public static final String PROGRAMA_LANGUAGE_KEY = "PROGRAMA_LANGUAGE";
	
	/** 默认上级主键 */
	public static final int PARENT_ID_DEFAULT = 0;
	/** 默认上级标题 */
	public static final String PARENT_NAME_DEFAULT = "根部栏目";
	
	/** 系统缓存(缓存类别) */
	public static final String PROGRAMA_CACHE_KEY = "PROGRAMA_CACHE_%s";
	
	/** 替换占位符 */
	public static final String REPLACE_PLACEHOLDER = "$";
	/** 空格占位符 */
	public static final String SPACE_PLACEHOLDER = "$nbsp;";
	
	/**
	 * 栏目标题
	 */
	private String programaTitle;

	/**
	 * 栏目地址
	 */
	private String programaUrl;

	/**
	 * 栏目类型(0:栏目、1:信息)
	 */
	private String programaType;

	/**
	 * 栏目简介
	 */
	private String programaBrief;

	/**
	 * 栏目序列
	 */
	private Integer programaSequence;

	/**
	 * 语言环境(中文、英文)
	 */
	private String programaLanguage;

	/**
	 * 是否隐藏(1:是、0:否)
	 */
	private String programaIsDisplay;

	/**
	 * 上级主键
	 */
	private Integer programaParentId;
	
	/**
	 * 权限管理
	 */
	private String adminAdministrate;
	

	public String getAdminAdministrate() {
		return adminAdministrate;
	}

	public void setAdminAdministrate(String adminAdministrate) {
		this.adminAdministrate = adminAdministrate;
	}

	public String getProgramaTitle() {
		return programaTitle;
	}

	public void setProgramaTitle(String programaTitle) {
		this.programaTitle = programaTitle==null? null:programaTitle.trim();
	}

	public String getProgramaUrl() {
		return programaUrl;
	}

	public void setProgramaUrl(String programaUrl) {
		this.programaUrl = programaUrl ==null? null:programaUrl.trim();
	}

	public String getProgramaType() {
		return programaType;
	}

	public void setProgramaType(String programaType) {
		this.programaType = programaType == null ? null : programaType.trim();
	}

	public String getProgramaBrief() {
		return programaBrief;
	}

	public void setProgramaBrief(String programaBrief) {
		this.programaBrief = programaBrief ==null? null:programaBrief.trim();
	}

	public Integer getProgramaSequence() {
		return programaSequence;
	}

	public void setProgramaSequence(Integer programaSequence) {
		this.programaSequence = programaSequence;
	}

	public String getProgramaLanguage() {
		return programaLanguage;
	}

	public void setProgramaLanguage(String programaLanguage) {
		this.programaLanguage = programaLanguage;
	}

	public String getProgramaIsDisplay() {
		return programaIsDisplay;
	}

	public void setProgramaIsDisplay(String programaIsDisplay) {
		this.programaIsDisplay = programaIsDisplay == null ? null : programaIsDisplay.trim();
	}

	public Integer getProgramaParentId() {
		return programaParentId;
	}

	public void setProgramaParentId(Integer programaParentId) {
		this.programaParentId = programaParentId;
	}

	public List<Programa> getChild() {
		return child;
	}

	public void setChild(List<Programa> child) {
		this.child = child;
	}

	public Programa getParent() {
		return parent;
	}

	public void setParent(Programa parent) {
		this.parent = parent;
	}

	private List<Programa> child;

	private Programa parent;
}
