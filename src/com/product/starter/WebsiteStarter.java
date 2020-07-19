package com.product.starter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.product.bean.Website;
import com.product.initializer.Starter;
import com.product.service.WebsiteService;

/**
 * 网站信息缓存-启动器
 * 
 * @author ymc
 *
 */
@Component
public class WebsiteStarter extends Starter {

	@Autowired
	private WebsiteService websiteService;
	
	@Override
	public void init(ServletContext servlet) {
		// 是否拦截后台登录
		//servlet.setAttribute(Constants.ADMIN_CONTEXT_INTERCEPTOR, Constants.CONTEXT_INTERCEPTOR_NOT);
	}

	@Override
	public void load(ServletContext servlet) {
		Map<String, String> param = new HashMap<>();
		param.put("order_id", "desc");
		List<Website> websites = websiteService.getList(null);
		if(websites != null && !websites.isEmpty() && websites.size() > 0) {
			servlet.setAttribute(Website.DEFAULT_WEBSITE_KEY, websites.get(0));
		}
	}
}