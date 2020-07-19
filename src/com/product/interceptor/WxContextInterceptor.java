package com.product.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.product.constants.Constants;
import com.product.utils.StrUtils;

/**
 * 微信登录-拦截器
 * 
 * @author syl
 *
 */
public class WxContextInterceptor implements HandlerInterceptor {

	/** 返回地址 */
	private String url;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean flag = false;
		HttpSession session = request.getSession();
		String openid = (String) session.getAttribute(Constants.WEB_USER_OPENID_SESSION);
		if (StrUtils.isEmpty(openid) && session.getAttribute(Constants.WEB_USER_SESSION) == null) {
			String header = request.getHeader("x-requested-with");
			String referer = null;
			if (StringUtils.isNotBlank(header) && header.equalsIgnoreCase("XMLHttpRequest")) {
				referer = request.getHeader("Referer");

				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			} else {
				StringBuffer tmp = request.getRequestURL();
				String query = request.getQueryString();
				if (StringUtils.isNotBlank(query)) {
					tmp.append("?").append(query);
				}
				referer = tmp.toString();

			}
			String Url = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + url
					+ "?ref=" + referer;
			StrUtils.getCode(Url, response);
		} else {
			flag = true;

		}

		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}