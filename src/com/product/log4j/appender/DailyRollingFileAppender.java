package com.product.log4j.appender;

import org.apache.log4j.Priority;

/**
 * 自定义日志处理器－定期回滚
 * 
 * @author syl
 *
 */
public class DailyRollingFileAppender extends org.apache.log4j.DailyRollingFileAppender {

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		return (super.threshold == null) || super.threshold.equals(priority);
	}
}