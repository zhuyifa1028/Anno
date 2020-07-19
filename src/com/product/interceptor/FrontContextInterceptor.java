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
 * 微信登录-拦截器
 * 
 * @author syl
 *
 */
public class FrontContextInterceptor implements HandlerInterceptor {
	
	/** 返回地址 */
	private String url;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		HttpSession session = request.getSession();
		ServletContext servlet = session.getServletContext();
		Object is_interceptor = servlet.getAttribute(Constants.FRONT_CONTEXT_INTERCEPTOR);
		if(session.getAttribute(Constants.FRONT_USER_SESSION) == null && is_interceptor != null && is_interceptor.equals(Constants.CONTEXT_INTERCEPTOR_YES)) {
			String header = request.getHeader("x-requested-with");
			if(StringUtils.isNotBlank(header) && header.equalsIgnoreCase("XMLHttpRequest")) {
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			} else {
				StringBuffer tmp = request.getRequestURL();
				String query = request.getQueryString();
				if(StringUtils.isNotBlank(query)) {
					tmp.append("?").append(query);
				}
				
//				FlashMap flash = RequestContextUtils.getOutputFlashMap(request);
//				flash.put(Constants.REDIRECT_FORWARD_URL, tmp.toString());
//				FlashMapManager manager = RequestContextUtils.getFlashMapManager(request);
//				manager.saveOutputFlashMap(flash, request, response);
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