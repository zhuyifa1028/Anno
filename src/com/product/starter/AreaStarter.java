package com.product.starter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.product.bean.Area;
import com.product.initializer.Starter;
import com.product.service.AreaService;

import net.sf.json.JSONArray;


/**
 * 区域缓存-启动器
 * 
 * @author ymc
 *
 */
@Component
public class AreaStarter extends Starter {
	@Autowired
	private AreaService areaService;

	@Override
	public void init(ServletContext servlet) {
		Map<String, String> param = null;
		// 设置area缓存(是否隐藏)
		{
			param = new LinkedHashMap<>();
			param.put(Area.AREA_IS_DISPLAY_NOT, "否");
			param.put(Area.AREA_IS_DISPLAY_YES, "是");
			servlet.setAttribute(Area.AREA_IS_DISPLAY_KEY, param);
		}
		// 设置area缓存(语言)
		{
			param = new LinkedHashMap<>();
			param.put(Area.AREA_LANGUAGE_CN, "中文");
			param.put(Area.AREA_LANGUAGE_US, "英文");
			servlet.setAttribute(Area.AREA_LANGUAGE_KEY, param);
		}
		// 设置区域缓存
		String pid = String.valueOf(Area.AREA_DEFAULT_ID);
	    Map<String, String> param1 = new HashMap<>();
		param1.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
		List<Area> areas = areaService.getChilds(param1);
		Map<String, String> keys = new HashMap<>();
		keys.put(pid, "true");
		JSONArray json = areaService.getChildsTrees(areas, keys);
		servlet.setAttribute(Area.AREA_MAP_KEY, json);
		
		// 设置区域缓存英文
		String pids = String.valueOf(Area.AREA_DEFAULT_ID);
		Map<String, String> param1s = new HashMap<>();
		param1s.put("parent_id", String.valueOf(Area.AREA_DEFAULT_ID));
		List<Area> areass = areaService.getChilds(param1s);
		Map<String, String> keyss = new HashMap<>();
		keyss.put(pids, "true");
		JSONArray jsons = areaService.getChildsTreeyw(areass, keyss);
		servlet.setAttribute(Area.AREA_MAPS_KEY, jsons);
	}

}
