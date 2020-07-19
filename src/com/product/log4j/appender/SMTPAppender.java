package com.product.log4j.appender;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Priority;

import com.product.constants.Constants;
import com.product.utils.DesUtils;

/**
 * 自定义日志处理器－电子邮件
 * 
 * @author syl
 *
 */
public class SMTPAppender extends org.apache.log4j.net.SMTPAppender {

	/**
	 * 密码解密
	 */
	@Override
	public void setSMTPPassword(String password) {
		if(StringUtils.isNotBlank(password)) {
			try {
				DesUtils des = new DesUtils(Constants.PRIVATE_SECRET);
				password = des.decrypt(password);
			} catch (Exception e) {
				super.errorHandler.error("smtp des error");
				e.printStackTrace();
			}
		}
		super.setSMTPPassword(password);
	}
	
	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		return (super.threshold == null) || super.threshold.equals(priority);
	}
}