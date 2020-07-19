package com.product.log4j.appender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.product.constants.Constants;
import com.product.utils.DesUtils;

/**
 * 自定义日志处理器－手机短信
 * 
 * @author syl
 *
 */
public class SMSAppender extends AppenderSkeleton {
	
	private String url;			//请求地址
	private String username;	//用名名称
	private String password;	//用户密码
	private String phone;		//用户手机
	
	@Override
	public void activateOptions() {
		if(StringUtils.isBlank(url)) {
			super.errorHandler.error("No url set for appender named ["+name+"].");
		}
	}

	@Override
	public synchronized void close() {
		this.closed = true;
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			StringBuffer param = new StringBuffer();
			param.append("action").append("=").append("send");
			param.append("&").append("account").append("=").append(username);
			param.append("&").append("password").append("=").append(password);
			param.append("&").append("mobile").append("=").append(phone);
			if(layout != null) {
				param.append("&").append("content").append("=").append(layout.format(event));
			} else {
				super.errorHandler.error("No layout set for appender named ["+name+"].");
				return;
			}
			StringBuffer buffer = new StringBuffer();
			try {
				URL basis = new URL(url);
				URLConnection connection = basis.openConnection();
				
				// 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				
				connection.setDoOutput(true);
				connection.setDoInput(true);
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.println(param);
				out.flush();
				
				connection.connect();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line;
	            while ((line = in.readLine()) != null) {
	            	buffer.append(line);
	            }
	            in.close();
	            
	            if(!buffer.toString().contains("\"returnstatus\":\"Success\"")) {
	            	super.errorHandler.error("SMS transmission failure");
	            }
	            
			} catch (Exception e) {
				super.errorHandler.error("Transmits the POST request to appear exceptionally");
				e.printStackTrace();
			}
		} else {
			super.errorHandler.error("No username or password set for appender named ["+name+"].");
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(StringUtils.isNotBlank(password)) {
			try {
				DesUtils des = new DesUtils(Constants.PRIVATE_SECRET);
				this.password = des.decrypt(password);
			} catch (Exception e) {
				super.errorHandler.error("sms des error");
				e.printStackTrace();
			}
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}