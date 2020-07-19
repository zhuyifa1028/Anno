package com.product.starter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.product.bean.Record;
import com.product.initializer.Starter;

/**
 * 记录信息-启动器
 * 
 * @author ymc
 *
 */
@Component
public class RecordStarter extends Starter {

	@Override
	public void init(ServletContext servlet) {
		Map<String, String> param = null;
		// 设置Record缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(Record.RECORD_IS_DISPLAY_NOT, "否");
			param.put(Record.RECORD_IS_DISPLAY_YES, "是");
			servlet.setAttribute(Record.RECORD_IS_DISPLAY_KEY, param);
		}
		// 设置Record缓存(语言)
		{
			param = new LinkedHashMap<>();
			param.put(Record.RECORD_LANGUAGE_CN, "中文");
			param.put(Record.RECORD_LANGUAGE_US, "英文");
			servlet.setAttribute(Record.RECORD_LANGUAGE_KEY, param);
		}

	}
	@Override
	public void load(ServletContext servlet) {
		return;
	}
}
