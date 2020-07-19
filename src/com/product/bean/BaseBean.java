package com.product.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础－实体类
 * 
 * @author syl
 *
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String initAddr;
	
	private Date initDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInitAddr() {
		return initAddr;
	}

	public void setInitAddr(String initAddr) {
		this.initAddr = initAddr;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
}