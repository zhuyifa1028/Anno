package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Programa;
import com.product.constants.Constants;
import com.product.dao.ProgramaDao;
import com.product.service.ProgramaService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 栏目信息-服务类
 * 
 * @author ymc
 *
 */
@Service
public class ProgramaServiceImpl implements ProgramaService {

	@Autowired
	private ProgramaDao programaDao;
	
	/**
	 * 递归遍历
	 * 
	 * @param modules
	 * 			集合模块
	 * @param json
	 * 			返回结果
	 * @param id
	 * 			模块主键
	 * @param flag
	 * 			是否选择
	 */
	public void hanoi(List<Programa> programas, JSONArray json, Map<String, String> ids, JSONObject flag) {
		flag.put("flag", false);
		for (Programa programa : programas) {
			JSONObject tmp = new JSONObject();
			
			tmp.put("id", String.valueOf(programa.getId()));
			tmp.put("name", programa.getProgramaTitle());
			tmp.put("pid", String.valueOf(programa.getProgramaParentId()));
			tmp.put("url", programa.getProgramaUrl());
			if(ids.containsKey(String.valueOf(programa.getId()))) {
				flag.put("flag", true);
				tmp.put("checked", "true");
			}
		
			List<Programa> childs = programa.getChild();
			if(childs != null && !childs.isEmpty()) {
				JSONArray child = new JSONArray();
				if(flag.getBoolean("flag")) {
					flag = new JSONObject();
				}
				hanoi(childs, child, ids, flag);
				if(flag.getBoolean("flag")) {
					tmp.put("open", "true");
				}
				tmp.put("children", child);
			}
			json.add(tmp);
		}
	}

	@Override
	public Programa getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return programaDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Programa programa, String ip) {
		Date date = new Date();
		if(programa.getId() != null && programa.getId() > 0) {
			return programaDao.update(programa) > 0 ? true : false;
		}
		programa.setInitDate(date);
		programa.setInitAddr(ip);
		return programaDao.save(programa) > 0 ? true : false;
	}
	
	@Override
	public List<Programa> getChilds(Map<String, String> param) {
		return programaDao.childs(param);
	}
	
	@Override
	public List<Programa> getChild(Map<String, String> param) {
		return programaDao.child(param);
	}
	
	@Override
	public JSONArray getChilds(List<Programa> modules, Map<String, String> ids) {
		JSONArray json = new JSONArray();
		JSONObject flag = new JSONObject();
		hanoi(modules, json, ids, flag);
		return json;
	}
	
	@Override
	public List<Programa> getParents(Map<String, String> param) {
		return programaDao.parents(param);
	}
	
	@Override
	public List<Programa> getParent(Map<String, String> param) {
		return programaDao.parent(param);
	}

	@Override
	public List<Programa> getList(Map<String, String> param) {
		return programaDao.list(param);
	}

	@Override
	public PageList<Programa> getPage(Map<String, String> param) {
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
		return programaDao.page(param, bounds);
	}

}
