package com.product.bean;

import java.util.Date;
import java.util.List;

/**
 * 记录信息-实体类
 * 
 * @author YMC
 *
 */
public class Record extends BaseBean {

	private static final long serialVersionUID = 1L;

	/** 是否隐藏(是) */
	public static final String RECORD_IS_DISPLAY_YES = "1";
	/** 是否隐藏(否) */
	public static final String RECORD_IS_DISPLAY_NOT = "0";
	/** 系统缓存(是否隐藏) */
	public static final String RECORD_IS_DISPLAY_KEY = "RECORD_IS_DISPLAY";

	/** 系统缓存(缓存类别) */
	public static final String RECORD_CACHE_KEY = "RECORD_CACHE_%s";

	/** 语言环境(中文) */
	public static final String RECORD_LANGUAGE_CN = "0";
	/** 语言环境(英文) */
	public static final String RECORD_LANGUAGE_US = "1";
	/** 系统缓存(语言环境) */
	public static final String RECORD_LANGUAGE_KEY = "RECORD_LANGUAGE";

	/**
	 * 司机ID
	 */
	private Integer recordDriverid;

	/**
	 * 安诺租车说明
	 */
	private String recordCarshow;
	/**
	 * 安诺租车标题
	 */
	private String recordCartitle;

	/**
	 * 英文安诺租车说明
	 */
	private String recordUscarshow;
	/**
	 * 英文安诺租车标题
	 */
	private String recordUscartitle;

	/**
	 * 客服电话
	 */
	private String recordKftel;

	/**
	 * 客服图片
	 */
	private String recordKfpic;

	/**
	 * 浏览数量
	 */
	private Integer recordBrowsenumber;

	/**
	 * 培训文章的图片
	 */
	private String recordTrainstartpic;

	/**
	 * 培训标题
	 */
	private String recordTraintitle;

	/**
	 * 培训副标题
	 */
	private String recordTrainvicetitle;

	/***
	 * 培训时间
	 */
	private Date recordTraindate;

	/**
	 * 培训内容简介
	 */
	private String recordTrainbrief;

	/**
	 * 培训内容
	 */
	private String recordTraincontent;

	/**
	 * 语言环境(1:英文、0:中文)
	 */
	private String recordLanguage;

	/**
	 * 是否隐藏(1:是、0:否)
	 */
	private String recordIsDisplay;

	private List<Record> child;

	private Record record;

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public List<Record> getChild() {
		return child;
	}

	public void setChild(List<Record> child) {
		this.child = child;
	}

	public String getRecordLanguage() {
		return recordLanguage;
	}

	public void setRecordLanguage(String recordLanguage) {
		this.recordLanguage = recordLanguage == null ? null : recordLanguage.trim();
	}

	public Integer getRecordDriverid() {
		return recordDriverid;
	}

	public String getRecordCarshow() {
		return recordCarshow;
	}

	public String getRecordKftel() {
		return recordKftel;
	}

	public String getRecordKfpic() {
		return recordKfpic;
	}

	public Integer getRecordBrowsenumber() {
		return recordBrowsenumber;
	}

	public String getRecordTrainstartpic() {
		return recordTrainstartpic;
	}

	public String getRecordTraintitle() {
		return recordTraintitle;
	}

	public String getRecordTrainvicetitle() {
		return recordTrainvicetitle;
	}

	public String getRecordTraincontent() {
		return recordTraincontent;
	}

	public void setRecordDriverid(Integer recordDriverid) {
		this.recordDriverid = recordDriverid;
	}

	public void setRecordCarshow(String recordCarshow) {
		this.recordCarshow = recordCarshow == null ? null : recordCarshow.trim();
	}

	public void setRecordKftel(String recordKftel) {
		this.recordKftel = recordKftel == null ? null : recordKftel.trim();
	}

	public void setRecordKfpic(String recordKfpic) {
		this.recordKfpic = recordKfpic == null ? null : recordKfpic.trim();
	}

	public void setRecordBrowsenumber(Integer recordBrowsenumber) {
		this.recordBrowsenumber = recordBrowsenumber;
	}

	public void setRecordTrainstartpic(String recordTrainstartpic) {
		this.recordTrainstartpic = recordTrainstartpic == null ? null : recordTrainstartpic.trim();
	}

	public void setRecordTraintitle(String recordTraintitle) {
		this.recordTraintitle = recordTraintitle == null ? null : recordTraintitle.trim();
	}

	public void setRecordTrainvicetitle(String recordTrainvicetitle) {
		this.recordTrainvicetitle = recordTrainvicetitle == null ? null : recordTrainvicetitle.trim();
	}

	public void setRecordTraincontent(String recordTraincontent) {
		this.recordTraincontent = recordTraincontent == null ? null : recordTraincontent.trim();
	}

	public String getRecordIsDisplay() {
		return recordIsDisplay;
	}

	public void setRecordIsDisplay(String recordIsDisplay) {
		this.recordIsDisplay = recordIsDisplay == null ? null : recordIsDisplay.trim();
	}

	public Date getRecordTraindate() {
		return recordTraindate;
	}

	public void setRecordTraindate(Date recordTraindate) {
		this.recordTraindate = recordTraindate;
	}

	public String getRecordCartitle() {
		return recordCartitle;
	}

	public void setRecordCartitle(String recordCartitle) {
		this.recordCartitle = recordCartitle == null ? null : recordCartitle.trim();
	}

	public String getRecordUscarshow() {
		return recordUscarshow;
	}

	public void setRecordUscarshow(String recordUscarshow) {
		this.recordUscarshow = recordUscarshow;
	}

	public String getRecordUscartitle() {
		return recordUscartitle;
	}

	public void setRecordUscartitle(String recordUscartitle) {
		this.recordUscartitle = recordUscartitle;
	}

	public String getRecordTrainbrief() {
		return recordTrainbrief;
	}

	public void setRecordTrainbrief(String recordTrainbrief) {
		this.recordTrainbrief = recordTrainbrief;
	}

}
