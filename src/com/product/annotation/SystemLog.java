package com.product.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日子-注解
 * 
 * @author syl
 *
 */
@Target(ElementType.METHOD)//注解定义在方法上
@Retention(RetentionPolicy.RUNTIME)//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented//该注解将被包含在javadoc中
public @interface SystemLog {
	
	/**
	 * 操作描述
	 * 
	 * @return
	 */
	public String description() default "";
}