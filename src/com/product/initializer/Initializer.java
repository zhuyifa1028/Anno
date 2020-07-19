package com.product.initializer;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系统缓存-监听器
 * 
 * @author syl
 *
 */
public class Initializer implements ServletContextListener  {
	
	private static Log tag = LogFactory.getLog("tag");

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		WebApplicationContext spring = WebApplicationContextUtils.getWebApplicationContext(sc);
		Map<String, Starter> starters = spring.getBeansOfType(Starter.class);
		for (Map.Entry<String, Starter> entry : starters.entrySet()) {
			Starter starter = entry.getValue();
			if(starter != null) {
				starter.init(sc);
				starter.load(sc);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		Enumeration<String> attrs = sc.getAttributeNames();
		while (attrs.hasMoreElements()) {
			String attr = attrs.nextElement();
			if(attr.endsWith("Starter")) {
				Object value = sc.getAttribute(attr);
				if (value instanceof DisposableBean) {
					try {
						((DisposableBean) value).destroy();
					}catch (Exception ex) {
						tag.error("Couldn't invoke destroy method of attribute with name '" + attr + "'", ex);
					}
				}
			}
		}
	}
}