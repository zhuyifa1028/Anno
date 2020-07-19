package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Record;
import com.product.constants.Constants;
import com.product.dao.RecordDao;
import com.product.service.RecordService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 记录信息-服务类
 * 
 * @author ymc
 *
 */
@Service
public class RecordServiceImpl implements RecordService {
	@Autowired
	private RecordDao recordDao;
	
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
	public void hanoi(List<Record> records, JSONArray json, Map<String, String> ids, JSONObject flag) {
		flag.put("flag", false);
		for (Record record : records) {
			JSONObject tmp = new JSONObject();
			
			tmp.put("id", String.valueOf(record.getId()));
			
			if(ids.containsKey(String.valueOf(record.getId()))) {
				flag.put("flag", true);
				tmp.put("checked", "true");
			}
		
			List<Record> childs = record.getChild();
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
	public List<Record> getList(Map<String, String> param) {
		return recordDao.list(param);
	}

	@Override
	public PageList<Record> getPage(Map<String, String> param) {
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
		return recordDao.page(param, bounds);
	}

	@Override
	public Record getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return recordDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Record record, String ip) {
		Date date = new Date();
		if(record.getId() != null && record.getId() > 0) {
			return recordDao.update(record) > 0 ? true : false;
		}
		record.setInitDate(date);
		record.setInitAddr(ip);
		return recordDao.save(record) > 0 ? true : false;
	}

	@Override
	public JSONArray getChilds(List<Record> modules, Map<String, String> ids) {
		JSONArray json = new JSONArray();
		JSONObject flag = new JSONObject();
		hanoi(modules, json, ids, flag);
		return json;
	}
    //浏览数量
	@Override
	public Record browse(String id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		Record record = recordDao.get(param);
		if(record != null) {
			Integer total = record.getRecordBrowsenumber();
			if(total != null) {
				total ++;
			} else {
				total = 0;
			}
			// 修改浏览次数
			Record tmp = new Record();
			tmp.setId(record.getId());
			tmp.setRecordBrowsenumber(total);
			if(recordDao.update(tmp) > 0) {
				return record;
			}
		}
		return null;
	}


	@Override
	public List<Record> getChilds(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Record> getChild(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Record> getParents(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Record> getParent(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
