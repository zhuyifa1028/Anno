package com.product.result;

/**
 * 结果状态-枚举类
 * 
 * @author syl
 *
 */
public enum Status {
	
	SUCCESS("1", "成功"), FAILED("0", "失败"), ERROR("-1", "错误");
	
	/**代码*/
	private String code;
	/**消息*/
	private String message;
	
	Status(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}