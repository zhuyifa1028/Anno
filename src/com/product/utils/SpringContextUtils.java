package com.product.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 对象管理-工具类
 * 
 * @author syl
 *
 */
public class SpringContextUtils {

	private static WebApplicationContext context;
	static {
		context = ContextLoader.getCurrentWebApplicationContext();
	}
	
	/**
	 * 获取管理对象
	 * 
	 * @param name
	 * 			对象名称
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		if(context != null) {
			return context.getBean(name);
		}
		return null;
	}
	
	/**
	 * 获取管理对象
	 * 
	 * @param name
	 * 			对象名称
	 * @param args
	 * 			参数集合
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name, Object... args) throws BeansException {
		if(context != null) {
			return context.getBean(name, args);
		}
		return null;
	}
	
	/**
	 * 获取管理对象
	 * 
	 * @param name
	 * 			对象名称
	 * @param clasz
	 * 			对象类型
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> clasz) throws BeansException {
		if(context != null) {
			return context.getBean(name, clasz);
		}
		return null;
	}
	
	/**
	 * 获取管理对象
	 * 
	 * @param clasz
	 * 			对象类型
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> clasz) throws BeansException {
		if(context != null) {
			return context.getBean(clasz);
		}
		return null;
	}
	
	/**
	 * 获取管理对象
	 * 
	 * @param clasz
	 * 			对象类型
	 * @param args
	 * 			参数集合
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> clasz, Object... args) throws BeansException {
		if(context != null) {
			return context.getBean(clasz, args);
		}
		return null;
	}
	
	/**
	 * 获取管理对象集合
	 * 
	 * @param clasz
	 * 			对象类型
	 * @return
	 * @throws BeansException
	 */
	public static <T> Map<String, T> getBeans(Class<T> clasz) throws BeansException {
		if(context != null) {
			return context.getBeansOfType(clasz);
		}
		return null;
	}
}