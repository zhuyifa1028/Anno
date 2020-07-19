package com.product.bean;

import java.util.List;

/**
 * 区域字典-实体类
 * 
 * @author YMC
 */
public class Area extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	/** 系统缓存(上级主键) */
	public static final String AREA_PARENT_MAP_KEY = "AREA_PARENT_MAP";
	/** 系统缓存(区域主键) */
	public static final String AREA_ID_MAP_KEY = "AREA_ID_MAP";
	/** 系统缓存(层叠级别区域信息) */
	public static final String AREA_MAP_KEY = "AREA_MAP";
	/** 系统缓存(层叠级别区域信息) */
	public static final String AREA_MAPS_KEY = "AREA_MAPS";

	/** 默认上级主键 */
	public static final Integer AREA_DEFAULT_ID = 0;
	/** 默认上级区域 */
	public static final String AREA_DEFAULT_NAME = "根部区域";

	/** 是否隐藏(是) */
	public static final String AREA_IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String AREA_IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String AREA_IS_DISPLAY_KEY = "AREA_IS_DISPLAY";

	/** 语言环境(中文) */
	public static final String AREA_LANGUAGE_CN = "0";
	/** 语言环境(英文) */
	public static final String AREA_LANGUAGE_US = "1";
	/** 系统缓存(语言环境) */
	public static final String AREA_LANGUAGE_KEY = "AREA_LANGUAGE";

	/**
	 * 是否隐藏(1:是、0:否)
	 */
	private String areaIsDisplay;
	/**
	 * 语言环境(中文、英文)
	 */
	private String areaLanguage;

	
	
	private  String  areaWay;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 区域简称
	 */
	private String areaShort;
	/**
	 * 区域序列
	 */
	private Integer areaSequence;

	/**
	 * 上级主键
	 */
	private Integer areaParentId;
	private List<Area> child;
	private Area parent;

	public String getAreaIsDisplay() {
		return areaIsDisplay;
	}

	public void setAreaIsDisplay(String areaIsDisplay) {
		this.areaIsDisplay = areaIsDisplay;
	}

	public String getAreaLanguage() {
		return areaLanguage;
	}

	public void setAreaLanguage(String areaLanguage) {
		this.areaLanguage = areaLanguage;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	public String getAreaShort() {
		return areaShort;
	}

	public void setAreaShort(String areaShort) {
		this.areaShort = areaShort == null ? null : areaShort.trim();
	}

	public Integer getAreaSequence() {
		return areaSequence;
	}

	public void setAreaSequence(Integer areaSequence) {
		this.areaSequence = areaSequence;
	}

	public Integer getAreaParentId() {
		return areaParentId;
	}

	public void setAreaParentId(Integer areaParentId) {
		this.areaParentId = areaParentId;
	}

	public List<Area> getChild() {
		return child;
	}

	public void setChild(List<Area> child) {
		this.child = child;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public String getAreaWay() {
		return areaWay;
	}

	public void setAreaWay(String areaWay) {
		this.areaWay = areaWay ==null ? null:areaWay.trim();
	}

}
