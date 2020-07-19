package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Driver;
import com.product.constants.Constants;
import com.product.dao.DriverDao;
import com.product.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverDao driverDao;

	@Override
	public int delById(int id) {
		// TODO Auto-generated method stub
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return driverDao.del(param);
	}

	@Override
	public Driver getById(int id) {
		// TODO Auto-generated method stub
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return driverDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Driver driver, String ip) {
		// TODO Auto-generated method stub
		Date date = new Date();
		if (driver.getId() != null && driver.getId() > 0) {
			return driverDao.update(driver) > 0 ? true : false;
		}
		driver.setInitDate(date);
		driver.setInitAddr(ip);
		return driverDao.save(driver) > 0 ? true : false;
	}

	@Override
	public List<Driver> getList(Map<String, String> param) {
		queryProcessing(param);
		return driverDao.list(param);
	}

	@Override
	public PageList<Driver> getPage(Map<String, String> param) {
		PageBounds bounds = new PageBounds();
		bounds.setContainsTotalCount(true);

		if (param == null) {
			param = new HashMap<>();
		}

		if (param.containsKey(Constants.PAGE_NUM) && !param.get(Constants.PAGE_NUM).equals(0)) {
			bounds.setPage(Integer.valueOf(param.get(Constants.PAGE_NUM)));
		} else {
			bounds.setPage(Constants.PAGE_DEFAULT_NUM);
		}
		if (param.containsKey(Constants.PAGE_SIZE) && !param.get(Constants.PAGE_SIZE).equals(0)) {
			bounds.setLimit(Integer.valueOf(param.get(Constants.PAGE_SIZE)));
		} else {
			bounds.setLimit(Constants.PAGE_DEFAULT_SIZE);
		}

		/**
		 * 查询前处理参数：保存原来的参数，为日期参数添加" 00:00:00"或" 23:59:59"
		 */
		Map<String, String> storage = new HashMap<>();
		storage.putAll(param);
		queryProcessing(param);

		PageList<Driver> pageList = driverDao.page(param, bounds);

		param.putAll(storage);

		return pageList;
	}

	private void queryProcessing(Map<String, String> param) {
		if (param != null) {
			Set<String> keySet = param.keySet();
			for (String key : keySet) {
				if (key.contains("gt_") || key.contains("ge_")) {
					String value = param.get(key);
					param.put(key, value + " 00:00:00");
				}
				if (key.contains("lt_") || key.contains("le_")) {
					String value = param.get(key);
					param.put(key, value + " 23:59:59");
				}
			}
		}

	}

	@Override
	public long getCount(Map<String, Object> param) {
		return driverDao.count(param);
	}

}
