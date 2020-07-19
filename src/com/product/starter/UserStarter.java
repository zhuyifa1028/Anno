package com.product.starter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.product.bean.User;
import com.product.initializer.Starter;

/**
 * 用户缓存-启动器
 * 
 * @author ymc
 *
 */
@Component
public class UserStarter extends Starter {

	@Override
	public void init(ServletContext servlet) {

		Map<String, String> param = null;
		// 设置用户缓存(账号状态)
		{
			param = new LinkedHashMap<>();
			param.put(User.USER_STATE_NOT, "启用");
			param.put(User.USER_STATE_YES, "禁用");
			servlet.setAttribute(User.USER_STATE_KEY, param);
		}

		// 设置用户缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(User.USER_IS_DISPLAY_NOT, "否");
			param.put(User.USER_IS_DISPLAY_YES, "是");
			servlet.setAttribute(User.USER_IS_DISPLAY_KEY, param);
		}
	}
}
