package com.product.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核－实体类
 * 
 * @author syl
 *
 */
public class AuditBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 审核人员(默认) */
	public static final Integer DEFAULT_AUDIT_ID = 0;
	
	/** 是否通过(否) */
	public static final String AUDIT_STATUS_NOT = "0";
	/** 是否通过(是) */
	public static final String AUDIT_STATUS_YES = "1";
	/** 系统缓存(是否通过) */
	public static final String AUDIT_STATUS_KEY = "AUDIT_STATUS";
	
	/**
	 * 审核人员
	 */
	private Integer auditId;
	
	/**
	 * 审核时间
	 */
	private Date auditDate;
	
	/**
	 * 审核地址
	 */
	private String auditAddr;
	
	/**
	 * 是否通过(0:否、1:是)
	 */
	private String auditStatus;
	
	/**
	 * 审核备注
	 */
	private String auditNote;

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditAddr() {
		return auditAddr;
	}

	public void setAuditAddr(String auditAddr) {
		this.auditAddr = auditAddr;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
}