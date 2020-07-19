package com.product.result;

/**
 * 结果-处理类
 * 
 * @author syl
 *
 */
public class JsonResult {

	/**代码*/
	private String code;
	/**消息*/
	private String message;
	/**结果*/
	private Object result;
	
	public JsonResult(Status status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}
	
	public JsonResult(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public JsonResult(String code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}
	
	public boolean isSucceed() {
		if(this.code.equals(Status.SUCCESS.getCode())) {
			return true;
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		if(this.code.equals(Status.ERROR.getCode())) {
			this.result = null;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}