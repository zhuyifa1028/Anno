package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Area;
import com.product.constants.Constants;
import com.product.dao.AreaDao;
import com.product.service.AreaService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 区域字典-服务类
 * 
 * @author ymc
 *
 */
@Service
public class AreaServiceImpl implements AreaService{
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public Area getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return areaDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Area area, String ip) {
		Date date = new Date();
		if(area.getId() != null && area.getId() > 0) {
			return areaDao.update(area) > 0 ? true : false;
		}
		area.setInitDate(date);
		area.setInitAddr(ip);
		return areaDao.save(area) > 0 ? true : false;
	}

	@Override
	public List<Area> getChilds(Map<String, String> param) {
		return areaDao.childs(param);
	}

	@Override
	public List<Area> getChild(Map<String, String> param) {
		return areaDao.child(param);
	}

	@Override
	public List<Area> getParents(Map<String, String> param) {
		return areaDao.parents(param);
	}

	@Override
	public List<Area> getParent(Map<String, String> param) {
		return areaDao.parent(param);
	}

	@Override
	public JSONArray getChildsTree(List<Area> areas, Map<String, String> keys) {
		JSONArray json = new JSONArray();
		JSONObject flag = new JSONObject();
		hanoi(areas, json, keys, flag);
		return json;
	}
	
	@Override
	public JSONArray getChildsTrees(List<Area> areas, Map<String, String> keys) {
		JSONArray json = new JSONArray();
		JSONObject flag = new JSONObject();
		hanois(areas, json, keys, flag);
		return json;
	}
	
	@Override
	public JSONArray getChildsTreeyw(List<Area> areas, Map<String, String> keys) {
		JSONArray json = new JSONArray();
		JSONObject flag = new JSONObject();
		hanoiyw(areas, json, keys, flag);
		return json;
	}

	@Override
	public List<Area> getList(Map<String, String> param) {
		return areaDao.list(param);
	}

	@Override
	public PageList<Area> getPage(Map<String, String> param) {
		PageBounds bounds = new PageBounds();
		bounds.setContainsTotalCount(true);
		
		if(param == null) {
			param = new HashMap<>();
		}
		
		if(param.containsKey(Constants.PAGE_NUM) && !param.get(Constants.PAGE_NUM).equals(0)) {
			bounds.setPage(Integer.valueOf(param.get(Constants.PAGE_NUM)));
		} else {
			bounds.setPage(Constants.PAGE_DEFAULT_NUM);
		}
		if(param.containsKey(Constants.PAGE_SIZE) && !param.get(Constants.PAGE_SIZE).equals(0)) {
			bounds.setLimit(Integer.valueOf(param.get(Constants.PAGE_SIZE)));
		} else {
			bounds.setLimit(Constants.PAGE_DEFAULT_SIZE);
		}
		return areaDao.page(param, bounds);
	}
	/**
	 * 递归遍历
	 * 
	 * @param modules
	 * 			集合模块
	 * @param json
	 * 			返回结果
	 * @param keys
	 * 			对比选择
	 * @param flag
	 * 			是否选择
	 */
	public void hanoi(List<Area> areas, JSONArray json, Map<String, String> keys, JSONObject flag) {
		flag.put("flag", false);
		for (Area area : areas) {
			JSONObject tmp = new JSONObject();
			
			tmp.put("id", String.valueOf(area.getId()));
			tmp.put("name", area.getAreaName());
			tmp.put("pid", String.valueOf(area.getAreaParentId()));
			if(keys != null && keys.containsKey(String.valueOf(area.getId()))) {
				flag.put("flag", true);
				tmp.put("checked", "true");
			}
		
			List<Area> childs = area.getChild();
			if(childs != null && !childs.isEmpty()) {
				JSONArray child = new JSONArray();
				if(flag.getBoolean("flag")) {
					flag = new JSONObject();
				}
				hanoi(childs, child, keys, flag);
				if(flag.getBoolean("flag")) {
					tmp.put("open", "true");
				}
				tmp.put("children", child);
			}
			json.add(tmp);
		}
	}
	
	/**
	 * 递归遍历
	 * 
	 * @param modules
	 * 			集合模块
	 * @param json
	 * 			返回结果
	 * @param keys
	 * 			对比选择
	 * @param flag
	 * 			是否选择
	 */
	public void hanois(List<Area> areas, JSONArray json, Map<String, String> keys, JSONObject flag) {
		flag.put("flag", false);
		for (Area area : areas) {
			if (area.getAreaIsDisplay().equals("0")) {
			JSONObject tmp = new JSONObject();
				tmp.put("value", String.valueOf(area.getId()));
				tmp.put("text", area.getAreaName());
			
			if(keys != null && keys.containsKey(String.valueOf(area.getId()))) {
				flag.put("flag", true);
				tmp.put("checked", "true");
			}
		
			List<Area> childs = area.getChild();
			if(childs != null && !childs.isEmpty()) {
				JSONArray child = new JSONArray();
				if(flag.getBoolean("flag")) {
					flag = new JSONObject();
				}
				hanois(childs, child, keys, flag);
				if(flag.getBoolean("flag")) {
					tmp.put("open", "true");
				}
				tmp.put("children", child);
			}
			json.add(tmp);
			}
		}
	}
	
	/**
	 * 递归遍历英文
	 * 
	 * @param modules
	 * 			集合模块
	 * @param json
	 * 			返回结果
	 * @param keys
	 * 			对比选择
	 * @param flag
	 * 			是否选择
	 */
	public void hanoiyw(List<Area> areas, JSONArray json, Map<String, String> keys, JSONObject flag) {
		flag.put("flag", false);
		for (Area area : areas) {
			if (area.getAreaIsDisplay().equals("0")) {
			JSONObject tmp = new JSONObject();
			tmp.put("value", String.valueOf(area.getId()));
			tmp.put("text", area.getAreaWay());
			if(keys != null && keys.containsKey(String.valueOf(area.getId()))) {
				flag.put("flag", true);
				tmp.put("checked", "true");
			}
		
			List<Area> childs = area.getChild();
			if(childs != null && !childs.isEmpty()) {
				JSONArray child = new JSONArray();
				if(flag.getBoolean("flag")) {
					flag = new JSONObject();
				}
				hanoiyw(childs, child, keys, flag);
				if(flag.getBoolean("flag")) {
					tmp.put("open", "true");
				}
				tmp.put("children", child);
			}
			json.add(tmp);
			}
		}
	}
}
