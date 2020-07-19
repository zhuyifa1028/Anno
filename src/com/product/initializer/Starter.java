package com.product.initializer;

import javax.servlet.ServletContext;

/**
 * 系统缓存-抽象类
 * 
 * @author syl
 *
 */
public abstract class Starter {

	/**
	 * 加载静态资源
	 * 
	 * @param servlet
	 */
	public abstract void init(ServletContext servlet);
	
	/**
	 * 加载动态资源
	 * 
	 * @param servlet
	 */
	public void load(ServletContext servlet) {
		return;
	}
}