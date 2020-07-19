package com.product.log4j.appender;

import org.apache.log4j.Priority;

/**
 * 自定义日志处理器－控制台
 * 
 * @author syl
 *
 */
public class ConsoleAppender extends org.apache.log4j.ConsoleAppender {

	//是否按优先级输出
	private boolean append = true;
	
	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		if(append) {
			return super.isAsSevereAsThreshold(priority);
		} else {
			//是判断是否相等，而不判断优先级
			return (super.threshold == null) || super.threshold.equals(priority);
		}
	}

	public boolean getAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
}