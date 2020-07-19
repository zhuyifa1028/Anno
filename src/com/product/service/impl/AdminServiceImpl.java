package com.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.product.bean.Admin;
import com.product.constants.Constants;
import com.product.dao.AdminDao;
import com.product.service.AdminService;

/**
 * admin-服务类
 * 
 * @author ymc
 *
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Override
	public Admin getById(int id) {
		Map<String, String> param = new HashMap<>();
		param.put("id", String.valueOf(id));
		return adminDao.get(param);
	}

	@Override
	public boolean saveOrUpdate(Admin admin, String ip) {
		Date date = new Date();
		if (admin.getId() != null && admin.getId() > 0) {
			return adminDao.update(admin) > 0 ? true : false;
		}
		admin.setInitDate(date);
		admin.setInitAddr(ip);
		return adminDao.save(admin) > 0 ? true : false;
	}

	@Override
	public List<Admin> getList(Map<String, String> param) {
		return adminDao.list(param);
	}

	@Override
	public PageList<Admin> getPage(Map<String, String> param) {
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
		return adminDao.page(param, bounds);
	}
}
