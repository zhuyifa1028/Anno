package com.product.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.product.constants.Constants;

/**
 * 后台登录-拦截器
 * 
 * @author syl
 *
 */
public class AdminContextInterceptor implements HandlerInterceptor {
	
	/** 返回地址 */
	private String url;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		HttpSession session = request.getSession();
		ServletContext servlet = session.getServletContext();
		Object is_interceptor = servlet.getAttribute(Constants.ADMIN_CONTEXT_INTERCEPTOR);
		if(session.getAttribute(Constants.ADMIN_USER_SESSION) == null && is_interceptor != null && is_interceptor.equals(Constants.CONTEXT_INTERCEPTOR_YES)) {
			String header = request.getHeader("x-requested-with");
			if(StringUtils.isNotBlank(header) && header.equalsIgnoreCase("XMLHttpRequest")) {
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			} else {
				response.sendRedirect(request.getContextPath() + url);
			}
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}