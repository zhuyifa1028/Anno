package com.product.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.product.bean.Programa;
import com.product.constants.Constants;

/**
 * 国际匹配-拦截器
 * 
 * @author syl
 *
 */
public class LocaleContextInterceptor implements HandlerInterceptor {

	private static Log tag = LogFactory.getLog("tag");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
		try {
			if(view != null) {
				Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
				if(Locale.US.equals(locale)) {
					view.addObject("locale", Programa.PROGRAMA_LANGUAGE_US);
				} else {
					view.addObject("locale", Programa.PROGRAMA_LANGUAGE_CN);
				}
			}
		} catch (Exception e) {
			view.addObject(Constants.MODEL_MESSAGE, "参数错误");
			tag.error(e.getMessage(), e);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
}